package com.ivan.pazar.persistence.service.impl;

import com.ivan.pazar.domain.model.entity.Advertisement;
import com.ivan.pazar.domain.model.entity.Message;
import com.ivan.pazar.domain.model.entity.User;
import com.ivan.pazar.persistence.model.service.MessageAddServiceModel;
import com.ivan.pazar.persistence.model.service.MessagePageServiceModel;
import com.ivan.pazar.persistence.model.service.MessageServiceModel;
import com.ivan.pazar.persistence.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MessageServiceTests {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private AdvertisementServiceImpl advertisementService;

    @Mock
    private UserServiceImpl userService;

    private ModelMapper modelMapper;
    private MessageServiceImpl messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        modelMapper = new ModelMapper();
        messageService = new MessageServiceImpl(messageRepository, advertisementService, userService, modelMapper);
    }

    @Test
    public void testMessageService_sendMessage_expectMessageSent() {
        User user = mock(User.class);
        Advertisement advertisement = mock(Advertisement.class);
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(advertisementService.getAdvertisementById(anyString())).thenReturn(advertisement);
        MessageAddServiceModel messageServiceModel = mock(MessageAddServiceModel.class);
        messageService.sendMessage("123", messageServiceModel, "pesho");

        verify(messageRepository).save(any());
    }

    @Test
    public void testMessageService_deleteById_expectDeleteMessageIsInvoked() {
        String id = "123";
        messageService.deleteById(id);
        verify(messageRepository).deleteById(id);
    }

    @Test
    public void testMessageService_hide_expectMessageIsHidden() {
        Message message = mock(Message.class);
        Optional<Message> optionalMessage = Optional.of(message);
        when(messageRepository.findById(anyString())).thenReturn(optionalMessage);
        messageService.hide("123");
        verify(message).setHidden(anyBoolean());
        verify(messageRepository).saveAndFlush(message);
    }

    @Test
    public void testMessageService_findAllByReceiverUsername_expectMessagesByUsername() {
        PageRequest pageRequest = mock(PageRequest.class);
        Page<Message> messagePage = mock(Page.class);
        when(messagePage.get()).thenReturn(Stream.of());
        when(messagePage.getTotalElements()).thenReturn(1L);
        final String username = "user";
        when(messageRepository.findAllByReceiverUsername(username, pageRequest)).thenReturn(messagePage);

        MessagePageServiceModel messagePageServiceModel =
                messageService.findReceivedMessagesByUserUsername(username, pageRequest);
        assertEquals(Collections.emptyList(), messagePageServiceModel.getMessageServiceModels());
        assertEquals(0, messagePageServiceModel.getPages());
    }

    @Test
    public void testMessageService_findSentMessagesByUserUsername_expectMessagesByUsername() {
        PageRequest pageRequest = mock(PageRequest.class);
        Page<Message> messagePage = mock(Page.class);
        when(messagePage.get()).thenReturn(Stream.of());
        when(messagePage.getTotalElements()).thenReturn(1L);
        final String username = "user";
        when(messageRepository.findAllBySenderUsername(username, pageRequest)).thenReturn(messagePage);

        MessagePageServiceModel messagePageServiceModel =
                messageService.findSentMessagesByUserUsername(username, pageRequest);
        assertEquals(Collections.emptyList(), messagePageServiceModel.getMessageServiceModels());
        assertEquals(0, messagePageServiceModel.getPages());
    }

    @Test
    public void testMessageService_findReceivedMessagesByUserUsername_expectReceivedMessagesByUsername() {
        PageRequest pageRequest = mock(PageRequest.class);
        Page<Message> messagePage = mock(Page.class);
        when(messagePage.get()).thenReturn(Stream.of());
        when(messagePage.getTotalElements()).thenReturn(1L);
        final String username = "user";
        when(messageRepository.findAllByReceiverUsername(username, pageRequest)).thenReturn(messagePage);

        MessagePageServiceModel messagePageServiceModel =
                messageService.findReceivedMessagesByUserUsername(username, pageRequest);
        assertEquals(Collections.emptyList(), messagePageServiceModel.getMessageServiceModels());
        assertEquals(0, messagePageServiceModel.getPages());
    }

    @Test
    public void testMessageService_findById_expectMessageById() {
        Message message = mock(Message.class);
        when(message.getId()).thenReturn("123");
        User sender = mock(User.class);
        User receiver = mock(User.class);
        when(message.getSender()).thenReturn(sender);
        when(message.getReceiver()).thenReturn(receiver);
        Optional<Message> optionalMessage = Optional.of(message);
        when(messageRepository.findById(anyString())).thenReturn(optionalMessage);
        MessageServiceModel byId = messageService.findById("123");
        assertEquals("123", byId.getId());
    }

    @Test
    public void testMessageService_replyMessage_expectMessageReplied() {
        MessageAddServiceModel messageAddServiceModel = mock(MessageAddServiceModel.class);
        messageService.replyMessage("123", messageAddServiceModel, "petko", "pesho");
        verify(messageRepository).save(any());
    }
}