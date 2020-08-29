package com.project.newzyfi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.project.newzyfi.R;
import com.project.newzyfi.fragment.SourceFragment;
import com.project.newzyfi.response.TrendingResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    ArrayList<TrendingResponse.articles> arrayList;
    SourceFragment context;
    OnCardClickListener onCardClickListener;
//    private AdView mBannerAd;
//    int AD_TYPE = 0;
//    int CONTENT_TYPE = 1;

        public SourceAdapter(ArrayList<TrendingResponse.articles> arrayList,SourceFragment context,OnCardClickListener onCardClickListener){
            this.arrayList = arrayList;
            this.context = context;
            this.onCardClickListener = onCardClickListener;
        }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            AdView adView;
//
//            if(viewType == AD_TYPE){
//                adView = new AdView(context.getActivity());
//                adView.setAdSize(AdSize.BANNER);
//                adView.setAdUnitId("a-app-pub-3940256099942544/6300978111");
//
//                float density = context.getResources().getDisplayMetrics().density;
//                int height = Math.round(AdSize.BANNER.getHeight() * density);
//                AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.FILL_PARENT, height);
//                adView.setLayoutParams(params);
//
//
//                AdRequest request = new AdRequest.Builder().build();
//                adView.loadAd(request);
//                return new ViewHolder(adView,onCardClickListener);
//
//            }else{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_list_row,parent,false);
                return new ViewHolder(view,onCardClickListener);
            //}


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

          //  if(position % 2 !=1 ){

                holder.tvTitle.setText(arrayList.get(position).getTitle());
                String publishedtemp[] = arrayList.get(position).getPublishedAt().split("T");
                String published = publishedtemp[0];
                holder.tvPublished.setText(published);

                String image = arrayList.get(position).getUrlToImage();


                Picasso.get().load(image).fit().centerCrop().into(holder.ivTrending);
         //   }



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivTrending;
        TextView tvTitle,tvPublished;
        OnCardClickListener onCardClickListener;


        public ViewHolder(View view,OnCardClickListener onCardClickListener){
            super(view);

          //  if (!(itemView instanceof AdView)) {
                ivTrending = view.findViewById(R.id.iv_trending);
                tvTitle = view.findViewById(R.id.tv_title);
                tvPublished = view.findViewById(R.id.tv_time_published);
                this.onCardClickListener = onCardClickListener;
                view.setOnClickListener(this);
          //  }



        }

        @Override
        public void onClick(View v) {
            onCardClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnCardClickListener{
            void onItemClick(int position);


    }
}
