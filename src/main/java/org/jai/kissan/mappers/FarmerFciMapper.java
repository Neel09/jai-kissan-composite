package org.jai.kissan.mappers;

import java.util.List;

import org.jai.kissan.api.farmer.composite.model.FciDealSummary;
import org.jai.kissan.api.farmer.fci.model.FarmerFciDeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FarmerFciMapper {

	@Mappings({ @Mapping(target = "fciDealIdentityCode", source = "farmerFciDeal.dealIdentityCode"),
			@Mapping(target = "cropDetails.cropIdentityCode", source = "farmerFciDeal.cropIdentityCode") })
	public FciDealSummary farmerFciDealApiModelToFciDealSummaryMapper(FarmerFciDeal farmerFciDeal);

	@Mappings({ @Mapping(target = "dealIdentityCode", source = "fciDealSummary.fciDealIdentityCode"),
			@Mapping(target = "cropIdentityCode", source = "fciDealSummary.cropDetails.cropIdentityCode") })
	public FarmerFciDeal fciDealSummaryToFarmerFciDealApiModelMapper(FciDealSummary fciDealSummary);

	@Mappings({ @Mapping(target = "fciDealIdentityCode", source = "farmerFciDeal.dealIdentityCode"),
			@Mapping(target = "cropDetails.cropIdentityCode", source = "farmerFciDeal.cropIdentityCode") })
	public List<FciDealSummary> farmerFciDealApiModelsToFciDealsSummaryMapper(List<FarmerFciDeal> farmerFciDeal);

	@Mappings({ @Mapping(target = "dealIdentityCode", source = "fciDealSummary.fciDealIdentityCode"),
			@Mapping(target = "cropIdentityCode", source = "fciDealSummary.cropDetails.cropIdentityCode") })
	public List<FarmerFciDeal> fciDealsSummaryToFarmerFciDealApiModelsMapper(List<FciDealSummary> fciDealSummary);
}
