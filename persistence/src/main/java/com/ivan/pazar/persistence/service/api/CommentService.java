package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.domain.model.dto.service.CommentServiceModel;

public interface CommentService {

    CommentServiceModel save(CommentServiceModel commentServiceModel);

}
