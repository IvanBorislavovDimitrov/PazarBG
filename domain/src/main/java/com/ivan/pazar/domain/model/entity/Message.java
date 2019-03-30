package com.ivan.pazar.domain.model.entity;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "messages")
public class Message extends IdEntity {

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private LocalDateTime addedOn;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "advertisement_id", referencedColumnName = "id")
    private Advertisement advertisement;

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
