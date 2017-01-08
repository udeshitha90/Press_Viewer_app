package com.pressviewer;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ProgressBar pgr;
	private static  int progress;
	private Handler h=new Handler();
	private int progressStatus=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        TextView bustxt=(TextView)findViewById(R.id.textView1);
        Typeface face=Typeface.createFromAsset(getAssets(),"KaushanScript-Regular.otf");
        bustxt.setTypeface(face);
		progress=0;
		pgr=(ProgressBar)findViewById(R.id.progressBar1);
		pgr.setMax(60);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (progressStatus < 60){
					progressStatus=doSomeWork();
					
					h.post(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pgr.setProgress(progressStatus);
						}
					});
				}
				h.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pgr.setVisibility(4);
						if(progress==pgr.getMax()){
							Intent in=new Intent(getApplicationContext(),Home.class);
							startActivity(in);
							
							
						}
					
		        			finish();
		        		
					}
				});
				
			}
			private int doSomeWork(){
				try {
					Thread.sleep(50);
					
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
					
				}
				return ++progress;
				
			}
			
		}).start();
		
		
	/*	pgr=(ProgressBar)findViewById(R.id.progressBar1);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<5;i++){
					progress =+ 20;
					h.post(new Runnable() {
						 
						@Override
						public void run() {
							// TODO Auto-generated method stub
							pgr.setProgress(progress);
							
							if(progress==pgr.getMax()){
								pgr.setVisibility(4);
							//	Intent in=new Intent(getApplicationContext(),Home.class);
								//startActivity(in);
								
							}
						}
					});
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO: handle exception
					}
					
				}
			}
		});
		*/
		
		/*
		Thread log=new Thread(){
	        	
	        	public void run(){
	        		try {
						
	        			int log=0;
	        			while(log<5000){ //5sec
	        				sleep(100);//0.1_sec
	        				log=log+100;
	        			}
	        			startActivity(new Intent("com.pressviewer.HOME"));
	        			
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
	        		finally{
	        			finish();
	        		}
	        	}
	        };
	        log.start();
		*/

	}
}
