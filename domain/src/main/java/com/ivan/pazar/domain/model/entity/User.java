package com.ivan.pazar.domain.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends IdEntity {

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "website_address")
    private String websiteAddress;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "author", targetEntity = Advertisement.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Advertisement> advertisements;

    @OneToMany(mappedBy = "sender", targetEntity = Message.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", targetEntity = Message.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> receivedMessages;

    @ManyToMany(mappedBy = "usersSearches", targetEntity = Advertisement.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Advertisement> searchedAdvertisements;

    @Column(name = "profile_picture")
    private String profilePictureName;

    @OneToMany(mappedBy = "author", targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", targetEntity = Review.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "usersFavourites", targetEntity = Advertisement.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Advertisement> favouriteAdvertisements;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public User() {
        advertisements = new ArrayList<>();
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
        searchedAdvertisements = new ArrayList<>();
        comments = new ArrayList<>();
        favouriteAdvertisements = new ArrayList<>();
        roles = new HashSet<>();
        reviews = new ArrayList<>();
    }

    public User(User user) {
        setUsername(user.username);
        setEmail(user.email);
        setPassword(user.password);
        setFirstName(user.firstName);
        setLastName(user.lastName);
        setPhoneNumber(user.phoneNumber);
        setWebsiteAddress(user.websiteAddress);
        setRegion(user.region);
        setTown(user.town);
        setRegisteredAt(user.getRegisteredAt());
        setDescription(user.description);
        setAdvertisements(user.advertisements);
        setRoles(user.roles);
        setFavouriteAdvertisements(user.favouriteAdvertisements);
        setComments(user.comments);
        setProfilePictureName(user.profilePictureName);
        setSearchedAdvertisements(user.searchedAdvertisements);
        setReceivedMessages(user.receivedMessages);
        setSentMessages(user.sentMessages);
        setReviews(user.reviews);
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
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

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public List<Advertisement> getSearchedAdvertisements() {
        return searchedAdvertisements;
    }

    public void setSearchedAdvertisements(List<Advertisement> searchedAdvertisements) {
        this.searchedAdvertisements = searchedAdvertisements;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Advertisement> getFavouriteAdvertisements() {
        return favouriteAdvertisements;
    }

    public void setFavouriteAdvertisements(List<Advertisement> favouriteAdvertisements) {
        this.favouriteAdvertisements = favouriteAdvertisements;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
