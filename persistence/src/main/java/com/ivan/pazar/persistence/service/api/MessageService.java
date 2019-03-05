package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.domain.model.dto.service.MessageServiceModel;

public interface MessageService {

    MessageServiceModel save(MessageServiceModel messageServiceModel);

}
