package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.persistence.service.api.AdvertisementService;

public interface AdvertisementServiceExtended extends AdvertisementService {

    Advertisement getAdvertisementById(String id);
}
