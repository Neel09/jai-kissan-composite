package org.jai.kissan.controller;

import org.jai.kissan.api.farmer.composite.contoller.IFarmerCompositeController;
import org.jai.kissan.api.farmer.composite.model.FarmerComposite;
import org.jai.kissan.service.FarmerCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FarmerCompositeController implements IFarmerCompositeController {

	private final FarmerCompositeService farmerCompositeService;

	@Autowired
	public FarmerCompositeController(FarmerCompositeService farmerCompositeService) {
		this.farmerCompositeService = farmerCompositeService;
	}

	@Override
	public void createFarmerAndFciDeal(FarmerComposite farmerComposite) {
		farmerCompositeService.createFarmerAndFciDeal(farmerComposite);
	}

	@Override
	public void deleteFarmer(String farmerIdentityCode) {
		farmerCompositeService.deleteFarmer(farmerIdentityCode);
	}

	@Override
	public FarmerComposite getFarmerSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerSummary(farmerIdentityCode);
	}

	@Override
	public FarmerComposite getFarmerActiveFciSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerActiveFciSummary(farmerIdentityCode);
	}

	@Override
	public FarmerComposite getFarmerReviewingFciSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerReviewingFciSummary(farmerIdentityCode);
	}

	@Override
	public FarmerComposite getFarmerCompletedFciSummary(String farmerIdentityCode) {
		return farmerCompositeService.getFarmerCompletedFciSummary(farmerIdentityCode);
	}

}
