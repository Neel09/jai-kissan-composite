package org.jai.kissan.mappers;

import org.jai.kissan.api.farmer.composite.model.CropDetail;
import org.jai.kissan.api.farmer.crop.model.Crop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CropMapper {

	@Mappings({ @Mapping(target = "identityCode", source = "cropDetail.cropIdentityCode"),
			@Mapping(target = "name", source = "cropDetail.cropName") })
	public Crop cropDetailToCropApiModelMapper(CropDetail cropDetail);

	@Mappings({ @Mapping(target = "cropIdentityCode", source = "crop.identityCode"),
			@Mapping(target = "cropName", source = "crop.name") })
	public CropDetail cropApiModelToCropDetailMapper(Crop crop);
}
