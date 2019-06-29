package com.ivan.pazar.web.model.view;

import java.util.ArrayList;
import java.util.List;

public class UserProfileViewModel {

    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String websiteAddress;
    private RegionViewModel region;
    private TownViewModel town;
    private String description;
    private List<AdvertisementViewModel> advertisements;
    private List<MessageViewModel> sentMessages;
    private List<MessageViewModel> receivedMessages;
    private List<AdvertisementViewModel> searchedAdvertisements;
    private String profilePictureName;
    private List<ReviewViewModel> reviews;

    public UserProfileViewModel() {
        advertisements = new ArrayList<>();
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
        searchedAdvertisements = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public RegionViewModel getRegion() {
        return region;
    }

    public void setRegion(RegionViewModel region) {
        this.region = region;
    }

    public TownViewModel getTown() {
        return town;
    }

    public void setTown(TownViewModel town) {
        this.town = town;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AdvertisementViewModel> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<AdvertisementViewModel> advertisements) {
        this.advertisements = advertisements;
    }

    public List<MessageViewModel> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<MessageViewModel> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<MessageViewModel> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<MessageViewModel> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<AdvertisementViewModel> getSearchedAdvertisements() {
        return searchedAdvertisements;
    }

    public void setSearchedAdvertisements(List<AdvertisementViewModel> searchedAdvertisements) {
        this.searchedAdvertisements = searchedAdvertisements;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }

    public List<ReviewViewModel> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewViewModel> reviews) {
        this.reviews = reviews;
    }
}
