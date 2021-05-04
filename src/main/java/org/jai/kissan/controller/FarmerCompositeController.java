package org.jai.kissan.controller;

import org.jai.kissan.api.farmer.composite.contoller.IFarmerCompositeController;
import org.jai.kissan.api.farmer.composite.model.FarmerComposite;
import org.jai.kissan.service.FarmerCompositeService;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class FarmerCompositeController implements IFarmerCompositeController {

	private final FarmerCompositeService farmerCompositeService;

	@Override
	public Mono<String> createFarmerAndFciDeal(FarmerComposite farmerComposite) {
		return farmerCompositeService.createFarmerAndFciDeal(farmerComposite);
	}

	@Override
	public Mono<Void> deleteFarmer(String farmerIdentityCode) {
		return farmerCompositeService.deleteFarmer(farmerIdentityCode);
	}

	@Override
	public Mono<FarmerComposite> getFarmerSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerSummary(farmerIdentityCode);
	}

	@Override
	public Mono<FarmerComposite> getFarmerActiveFciSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerActiveFciSummary(farmerIdentityCode);
	}

	@Override
	public Mono<FarmerComposite> getFarmerReviewingFciSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerReviewingFciSummary(farmerIdentityCode);
	}

	@Override
	public Mono<FarmerComposite> getFarmerCompletedFciSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerCompletedFciSummary(farmerIdentityCode);
	}

}
