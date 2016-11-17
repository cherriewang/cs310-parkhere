package itp341.wang.cherrie.parkhere.model;

import android.net.Uri;
import android.util.Base64;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private boolean recentTransactionApproved;
    private HashMap<String, Booking> mBookings;
    private HashMap<String, Listing> mListings;
    private HashMap<String, Transaction> mTransactions;
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

    public boolean hasRecentTransactionApproved() {
        return recentTransactionApproved;
    }

    public boolean isRecentTransactionApproved() {
        return recentTransactionApproved;
    }

    public HashMap<String, Booking> getmBookings() { return mBookings; }

    public HashMap<String, Listing> getmListings() { return mListings; }

    public HashMap<String, Transaction> getmTransactions() { return mTransactions; }

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

    public void setRecentTransactionApproved(boolean recentTransactionApproved) {
        this.recentTransactionApproved = recentTransactionApproved;
    }

    public void appendListing(Listing l) {
        if (mListings == null) {
            mListings = new HashMap<>();
        }

        mListings.put(l.getListingTitle(), l);
    }

    public void appendTransaction(Transaction t) {
        if (mTransactions == null) {
            mTransactions = new HashMap<>();
        }

        mTransactions.put(t.getListingOwner(), t);
    }

    public void appendBooking(Booking b) {
        if (mBookings == null) {
            mBookings = new HashMap<>();
        }

        mBookings.put(b.getBookingTitle(), b);
    }

    public void removeListing(Listing l) {
        if(mListings != null)
            mListings.remove(l.getListingTitle());
    }

    public void removeBooking(Booking b) {
        if (mBookings != null)
            mBookings.remove(b.getBookingTitle());
    }

    public void removeBooking(Transaction t) {
        if (mTransactions != null)
            mTransactions.remove(t.getListingName());
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public float getAverageRating() {
        float sum = 0;
        float numReviews = 0;
        if(mListings != null){
            Collection<Listing> listings =  mListings.values();
            for(Listing listing : listings){
                if(listing.getReviews() == null) {
                    sum += 0;
                    numReviews += 0;
                }
                else{
                    Collection<Review> reviews = listing.getReviews().values();
                    for (Review r : reviews) {
                        sum += r.getOwnerRating();
                    }
                    numReviews += listing.getReviews().size();
                }
            }
        }

        if(numReviews != 0)
            return (float)sum / (float)numReviews;
        else
            return 0;
    }
}
