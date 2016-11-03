package itp341.wang.cherrie.parkhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import itp341.wang.cherrie.parkhere.model.Booking;
import itp341.wang.cherrie.parkhere.model.Review;

/**
 * Created by Glarence Zhao on 11/2/2016.
 */

public class RatingAdapter extends ArrayAdapter<Review>{

    private final LayoutInflater mLayoutInflater;

    public RatingAdapter(Context context, int resource, ArrayList<Review> objects){
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

        Review results = getItem(position);

        holder.listingTitleTextView.setText(results.getReviewText());
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
