package com.ivan.pazar.persistence.dto.service;

import com.ivan.pazar.domain.model.entity.Region;
import com.ivan.pazar.domain.model.entity.Role;
import com.ivan.pazar.domain.model.entity.Town;

import java.util.ArrayList;
import java.util.List;

public class UserServiceModel extends IdServiceModel {

    private String email;

    private String username;

    private String password;

    private String confirmPassword;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String websiteAddress;

    private Region region;

    private Town town;

    private String description;

    private double rating;

    private List<AdvertisementServiceModel> advertisements;

    private List<MessageServiceModel> sentMessages;

    private List<MessageServiceModel> receivedMessages;

    private List<AdvertisementServiceModel> searchedAdvertisements;

    private byte[] profilePicture;

    private List<CommentServiceModel> comments;

    private List<AdvertisementServiceModel> favouriteAdvertisements;

    private Role role;

    public UserServiceModel() {
        advertisements = new ArrayList<>();
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
        searchedAdvertisements = new ArrayList<>();
        comments = new ArrayList<>();
        favouriteAdvertisements = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsiteAddress() {
        return websiteAddress;
    }

    public void setWebsiteAddress(String websiteAddress) {
        this.websiteAddress = websiteAddress;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<AdvertisementServiceModel> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<AdvertisementServiceModel> advertisements) {
        this.advertisements = advertisements;
    }

    public List<MessageServiceModel> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<MessageServiceModel> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<MessageServiceModel> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<MessageServiceModel> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<AdvertisementServiceModel> getSearchedAdvertisements() {
        return searchedAdvertisements;
    }

    public void setSearchedAdvertisements(List<AdvertisementServiceModel> searchedAdvertisements) {
        this.searchedAdvertisements = searchedAdvertisements;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<CommentServiceModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentServiceModel> comments) {
        this.comments = comments;
    }

    public List<AdvertisementServiceModel> getFavouriteAdvertisements() {
        return favouriteAdvertisements;
    }

    public void setFavouriteAdvertisements(List<AdvertisementServiceModel> favouriteAdvertisements) {
        this.favouriteAdvertisements = favouriteAdvertisements;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
