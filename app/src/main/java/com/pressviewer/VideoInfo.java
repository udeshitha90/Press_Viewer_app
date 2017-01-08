package com.pressviewer;



public class VideoInfo {
private int id;
String title,displayDuration;
int duration;
public int getId() {
	return id;
}

public String getDisplayDuration() {
	return displayDuration;
}


public VideoInfo(int id, String title, int duration) {
	super();
	this.id=id;
	this.title = title;
	this.duration=duration;
	this.displayDuration = durationInSeconds(duration);
	
}

public String getTitle() {
	return title;
}



public int getDuration() {
	return duration;
}

public String durationInSeconds(int dur)
{
	int min,sec;
	int time=dur/1000;
	min=time/60;
	sec=time%60;
	String minPart= Integer.toString(min);
	if (min<10)
	minPart="0"+ Integer.toString(min);
	String secPart= Integer.toString(sec);
	if (sec<10)
		secPart="0"+ Integer.toString(sec);
	return minPart+":"+secPart;
}
}
