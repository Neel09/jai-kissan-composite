package org.jai.kissan.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.jai.kissan.api.farmer.composite.model.CropDetail;
import org.jai.kissan.api.farmer.composite.model.FarmerComposite;
import org.jai.kissan.api.farmer.composite.model.FciDealSummary;
import org.jai.kissan.api.farmer.crop.model.Crop;
import org.jai.kissan.api.farmer.crop.model.Farmer;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.integration.FarmerCompositeIntegration;
import org.jai.kissan.mappers.CropMapper;
import org.jai.kissan.mappers.FarmerFciMapper;
import org.jai.kissan.mappers.FarmerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("farmerCompositeService")
public class FarmerCompositeServiceImpl implements FarmerCompositeService {

	private final FarmerCompositeIntegration compositeIntegration;

	private final FarmerMapper farmerMapper;

	private final CropMapper cropMapper;

	private final FarmerFciMapper farmerFciMapper;

	@Autowired
	public FarmerCompositeServiceImpl(FarmerCompositeIntegration compositeIntegration, FarmerMapper farmerMapper,
			CropMapper cropMapper, FarmerFciMapper farmerFciMapper) {

		this.compositeIntegration = compositeIntegration;
		this.farmerMapper = farmerMapper;
		this.cropMapper = cropMapper;
		this.farmerFciMapper = farmerFciMapper;
	}

	@Override
	public void createFarmerAndFciDeal(FarmerComposite farmerComposite) {
		String farmerIdentityCode = compositeIntegration
				.createFarmer(farmerMapper.farmerDetailToFarmerApiModelMapper(farmerComposite.getFarmerDetail()));

		if (CollectionUtils.isNotEmpty(farmerComposite.getFciDeals())) {
			List<FciDealSummary> dealSummaries = farmerComposite.getFciDeals().stream().map(d -> {
				d.setFciDealIdentityCode(farmerIdentityCode);
				return d;
			}).collect(Collectors.toList());

			compositeIntegration
					.createFciDeal(farmerFciMapper.fciDealSummaryToFarmerFciDealApiModelMapper(dealSummaries.get(0)));
		}
	}

	@Override
	public void deleteFarmer(String farmerIdentityCode) {
		compositeIntegration.deleteFarmer(farmerIdentityCode);
		compositeIntegration.deleteFarmerFciDealsUsingFarmerIdentityCode(farmerIdentityCode);
	}

	@Override
	public FarmerComposite getFarmerSummary(String farmerIdentityCode) {

		Farmer farmer = compositeIntegration.getFarmerDetails(farmerIdentityCode);

		List<FarmerFciDeal> fciDeals = compositeIntegration.getFarmerAllFciDeals(farmerIdentityCode);

		FarmerComposite composite = new FarmerComposite();

		composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper(farmer));
		composite.setFciDeals(farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper(fciDeals));

		setCropDetails(composite);

		return composite;
	}

	@Override
	public FarmerComposite getFarmerActiveFciSummary(String farmerIdentityCode) {
		Farmer farmer = compositeIntegration.getFarmerDetails(farmerIdentityCode);

		List<FarmerFciDeal> fciDeals = compositeIntegration.getFarmerActiveFciDeals(farmerIdentityCode);

		FarmerComposite composite = new FarmerComposite();
		composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper(farmer));
		composite.setFciDeals(farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper(fciDeals));

		setCropDetails(composite);

		return composite;
	}

	@Override
	public FarmerComposite getFarmerReviewingFciSummary(String farmerIdentityCode) {
		Farmer farmer = compositeIntegration.getFarmerDetails(farmerIdentityCode);

		List<FarmerFciDeal> fciDeals = compositeIntegration.getFarmerReviewingFciDeals(farmerIdentityCode);

		FarmerComposite composite = new FarmerComposite();
		composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper(farmer));
		composite.setFciDeals(farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper(fciDeals));
		setCropDetails(composite);
		return composite;
	}

	@Override
	public FarmerComposite getFarmerCompletedFciSummary(String farmerIdentityCode) {
		Farmer farmer = compositeIntegration.getFarmerDetails(farmerIdentityCode);

		List<FarmerFciDeal> fciDeals = compositeIntegration.getFarmerCompletedFciDeals(farmerIdentityCode);

		FarmerComposite composite = new FarmerComposite();
		composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper(farmer));
		composite.setFciDeals(farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper(fciDeals));
		setCropDetails(composite);
		return composite;
	}

	private void setCropDetails(FarmerComposite composite) {
		List<String> cropIdentityCodes = composite.getFciDeals().stream()
				.map(d -> d.getCropDetails().getCropIdentityCode()).distinct().collect(Collectors.toList());

		Map<String, String> cropMap = cropIdentityCodes.stream()
				.map(crodId -> compositeIntegration.getCropDetails(crodId))
				.collect(Collectors.toMap(Crop::getIdentityCode, Crop::getName));

		List<FciDealSummary> fciDealSummariesWithCropDetails = composite.getFciDeals().stream().map(deal -> {
			CropDetail cropDetail = deal.getCropDetails();
			cropDetail.setCropName(cropMap.get(cropDetail.getCropIdentityCode()));
			return deal;
		}).collect(Collectors.toList());

		composite.setFciDeals(fciDealSummariesWithCropDetails);
	}

}
