package com.ivan.pazar.web.model.binding;

import com.ivan.pazar.persistence.validation_annotations.FreeEmail;
import com.ivan.pazar.persistence.validation_annotations.FreePhoneNumber;
import com.ivan.pazar.persistence.validation_annotations.FreeUsername;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    private static final String INVALID_EMAIL = "Invalid email";
    private static final String INVALID_USERNAME = "Invalid username";
    private static final String INVALID_PASSWORD = "Invalid password";
    private static final String INVALID_CONFIRM_PASSWORD = "Invalid confirm password";
    private static final String INVALID_FIRST_NAME = "Invalid first name";
    private static final String INVALID_LAST_NAME = "Invalid last name";
    private static final String INVALID_PHONE_NUMBER = "Invalid phone number";
    private static final String INVALID_REGION = "Invalid region";
    private static final String INVALID_TOWN = "Invalid town";
    private static final String EMAIL_TAKEN = "The email is already taken";
    private static final String USERNAME_TAKEN = "The username is already taken";
    private static final String PHONE_NUMBER_TAKEN = "The phone number is already taken";

    @Pattern(regexp = "^[A-Za-z][A-Za-z.0-9]+@([A-Za-z]+(\\.)){1,}[A-Za-z0-9]+$", message = INVALID_EMAIL)
    @FreeEmail(message = EMAIL_TAKEN)
    private String email;

    @Size(min = 3, message = INVALID_USERNAME)
    @FreeUsername(message = USERNAME_TAKEN)
    private String username;

    @Size(min = 8, max = 32, message = INVALID_PASSWORD)
    private String password;

    @Size(min = 8, max = 32, message = INVALID_CONFIRM_PASSWORD)
    private String confirmPassword;

    @Size(min = 3, message = INVALID_FIRST_NAME)
    private String firstName;

    @Size(min = 3, message = INVALID_FIRST_NAME)
    private String lastName;

    @Size(min = 3, max = 20, message = INVALID_PHONE_NUMBER)
    @FreePhoneNumber(message = PHONE_NUMBER_TAKEN)
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
