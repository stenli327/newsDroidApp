package com.news.client;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;

public class CustomProgressDialog  extends AlertDialog{

    public  CustomProgressDialog(Context context) {
        super(context);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
    }

    @Override
    public void show() {
        super.show();

        setContentView(R.layout.dialog_progress);
    }
}
