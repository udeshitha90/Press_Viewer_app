package com.pressviewer;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CHATHUSANKA on 8/19/2015.
 */
public class AllPoliticalActivity extends ListActivity {
    private ProgressDialog pDialog;
    JSONParser jParser = new JSONParser();
    ArrayList<HashMap<String, String>> LinkList;

    private static String url_all_link = "http://pressviewersl.tk/get_all_link_Political.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_AllLINKS = "Alllinks";
    private static final String TAG_LINK = "link";
    private static final String TAG_NAME = "news";

    JSONArray newslink = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_political);
        TextView tottxt=(TextView)findViewById(R.id.txtpolitical);
        Typeface face=Typeface.createFromAsset(getAssets(),"KaushanScript-Regular.otf");
        tottxt.setTypeface(face);
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
        }
        if(connected==true) {
        LinkList = new ArrayList<HashMap<String, String>>();
        new LoadAlllinks().execute();
        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slink = ((TextView) view.findViewById(R.id.pid)).getText().toString();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, Slink, duration);
                toast.show();
                Bundle basket=new Bundle();
                basket.putString("key",Slink);
                Intent a=new Intent(AllPoliticalActivity.this,Youtube.class);
                a.putExtras(basket);
                startActivity(a);

            }
        });
        } else{
            Context context = getApplicationContext();
            CharSequence text = "Can't find network";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
    class LoadAlllinks extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllPoliticalActivity.this);
            pDialog.setMessage("Loading politicals. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_link, "GET", params);

            if (json==null) {
                Log.d("ALERT", "json is null");
            }
            // Check your log cat for JSON reponse
            Log.d("All links: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of links
                    newslink = json.getJSONArray(TAG_AllLINKS);

                    // looping through All link
                    for (int i = 0; i < newslink.length(); i++) {
                        JSONObject c = newslink.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_LINK);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap link => news
                        map.put(TAG_LINK, id);
                        map.put(TAG_NAME, name);

                        // adding HashList to ArrayList
                        LinkList.add(map);
                    }
                } else {

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllPoliticalActivity.this, LinkList, R.layout.list_item, new String[] { TAG_LINK,TAG_NAME},new int[] { R.id.pid, R.id.name });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
        }
    }

}
