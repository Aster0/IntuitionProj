package com.example.intuitionproject.models;

public class Listing {
    private String destinationRegion;
    private String details;
    private String meetupRegion;
    private String paymentMethod;
    private String pictureUrl;
    private String title;
    private String authorId;

    public Listing(String destinationRegion, String details, String meetupRegion, String paymentMethod, String pictureUrl, String title, String authorId) {
        this.destinationRegion = destinationRegion;
        this.details = details;
        this.meetupRegion = meetupRegion;
        this.paymentMethod = paymentMethod;
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.authorId = authorId;
    }

    public String getDestinationRegion() {
        return destinationRegion;
    }

    public void setDestinationRegion(String destinationRegion) {
        this.destinationRegion = destinationRegion;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMeetupRegion() {
        return meetupRegion;
    }

    public void setMeetupRegion(String meetupRegion) {
        this.meetupRegion = meetupRegion;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
