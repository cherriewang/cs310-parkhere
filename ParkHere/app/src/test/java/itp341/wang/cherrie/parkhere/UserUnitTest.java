package itp341.wang.cherrie.parkhere;

import org.junit.Before;
import org.junit.Test;

import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.Listing;
import itp341.wang.cherrie.parkhere.model.Review;
import itp341.wang.cherrie.parkhere.model.User;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserUnitTest {

    @Test
    public void addBooking_isCorrect() throws Exception {
        User user = new User();
        Booking booking1 = new Booking(new Listing());
        Booking booking2 = new Booking(new Listing());
        user.appendBooking(booking1);
        user.appendBooking(booking2);
        assertEquals(user.getmBookings().size(), 2);
    }

    @Test
    public void addListing_isCorrect() throws Exception {
        User user = new User();
        Listing listing1 = new Listing();
        Listing listing2 = new Listing();
        user.appendListing(listing1);
        user.appendListing(listing2);
        assertEquals(user.getmListings().size(), 2);
    }

    @Test
    public void RatingCorrect() throws Exception {
        User user = new User();
        Listing listing1 = new Listing();
        Review review1 = new Review();
        review1.setOwnerRating(5);
        listing1.addReview(review1);
        user.appendListing(listing1);
        assertEquals(user.getAverageRating(), 5.0, 0);
    }

    @Test
    public void AvgRatingCorrect1() throws Exception {
        User user = new User();
        Listing listing1 = new Listing();
        Review review1 = new Review();
        review1.setOwnerRating(5);
        Review review2 = new Review();
        review2.setOwnerRating(4);
        listing1.addReview(review1);
        listing1.addReview(review2);
        user.appendListing(listing1);
        assertEquals(user.getAverageRating(), 4.5, 0);
    }
    @Test
    public void AvgRatingCorrect2() throws Exception {
        User user = new User();
        Listing listing1 = new Listing();
        Review review1 = new Review();
        review1.setOwnerRating(5);
        Listing listing2 = new Listing();
        Review review2 = new Review();
        review2.setOwnerRating(4);
        listing1.addReview(review1);
        listing2.addReview(review2);
        user.appendListing(listing1);
        user.appendListing(listing2);
        assertEquals(user.getAverageRating(), 4.5, 0);
    }
    @Test
    public void AvgRatingCorrect3() throws Exception {
        User user = new User();
        Listing listing1 = new Listing();
        user.appendListing(listing1);
        assertEquals(user.getAverageRating(), 0, 0);
    }
}

