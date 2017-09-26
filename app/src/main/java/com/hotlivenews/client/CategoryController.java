package com.hotlivenews.client;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hotlivenews.api.DataProxy;
import com.hotlivenews.api.filter.ECountry;
import com.hotlivenews.api.filter.ELanguage;
import com.hotlivenews.api.filter.ECategory;
import com.hotlivenews.api.Source;
import com.hotlivenews.api.filter.FilterType;
import com.hotlivenews.api.model.SourceResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryController extends AppCompatActivity {

    private static final String TAG = "CategoryController";

    private List<Source> newslist = null;
    private CategoryAdaptor newsadaptor = null;
    public Source itemdata = null;

    private SourceLoader loader;

    private ListView lv;

    //filter
    private ECategory filterCategory = ECategory.ALL;
    private ELanguage filterLanguate = ELanguage.ALL;
    private ECountry filterCountry = ECountry.ALL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_category);

//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar != null) actionBar.hide();

        lv = (ListView) findViewById(R.id.list);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Source data = newslist.get(position);

                Intent intent = new Intent(getBaseContext(), ArticlesController.class);
                intent.putExtra("SOURCE_ID", data.getId());
                startActivity(intent);
            }
        });

//        View guillotineMenu = LayoutInflater.from(getBaseContext()).inflate(R.layout.guillotine, null);
//        lv.addView(guillotineMenu);
//        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), lv)
//                .setActionBarViewForAnimation(toolbar)
//                .build();

        new SourceLoader().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void startAlertDialog(String title, String[] values, final FilterType type){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title)
        .setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO
                Log.d(TAG, String.format("Select category element: %s", which));

                switch (type) {
                    case CATEGORY:
                        filterCategory = ECategory.values()[which];
                        reloadData();
                        return;
                    case LANGUAGE:
                        filterLanguate = ELanguage.values()[which];
                        reloadData();
                        return;
                    case COUNTRY:
                        filterCountry = ECountry.values()[which];
                        reloadData();
                        return;
                    default:
                        return;
                }
            }
        })
        // Set the action buttons
//        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//            }
//        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // Set other dialog properties

        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_category:
                startAlertDialog("Pick a Category", ECategory.getDescriptionArray(), FilterType.CATEGORY);
                return true;
            case R.id.action_language:
                startAlertDialog("Pick a Language", ELanguage.getDescriptionArray(), FilterType.LANGUAGE);
                return true;
            case R.id.action_country:
                startAlertDialog("Pick a Country", ECountry.getDescriptionArray(), FilterType.COUNTRY);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void reloadData(){
        new SourceLoader().execute();
    }

    private class SourceLoader extends AsyncTask<Void, Void, Void> {

        private CustomProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                DataProxy proxy = new DataProxy();
                SourceResponse result = proxy.loadSources(filterCategory, filterLanguate, filterCountry);

                //TODO  validate !

                newslist = result.getSources();

            } catch (Exception ex) {

                Log.e("", String.format("CategoryController: %s", ex.getMessage()));
                newslist = new ArrayList<>();
            }

            newsadaptor = new CategoryAdaptor(CategoryController.this, R.layout.article_item, newslist);
            return null;
        }

        @Override
        protected void onPreExecute() {
            progress = new CustomProgressDialog(CategoryController.this);
            progress.show();

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {

            lv.setAdapter(newsadaptor);
            //setListAdapter(newsadaptor);

            progress.dismiss();

            super.onPostExecute(result);
        }
    }

    private class CategoryAdaptor extends ArrayAdapter<Source> {

        private List<Source> listitem = null;

        public CategoryAdaptor(Context cxt, int txtview, List<Source> list) {
            super(cxt, txtview, list);
            this.listitem = list;
        }

        public View getView(int inpos, View inview, ViewGroup ingroup) {

            View view = inview;
            itemdata = listitem.get(inpos);
            if (view == null) {
                LayoutInflater layinf = (LayoutInflater) CategoryController.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layinf.inflate(R.layout.category_item, null);
            }

            if (itemdata != null) {
                TextView title = (TextView) view.findViewById(R.id.txtTitle);
                TextView description = (TextView) view.findViewById(R.id.txtDescription);
                TextView url = (TextView) view.findViewById(R.id.txtDate);
                title.setText(itemdata.getName());
                description.setText(itemdata.getDescription());
                url.setText(itemdata.getUrl());
            }

            return view;
        }
    }
}