package hridoy.aiz.simplerssexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvRss;
    String myrssurl = "http://feeds.news24.com/articles/fin24/tech/rss";
    ArrayList<String> titles;
    ArrayList<String> links;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvRss = findViewById(R.id.lvRss);
        titles = new ArrayList<String>();
        links = new ArrayList<String>();

        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        new ProcessInBckground().execute();
    }

    public InputStream getIInputStream(URL url) {

        try {
           return url.openConnection().getInputStream();

        } catch (IOException e) {
            return null;
        }
    }


    public class ProcessInBckground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        Exception excepiton = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("busy loading the rss feed ...");
            pd.show();
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            try {
                URL url = new URL(myrssurl);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getIInputStream(url), "UTF-8");

                boolean insideItem = false;
                int eventType = xpp.getEventType(); //what tag

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                titles.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) {
                                links.add(xpp.nextText());
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = true;
                    }

                    eventType = xpp.next();
                }

            } catch (
                    MalformedURLException e) {
                excepiton = e;
            } catch (
                    XmlPullParserException e) {
                excepiton = e;
            } catch (
                    IOException e) {
                excepiton = e;
            }

            return null;

        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, titles);
            lvRss.setAdapter(adapter);
            pd.dismiss();
        }
    }
}
