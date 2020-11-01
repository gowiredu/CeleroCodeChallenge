package com.example.retrofittutorial;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CardViewAdapterDetailsActivity extends RecyclerView.Adapter<CardViewAdapterDetailsActivity.ViewHolder> {

    private List<String> mCustomerDetails;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    CardViewAdapterDetailsActivity(Context context, List<String> customerDetails) {
        this.mInflater = LayoutInflater.from(context);
        this.mCustomerDetails = customerDetails;
    }

    // inflates the row layout from xml when needed
    @Override
    public CardViewAdapterDetailsActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_view, parent, false);
        return new CardViewAdapterDetailsActivity.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.customerName.setText(mCustomerDetails.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mCustomerDetails.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName;
        ImageView customerIcon;

        ViewHolder(View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            customerIcon = itemView.findViewById(R.id.customerIcon);
            //itemView.setOnClickListener(this);
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mCustomerDetails.get(id);
    }
}
