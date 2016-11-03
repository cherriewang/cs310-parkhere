package itp341.wang.cherrie.parkhere;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import itp341.wang.cherrie.parkhere.model.Listing;

/**
 * Created by Glarence Zhao on 11/2/2016.
 */

public class ListingAdapter extends ArrayAdapter<Listing>{

    private final LayoutInflater mLayoutInflater;

    public ListingAdapter(Context context, int resource, ArrayList<Listing> objects){
        super(context, resource, objects);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ResultsViewHolder holder;

        if (row == null) {
            row = mLayoutInflater.inflate(R.layout.row_listing_layout, parent, false);
            holder = new ResultsViewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (ResultsViewHolder) row.getTag();
        }

        Listing results = getItem(position);

        holder.listingTitleTextView.setText(results.getListingTitle());
        return row;
    }

    static final class ResultsViewHolder {
        TextView listingTitleTextView;
        SimpleDraweeView listingImageView;

        public ResultsViewHolder(View v) {
            listingTitleTextView = (TextView) v.findViewById(R.id.listingTitleTextView);
            listingImageView = (SimpleDraweeView) v.findViewById(R.id.listingImageView);
        }
    }

}
