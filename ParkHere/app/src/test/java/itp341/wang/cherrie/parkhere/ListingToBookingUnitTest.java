package itp341.wang.cherrie.parkhere;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;

import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.Review;
import itp341.wang.cherrie.parkhere.model.User;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ListingToBookingUnitTest {

    private Listing myListing;
    private Booking myBooking;
    private User myUser;

    @Before
    public void SetUp() throws Exception {
        myListing = new Listing();
        myUser = new User();

        myUser.setmFirstName("Arush");

        myListing.setAbout("About");
        myListing.setOwner(myUser);
        myListing.setListingTitle("Title!");
        myListing.setCovered(true);
        myListing.setSuv(true);
        myListing.setListingOwner(myUser.getmFirstName());
        myListing.setPrice(3.0);
        myListing.setHandicapped(true);
        myListing.setTandem(true);

        myBooking = new Booking(myListing, myUser.getmFirstName(), 0, 0, 0, 0);
    }

    @Test
    public void ListingToBookingPrice() throws Exception {
        assertEquals(myBooking.getPrice(), 3.0, 0);
    }

    @Test
    public void ListingToBookingTandem() throws Exception {
        assertEquals(myBooking.isTandem(), true);
    }

    @Test
    public void ListingToBookingTitle() throws Exception {
        assertEquals(myBooking.getBookingTitle(), "Title!");
    }

    @Test
    public void ListingToBookingOwner() throws Exception {
        assertEquals(myBooking.getBookingOwner(), myListing.getListingOwner());
    }

    @Test
    public void ListingToBookingCovered() throws Exception {
        assertEquals(myBooking.isCovered(), true);
    }

    @Test
    public void ListingToBookingAbout() throws Exception {
        assertEquals(myBooking.getAbout(), "About");
    }

    @Test
    public void ListingToBookingHandicapped() throws Exception {
        assertEquals(myBooking.isHandicapped(), true);
    }

    @Test
    public void ListingToBookingSUV() throws Exception {
        assertEquals(myBooking.isSuv(), true);
    }
}