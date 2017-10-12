package com.example.kevin.dininghallmenu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;



public class ScrollingActivity extends AppCompatActivity{

    @Override

    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        downloadFiles asyncTask = new downloadFiles(new AsyncResponse(){
            @Override
            public void processFinish(ArrayList<String> output){
                GlobalVariables.getInstance().menu = output;
            }
        });
        asyncTask.execute(GlobalVariables.getInstance().menu);

        ArrayList<String> menu = GlobalVariables.getInstance().menu;
        for(int i = 0; i<menu.size();i++)
        {
            if(i==0) {
                TextView a = (TextView) findViewById((R.id.textView0));
                a.append(menu.get(0));
            }
            if(i==1) {
                TextView b = (TextView) findViewById(R.id.textView1);
                b.append(menu.get(1));
            }
            if(i==2) {
                TextView c = (TextView) findViewById(R.id.textView2);
                c.append(menu.get(2));
            }
            if(i==3)
            {
                TextView d = (TextView) findViewById(R.id.textView3);
                d.append(menu.get(3));
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class downloadFiles extends AsyncTask<ArrayList<String>, Void, ArrayList<String>>
    {
        public AsyncResponse delegate = null;

        public downloadFiles(AsyncResponse asyncResponse)
        {
            delegate = asyncResponse;
        }
        protected ArrayList<String> doInBackground(ArrayList<String>... args) {
            Document doc;
            //int i =0;
            try {
                ArrayList<String> items = new ArrayList<String>();
                doc = Jsoup.connect("http://menu.dining.ucla.edu/Menus").get();
                String title = doc.title();
                Elements diningHalls = doc.getElementsByClass("menu-block half-col");
                if (diningHalls.size()!=0) {
                    for (Element text : diningHalls) {
                        Elements removed = text.getElementsByClass("tt-prodwebcode");
                        for(Element shit: removed)
                        {
                            shit.empty();
                        }
                        removed = text.getElementsByClass("item-description");
                        for(Element shit: removed)
                        {
                            shit.empty();
                        }
                        HtmlToPlainText translator = new HtmlToPlainText();
                        String added = translator.getPlainText(text);
                        added = added.replaceAll("<.*?>", "");
                        items.add(added);
                    }
                    return items;
                }
                else
                {
                    diningHalls = doc.getElementsByClass("menu-block third-col");
                    for (Element text : diningHalls) {
                        Elements removed = text.getElementsByClass("tt-prodwebcode");
                        for(Element shit: removed)
                        {
                            shit.empty();
                        }
                        removed = text.getElementsByClass("item-description");
                        for(Element shit: removed)
                        {
                            shit.empty();
                        }
                        HtmlToPlainText translator = new HtmlToPlainText();
                        String added = translator.getPlainText(text);
                        added = added.replaceAll("<.*?>", "");
                        Log.d("blah", added);
                        items.add(added);
                    }
                    return items;
                }
            }
            catch(IOException ie){
                Log.d("ID: ", "Cannot find Website");
            }
            return null;
        }


        protected void onPostExecute(ArrayList<String> result)
        {
            delegate.processFinish(result);
        }

    }

}
