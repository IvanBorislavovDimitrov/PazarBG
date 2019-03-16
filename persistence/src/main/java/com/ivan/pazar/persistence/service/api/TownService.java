package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.TownServiceModel;
import com.ivan.pazar.persistence.model.service.rest.TownRestServiceModel;

import java.util.List;

public interface TownService {

    TownServiceModel save(TownServiceModel townServiceModel);

    List<TownRestServiceModel> getAllByRegionRest(String region);
}
