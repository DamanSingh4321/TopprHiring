package com.singh.daman.topprhiring;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by daman on 24/9/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    ArrayList<String> id, name, image, category;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView txtname, txtcategory;
        public ImageView imgevent;

        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            txtname = (TextView) v.findViewById(R.id.event_name);
            txtcategory = (TextView) v.findViewById(R.id.event_category);
            imgevent = (ImageView) v.findViewById(R.id.event_image);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventsAdapter(Context context, ArrayList<String> id, ArrayList<String> name,
                         ArrayList<String> image, ArrayList<String> category) {

        this.mContext = context;
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
    }

    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        MyViewHolder holder;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_events, parent, false);
        holder = new MyViewHolder(v);
        holder.mCardView.setTag(holder);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            holder.txtname.setText(name.get(position));
            holder.txtcategory.setText(category.get(position));
            String imagestr = image.get(position);
            imagestr = imagestr.replace("\\","");
            Picasso.with(mContext).load(imagestr).fit().into(holder.imgevent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(200);
        view.startAnimation(anim);
    }
}