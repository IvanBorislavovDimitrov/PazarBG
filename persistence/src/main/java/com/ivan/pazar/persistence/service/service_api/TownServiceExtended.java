package com.ivan.pazar.persistence.service.service_api;

import com.ivan.pazar.domain.model.entity.Town;
import com.ivan.pazar.persistence.service.api.TownService;

public interface TownServiceExtended extends TownService {
    Town findByName(String town);

    Town getTownByName(String town);
}
