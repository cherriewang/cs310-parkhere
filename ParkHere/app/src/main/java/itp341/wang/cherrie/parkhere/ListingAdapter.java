package itp341.wang.cherrie.parkhere;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

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
            //Hover code
            BlurLayout listingBlurLayout = (BlurLayout) row.findViewById(R.id.listingBlurLayout);
            View hover = LayoutInflater.from(getContext()).inflate(R.layout.hover_listing_layout, null);
            setHoverIcons(hover);
            listeners(hover);
            listingBlurLayout.setHoverView(hover);
            holder = new ResultsViewHolder(row);
            listingBlurLayout.setBlurDuration(100);
            listingBlurLayout.addChildAppearAnimator(hover, R.id.listingDetail, Techniques.FlipInX, 550, 0);
            listingBlurLayout.addChildAppearAnimator(hover, R.id.edit, Techniques.FlipInX, 550, 250);
            listingBlurLayout.addChildAppearAnimator(hover, R.id.cancel, Techniques.FlipInX, 550, 500);
            listingBlurLayout.addChildDisappearAnimator(hover, R.id.listingDetail, Techniques.FlipOutX, 550, 500);
            listingBlurLayout.addChildDisappearAnimator(hover, R.id.edit, Techniques.FlipOutX, 550, 250);
            listingBlurLayout.addChildDisappearAnimator(hover, R.id.cancel, Techniques.FlipOutX, 550, 0);
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
        v.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Wobble).duration(200).playOn(view);
                Intent i = new Intent(getContext(), CreateEditListingActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //pass listing to edit
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

    private void setHoverIcons(View v){
        ImageView listingDetail = (ImageView)v.findViewById(R.id.listingDetail);
        listingDetail.setImageDrawable(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_info).color(getContext().getResources().getColor(R.color.colorAccent)).sizeDp(20));
        ImageView edit = (ImageView)v.findViewById(R.id.edit);
        edit.setImageDrawable(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_pencil_square).color(getContext().getResources().getColor(R.color.colorAccent)).sizeDp(20));
        ImageView cancel = (ImageView)v.findViewById(R.id.cancel);
        cancel.setImageDrawable(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_trash).color(getContext().getResources().getColor(R.color.colorAccent)).sizeDp(20));
    }

}
