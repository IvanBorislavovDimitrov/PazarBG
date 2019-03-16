package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.RegionServiceModel;
import com.ivan.pazar.persistence.model.service.rest.RegionRestServiceModel;
import com.ivan.pazar.domain.model.entity.Region;

import java.util.List;

public interface RegionService {

    RegionServiceModel save(RegionServiceModel regionServiceModel);

    List<RegionRestServiceModel> getAllRegionsRest();

    Region findByName(String name);
}
