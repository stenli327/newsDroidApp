package com.news.client;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.news.api.DataProxy;
import com.news.api.ECountry;
import com.news.api.ELanguage;
import com.news.api.ESourceCategory;
import com.news.api.Source;
import com.news.api.SourceResponse;

import java.util.ArrayList;
import java.util.List;

public class CategoryController extends ListActivity {

    private List<Source> newslist = null;
    private CategoryAdaptor newsadaptor = null;
    public Source itemdata = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_category);

        new SourceLoader().execute();
    }

    @Override
    protected void onListItemClick(ListView list, View view, int pos, long id) {

        super.onListItemClick(list, view, pos, id);

        Source data = newslist.get(pos);

        Intent intent = new Intent(getBaseContext(), ArticlesController.class);
        intent.putExtra("SOURCE_ID", data.getId());
        startActivity(intent);
    }

    private class SourceLoader extends AsyncTask<Void, Void, Void> {

        private CustomProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                DataProxy proxy = new DataProxy();
                SourceResponse result = proxy.loadSources(ESourceCategory.GENERAL, ELanguage.ENGLISH, ECountry.USA);

                //TODO  result validation

                newslist = result.getSources();

            } catch (Exception ex) {

                //TODO logo errors ...
                //ex.printStackTrace();
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

            setListAdapter(newsadaptor);

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