package com.training.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.training.android.Data.NewsFeed;
import com.training.android.newsfeed.R;

import java.util.List;

public class Adapter extends ArrayAdapter<NewsFeed> {

    public Adapter(Context context, List<NewsFeed> objects) {
        super(context, 0, objects);
    }

    public Adapter(Context context, int resource, List<NewsFeed> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.feed_layout, parent, false);
        }

        NewsFeed newsFeed = getItem(position);


        TextView mtvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
        TextView mtvDesc = (TextView) view.findViewById(R.id.tvDescription);
        TextView mtvUrl = (TextView) view.findViewById(R.id.tvUrl);
        TextView mtvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView mtvPublished = (TextView) view.findViewById(R.id.tvPublished);
        ImageView mivImage = (ImageView) view.findViewById(R.id.ivImage);

        Picasso.with(getContext())
                .load(newsFeed.getUrlToImage())
                .into(mivImage);

        mtvAuthor.setText(newsFeed.getAuthor());
        mtvDesc.setText(newsFeed.getDescription());
        mtvUrl.setText(newsFeed.getUrl());
        mtvTitle.setText(newsFeed.getTitle());
        mtvPublished.setText(newsFeed.getPublishedAt().replace("T","  "));


        return view;
    }


}
