package org.jai.kissan.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class Endpoints {

	private final String farmerCropServiceUrlForFarmer;

	private final String farmerCropServiceUrlForCrop;

	private final String farmerFciServiceUrlToCreateDeal;

	private final String farmerFciServiceUrlToBuyDeal;

	private final String farmerFciServiceUrlToUpdateQuantity;

	private final String farmerFciServiceUrlToUpdateStatusToReview;

	private final String farmerFciServiceUrlToGetNewDeals;

	private final String farmerFciServiceUrlToGetReviewingDeals;

	private final String farmerFciServiceUrlToGetActiveDeals;

	private final String farmerFciServiceUrlToDeleteAllFarmerDeals;

	private final String farmerFciServiceUrlToDeleteFciDeal;

	public Endpoints(@Value("${app.farmer-crop-service.host}") String farmerCropServiceHost,
			@Value("${app.farmer-crop-service.port}") String farmerCropServicePort,
			@Value("${app.farmer-fci-service.host}") String farmerFciServiceHost,
			@Value("${app.farmer-fci-service.port}") String farmerFciServicePort,
			@Value("${app.farmer-crop-service.endpoint.farmer}") String farmerCropServiceEndpointForFarmer,
			@Value("${app.farmer-crop-service.endpoint.crop}") String farmerCropServiceEndpointForCrop,
			@Value("${app.farmer-fci-service.endpoint.deal}") String farmerFciServiceEndpointToCreateDeal,
			@Value("${app.farmer-fci-service.endpoint.buy-deal}") String farmerFciServiceEndpointToBuyDeal,
			@Value("${app.farmer-fci-service.endpoint.quantity}") String farmerFciServiceEndpointToUpdateQuantity,
			@Value("${app.farmer-fci-service.endpoint.status-to-review}") String farmerFciServiceEndpointToUpdateStatusToReview,
			@Value("${app.farmer-fci-service.endpoint.new-deals}") String farmerFciServiceEndpointToGetNewDeals,
			@Value("${app.farmer-fci-service.endpoint.reviewing-deals}") String farmerFciServiceEndpointToGetReviewingDeals,
			@Value("${app.farmer-fci-service.endpoint.active-deals}") String farmerFciServiceEndpointToGetActiveDeals,
			@Value("${app.farmer-fci-service.endpoint.farmer-deals}") String farmerFciServiceEndpointToDeleteAllFarmerDeals,
			@Value("${app.farmer-fci-service.endpoint.fci-deal}") String farmerFciServiceEndpointToDeleteFciDeal) {

		String farmerCropServiceUrl = "http://" + farmerCropServiceHost + ":" + farmerCropServicePort;
		String farmerFciServiceUrl = "http://" + farmerFciServiceHost + ":" + farmerFciServicePort;

		// Farmer-Crop Service Endpoints
		this.farmerCropServiceUrlForFarmer = farmerCropServiceUrl + farmerCropServiceEndpointForFarmer;
		this.farmerCropServiceUrlForCrop = farmerCropServiceUrl + farmerCropServiceEndpointForCrop;

		// Farmer-Fci service Endpoints
		this.farmerFciServiceUrlToCreateDeal = farmerFciServiceUrl + farmerFciServiceEndpointToCreateDeal;
		this.farmerFciServiceUrlToBuyDeal = farmerFciServiceUrl + farmerFciServiceEndpointToBuyDeal;
		this.farmerFciServiceUrlToUpdateQuantity = farmerFciServiceUrl + farmerFciServiceEndpointToUpdateQuantity;
		this.farmerFciServiceUrlToUpdateStatusToReview = farmerFciServiceUrl
				+ farmerFciServiceEndpointToUpdateStatusToReview;
		this.farmerFciServiceUrlToGetNewDeals = farmerFciServiceUrl + farmerFciServiceEndpointToGetNewDeals;
		this.farmerFciServiceUrlToGetReviewingDeals = farmerFciServiceUrl + farmerFciServiceEndpointToGetReviewingDeals;
		this.farmerFciServiceUrlToGetActiveDeals = farmerFciServiceUrl + farmerFciServiceEndpointToGetActiveDeals;
		this.farmerFciServiceUrlToDeleteAllFarmerDeals = farmerFciServiceUrl
				+ farmerFciServiceEndpointToDeleteAllFarmerDeals;
		this.farmerFciServiceUrlToDeleteFciDeal = farmerFciServiceUrl + farmerFciServiceEndpointToDeleteFciDeal;

	}

}
