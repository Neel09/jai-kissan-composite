package org.jai.kissan.mappers;

import org.jai.kissan.api.farmer.composite.model.FarmerDetail;
import org.jai.kissan.api.farmer.crop.model.Farmer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FarmerMapper {

	@Mappings({ @Mapping(target = "identityCode", source = "farmerDetail.farmerIdentityCode"),
			@Mapping(target = "name", source = "farmerDetail.farmerName") })
	public Farmer farmerDetailToFarmerApiModelMapper(FarmerDetail farmerDetail);

	@Mappings({ @Mapping(target = "farmerIdentityCode", source = "farmer.identityCode"),
			@Mapping(target = "farmerName", source = "farmer.name") })
	public FarmerDetail farmerApiModelToFarmerDetailMapper(Farmer farmer);
}
