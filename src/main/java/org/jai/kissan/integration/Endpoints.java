package org.jai.kissan.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class Endpoints {

	private final String farmerCropServiceUriForFarmer;

	private final String farmerCropServiceUriForCrop;

	private final String farmerFciServiceUriToCreateDeal;

	private final String farmerFciServiceUriToBuyDeal;

	private final String farmerFciServiceUriToUpdateQuantity;

	private final String farmerFciServiceUriToUpdateStatusToReview;

	private final String farmerFciServiceUriToGetNewDeals;

	private final String farmerFciServiceUriToGetReviewingDeals;

	private final String farmerFciServiceUriToGetCompletedDeals;

	private final String farmerFciServiceUriToGetActiveDeals;

	private final String farmerFciServiceUriToGetAllDeals;

	private final String farmerFciServiceUriToDeleteAllFarmerDeals;

	private final String farmerFciServiceUriToDeleteFciDeal;

	public Endpoints(
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
			@Value("${app.farmer-fci-service.endpoint.fci-deal}") String farmerFciServiceEndpointToDeleteFciDeal,
			@Value("${app.farmer-fci-service.endpoint.all-deals}") String farmerFciServiceEndpointToGetAllDeals,
			@Value("${app.farmer-fci-service.endpoint.completed-deals}") String farmerFciServiceEndpointToGetCompletedDeals) {

		String farmerCropServiceHostAndPort = "http://farmer-crop-service";
		String farmerFciServiceHostAndPort = "http://farmer-fci-service";

		// Farmer-Crop Service Endpoints
		this.farmerCropServiceUriForFarmer = farmerCropServiceHostAndPort + farmerCropServiceEndpointForFarmer;
		this.farmerCropServiceUriForCrop = farmerCropServiceHostAndPort + farmerCropServiceEndpointForCrop;

		// Farmer-Fci service Endpoints
		this.farmerFciServiceUriToCreateDeal = farmerFciServiceHostAndPort + farmerFciServiceEndpointToCreateDeal;
		this.farmerFciServiceUriToBuyDeal = farmerFciServiceHostAndPort + farmerFciServiceEndpointToBuyDeal;
		this.farmerFciServiceUriToUpdateQuantity = farmerFciServiceHostAndPort
				+ farmerFciServiceEndpointToUpdateQuantity;
		this.farmerFciServiceUriToUpdateStatusToReview = farmerFciServiceHostAndPort
				+ farmerFciServiceEndpointToUpdateStatusToReview;
		this.farmerFciServiceUriToGetNewDeals = farmerFciServiceHostAndPort + farmerFciServiceEndpointToGetNewDeals;
		this.farmerFciServiceUriToGetReviewingDeals = farmerFciServiceHostAndPort
				+ farmerFciServiceEndpointToGetReviewingDeals;
		this.farmerFciServiceUriToGetActiveDeals = farmerFciServiceHostAndPort
				+ farmerFciServiceEndpointToGetActiveDeals;
		this.farmerFciServiceUriToDeleteAllFarmerDeals = farmerFciServiceHostAndPort
				+ farmerFciServiceEndpointToDeleteAllFarmerDeals;
		this.farmerFciServiceUriToDeleteFciDeal = farmerFciServiceHostAndPort + farmerFciServiceEndpointToDeleteFciDeal;
		this.farmerFciServiceUriToGetAllDeals = farmerFciServiceHostAndPort + farmerFciServiceEndpointToGetAllDeals;
		this.farmerFciServiceUriToGetCompletedDeals = farmerFciServiceHostAndPort
				+ farmerFciServiceEndpointToGetCompletedDeals;
	}

}
