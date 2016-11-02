package itp341.wang.cherrie.parkhere.model;

/**
 * Created by Cherrie on 11/1/16.
 */

public class Booking {


    // CONSTRUCTOR
    public Booking() {
        super();
    }

    private String bookingOwner;
    private String listingOwner;
    private String bookingTitle;
    private boolean handicapped;
    private boolean tandem;
    private boolean suv;
    private boolean covered;
    private String location;
    private String about;
    private long price;

    public String getBookingOwner() {
        return bookingOwner;
    }

    public String getListingOwner() {
        return listingOwner;
    }

    public String getBookingTitle() {
        return bookingTitle;
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

    public void setBookingTitle(String bookingTitle) {
        this.bookingTitle = bookingTitle;
    }

    public void setListingOwner(String listingOwner) {
        this.listingOwner = listingOwner;
    }

    public void setBookingOwner(String bookingOwner) {
        this.bookingOwner = bookingOwner;
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
}
