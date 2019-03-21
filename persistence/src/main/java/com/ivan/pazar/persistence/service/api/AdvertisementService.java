package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementServiceModel;
import com.ivan.pazar.persistence.model.service.rest.AdvertisementRestServiceModel;

import java.util.List;

public interface AdvertisementService {

    AdvertisementServiceModel save(String username, AdvertisementAddServiceModel advertisementAddServiceModel);

    List<AdvertisementRestServiceModel> findSixMostRecentAdvertisements();

}
