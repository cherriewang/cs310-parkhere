package itp341.wang.cherrie.parkhere.model;

import android.net.Uri;
import android.util.Base64;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Cherrie on 10/28/16.
 */

public class User implements Serializable {
    // first name
    private String mFirstName;
    // last name
    private String mLastName;
    // phone number
    private String mPhoneNumber;
    // profile image URL
    private String mProfileImageURL;
    // email
    private String mEmail;
    // hashed password
    private int mHashedPassword;

    private String mNormalizedEmail;

    private boolean isOwner;
    private boolean isSeeker;
    private HashMap<String, Booking> mBookings;
    private HashMap<String, Listing> mListings;
    private float rating;
    private int numRatings;
    private double accountBalance;

    // CONSTRUCTOR
    public User() {
        super();
    }

    // getters
    public String getmFirstName() {
        return mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmProfileImageURL() {
        return mProfileImageURL;
    }

    public String getmEmail() {
        return mEmail;
    }

    public int getmHashedPassword() {
        return mHashedPassword;
    }

    public String getmNormalizedEmail() { return mNormalizedEmail; }

    public boolean isOwner() {
        return isOwner;
    }

    public boolean isSeeker() {
        return isSeeker;
    }

    public HashMap<String, Booking> getmBookings() { return mBookings; }

    public HashMap<String, Listing> getmListings() { return mListings; }

    public double getAccountBalance() {
        return accountBalance;
    }

    // setters

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmProfileImageURL(String mProfileImageURL) {
        this.mProfileImageURL = mProfileImageURL;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
        this.mNormalizedEmail = mEmail.replace(".", "%2E");
    }

    public void setmHashedPassword(int mHashedPassword) {
        this.mHashedPassword = mHashedPassword;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public void setSeeker(boolean seeker) {
        isSeeker = seeker;
    }

    /*public void appendListing(Listing l) {
        if (mListings == null) {
            mListings = new ArrayList<Listing>();
        }

        mListings.add(l);
    }*/

    /*public void appendBooking(Booking b) {
        if (mBookings == null) {
            mBookings = new ArrayList<Booking>();
        }

        mBookings.add(b);
    }*/

    public void removeListing(Listing l) {
        if(mListings != null)
            mListings.remove(l.getListingTitle());
    }

    public void removeBooking(Booking b) {
        if (mBookings != null)
            mBookings.remove(b.getBookingTitle());
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public float getAverageRating() {
        float sum = 0;
        float numReviews = 0;
        if(mListings != null){
            List<Listing> listings = (List) mListings.values();
            for(Listing listing : listings){
                if(listing.getReviews() == null) {
                    sum += 0;
                    numReviews += 0;
                }
                else{
                    for (Review r : listing.getReviews()) {
                        sum += r.getOwnerRating();
                    }
                    numReviews += listing.getReviews().size();
                }
            }
        }

        if(numReviews != 0)
            return sum / (float)numReviews;
        else
            return 0;
    }
}
