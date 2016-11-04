package itp341.wang.cherrie.parkhere.model;

/**
 * Created by Cherrie on 11/3/16.
 */

public class Card {

    String payer;
    String listingOwner;
    double balance = 0.0;

    // CONSTRUCTOR
    public Card() {
        super();
    }

    // GETTERS
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
    public void setListingOwner(String listingOwner) {
        this.listingOwner = listingOwner;
    }
    public void setPayer(String payer) {
        this.payer = payer;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }


}
