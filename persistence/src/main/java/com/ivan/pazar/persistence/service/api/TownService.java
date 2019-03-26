package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.model.service.TownAddServiceModel;
import com.ivan.pazar.persistence.model.service.TownServiceModel;
import com.ivan.pazar.persistence.model.service.rest.TownRestServiceModel;

import java.util.List;

public interface TownService {

    TownServiceModel save(TownAddServiceModel townAddServiceModel);

    List<TownRestServiceModel> getAllByRegionRest(String region);
}
