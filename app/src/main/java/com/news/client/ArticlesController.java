package com.news.client;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.news.api.Article;
import com.news.api.ArticleResponse;
import com.news.api.DataProxy;
import com.news.util.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticlesController extends ListActivity {

    private List<Article> articleList = null;
    private NewsAdaptor articleAdapter = null;
    public Article article = null;
    private String sourceId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_article);
        articleList = new ArrayList<Article>();

        //extracting parameters
        Intent i = getIntent();
        Bundle b = i.getExtras();
        sourceId = b.getString("SOURCE_ID");

        new ArticleLoader().execute();
    }


    @Override
    protected void onListItemClick(ListView list, View view, int pos, long id) {
        super.onListItemClick(list, view, pos, id);

        Article data = articleList.get(pos);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getUrl()));

        startActivity(intent);
    }

    private class ArticleLoader extends AsyncTask<Void, Void, Void> {

        private CustomProgressDialog progress = null;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                DataProxy proxy = new DataProxy();
                ArticleResponse result = proxy.loadArticles(sourceId, "");

                //TODO  result validation

                articleList = result.getArticles();

            } catch (Exception ex) {
                //ex.printStackTrace();
                articleList = new ArrayList<>();
            }

            articleAdapter = new NewsAdaptor(ArticlesController.this, R.layout.article_item, articleList);
            return null;
        }

        @Override
        protected void onPreExecute() {
            progress = new CustomProgressDialog(ArticlesController.this);
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            setListAdapter(articleAdapter);

            progress.dismiss();

            super.onPostExecute(result);
        }
    }

    private class NewsAdaptor extends ArrayAdapter<Article> {

        private List<Article> listitem = null;

        public NewsAdaptor(Context cxt, int txtview, List<Article> list) {
            super(cxt, txtview, list);
            this.listitem = list;
        }

        public View getView(int inpos, View inview, ViewGroup ingroup) {
            View view = inview;
            article = listitem.get(inpos);
            if (view == null) {
                LayoutInflater layinf = (LayoutInflater) ArticlesController.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = layinf.inflate(R.layout.article_item, null);
            }

            if (article != null) {

                ImageView image = (ImageView) view.findViewById(R.id.imgArticle);

                Picasso.with(getBaseContext()).load(article.getUrlToImage()).into(image);

                TextView title = (TextView) view.findViewById(R.id.txtTitle);
                TextView author = (TextView) view.findViewById(R.id.txtAuthor);
                TextView description = (TextView) view.findViewById(R.id.txtDescription);
                TextView date = (TextView) view.findViewById(R.id.txtDate);
                title.setText(article.getTitle());
                author.setText("Author: " + article.getAuthor());
                description.setText(article.getDescription());

                String strDate = article.getPublishedAt();

                DateUtil.parseDateFromString(strDate);

                date.setText(article.getPublishedAt()); //TODO
            }

            return view;
        }
    }
}