package itp341.wang.cherrie.parkhere.model;

import java.io.Serializable;

/**
 * Created by glarencezhao on 11/28/16.
 */

public class ParkingSpot implements Serializable{

    private static final long serialVersionUID = 2L;

    private boolean handicapped;
    private boolean tandem;
    private boolean suv;
    private boolean covered;
    private String location;
    private String about;
    // lat long
    private double Latitude;
    private double Longitude;
    private String parkingSpotImageString;

    private String parkingSpotOwner;
    private String parkingSpotName;

    public ParkingSpot() {}

    public ParkingSpot(String about, boolean covered, boolean handicapped, double latitude, String location, double longitude, String parkingSpotName, String parkingSpotOwner, boolean suv, boolean tandem) {
        this.about = about;
        this.covered = covered;
        this.handicapped = handicapped;
        Latitude = latitude;
        this.location = location;
        Longitude = longitude;
        this.parkingSpotName = parkingSpotName;
        this.parkingSpotOwner = parkingSpotOwner;
        this.suv = suv;
        this.tandem = tandem;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public boolean isCovered() {
        return covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public boolean isHandicapped() {
        return handicapped;
    }

    public void setHandicapped(boolean handicapped) {
        this.handicapped = handicapped;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getParkingSpotName() {
        return parkingSpotName;
    }

    public void setParkingSpotName(String parkingSpotName) {
        this.parkingSpotName = parkingSpotName;
    }

    public String getParkingSpotOwner() {
        return parkingSpotOwner;
    }

    public void setParkingSpotOwner(String parkingSpotOwner) {
        this.parkingSpotOwner = parkingSpotOwner;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSuv() {
        return suv;
    }

    public void setSuv(boolean suv) {
        this.suv = suv;
    }

    public boolean isTandem() {
        return tandem;
    }

    public void setTandem(boolean tandem) {
        this.tandem = tandem;
    }

    public String getParkingSpotImageString() {
        return parkingSpotImageString;
    }

    public void setParkingSpotImageString(String parkingSpotImageString) {
        this.parkingSpotImageString = parkingSpotImageString;
    }
}
