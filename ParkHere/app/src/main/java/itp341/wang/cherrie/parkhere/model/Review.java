package itp341.wang.cherrie.parkhere.model;

import android.graphics.Bitmap;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Cherrie on 11/1/16.
 */

public class Review {

    private Listing owner;
    private String reviewer;
    private String reviewText;
    private Bitmap ownerReviewImage;
    private Bitmap listingImage;

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

}
