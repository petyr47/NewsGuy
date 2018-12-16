package com.example.android.newsguy;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsguy.data.NewsContract;
import com.squareup.picasso.Picasso;

/**
 * Created in com.example.android.newsguy by PETERR on 10/19/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final Context mContext;

    private Cursor mCursor;


    final private NewsAdapterOnClickHandler mClickHandler;

    public interface NewsAdapterOnClickHandler {
        void onClick(String url);
    }


    public NewsAdapter(@NonNull Context context, NewsAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        view.setFocusable(true);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        int idIndex = mCursor.getColumnIndex(NewsContract.NewsEntry._ID);


        int titleIndex = mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_TITLE);
        int DescIndex = mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DESCRIPTION);
        int UrlIndex = mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_URL);
        int imageUrlIndex= mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_IMAGE_URL);
        int dateIndex = mCursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_DATE);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String title= mCursor.getString(titleIndex);
        String desc = mCursor.getString(DescIndex);
        String imageUrl =mCursor.getString(imageUrlIndex);
        String url=mCursor.getString(UrlIndex);
        String date=mCursor.getString(dateIndex);

        if (desc.equals("null")){
            desc="Read more...";
        }



        //Set values
        holder.itemView.setTag(id);
        holder.title.setText(title);
        holder.desc.setText(desc);
        holder.date.setText(date);

        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.ic_photo_)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) {return 0;}
        return mCursor.getCount();
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }



    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

         ImageView image;
        TextView title;
        TextView desc;
        TextView date;



        NewsViewHolder(View view){
            super(view);

            image=view.findViewById(R.id.item_image);
            title=view.findViewById(R.id.title);
            desc=view.findViewById(R.id.item_desc);
            date=view.findViewById(R.id.date);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String url = mCursor.getString(3);
            mClickHandler.onClick(url);

        }
    }

}
