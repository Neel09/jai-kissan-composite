package org.jai.kissan.service;

import org.jai.kissan.api.farmer.composite.model.FarmerComposite;

public interface FarmerCompositeService {

	public void createFarmerAndFciDeal(FarmerComposite farmerComposite);

	public void deleteFarmer(String farmerIdentityCode);

	public FarmerComposite getFarmerSummary(String farmerIdentityCode);

	public FarmerComposite getFarmerActiveFciSummary(String farmerIdentityCode);

	public FarmerComposite getFarmerReviewingFciSummary(String farmerIdentityCode);

	public FarmerComposite getFarmerCompletedFciSummary(String farmerIdentityCode);
}
