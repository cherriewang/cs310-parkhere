package itp341.wang.cherrie.parkhere.model;

import android.graphics.Bitmap;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;

/**
 * Created by Cherrie on 11/1/16.
 */

public class Review implements Serializable{

    private Listing owner;
    private String reviewer;
    private String reviewText;
    private Bitmap ownerReviewImage;
    private Bitmap listingImage;
    private String title;
    private String date;
    private float listingRating;
    private float ownerRating;

    // CONSTRUCTOR
    public Review() {
        super();
    }

    // GETTERS
    public Listing getOwner() {
        return owner;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getReviewText() {
        return reviewText;
    }

    public Bitmap getOwnerReviewImage() {
        return ownerReviewImage;
    }

    public Bitmap getListingImage() {
        return listingImage;
    }

    // SETTERS

    public void setOwner(Listing owner) {
        this.owner = owner;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setOwnerReviewImage(Bitmap ownerReviewImage) {
        this.ownerReviewImage = ownerReviewImage;
    }

    public void setListingImage(Bitmap listingImage) {
        this.listingImage = listingImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getListingRating() {
        return listingRating;
    }

    public void setListingRating(float listingRating) {
        this.listingRating = listingRating;
    }

    public float getOwnerRating() {
        return ownerRating;
    }

    public void setOwnerRating(float ownerRating) { this.ownerRating = ownerRating; }
}
