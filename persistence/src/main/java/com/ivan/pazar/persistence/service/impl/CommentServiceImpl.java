package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.persistence.dto.service.CommentServiceModel;
import com.ivan.pazar.persistence.repository.CommentRepository;
import com.ivan.pazar.persistence.service.api.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentServiceModel save(CommentServiceModel commentServiceModel) {
        return null;
    }
}
