package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementServiceModel;

public interface AdvertisementService {

    AdvertisementServiceModel save(String username, AdvertisementAddServiceModel advertisementAddServiceModel);

}
