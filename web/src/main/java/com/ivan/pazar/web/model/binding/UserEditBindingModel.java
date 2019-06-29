package com.ivan.pazar.web.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserEditBindingModel {

    private static final String INVALID_EMAIL = "Invalid email";
    private static final String INVALID_FIRST_NAME = "Invalid first name";
    private static final String INVALID_LAST_NAME = "Invalid last name";
    private static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    private static final String INVALID_REGION = "Invalid region";
    private static final String INVALID_TOWN = "Invalid town";

    @Pattern(regexp = "^[A-Za-z][A-Za-z.0-9]+@([A-Za-z]+(\\.)){1,}[A-Za-z0-9]+$", message = INVALID_EMAIL)
    private String email;

    @NotEmpty(message = INVALID_FIRST_NAME)
    private String firstName;

    @NotEmpty(message = INVALID_LAST_NAME)
    private String lastName;

    @Size(min = 3, max = 20, message = INVALID_PHONE_NUMBER)
    private String phoneNumber;

    private String websiteAddress;

    private String description;

    @NotEmpty(message = INVALID_REGION)
    private String region;

    @NotEmpty(message = INVALID_TOWN)
    private String town;

    private MultipartFile profilePicture;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

}
