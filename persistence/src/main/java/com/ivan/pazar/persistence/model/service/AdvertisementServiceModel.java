package com.ivan.pazar.persistence.model.service;

import com.ivan.pazar.domain.model.enums.Shipment;
import com.ivan.pazar.domain.model.enums.State;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementServiceModel extends IdServiceModel {

    private String title;

    private LocalDate addedOn;

    private Shipment shipment;

    private int views;

    private BigDecimal price;

    private UserServiceModel author;

    private TownServiceModel town;

    private State state;

    private List<UserServiceModel> usersSearches;

    private List<UserServiceModel> usersFavourites;

    private List<CommentServiceModel> comments;

    private CategoryServiceModel category;

    private List<TagServiceModel> tags;

    private VideoServiceModel video;

    private boolean isActive;

    public AdvertisementServiceModel() {
        usersSearches = new ArrayList<>();
        usersFavourites = new ArrayList<>();
        comments = new ArrayList<>();
        tags = new ArrayList<>();
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

    public UserServiceModel getAuthor() {
        return author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }

    public TownServiceModel getTown() {
        return town;
    }

    public void setTown(TownServiceModel town) {
        this.town = town;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<UserServiceModel> getUsersSearches() {
        return usersSearches;
    }

    public void setUsersSearches(List<UserServiceModel> usersSearches) {
        this.usersSearches = usersSearches;
    }

    public List<UserServiceModel> getUsersFavourites() {
        return usersFavourites;
    }

    public void setUsersFavourites(List<UserServiceModel> usersFavourites) {
        this.usersFavourites = usersFavourites;
    }

    public List<CommentServiceModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentServiceModel> comments) {
        this.comments = comments;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

    public List<TagServiceModel> getTags() {
        return tags;
    }

    public void setTags(List<TagServiceModel> tags) {
        this.tags = tags;
    }

    public VideoServiceModel getVideo() {
        return video;
    }

    public void setVideo(VideoServiceModel video) {
        this.video = video;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
