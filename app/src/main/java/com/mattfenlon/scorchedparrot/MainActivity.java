package com.mattfenlon.scorchedparrot;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }

        // Button stuff
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //sendIntentToBuffer();
            }
        });

    }

    private void handleSendText(Intent intent){

        TextView tv = (TextView) findViewById(R.id.textView);

        Bundle bundle = intent.getExtras();
        tv.setText("Number of keys in the bundle: " + Integer.toString(bundle.size()) + "\n");

        for(String keyName : bundle.keySet()){

            tv.setText(tv.getText() + "\n" + "** " + keyName + " **");

            Object extraObj = bundle.get(keyName);
            if (extraObj instanceof Long){
                tv.setText(tv.getText() + "\n["+extraObj.getClass().toString()+"] :\n " + Long.toString(bundle.getLong(keyName)) + "\n\n");
            } else if (extraObj instanceof String) {
                tv.setText(tv.getText() + "\n["+extraObj.getClass().toString()+"] :\n " + bundle.getString(keyName) + "\n\n");
            }

        }

        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {

            StringBuilder sb = new StringBuilder();

            // Make a string in the format that Buffer can swallow.
            sb.append("Check out @");
            sb.append(sharedText.split("/")[3]);    // Get the Twitter username from the URL.
            sb.append("'s Tweet: ");
            sb.append(sharedText);  // Include the URL.
            sb.append("?s=09"); // Add this to the end. Because... ?!

            // Punt it over.
            sendIntentToBuffer(sb.toString());

        }

    }

    private void sendIntentToBuffer(String s){

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);
        shareIntent.setType("text/plain");

        startActivity(shareIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
