package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.*;
import com.ivan.pazar.persistence.model.service.rest.AdvertisementRestServiceModel;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AdvertisementService {

    AdvertisementViewServiceModel findById(String id);

    AdvertisementServiceModel save(String username, AdvertisementAddServiceModel advertisementAddServiceModel);

    List<AdvertisementRestServiceModel> findSixMostRecentAdvertisements();

    AdvertisementPageServiceModel findAllByCategoryLikeWithPage(String category, PageRequest pageRequest);

    AdvertisementPageServiceModel findNonConfirmedAdvertisements(PageRequest pageRequest);

    void activateAdvertisement(String advertId);

    AdvertisementPageServiceModel findByKeyword(String keyword, PageRequest pageRequest);

    void incrementViews(String id);

    void deleteById(String advertId);

    AdvertisementPageServiceModel findAllByUsername(String username, PageRequest pageRequest);
}
