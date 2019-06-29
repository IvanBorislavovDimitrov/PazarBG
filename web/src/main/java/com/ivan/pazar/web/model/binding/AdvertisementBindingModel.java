package com.ivan.pazar.web.model.binding;

import com.ivan.pazar.persistence.validation_annotations.ValidPicturesExtensions;
import com.ivan.pazar.persistence.validation_annotations.ValidShipment;
import com.ivan.pazar.persistence.validation_annotations.ValidVideo;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.List;

public class AdvertisementBindingModel {

    private static final String INVALID_TITLE = "Invalid title";
    private static final String INVALID_SHIPMENT = "Invalid shipment";
    private static final String INVALID_STATE = "Invalid state";
    private static final String INVALID_CATEGORY = "Invalid category";
    private static final String INVALID_SUBCATEGORY = "Invalid subcategory";
    private static final String INVALID_PICTURES = "One or more pictures are not JPEG, PNG or JPG";
    private static final String INVALID_VIDEO_FORMAT = "Invalid video format.";

    private String id;

    @NotEmpty(message = INVALID_TITLE)
    private String title;

    @ValidShipment(message = INVALID_SHIPMENT)
    private String shipment;

    @Min(0)
    private BigDecimal price;

    private String region;

    private String town;

    @NotEmpty(message = INVALID_STATE)
    private String state;

    @NotEmpty(message = INVALID_CATEGORY)
    private String category;

    @ValidVideo(message = INVALID_VIDEO_FORMAT)
    private MultipartFile video;

    @ValidPicturesExtensions(message = INVALID_PICTURES)
    private List<MultipartFile> photos;

    @NotEmpty(message = INVALID_SUBCATEGORY)
    private String subcategory;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
