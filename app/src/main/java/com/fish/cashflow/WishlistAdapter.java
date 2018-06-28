package com.fish.cashflow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class WishlistAdapter extends ArrayAdapter<WishlishObj> {
    //log
    private static final String TAG = "WishlistAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        ProgressBar percentTV;
        TextView descriptionTV;
        TextView showPercentTV;
    }

    /**
     * Default constructor for the PersonListAdapter
     *
     * @param context
     * @param resource
     * @param objects
     */
    public WishlistAdapter(Context context, int resource, ArrayList<WishlishObj> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the wishlist information
        String description = getItem(position).getDescription();
        double percent = getItem(position).getPercent();

        //Create the wishlist object with the information
        WishlishObj myObj = new WishlishObj(description, percent);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView descriptionTV = convertView.findViewById(R.id.descriptionTV);
        ProgressBar percentTV = convertView.findViewById(R.id.percentTV);
        TextView showPercentTV = convertView.findViewById(R.id.showPercentTV);

        descriptionTV.setText("  "+description);
        Log.d(TAG, "PERCENT --->"+percent);
        showPercentTV.setText((int)percent + "%");
        //Log.d(TAG, "PERCENT AFTER INT --->"+(int) Integer.parseInt(percent));

        percentTV.setProgress((int)percent);

        return convertView;
    }
}
