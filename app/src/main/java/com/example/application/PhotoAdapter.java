package com.example.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.bean.Photo;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder>{

    private List<Photo> listPhotos;

    public PhotoAdapter(List<Photo> listPhotos) {
        this.listPhotos = listPhotos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemphotos, parent, false);

        return new PhotoAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Photo photo = listPhotos.get(position);
        holder.image.setImageResource(photo.getSrc());
        holder.description.setText(photo.getDescription());
    }

    @Override
    public int getItemCount() {
        return listPhotos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView description;
        private ImageView image;

        public MyViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.desc_photo_img);
            image = view.findViewById(R.id.photo_photo_img);

        }
    }
}
