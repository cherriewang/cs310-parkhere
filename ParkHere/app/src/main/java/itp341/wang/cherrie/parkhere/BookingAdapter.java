package itp341.wang.cherrie.parkhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            holder = new ResultsViewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (ResultsViewHolder) row.getTag();
        }

        Booking results = getItem(position);

        holder.listingTitleTextView.setText(results.getBookingTitle());
        holder.listingOwnerTextView.setText(results.getListingOwner());

        // Load the screen  image on a background thread
        /*if(!results.getImage_url().isEmpty()) {

            Picasso.with(getContext())

                    .load(results.getImage_url())

                    .into(holder.coffeeImg);

        }*/
        //else {
            //holder.listingImageView.setImageDrawable(R.drawable.placeholder_parking_spot);
        //}

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

}
