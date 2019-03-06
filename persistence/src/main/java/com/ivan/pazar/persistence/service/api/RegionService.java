package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.domain.model.dto.service.RegionServiceModel;
import com.ivan.pazar.domain.model.dto.service.rest.RegionRestServiceModel;

import java.util.List;

public interface RegionService {

    RegionServiceModel save(RegionServiceModel regionServiceModel);

    List<RegionRestServiceModel> getAllRegionsRest();
}
