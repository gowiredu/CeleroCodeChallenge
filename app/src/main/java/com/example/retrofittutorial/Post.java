package com.example.retrofittutorial;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {
    @SerializedName("identifier")
    @Expose
    private Integer identifier;
    @SerializedName("visitOrder")
    @Expose
    private Integer visitOrder;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("profilePicture")
    @Expose
    private ProfilePicture profilePicture;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("serviceReason")
    @Expose
    private String serviceReason;
    @SerializedName("problemPictures")
    @Expose
    private List<String> problemPictures = null;

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public Integer getVisitOrder() {
        return visitOrder;
    }

    public void setVisitOrder(Integer visitOrder) {
        this.visitOrder = visitOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(ProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getServiceReason() {
        return serviceReason;
    }

    public void setServiceReason(String serviceReason) {
        this.serviceReason = serviceReason;
    }

    public List<String> getProblemPictures() {
        return problemPictures;
    }

    public void setProblemPictures(List<String> problemPictures) {
        this.problemPictures = problemPictures;
    }

}
