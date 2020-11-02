package com.example.retrofittutorial;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private List<String> mCustomerNames;
    private LayoutInflater mInflater;
    private List<Uri> mProfilePicture;
    private ItemClickListener mClickListener;

    CardViewAdapter(Context context, List<String> customerNames, List<Uri> profilePicture) {
        this.mInflater = LayoutInflater.from(context);
        this.mCustomerNames = customerNames;
        this.mProfilePicture = profilePicture;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Picasso.get().load(mProfilePicture.get(position)).into(holder.customerIcon);

        Picasso.get()
                .load(mProfilePicture.get(position))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.customerIcon, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(mProfilePicture.get(position))
                                .error(R.drawable.ic_no_image)
                                .into(holder.customerIcon, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        Log.v("Picasso","Could not fetch image");
                                    }
                                });
                    }
                });

        holder.customerName.setText(mCustomerNames.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mCustomerNames.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView customerName;
        ImageView customerIcon;

        ViewHolder(View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            customerIcon = itemView.findViewById(R.id.customerIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    String getItem(int id) {
        return mCustomerNames.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}