package com.example.application.ui.media.photo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.application.MessageAdapter;
import com.example.application.PhotoAdapter;
import com.example.application.R;
import com.example.application.bean.Photo;
import com.example.application.ui.media.video.VideoFragment;

import java.util.ArrayList;
import java.util.List;


public class PhotoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        List<Photo> photoList = new ArrayList<>();
        photoList.add(new Photo(R.drawable.photo1, "Photo 1"));
        photoList.add(new Photo(R.drawable.photo2, "Photo 2"));
        photoList.add(new Photo(R.drawable.photo3, "Photo 3"));
        photoList.add(new Photo(R.drawable.photo4, "Photo 4"));
        photoList.add(new Photo(R.drawable.photo5, "Photo 5"));
        photoList.add(new Photo(R.drawable.photo6, "Photo 6"));
        photoList.add(new Photo(R.drawable.photo7, "Photo 7"));
        photoList.add(new Photo(R.drawable.photo8, "Photo 8"));
        photoList.add(new Photo(R.drawable.photo9, "Photo 9"));
        photoList.add(new Photo(R.drawable.photo10, "Photo 10"));
        photoList.add(new Photo(R.drawable.photo11, "Photo 11"));
        photoList.add(new Photo(R.drawable.photo12, "Photo 12"));
        photoList.add(new Photo(R.drawable.photo13, "Photo 13"));
        photoList.add(new Photo(R.drawable.photo14, "Photo 14"));
        photoList.add(new Photo(R.drawable.photo15, "Photo 15"));
        photoList.add(new Photo(R.drawable.photo16, "Photo 16"));
        photoList.add(new Photo(R.drawable.photo17, "Photo 17"));
        photoList.add(new Photo(R.drawable.photo18, "Photo 18"));
        photoList.add(new Photo(R.drawable.photo19, "Photo 19"));
        photoList.add(new Photo(R.drawable.photo20, "Photo 20"));
        photoList.add(new Photo(R.drawable.photo21, "Photo 21"));
        photoList.add(new Photo(R.drawable.photo22, "Photo 22"));
        photoList.add(new Photo(R.drawable.photo23, "Photo 23"));
        photoList.add(new Photo(R.drawable.photo24, "Photo 24"));
        photoList.add(new Photo(R.drawable.photo25, "Photo 25"));

        View v = inflater.inflate(R.layout.fragment_photo, container, false);

        RecyclerView listPhotoRV = v.findViewById(R.id.photo_rv);
        PhotoAdapter adapter = new PhotoAdapter(photoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        listPhotoRV.setLayoutManager(mLayoutManager);
        listPhotoRV.setItemAnimator(new DefaultItemAnimator());
        listPhotoRV.setAdapter(adapter);

        return v;
    }
}