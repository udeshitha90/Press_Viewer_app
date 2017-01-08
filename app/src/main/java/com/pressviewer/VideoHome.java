package com.pressviewer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VideoHome extends Activity {
	ListView videoList;
	ArrayList<VideoInfo> videoDetails;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist);
        videoList=(ListView)findViewById(R.id.videoList);
        TextView header=new TextView(this);
        header.setTextColor(Color.RED);
        header.setTextSize(36);
        header.setBackgroundColor(Color.BLACK);
        header.setText("\tTop Stories");
        videoList.addHeaderView(header);
       // Log.i("mvp", "Player App started, initializing video list...");
        initializeList();
        //Log.i("mvp", "List Initialized, select video to play...");
    }
    



@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode==1)
	{
		Log.i("mvp", "Terminating app...");
		Process.killProcess(Process.myPid());
		
		
	}
}



private void initializeList()
{
	 //
	//.i("mvp", "Fetching MediaStore contents... ");
	 ContentResolver resolver=getContentResolver();
	 String projection[]={MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION};
	 Cursor cursor= resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
	 /*Cursor cursor= resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
	 int recCount=cursor.getCount();
	 Log.i("mvp", "No. of Videos Found :" +recCount);
	 int fCount=cursor.getColumnCount();
	 Log.i("mvp", "No. of Columns Found :" +fCount);
	 for(int i=0;i<fCount;i++)
		 Log.i("mvp", "No. of Columns Found :" +cursor.getColumnName(i));
	 
	 while(cursor.moveToNext())
	 {
		StringBuffer b=new StringBuffer();
		 for(int i=0;i<fCount;i++)
		 {
			 
			 b.append(cursor.getString(i)).append("\t");
		 }
		 Log.i("mvp", b.toString());
	 }*/
	 videoDetails=new ArrayList<VideoInfo>();
	 while(cursor.moveToNext())
	 {
		int id=cursor.getInt(0);
		String title=cursor.getString(1);
		int dur=cursor.getInt(2);
		videoDetails.add(new VideoInfo(id,title,dur));
		
	 }
	 cursor.close();
	 Log.i("mvp", "Content Fetched, displaying on list... ");
	 ArrayAdapter<VideoInfo> adapter=new MyArrayAdapter  (getApplicationContext(),R.layout.videolist,videoDetails);
	 videoList.setAdapter(adapter);
     videoList.setOnItemClickListener(new OnItemClickListener() {

	@Override
	
		public void onItemClick(AdapterView<?> parent, View clickedView, int index, long id) {
			Log.i("mvp", "Video indexed " + index + " is selected for playing, creating uri...");
			VideoInfo info=videoDetails.get(index-1);
			String uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI+"/"+info.getId();
			Log.i("mvp", "starting activity to play video...");
			Intent intent=new Intent(getApplicationContext(),PlayingVideo.class);
			intent.putExtra("uri", uri);
			intent.putExtra("title", info.getTitle());
			intent.putExtra("duration",info.getDuration());
			startActivityForResult(intent,1);
		}
	
});
	 // videoList.setOnItemClickListener(new VideoSelectionListener());
	 Log.i("mvp", "Adapter set, Listener registered... ");
}
private class MyArrayAdapter extends ArrayAdapter<VideoInfo>
{
	
	int layout;
	
	public MyArrayAdapter(Context context, int layoutId,
			List<VideoInfo> objects) {
		super(context, layoutId, objects);
		layout=layoutId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout newView;
		VideoInfo detail= videoDetails.get(position);
		 Log.i("mvp", "Generating view for the list... ");
		if (convertView==null)
		{
			Log.i("mvp", "passed view is null, creating new view... ");
			newView=new RelativeLayout(getContext());
			LayoutInflater li=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
			li.inflate(layout, newView, true);
			Log.i("mvp", "new view created... ");
		}
		else
		{
			newView=(RelativeLayout)convertView;
			Log.i("mvp", "passed view isn't null... ");
			
		}
		Log.i("mvp", "setting values to list memebers...");
		TextView title=(TextView)newView.findViewById(R.id.title);
		Log.i("mvp", "setting value of title...");
		title.setText(detail.getTitle());
		Log.i("mvp", "value of title set...");
		TextView duration=(TextView)newView.findViewById(R.id.duration);
		duration.setText(detail.getDisplayDuration());
		Log.i("mvp", "value of duration set...");
		
		
		return newView;
	}
	
}
}