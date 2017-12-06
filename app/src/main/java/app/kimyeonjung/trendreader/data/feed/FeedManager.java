package app.kimyeonjung.trendreader.data.feed;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.widget.ContentLoadingProgressBar;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;

public class FeedManager {
    private OnFinishCallback callback;

    public void fetchFeed(String requestUrl, OnFinishCallback callback) {

        this.callback = callback;
        new FeedFetcher().execute(requestUrl);
    }

    public interface OnFinishCallback {

        void done(LinkedList<FeedItem> result);
    }

    public FeedManager() {
    }

    @SuppressLint("StaticFieldLeak")
    private class FeedFetcher extends AsyncTask<String, Void, LinkedList<FeedItem>> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(LinkedList<FeedItem> result) {

            super.onPostExecute(result);
            Collections.sort(result, (f1, f2) -> f2.getUpdateAt().compareTo(f1.getUpdateAt()));
            callback.done(result);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected LinkedList<FeedItem> doInBackground(String... params) {

            LinkedList<FeedItem> feedList = new LinkedList<>();
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(params[0]).build();
                Response response = client.newCall(request).execute();

                JSONArray jsonArray = new JSONArray(response.body().string());

                for (int index = 0; index < jsonArray.length(); index++) {
                    JSONObject tempJson = jsonArray.getJSONObject(index);

                    FeedItem feedItem = new FeedItem();
                    //BlogI Info
                    feedItem.setBlogName(tempJson.getString("blog_name"));
                    feedItem.setFaviconUrl(tempJson.getString("favicon_url"));
                    feedItem.setBlogUrl(tempJson.getString("blog_url"));

                    // Content
                    feedItem.setPostTitle(tempJson.getString("post_title").replaceAll("\n", "").trim());
                    feedItem.setPostContent(tempJson.getString("post_content").trim());
                    feedItem.setPostUrl(tempJson.getString("post_url"));
                    feedItem.setUpdateAt(tempJson.getString("update_at"));

                    feedList.add(feedItem);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return feedList;
        }
    }
}
