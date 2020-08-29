package com.project.newzyfi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.newzyfi.R;
import com.project.newzyfi.fragment.SavedFragment;
import com.project.newzyfi.fragment.SourceFragment;
import com.project.newzyfi.model.SavedNewsModel;
import com.project.newzyfi.response.TrendingResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    ArrayList<SavedNewsModel> arrayList;
    SavedFragment context;
    OnCardClickListener onCardClickListener;


    public SavedAdapter(ArrayList<SavedNewsModel> arrayList, SavedFragment context,OnCardClickListener onCardClickListener){
        this.arrayList = arrayList;
        this.context = context;
        this.onCardClickListener = onCardClickListener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_list_row,parent,false);
        return new ViewHolder(view,onCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tvTitle.setText(arrayList.get(position).getTitle());
        String publishedtemp[] = arrayList.get(position).getPublished().split("T");
        String published = publishedtemp[0];
        holder.tvPublished.setText(published);

        String image = arrayList.get(position).getUrl_image();

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteCard(arrayList.get(position).getId());
            }
        });


        Picasso.get().load(image).fit().centerCrop().into(holder.ivTrending);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivTrending;
        TextView tvTitle,tvPublished,tvDelete;
        OnCardClickListener onCardClickListener;

        public ViewHolder(View view,OnCardClickListener onCardClickListener){
            super(view);

            ivTrending = view.findViewById(R.id.iv_trending_saved);
            tvTitle = view.findViewById(R.id.tv_title_saved);
            tvPublished = view.findViewById(R.id.tv_time_published_saved);
            tvDelete = view.findViewById(R.id.tv_delete);
            this.onCardClickListener = onCardClickListener;
            view.setOnClickListener(this);
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
