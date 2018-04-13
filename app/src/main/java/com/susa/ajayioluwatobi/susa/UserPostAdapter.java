package com.susa.ajayioluwatobi.susa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ajayioluwatobi on 4/11/18.
 */

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.UserPostHolder> {

    ArrayList<UserPost> mUserPosts = new ArrayList<UserPost>();
   // UserPostAdapter custom= new UserPostAdapter(FeedActivity.this);




    public static class UserPostHolder extends RecyclerView.ViewHolder
    {
        public  TextView post_addy;
        public  TextView post_price;
        public  TextView post_location;
        public  TextView post_image;
        public  TextView post_contact;
        public  TextView post_details;
        public  TextView post_share;





        View mView;
        public UserPostHolder(View itemView)
        {
            super(itemView);
            mView= itemView;
        }




        public void setAddress(String addy){
            TextView post_addy= (TextView)mView. findViewById(R.id.post_address);
            post_addy.setText(addy);
        }

        public void setPrice(int price){
            TextView post_addy= (TextView)mView. findViewById(R.id.post_id);
            post_addy.setText(Integer.toString(price));
        }

        public void setImage(Context ctx, String image){
            ImageView post_image= (ImageView) mView. findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_image);
        }

        public void setLocation(String city){
            TextView post_city= (TextView)mView. findViewById(R.id.post_location);
            post_city.setText(city);
        }
    }

    public UserPostAdapter(ArrayList<UserPost> user) {
        mUserPosts=user;

    }



    @Override
    public void onBindViewHolder(UserPostHolder holder, int position) {

            holder.post_addy.setText((CharSequence) mUserPosts.get(position));
        holder.post_price.setText((CharSequence) mUserPosts.get(position));
        holder.post_location.setText((CharSequence) mUserPosts.get(position));
        holder.post_image.setText((CharSequence) mUserPosts.get(position));


    }

    @Override
    public int getItemCount() {

    return 1;
    }

    @Override
    public UserPostAdapter.UserPostHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        CardView v= (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,parent,false);
        TextView post_addy= (TextView)v.findViewById(R.id.post_address);
        TextView post_price= (TextView)v. findViewById(R.id.post_id);
        ImageView post_image= (ImageView)v. findViewById(R.id.post_image);
        TextView post_city= (TextView)v. findViewById(R.id.post_location);
        TextView post_contact= (TextView)v. findViewById(R.id.post_contact);
        TextView post_share= (TextView)v. findViewById(R.id.post_share);
        TextView post_details= (TextView)v. findViewById(R.id.post_details);
        UserPostHolder vh = new UserPostHolder(v);
        return vh;
    }

}
