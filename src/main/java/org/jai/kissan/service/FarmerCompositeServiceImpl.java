package org.jai.kissan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.jai.kissan.api.farmer.composite.model.FarmerComposite;
import org.jai.kissan.api.farmer.crop.model.Crop;
import org.jai.kissan.api.farmer.crop.model.Farmer;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.jai.kissan.integration.FarmerCompositeIntegration;
import org.jai.kissan.mappers.CropMapper;
import org.jai.kissan.mappers.FarmerFciMapper;
import org.jai.kissan.mappers.FarmerMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("farmerCompositeService")
@RequiredArgsConstructor
@Slf4j
public class FarmerCompositeServiceImpl implements FarmerCompositeService {

	private final FarmerCompositeIntegration compositeIntegration;

	private final FarmerMapper farmerMapper;

	private final CropMapper cropMapper;

	private final FarmerFciMapper farmerFciMapper;

	@Override
	public Mono<String> createFarmerAndFciDeal(FarmerComposite fc) {

		Farmer farmer = farmerMapper.farmerDetailToFarmerApiModelMapper(fc.getFarmerDetail());

		return compositeIntegration.createFarmer(farmer).flatMap((farmerIdentityCode) -> {
			if (CollectionUtils.isNotEmpty(fc.getFciDeals())) {

				FarmerFciDeal deal = farmerFciMapper
						.fciDealSummaryToFarmerFciDealApiModelMapper(fc.getFciDeals().get(0));
				deal.setFarmerIdentityCode(farmerIdentityCode);

				return compositeIntegration.createFciDeal(deal).thenReturn(farmerIdentityCode);
			}
			return Mono.just(farmerIdentityCode);
		});
	}

	@Override
	public Mono<Void> deleteFarmer(String farmerIdentityCode) {
		return compositeIntegration.deleteFarmer(farmerIdentityCode)
				.then(compositeIntegration.deleteFarmerFciDealsUsingFarmerIdentityCode(farmerIdentityCode));
	}

	@Override
	public Mono<FarmerComposite> getFarmerSummary(String farmerIdentityCode) {

		return Mono.zip(values -> {
			FarmerComposite composite = new FarmerComposite();
			composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper((Farmer) values[0]));
			if (values[1] != null) {
				composite.setFciDeals(
						farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper((List<FarmerFciDeal>) values[1]));
			}
			setCropDetails(composite);
			return composite;
		}, compositeIntegration.getFarmerDetails(farmerIdentityCode),
				compositeIntegration.getFarmerAllFciDeals(farmerIdentityCode).collectList());
	}

	@Override
	public Mono<FarmerComposite> getFarmerActiveFciSummary(String farmerIdentityCode) {

		return Mono.zip(values -> {
			FarmerComposite composite = new FarmerComposite();
			composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper((Farmer) values[0]));
			if (values[1] != null) {
				composite.setFciDeals(
						farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper((List<FarmerFciDeal>) values[1]));
			}
			return composite;
		}, compositeIntegration.getFarmerDetails(farmerIdentityCode),
				compositeIntegration.getFarmerActiveFciDeals(farmerIdentityCode).collectList())
				.doOnNext((composite) -> setCropDetails(composite));
	}

	@Override
	public Mono<FarmerComposite> getFarmerReviewingFciSummary(String farmerIdentityCode) {

		return Mono.zip(values -> {
			FarmerComposite composite = new FarmerComposite();
			composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper((Farmer) values[0]));
			if (values[1] != null) {
				composite.setFciDeals(
						farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper((List<FarmerFciDeal>) values[1]));
			}
			return composite;
		}, compositeIntegration.getFarmerDetails(farmerIdentityCode),
				compositeIntegration.getFarmerReviewingFciDeals(farmerIdentityCode).collectList())
				.doOnNext((composite) -> setCropDetails(composite));
	}

	@Override
	public Mono<FarmerComposite> getFarmerCompletedFciSummary(String farmerIdentityCode) {

		return Mono.zip(values -> {
			FarmerComposite composite = new FarmerComposite();
			composite.setFarmerDetail(farmerMapper.farmerApiModelToFarmerDetailMapper((Farmer) values[0]));
			if (values[1] != null) {
				composite.setFciDeals(
						farmerFciMapper.farmerFciDealApiModelsToFciDealsSummaryMapper((List<FarmerFciDeal>) values[1]));
			}
			return composite;
		}, compositeIntegration.getFarmerDetails(farmerIdentityCode),
				compositeIntegration.getFarmerCompletedFciDeals(farmerIdentityCode).collectList())
				.doOnNext((composite) -> setCropDetails(composite));
	}

	private void setCropDetails(FarmerComposite composite) {

		Map<String, Crop> cropMap = new HashMap<>();

		Flux.fromIterable(composite.getFciDeals()).flatMap(deal -> {
			String cropId = deal.getCropDetails().getCropIdentityCode();
			Crop crop = cropMap.get(cropId);
			if (crop == null) {
				return compositeIntegration.getCropDetails(cropId).doOnNext((cropE) -> {
					cropMap.put(cropId, cropE);
					log.info("****1*****" + cropE.getName() + "------------");
					deal.setCropDetails(cropMapper.cropApiModelToCropDetailMapper(cropE));
				});
			}
			log.info("****2*****" + crop.getName() + "------------");
			deal.setCropDetails(cropMapper.cropApiModelToCropDetailMapper(crop));
			return Mono.just(crop);
		}).onErrorContinue((error, cropId) -> log.error("CropId {} not found due to {}", cropId, error.getMessage()))
				.subscribe();
	}

}
