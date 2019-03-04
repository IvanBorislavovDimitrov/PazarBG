package com.ivan.pazar.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment extends IdEntity {

    @Column(name = "content", nullable = false,columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "advertisement_id",  referencedColumnName = "id")
    private Advertisement advertisement;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
