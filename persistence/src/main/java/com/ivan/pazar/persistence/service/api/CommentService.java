package com.ivan.pazar.persistence.service.api;

import com.ivan.pazar.persistence.dto.service.CommentServiceModel;

public interface CommentService {

    CommentServiceModel save(CommentServiceModel commentServiceModel);

}
