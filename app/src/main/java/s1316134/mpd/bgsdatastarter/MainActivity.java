
/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Jordan O'Donnell
// Student ID           s1316134
// Programme of Study   Computing
//

// Update the package name to include your Student Identifier
package s1316134.mpd.bgsdatastarter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import s1316134.mpd.bgsdatastarter.R;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private Button mapsButton;
    private String result ="";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private LinkedList<Earthquake> eqData;
    private LinkedList<Earthquake> eqDataSet;
    private ListView parseDataDisplay;
    private LinkedListAdapter adapter;
    private Button searchButton;
    private EditText searchInput;

    private String userSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        LinkedList <Earthquake> alist = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        searchInput = (EditText) findViewById(R.id.searchInput);
        searchInput.setFocusable(true);
        mapsButton = (Button)findViewById(R.id.mapsButton);
        mapsButton.setOnClickListener(this);
        parseDataDisplay = (ListView)findViewById(R.id.parseDataDisplay);
        startProgress();

        // More Code goes here
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.main){
            startProgress();
        }

        if(id == R.id.search){
            Earthquake eq;
            Intent intent = new Intent(this, MapsActivity.class);
            for(int i =0; i<eqDataSet.size();i++){
                eq = eqDataSet.get(i);

                String location = eq.getLocation();
                Double magnitude = eq.getMagnitude();
                String date = eq.getPubDate();
                String depth = eq.getDepth();
                Double lat = eq.getGeoLat();
                Double glong = eq.getGeoLong();
                String colour = eq.getColour();

                intent.putExtra(i + "Location", location);
                intent.putExtra(i + "Magnitude", magnitude);
                intent.putExtra(i + "date", date);
                intent.putExtra(i + "depth", depth);
                intent.putExtra(i + "lat", lat);
                intent.putExtra(i + "geolong", glong);
                intent.putExtra(i + "colour", colour);


            }
            startActivity(intent);


        }

        if(id == R.id.help){
            Context context = getApplicationContext();
            CharSequence text = "Earthquake Key; Green = Small, Yellow = Medium, Red = Large";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View aview)
    {
        if(aview == searchButton){

        if(searchInput.getText().toString().equals("")){
            Context context = getApplicationContext();
            CharSequence text = "Please enter a search query!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }

        userSearch = searchInput.getText().toString().toUpperCase();

        searchFunction(userSearch);


        }
        if(aview == mapsButton) {
            Earthquake eq;
            Intent intent = new Intent(this, MapsActivity.class);
            for(int i =0; i<eqDataSet.size();i++){
                eq = eqDataSet.get(i);
                String location = eq.getLocation();
                Double magnitude = eq.getMagnitude();
                String date = eq.getPubDate();
                String depth = eq.getDepth();
                Double lat = eq.getGeoLat();
                Double glong = eq.getGeoLong();
                String colour = eq.getColour();


                intent.putExtra(i + "Location", location);
                intent.putExtra(i + "Magnitude", magnitude);
                intent.putExtra(i + "date", date);
                intent.putExtra(i + "depth", depth);
                intent.putExtra(i + "lat", lat);
                intent.putExtra(i + "geolong", glong);
                intent.putExtra(i + "colour", colour);

            }
            startActivity(intent);



        }
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    eqData = parseData(result);
                    //Creates a dataset to be manipulated for searching
                    eqDataSet = new LinkedList<>();
                    eqDataSet.addAll(eqData);
                    adapter = new LinkedListAdapter(getApplicationContext(), R.id.title, eqData);
                    parseDataDisplay.setAdapter(adapter);


                }
            });
        }


        private LinkedList<Earthquake> parseData(String dataToParse)
        {
            Earthquake earthquake = null;
            LinkedList <Earthquake> alist = null;
            try
            {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader( dataToParse ) );
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {

                    // Found a start tag
                    if(eventType == XmlPullParser.START_TAG)
                    {

                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("channel"))
                        {

                            alist  = new LinkedList<Earthquake>();
                        }
                        else
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            Log.e("MyTag","Item Start Tag found");


                                earthquake = new Earthquake();

                        }
                        else
                        if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            Log.e("MyTag","title is " + temp);
                            if(earthquake != null) {

                                earthquake.setTitle(temp);
                            }
                        }
                        else
                            // Check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("description"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                Log.e("MyTag","Desc " + temp);
                                if(earthquake != null) {
                                    earthquake.setDesc(temp);
                                }
                            }
                            else
                                // Check which Tag we have
                                if (xpp.getName().equalsIgnoreCase("pubDate"))
                                {
                                    // Now just get the associated text
                                    String temp = xpp.nextText();
                                    // Do something with text
                                    Log.e("MyTag","Pub Date " + temp);
                                    if(earthquake != null) {
                                        earthquake.setPubDate(temp);
                                    }
                                }
                                else
                                    // Check which Tag we have
                                    if (xpp.getName().equalsIgnoreCase("lat"))
                                    {
                                        // Now just get the associated text
                                        Double temp = Double.parseDouble(xpp.nextText());
                                        // Do something with text
                                        Log.e("MyTag","Lat " + temp);
                                        if(earthquake != null) {
                                            earthquake.setGeoLat(temp);
                                        }
                                    }
                                    else
                                        // Check which Tag we have
                                        if (xpp.getName().equalsIgnoreCase("long"))
                                        {
                                            // Now just get the associated text
                                            Double temp = Double.parseDouble(xpp.nextText());
                                            // Do something with text
                                            Log.e("MyTag","Long " + temp);
                                            if(earthquake != null) {
                                                earthquake.setGeoLong(temp);
                                            }
                                        }
                    }

                    else
                    if(eventType == XmlPullParser.END_TAG)
                    {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            Log.e("MyTag","Earthquake is " + earthquake.toString());
                            alist.add(earthquake);
                        }

                    }


                    // Get the next event
                    eventType = xpp.next();

                } // End of while

                //return alist;
            }
            catch (XmlPullParserException ae1)
            {
                Log.e("MyTag","Parsing error" + ae1.toString());
            }
            catch (IOException ae1)
            {
                Log.e("MyTag","IO error during parsing");
            }

            Log.e("MyTag","End document");

            return alist;

        }
    }



    private void searchFunction(String userSearch){

        if(eqData == null){
            startProgress();
        }
        LinkedList<Earthquake> searchResults = new LinkedList<>();
        for(Earthquake e: eqData){
            if (e.getTitle().contains(userSearch))
                searchResults.add(e);
        }
        if(searchResults.isEmpty()){
            Context context = getApplicationContext();
            CharSequence text = "No Matches Found!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else {
            eqDataSet = searchResults;
            adapter = new LinkedListAdapter(getApplicationContext(), R.id.title, eqDataSet);
            parseDataDisplay.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }




}
