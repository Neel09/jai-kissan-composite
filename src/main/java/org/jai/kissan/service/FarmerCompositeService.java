package org.jai.kissan.service;

import org.jai.kissan.api.farmer.composite.model.FarmerComposite;

import reactor.core.publisher.Mono;

public interface FarmerCompositeService {

	public Mono<String> createFarmerAndFciDeal(FarmerComposite farmerComposite);

	public Mono<Void> deleteFarmer(String farmerIdentityCode);

	public Mono<FarmerComposite> getFarmerSummary(String farmerIdentityCode);

	public Mono<FarmerComposite> getFarmerActiveFciSummary(String farmerIdentityCode);

	public Mono<FarmerComposite> getFarmerReviewingFciSummary(String farmerIdentityCode);

	public Mono<FarmerComposite> getFarmerCompletedFciSummary(String farmerIdentityCode);
}
