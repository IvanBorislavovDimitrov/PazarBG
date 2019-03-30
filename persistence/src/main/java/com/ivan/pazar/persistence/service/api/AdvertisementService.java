package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.AdvertisementAddServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertisementViewServiceModel;
import com.ivan.pazar.persistence.model.service.AdvertismentHomePageServiceModel;
import com.ivan.pazar.persistence.model.service.rest.AdvertisementRestServiceModel;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AdvertisementService {

    AdvertisementViewServiceModel findById(String id);

    AdvertisementServiceModel save(String username, AdvertisementAddServiceModel advertisementAddServiceModel);

    List<AdvertisementRestServiceModel> findSixMostRecentAdvertisements();

    AdvertismentHomePageServiceModel findAllByCategoryLikeWithPage(String category, PageRequest pageRequest);

    AdvertismentHomePageServiceModel findNonConfirmedAdvertisements(PageRequest pageRequest);

    void activateAdvertisement(String advertId);
}
