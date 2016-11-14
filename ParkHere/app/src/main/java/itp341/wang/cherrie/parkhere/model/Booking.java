package itp341.wang.cherrie.parkhere.model;

/*
 * Created by Cherrie on 11/1/16.
 */

import java.io.Serializable;

public class Booking implements Serializable{

    // CONSTRUCTOR
    public Booking() {
        super();
    }

    public Booking(Listing l, String bookingOwner) {
        this();
        if (l != null) {
            this.bookingOwner = bookingOwner;
            this.owner = l;
            this.bookingTitle = l.getListingTitle();
            this.handicapped = l.isHandicapped();
            this.tandem = l.isTandem();
            this.suv = l.isSuv();
            this.covered = l.isCovered();
            this.location = l.getLocation();
            this.about = l.getAbout();
            this.price = l.getPrice();
        }
    }

    private String bookingOwner;
    private Listing owner;
    private String bookingTitle;
    private boolean handicapped;
    private boolean tandem;
    private boolean suv;
    private boolean covered;
    private String location;
    private String about;
    private double price;

    public String getBookingOwner() {
        return bookingOwner;
    }

    public Listing getOwner() {
        return owner;
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

    public double getPrice() {
        return price;
    }

    public void setBookingTitle(String bookingTitle) {
        this.bookingTitle = bookingTitle;
    }

    public void setOwner(Listing owner) {
        this.owner = owner;
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

    public void setPrice(double price) {
        this.price = price;
    }
}
