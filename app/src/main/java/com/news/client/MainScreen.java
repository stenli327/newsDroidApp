
package com.news.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_screen);

        TextView t = (TextView) findViewById(R.id.Intro);
        t.setText("NEWS APP");

        TextView t1 = (TextView) findViewById(R.id.Intro1);
        t.setText("Get latest news update from various channels with news application. TopStories, Business, Sports ...");

        Button btnNews = (Button) findViewById(R.id.button1);
        btnNews.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CategoryController.class);
                startActivityForResult(myIntent, 0);
                MainScreen.this.finish();
            }
        });

    }
}
