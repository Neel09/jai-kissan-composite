package org.jai.kissan.integration;

import java.io.IOException;

import org.jai.kissan.api.exception.CreateDataException;
import org.jai.kissan.api.exception.ReadDataException;
import org.jai.kissan.api.exception.handler.HttpErrorInfo;
import org.jai.kissan.api.farmer.crop.model.Crop;
import org.jai.kissan.api.farmer.crop.model.Farmer;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class FarmerCompositeIntegration {

	private final Endpoints endpoints;
	private final ObjectMapper mapper;
	private final WebClient webClient;

	@Autowired
	public FarmerCompositeIntegration(WebClient.Builder webClientBuilder, Endpoints endpoints, ObjectMapper mapper) {
		this.webClient = webClientBuilder.build();
		this.endpoints = endpoints;
		this.mapper = mapper;
	}

	public Mono<String> createFarmer(Farmer farmer) {
		log.debug("Creating farmer..");
		return webClient.post().uri(endpoints.getFarmerCropServiceUriForFarmer()).bodyValue(farmer).retrieve()
				.bodyToMono(String.class).log().onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Mono<String> createFciDeal(FarmerFciDeal deal) {

		log.debug("Creating fci deal..");
		return webClient.post().uri(endpoints.getFarmerFciServiceUriToCreateDeal()).bodyValue(deal).retrieve()
				.bodyToMono(String.class).log().onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Mono<Void> deleteFarmer(String farmerIdentityCode) {
		return webClient.delete().uri(endpoints.getFarmerCropServiceUriForFarmer() + "/" + farmerIdentityCode)
				.retrieve().bodyToMono(Void.class).log()
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Mono<Void> deleteFarmerFciDeal(String fciDealIdentityCode) {
		return webClient.delete().uri(endpoints.getFarmerFciServiceUriToDeleteFciDeal() + "/" + fciDealIdentityCode)
				.retrieve().bodyToMono(Void.class).log()
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Mono<Void> deleteFarmerFciDealsUsingFarmerIdentityCode(String farmerIdentityCode) {
		return webClient.delete()
				.uri(endpoints.getFarmerFciServiceUriToDeleteAllFarmerDeals() + "/" + farmerIdentityCode).retrieve()
				.bodyToMono(Void.class).log().onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Mono<Farmer> getFarmerDetails(String farmerIdentityCode) {
		return webClient.get().uri(endpoints.getFarmerCropServiceUriForFarmer() + "/" + farmerIdentityCode).retrieve()
				.bodyToMono(Farmer.class).log().onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Mono<Crop> getCropDetails(String cropIdentityCode) {
		return webClient.get().uri(endpoints.getFarmerCropServiceUriForCrop() + "/" + cropIdentityCode).retrieve()
				.bodyToMono(Crop.class).log().onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Flux<FarmerFciDeal> getFarmerActiveFciDeals(String farmerIdentityCode) {
		return webClient.get().uri(endpoints.getFarmerFciServiceUriToGetActiveDeals() + "/" + farmerIdentityCode)
				.retrieve().bodyToFlux(FarmerFciDeal.class).log()
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Flux<FarmerFciDeal> getFarmerAllFciDeals(String farmerIdentityCode) {
		return webClient.get().uri(endpoints.getFarmerFciServiceUriToGetAllDeals() + "/" + farmerIdentityCode)
				.retrieve().bodyToFlux(FarmerFciDeal.class).log()
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Flux<FarmerFciDeal> getFarmerReviewingFciDeals(String farmerIdentityCode) {
		return webClient.get().uri(endpoints.getFarmerFciServiceUriToGetReviewingDeals() + "/" + farmerIdentityCode)
				.retrieve().bodyToFlux(FarmerFciDeal.class).log()
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	public Flux<FarmerFciDeal> getFarmerCompletedFciDeals(String farmerIdentityCode) {
		return webClient.get().uri(endpoints.getFarmerFciServiceUriToGetCompletedDeals() + "/" + farmerIdentityCode)
				.retrieve().bodyToFlux(FarmerFciDeal.class).log()
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
	}

	private Throwable handleException(WebClientResponseException wcre) {

		switch (wcre.getStatusCode()) {

		case NOT_FOUND:
			return new ReadDataException(getErrorMessage(wcre));

		case UNPROCESSABLE_ENTITY:
			return new CreateDataException(getErrorMessage(wcre));

		case NOT_ACCEPTABLE:
			return new CreateDataException(getErrorMessage(wcre));

		default:
			log.warn("Got a unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
			log.warn("Error body: {}", wcre.getResponseBodyAsString());
			return wcre;
		}
	}

	private String getErrorMessage(WebClientResponseException ex) {
		try {
			return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}

}
