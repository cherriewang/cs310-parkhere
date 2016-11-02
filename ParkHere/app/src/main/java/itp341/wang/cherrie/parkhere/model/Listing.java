package itp341.wang.cherrie.parkhere.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cherrie on 11/1/16.
 */

public class Listing implements Serializable{

    // CONSTRUCTOR
    public Listing() {
        super();
    }

    private String listingOwner;
    private String listingTitle;
    private boolean handicapped;
    private boolean tandem;
    private boolean suv;
    private boolean covered;
    private String location;
    private String about;
    private long price;

    private List<Review> reviews;

    public String getListingOwner() {
        return listingOwner;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public boolean isHandicapped() {
        return handicapped;
    }

    public boolean isTandem() {
        return tandem;
    }

    public boolean isSuv() {
        return suv;
    }

    public boolean isCovered() {
        return covered;
    }

    public String getLocation() {
        return location;
    }

    public String getAbout() {
        return about;
    }

    public long getPrice() {
        return price;
    }

    public List<Review> getReviews() { return reviews; }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public void setListingOwner(String listingOwner) {
        this.listingOwner = listingOwner;
    }

    public void setHandicapped(boolean handicapped) {
        this.handicapped = handicapped;
    }

    public void setTandem(boolean tandem) {
        this.tandem = tandem;
    }

    public void setSuv(boolean suv) {
        this.suv = suv;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void addReview(Review r)
    {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }

        reviews.add(r);
    }
}
