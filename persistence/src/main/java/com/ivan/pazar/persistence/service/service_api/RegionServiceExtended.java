package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.persistence.service.api.RegionService;

public interface RegionServiceExtended extends RegionService {
    Region findByName(String region);

    Region getRegionByName(String region);
}
