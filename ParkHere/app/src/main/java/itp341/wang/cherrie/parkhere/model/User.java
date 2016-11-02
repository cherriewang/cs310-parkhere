package itp341.wang.cherrie.parkhere.model;

import android.net.Uri;
import android.util.Base64;

import java.io.Serializable;

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

}
