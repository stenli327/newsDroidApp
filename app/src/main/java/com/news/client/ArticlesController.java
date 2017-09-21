package com.news.client;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticlesController extends ListActivity {

    private List<NewsItem> newslist = null;
    private NewsAdaptor newsadaptor = null;
    public NewsItem itemdata = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_menu);
        newslist = new ArrayList<NewsItem>();

        //extracting parameter passed from NewsMenu screen
        Intent i = getIntent();
        Bundle b = i.getExtras();
//        inputurl = b.getString("ARRIVING_FROM");

        new ExtractFeed().execute();
    }


    @Override
    protected void onListItemClick(ListView list, View view, int pos, long id) {
        super.onListItemClick(list, view, pos, id);

        NewsItem newsdata = newslist.get(pos);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsdata.link));

        startActivity(intent);
    }

    private class ExtractFeed extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            try {
               //TODO
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            newsadaptor = new NewsAdaptor(ArticlesController.this, R.layout.newstagsvw, newslist);
            return null;
        }

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(
                    ArticlesController.this, null, "24/7 updates for you...");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(newsadaptor);

            progress.dismiss();

            super.onPostExecute(result);
        }
    }

    private class NewsAdaptor extends ArrayAdapter<NewsItem> {
        private List<NewsItem> listitem = null;

        //constructor
        public NewsAdaptor(Context cxt, int txtview, List<NewsItem> list) {
            super(cxt, txtview, list);
            this.listitem = list;
        }

        public View getView(int inpos, View inview, ViewGroup ingroup) {
            View view = inview;
            itemdata = listitem.get(inpos);
            if (view == null) {
                LayoutInflater layinf = (LayoutInflater) ArticlesController.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layinf.inflate(R.layout.newstagsvw, null);
            }


            if (itemdata != null) {
                TextView title = (TextView) view.findViewById(R.id.txtTitle);
                TextView date = (TextView) view.findViewById(R.id.txtDate);
                title.setText(itemdata.title);
                date.setText("on " + itemdata.date);
            }

            return view;
        }
    }
}