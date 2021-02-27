package com.example.intuitionproject.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Listing {
    private String destinationRegion;
    private String details;
    private String meetupRegion;
    private double paymentAmount;
    private String pictureUrl;
    private String title;
    private String authorId;
    private LocalDateTime time;

    public Listing(String destinationRegion, String details, String meetupRegion, double paymentAmount, String pictureUrl, String title, String authorId, long time) {
        this.destinationRegion = destinationRegion;
        this.details = details;
        this.meetupRegion = meetupRegion;
        this.paymentAmount = paymentAmount;
        this.pictureUrl = pictureUrl;
        this.title = title;
        this.authorId = authorId;
        setTime(time);
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

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Long paymentAmount) {
        this.paymentAmount = paymentAmount;
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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
