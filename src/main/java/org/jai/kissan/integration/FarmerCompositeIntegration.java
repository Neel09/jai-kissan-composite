package org.jai.kissan.integration;

import java.util.List;

import org.jai.kissan.api.farmer.crop.model.Crop;
import org.jai.kissan.api.farmer.crop.model.Farmer;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FarmerCompositeIntegration {

	private final RestTemplate restTemplate;
	private final Endpoints endpoints;
	private final ObjectMapper mapper;

	@Autowired
	public FarmerCompositeIntegration(RestTemplate restTemplate, Endpoints endpoints, ObjectMapper mapper) {
		this.restTemplate = restTemplate;
		this.mapper = mapper;
		this.endpoints = endpoints;
	}

	public String createFarmer(Farmer farmer) {
		log.debug("Creating farmer..");
		String identityCode = restTemplate.postForObject(endpoints.getFarmerCropServiceUrlForFarmer(), farmer,
				String.class);

		log.debug("Farmer created with Identity Code = {}", identityCode);
		return identityCode;
	}

	public String createFciDeal(FarmerFciDeal deal) {

		log.debug("Creating fci deal..");
		String dealIdentityCode = restTemplate.postForObject(endpoints.getFarmerFciServiceUrlToCreateDeal(), deal,
				String.class);

		log.debug("FCI Deal created with Identity Code = {}", dealIdentityCode);
		return dealIdentityCode;
	}

	public void deleteFarmer(String farmerIdentityCode) {

		restTemplate.delete(endpoints.getFarmerCropServiceUrlForFarmer() + "/" + farmerIdentityCode);
	}

	public void deleteFarmerFciDeal(String fciDealIdentityCode) {
		restTemplate.delete(endpoints.getFarmerFciServiceUrlToDeleteFciDeal() + "/" + fciDealIdentityCode);
	}

	public void deleteFarmerFciDealsUsingFarmerIdentityCode(String farmerIdentityCode) {

		restTemplate.delete(endpoints.getFarmerFciServiceUrlToDeleteAllFarmerDeals() + "/" + farmerIdentityCode);
	}

	public Farmer getFarmerDetails(String farmerIdentityCode) {
		return restTemplate.getForObject(endpoints.getFarmerCropServiceUrlForFarmer() + "/" + farmerIdentityCode,
				Farmer.class);
	}

	public Crop getCropDetails(String cropIdentityCode) {
		return restTemplate.getForObject(endpoints.getFarmerCropServiceUrlForCrop() + "/" + cropIdentityCode,
				Crop.class);
	}

	public List<FarmerFciDeal> getFarmerActiveFciDeals(String farmerIdentityCode) {
		return restTemplate.exchange(endpoints.getFarmerFciServiceUrlToGetActiveDeals(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<FarmerFciDeal>>() {
				}).getBody();
	}

	public List<FarmerFciDeal> getFarmerAllFciDeals(String farmerIdentityCode) {

		return null;
	}

	public List<FarmerFciDeal> getFarmerReviewingFciDeals(String farmerIdentityCode) {
		return null;
	}

	public List<FarmerFciDeal> getFarmerCompletedFciDeals(String farmerIdentityCode) {
		return null;
	}

}
