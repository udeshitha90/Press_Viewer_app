package com.pressviewer;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CHATHUSANKA on 8/22/2015.
 */
public class Youtube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String vid;
    JSONParser jParser = new JSONParser();
    String raturl;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle gotBasket=getIntent().getExtras();
       raturl="http://pressviewersl.tk/rating.php?link="+gotBasket.getString("key")+"";

        new RateLink().execute();
        vid=extractYoutubeId(gotBasket.getString("key"));
        setContentView(R.layout.youtube);
        YouTubePlayerView youTubeView = (YouTubePlayerView)findViewById(R.id.myVideo);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) youTubePlayer.cueVideo(vid);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
    private String extractYoutubeId(String url) {

        String video_id = "";
        if (url != null && url.trim().length() > 0 && url.startsWith("http")) {

            String expression = "^.*((youtu.be \\/)|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = url;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
            }
        }

        return video_id;
    }

    class RateLink extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            JSONObject json = jParser.makeHttpRequest(raturl,"POST", params);


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            //pDialog.dismiss();
        }

    }

}
