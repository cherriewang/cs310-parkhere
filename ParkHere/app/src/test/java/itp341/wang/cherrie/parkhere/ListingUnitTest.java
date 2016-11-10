package itp341.wang.cherrie.parkhere;

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
public class ListingUnitTest {

    @Test
    public void LatestReviewCorrect() throws Exception {
        Listing listing = new Listing();
        Review review1 = new Review();
        review1.setReviewText("first review");
        Review review2 = new Review();
        review2.setReviewText("second review");
        Review review3 = new Review();
        review3.setReviewText("third review");
        listing.addReview(review1);
        listing.addReview(review2);
        listing.addReview(review3);
        assertEquals(listing.getLatestReview().getReviewText(), "third review");
    }
}

