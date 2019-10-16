package hridoy.aiz.simplerssexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ListView lvRss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvRss = findViewById(R.id.lvRss);

        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public InputStream getIInputStream(URL url) {

        try{
            url.openConnection().getInputStream();
        }catch(IOException e){
            exception = e;
            return null;
        }catch (XmlPullParserException e){

        }

    }

    public class ProcessInBckground extends AsyncTask<Integer,Void,String> {
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        Exception excepiton = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("busy loading");
        }

        @Override
        protected String doInBackground(Integer... integers) {

            try{

            }catche ()

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
