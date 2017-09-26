
package com.hotlivenews.client;

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
