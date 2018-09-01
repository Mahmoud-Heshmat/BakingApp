package com.wasltec.ahmadalghamdi.bakingapp;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.Placeholder;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.wasltec.ahmadalghamdi.bakingapp.Activities.MainActivity;
import com.wasltec.ahmadalghamdi.bakingapp.adapters.StepsAdapter;
import com.wasltec.ahmadalghamdi.bakingapp.models.Steps;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends android.support.v4.app.Fragment
        implements View.OnClickListener, ExoPlayer.EventListener {

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.exoplayer) SimpleExoPlayerView mPlayerView;
    @BindView(R.id.recipeStepInstruction) TextView recipeStepInstruction;
    @BindView(R.id.nextStepBtn) Button nextStepBtn;
    @BindView(R.id.previousStepBtn) Button previousStepBtn;
    @BindView(R.id.placeholderError) ImageView placeholderImage;

    Steps step;
    int position;

    ArrayList<Steps> stepsArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = 0;

        step = (Steps) getArguments().getSerializable("step");
        position = getArguments().getInt("index");
        Log.d("responsePos", position + "  * " + step.getmDescription());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, rootView);

        stepsArrayList = MainActivity.list.get(position).getmSteps();

        nextStepBtn.setOnClickListener(this);
        previousStepBtn.setOnClickListener(this);

        Log.d("responseThum", "  " + step.getmThumbnailURL());

        prepareStep(step);

        return rootView;
    }

    private void prepareStep(Steps step){
        if (step != null) {
            Log.d("responseSteps", "   " + step.getmDescription());
            Log.d("responseSteps", "   " + step.getmVideoURL());
            if (step.getmVideoURL() != null && !step.getmVideoURL().isEmpty()) {
                mPlayerView.setVisibility(View.VISIBLE);
                placeholderImage.setVisibility(View.GONE);
                initializePlayer(Uri.parse(step.getmVideoURL()));

            }else if (step.getmThumbnailURL() != null && !step.getmThumbnailURL().isEmpty()){
                placeholderImage.setVisibility(View.VISIBLE);
                mPlayerView.setVisibility(View.GONE);
                mExoPlayer.stop();

                String path = step.getmThumbnailURL();
                Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,
                        MediaStore.Images.Thumbnails.MINI_KIND);

                placeholderImage.setImageBitmap(thumb);
                Log.d("responseImage", "response"   + " " + step.getmThumbnailURL());

            } else {
                mPlayerView.setVisibility(View.GONE);
                mExoPlayer.stop();
            }

            if (step.getmDescription() != null && !step.getmDescription().isEmpty()) {
                recipeStepInstruction.setText(step.getmDescription());
            }else{
                recipeStepInstruction.setVisibility(View.GONE);
            }
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            playVideo(mediaUri);
        }else{
            // Prepare the MediaSource.
            playVideo(mediaUri);
        }
    }

    private void playVideo(Uri mediaUri){
        String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextStepBtn:
                position += 1;
                Log.d("responseSS", position + " bb " + stepsArrayList.size());
                if (position <= stepsArrayList.size()){
                    prepareStep(stepsArrayList.get(position));
                }

                break;
            case R.id.previousStepBtn:
                position -= 1;
                Log.d("responseSS", position + " bb " + stepsArrayList.size());
                if (position <= stepsArrayList.size()){
                    prepareStep(stepsArrayList.get(position));
                }
                break;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

}