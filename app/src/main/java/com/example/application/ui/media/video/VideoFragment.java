package com.example.application.ui.media.video;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.application.R;


public class VideoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        VideoView video1VV = view.findViewById(R.id.video1_vv);
        video1VV.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video1));
        MediaController mediaController1 = new MediaController(getActivity());
        video1VV.setMediaController(mediaController1);
        video1VV.seekTo(2000);


        VideoView video2VV = view.findViewById(R.id.video2_vv);
        video2VV.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video2));
        MediaController mediaController2 = new MediaController(getActivity());
        video2VV.setMediaController(mediaController2);
        video2VV.seekTo(2000);


        return view;
    }
}