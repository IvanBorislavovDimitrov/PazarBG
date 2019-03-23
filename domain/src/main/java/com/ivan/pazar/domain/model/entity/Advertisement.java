package com.ivan.pazar.domain.model.entity;

import com.ivan.pazar.domain.model.enums.Shipment;
import com.ivan.pazar.domain.model.enums.State;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "advertisements")
public class Advertisement extends IdEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "added_on", nullable = false)
    private LocalDate addedOn;

    @Column(name = "shipment", nullable = false)
    @Enumerated(EnumType.STRING)
    private Shipment shipment;

    @Column(name = "views", nullable = false)
    private int views;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToMany
    @JoinTable(name = "advertisements_users_searches",
            joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> usersSearches;

    @ManyToMany
    @JoinTable(name = "advertisements_users_favourites",
            joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> usersFavourites;

    @OneToMany(mappedBy = "advertisement", targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @OneToOne(mappedBy = "advertisement", targetEntity = Video.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Video video;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "subcategory_id", referencedColumnName = "id")
    private Subcategory subcategory;

    @ElementCollection
    @CollectionTable(name = "pictures", joinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"))
    @Column(name = "id")
    private List<String> pictures;

    @OneToMany(mappedBy = "advertisement", targetEntity = Review.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Advertisement() {
        usersSearches = new ArrayList<>();
        comments = new ArrayList<>();
        usersFavourites = new ArrayList<>();
        pictures = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDate addedOn) {
        this.addedOn = addedOn;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public List<User> getUsersSearches() {
        return usersSearches;
    }

    public void setUsersSearches(List<User> usersSearches) {
        this.usersSearches = usersSearches;
    }

    public List<User> getUsersFavourites() {
        return usersFavourites;
    }

    public void setUsersFavourites(List<User> usersFavourites) {
        this.usersFavourites = usersFavourites;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
