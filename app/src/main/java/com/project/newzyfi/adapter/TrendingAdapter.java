package com.project.newzyfi.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newzyfi.R;
import com.project.newzyfi.fragment.TrendingFragment;
import com.project.newzyfi.response.TrendingResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

    ArrayList<TrendingResponse.articles> arrayList;
    TrendingFragment context;
    OnCardTrendingClickListener onCardTrendingClickListener;

        public TrendingAdapter(ArrayList<TrendingResponse.articles> arrayList,TrendingFragment context,OnCardTrendingClickListener onCardTrendingClickListener){

            this.arrayList = arrayList;
            this.context = context;
            this.onCardTrendingClickListener = onCardTrendingClickListener;

        }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_list_row,parent,false);

        return new ViewHolder(view,onCardTrendingClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.tvTitle.setText(arrayList.get(position).getTitle());
            String publishedtemp[] = arrayList.get(position).getPublishedAt().split("T");
            String published = publishedtemp[0];
            holder.tvPublished.setText(published);

            String image = arrayList.get(position).getUrlToImage();


            Picasso.get().load(image).fit().centerCrop().into(holder.ivTrending);



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView ivTrending;
            TextView tvTitle,tvPublished;
            OnCardTrendingClickListener onCardTrendingClickListener;

        public ViewHolder(View view,OnCardTrendingClickListener onCardTrendingClickListener){
            super(view);

            ivTrending = view.findViewById(R.id.iv_trending);
            tvTitle = view.findViewById(R.id.tv_title);
            tvPublished = view.findViewById(R.id.tv_time_published);
            this.onCardTrendingClickListener = onCardTrendingClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardTrendingClickListener.onItemTrendingClick(getAdapterPosition());

        }
    }

    public interface OnCardTrendingClickListener{
        void onItemTrendingClick(int position);

    }
}
