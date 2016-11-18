package itp341.wang.cherrie.parkhere.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Cherrie on 11/3/16.
 */

public class Transaction implements Serializable{

    String payer;
    String listingOwner;
    String listingName;
    String toHourString;
    String toMinuteString;
    int toYear;
    int toMonthOfYear;
    int toDayOfMonth;
    boolean approved;
    double balance = 0.0;

    // CONSTRUCTOR
    public Transaction() {
        super();
    }

    // GETTERS

    public String getToHourString() {
        return toHourString;
    }

    public void setToHourString(String toHourString) {
        this.toHourString = toHourString;
    }

    public String getToMinuteString() {
        return toMinuteString;
    }

    public void setToMinuteString(String toMinuteString) {
        this.toMinuteString = toMinuteString;
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

    public int getToDayOfMonth() {
        return toDayOfMonth;
    }

    public void setToDayOfMonth(int toDayOfMonth) {
        this.toDayOfMonth = toDayOfMonth;
    }

    public void setToMonthOfYear(int toMonthOfYear) {
        this.toMonthOfYear = toMonthOfYear;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getListingName() {
        return listingName;
    }

    public double getBalance() {
        return balance;
    }
    public String getListingOwner() {
        return listingOwner;
    }
    public String getPayer() {
        return payer;
    }

    // SETTERS

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public void setListingOwner(String listingOwner) {
        this.listingOwner = listingOwner;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Calendar getTransactionCalendar(){
        Calendar myCal = Calendar.getInstance();
        myCal.set(toYear, toMonthOfYear, toDayOfMonth, Integer.parseInt(toHourString), Integer.parseInt(toMinuteString));
        return myCal;
    }


}
