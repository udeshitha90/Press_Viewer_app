package com.pressviewer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class Home extends Activity{
	Button Aboutus,topstories,Enter,scan;
    Thread t;
	static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
        scan=(Button)findViewById(R.id.btnscan);
        Aboutus=(Button)findViewById(R.id.btaboutus);
        topstories=(Button)findViewById(R.id.btntopstries);
        Enter=(Button)findViewById(R.id.btnEnt);
        final Animation animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        Typeface face=Typeface.createFromAsset(getAssets(),"KaushanScript-Regular.otf");
        Aboutus.setTypeface(face);
        scan.setTypeface(face);
        topstories.setTypeface(face);
        Enter.setTypeface(face);

        Aboutus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                v.startAnimation(animTranslate);
                try {
                    t.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent abo=new Intent("com.pressviewer.ABOUTUS");
                startActivity(abo);
            }
        });
        topstories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                try {
                    t.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), AllTopSActivity.class);
                startActivity(i);
            }
        });
        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animTranslate);
                try {
                    t.sleep(120);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i=new Intent("com.pressviewer.ENTER");
                startActivity(i);
            }
        });
	}
	public void scanBar(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	public void scanQR(View v) {
		try {
			Intent intent = new Intent(ACTION_SCAN);
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		} catch (ActivityNotFoundException anfe) {
			showDialog(this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
		}
	}

	private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
		AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
		downloadDialog.setTitle(title);
		downloadDialog.setMessage(message);
		downloadDialog.setPositiveButton(buttonYes,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
				Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				try {
					act.startActivity(intent);
				} catch (ActivityNotFoundException anfe) {

				}
			}
		});
		downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int i) {
			}
		});
		return downloadDialog.show();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");// RESULT 
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

				Toast toast = Toast.makeText(this,contents , Toast.LENGTH_LONG);
				toast.show();

                try {
                    Bundle basket=new Bundle();
                    basket.putString("key",contents);
                    Intent a=new Intent(Home.this,Related.class);
                    a.putExtras(basket);
                    startActivity(a);

                }catch(Exception e){
                    Intent it=new Intent(android.content.Intent.ACTION_VIEW);
                    Uri data=Uri.parse(contents);
                    it.setDataAndType(data,"video/*");
                    startActivity(it);
                }
			}
		}
	}

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
