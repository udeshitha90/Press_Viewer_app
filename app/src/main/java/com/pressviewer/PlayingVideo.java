package com.pressviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

public class PlayingVideo extends Activity {
	Button pauseAndResume,playlist,upbutton,downbutton;
	ProgressBar videoProgress;
	ProgressBarUpdater pbarUpdater;
	public boolean playFlag;
	VideoView videoView;
	int vDuration;
    private AudioManager audio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.player);
			videoView=(VideoView)findViewById(R.id.myVideo);
			upbutton=(Button)findViewById(R.id.upbtn);
            downbutton=(Button)findViewById(R.id.downbtn);
			videoProgress=(ProgressBar)findViewById(R.id.videoProgress);
			pauseAndResume=(Button)findViewById(R.id.btnPauseResume);
			pauseAndResume.setOnClickListener(new PauseAndResumeListener());
			playlist=(Button)findViewById(R.id.btnPlaylist);
            audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            Bundle gotBasket=getIntent().getExtras();
        	playlist.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Log.i("mvp", "returning to playlist...");
					setResult(0);
					finish();
				}
				
				
			});
			Intent intent=getIntent();
			String uri=intent.getStringExtra("uri");
            uri=gotBasket.getString("key");
			//vDuration=intent.getIntExtra("duration",1000);
			//String title=intent.getStringExtra("title");
			//playing.setText("Currently Playing : "+title);
			Log.i("mvp", "Player Activity Started, obtaining media player...");
			playVideo(uri);


        upbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
               // return true;
            }
        });
        downbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
            }
        });
			
	}

	

	


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		playFlag=false;
	}






	private class PauseAndResumeListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			if (pauseAndResume.getText().equals("Pause"))
			{
				
				videoView.pause();
				pauseAndResume.setText("Resume");
				Log.i("mvp", "paused.");
			}
			else
			{
				
				videoView.start();
				pauseAndResume.setText("Pause");
				Log.i("mvp", "resumed.");
				
			}
		}
		
		
	}
	
	private class ProgressBarUpdater extends Thread
	{
	public ProgressBarUpdater()
	{
		playFlag=true;
		start();
		Log.i("mvp", "Max Duration set to " + vDuration);
		videoProgress.setMax(vDuration);
		Log.i("mvp", "Starting progress bar updater thread...");
		
	}
	public void run()
	{
		
		while(playFlag)
		{
		Log.i("mvp", "current position is " + videoView.getCurrentPosition());
		videoProgress.setProgress(videoView.getCurrentPosition());
		try{
			Thread.sleep(1000);
		}catch(Exception e){
			
			Log.i("mvp", "progress bar updater thread interrupted.");
		}
		}
	}
	}
	
	private void playVideo(String songUri) {
		Uri uri= Uri.parse(songUri);
		
		if (videoView.isPlaying() )
			{
			videoView.stopPlayback();
			}
        MediaController m=new MediaController(this);
        m.setAnchorView(videoView);
        videoView.setMediaController(m);
		videoView.setVideoURI(uri);
		videoView.start();
		pbarUpdater=new ProgressBarUpdater();
		videoView.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				playFlag=false;
				Toast.makeText(getApplicationContext(), "Video Completed.", Toast.LENGTH_LONG).show();
			}
		});
		
		Log.i("mvp", "player started...");
		}

	
	
}
