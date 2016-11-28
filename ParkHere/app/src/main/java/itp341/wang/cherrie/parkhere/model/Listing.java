package itp341.wang.cherrie.parkhere.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import itp341.wang.cherrie.parkhere.Debug;

/**
 * Created by Cherrie on 11/1/16.
 */

public class Listing implements Serializable{

    private static final long serialVersionUID = 1L;

    // CONSTRUCTOR
    public Listing() {

    }

    public Listing(String listingOwner, String listingTitle, boolean handicapped, boolean tandem, boolean suv, boolean covered, String location, String about, double price, boolean sunday, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, String fromHourString, String fromMinuteString, String toHourString, String toMinuteString, int fromYear, int fromMonthOfYear, int fromDayOfMonth, int toYear, int toMonthOfYear, int toDayOfMonth, User owner, double latitude, double longitude, int cancellationPolicy, String latestReviewer) {
        this.listingOwner = listingOwner;
        this.listingTitle = listingTitle;
        this.handicapped = handicapped;
        this.tandem = tandem;
        this.suv = suv;
        this.covered = covered;
        this.location = location;
        this.about = about;
        this.price = price;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.fromHourString = fromHourString;
        this.fromMinuteString = fromMinuteString;
        this.toHourString = toHourString;
        this.toMinuteString = toMinuteString;
        this.fromYear = fromYear;
        this.fromMonthOfYear = fromMonthOfYear;
        this.fromDayOfMonth = fromDayOfMonth;
        this.toYear = toYear;
        this.toMonthOfYear = toMonthOfYear;
        this.toDayOfMonth = toDayOfMonth;
        this.owner = owner;
        Latitude = latitude;
        Longitude = longitude;
        this.cancellationPolicy = cancellationPolicy;
        this.latestReviewer = latestReviewer;
    }

    private boolean handicapped;
    private boolean tandem;
    private boolean suv;
    private boolean covered;
    private String location;
    private String about;
    private String listingImageString;

    private String listingOwner;
    private String listingTitle;
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
    private int fromYear;
    private int fromMonthOfYear;
    private int fromDayOfMonth;
    private int toYear;
    private int toMonthOfYear;
    private int toDayOfMonth;
    private User owner;


    public String getListingImageString() {
        return listingImageString;
    }

    public void setListingImageString(String listingImageString) {
        this.listingImageString = listingImageString;
    }

    // lat long
    private double Latitude;
    private double Longitude;

    // cancellation
    private int cancellationPolicy;

    // reviewer
    private String latestReviewer;

    private HashMap<String, Review> reviews;
    private HashMap<String, Booking> bookings;

    public User getOwner() { return owner; }

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

    public int getCancellationPolicy() {
        return cancellationPolicy;
    }

    public HashMap<String, Review> getReviews() { return reviews; }

    public void setReviews(HashMap<String, Review> reviews) {
        this.reviews = reviews;
    }

    public void setOwner(User owner) { this.owner = owner; }

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

    public int getFromYear() {
        return fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getFromMonthOfYear() {
        return fromMonthOfYear;
    }

    public void setFromMonthOfYear(int fromMonthOfYear) {
        this.fromMonthOfYear = fromMonthOfYear;
    }

    public int getFromDayOfMonth() {
        return fromDayOfMonth;
    }

    public void setFromDayOfMonth(int fromDayOfMonth) {
        this.fromDayOfMonth = fromDayOfMonth;
    }

    public int getToYear() {
        return toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    public int getToMonthOfYear() {
        return toMonthOfYear;
    }

    public void setToMonthOfYear(int toMonthOfYear) {
        this.toMonthOfYear = toMonthOfYear;
    }

    public int getToDayOfMonth() {
        return toDayOfMonth;
    }

    public void setToDayOfMonth(int toDayOfMonth) {
        this.toDayOfMonth = toDayOfMonth;
    }

    public void setCancellationPolicy(int cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
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

    public String getLatestReviewer() {
        return latestReviewer;
    }

    public void setLatestReviewer(String latestReviewer) {
        this.latestReviewer = latestReviewer;
    }

    public void addReview(Review r)
    {
        if (reviews == null) {
            reviews = new HashMap<>();
        }

        reviews.put(r.getReviewer(), r);
    }

    /*public void addBooking(Booking b)
    {
        if (bookings == null) {
            bookings = new HashMap<>();
        }

        bookings.put(b.getBookingOwner(), b);
    }

    public void removeBooking(Booking b)
    {
        bookings.remove(b.getBookingOwner());
    }*/

    public Review getLatestReview(){
        if(reviews != null) {
            if(!latestReviewer.isEmpty())
                return reviews.get(latestReviewer);
        }

        return null;
    }

    public float getAverageRating() {
        float sum = 0;
        if(reviews == null) {
            return 0;
        }
        else{
            Collection<Review> listingReviews =  reviews.values();
            for (Review r : listingReviews) {
                sum += r.getListingRating();
            }
            return sum / (float)getReviews().size();
        }
    }
}
