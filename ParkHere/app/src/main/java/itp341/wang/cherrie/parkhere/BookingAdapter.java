package itp341.wang.cherrie.parkhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import itp341.wang.cherrie.parkhere.model.Booking;

/**
 * Created by Glarence Zhao on 11/2/2016.
 */

public class BookingAdapter extends ArrayAdapter<Booking>{

    private final LayoutInflater mLayoutInflater;

    public BookingAdapter(Context context, int resource, ArrayList<Booking> objects){
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultsViewHolder holder;

        if (row == null) {
            row = mLayoutInflater.inflate(R.layout.row_booking_layout, parent, false);
            //Hover code
            BlurLayout bookingBlurLayout = (BlurLayout) row.findViewById(R.id.bookingBlurLayout);
            View hover = LayoutInflater.from(getContext()).inflate(R.layout.hover_booking_layout, null);
            listeners(hover);
            bookingBlurLayout.setHoverView(hover);
            holder = new ResultsViewHolder(row);
            bookingBlurLayout.setBlurDuration(300);
            bookingBlurLayout.addChildAppearAnimator(hover, R.id.listingDetail, Techniques.FlipInX, 550, 0);
            bookingBlurLayout.addChildAppearAnimator(hover, R.id.review, Techniques.FlipInX, 550, 250);
            bookingBlurLayout.addChildAppearAnimator(hover, R.id.cancel, Techniques.FlipInX, 550, 500);
            bookingBlurLayout.addChildDisappearAnimator(hover, R.id.listingDetail, Techniques.FlipOutX, 550, 500);
            bookingBlurLayout.addChildDisappearAnimator(hover, R.id.review, Techniques.FlipOutX, 550, 250);
            bookingBlurLayout.addChildDisappearAnimator(hover, R.id.cancel, Techniques.FlipOutX, 550, 0);

            row.setTag(holder);
        }
        else {
            holder = (ResultsViewHolder) row.getTag();
        }

        Booking results = getItem(position);

        holder.listingTitleTextView.setText(results.getBookingTitle());
        return row;
    }

    static final class ResultsViewHolder {

        TextView listingTitleTextView;

        TextView listingOwnerTextView;

        SimpleDraweeView listingImageView;

        public ResultsViewHolder(View v) {
            listingTitleTextView = (TextView) v.findViewById(R.id.listingTitleTextView);
            listingOwnerTextView = (TextView) v.findViewById(R.id.listingOwnerTextView);
            listingImageView = (SimpleDraweeView) v.findViewById(R.id.listingImageView);
        }
    }

    private void listeners(View v){
        v.findViewById(R.id.listingDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Wobble).duration(200).playOn(view);
                Intent i = new Intent(getContext(), ListingDetailActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //pass listing details
                getContext().startActivity(i);
            }
        });
        v.findViewById(R.id.review).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Wobble).duration(200).playOn(view);
                Intent i = new Intent(getContext(), RateReviewActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //pass listing to review
                getContext().startActivity(i);
            }
        });
        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Wobble).duration(200).playOn(view);
                //code to cancel or remove booking
            }
        });
    }

}
