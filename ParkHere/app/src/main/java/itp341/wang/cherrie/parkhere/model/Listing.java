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
    private double price;
    // regarding times
    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private String fromHourString;
    private String fromMinuteString;
    private String toHourString;
    private String toMinuteString;

    // lat long
    private double Latitude;
    private double Longitude;


    private List<Review> reviews;

    public String getListingOwner() {
        return listingOwner;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public boolean isSunday() {
        return sunday;
    }

    public boolean isMonday() {
        return monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public String getFromHourString() {
        return fromHourString;
    }

    public String getFromMinuteString() {
        return fromMinuteString;
    }

    public String getToHourString() {
        return toHourString;
    }

    public String getToMinuteString() {
        return toMinuteString;
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

    public double getPrice() {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public void setFromHourString(String fromHourString) {
        this.fromHourString = fromHourString;
    }

    public void setFromMinuteString(String fromMinuteString) {
        this.fromMinuteString = fromMinuteString;
    }

    public void setToHourString(String toHourString) {
        this.toHourString = toHourString;
    }

    public void setToMinuteString(String toMinuteString) {
        this.toMinuteString = toMinuteString;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void addReview(Review r)
    {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }

        reviews.add(r);
    }

    public float getAverageRating() {
        float sum = 0;
        if(getReviews() == null)
            return 0;
        else{
            for (Review r : getReviews()) {
                sum += r.getRating();
            }
            return sum / (float)getReviews().size();
        }
    }
}
