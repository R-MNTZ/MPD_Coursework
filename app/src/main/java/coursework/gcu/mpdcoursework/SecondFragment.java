package coursework.gcu.mpdcoursework;
//Ramon Martinez Fernandez S1631216
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import java.util.LinkedList;


public class SecondFragment extends Fragment implements View.OnClickListener {

    View view;
    private String result ="";
    private Button startButton, startButton2, startButton3;
    private ProgressBar pb;
    private TextView tv, salutation;
    // Traffic Scotland URLs
    //private String urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    public static String urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    //private String urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    public static LinkedList<CurrentIncidents> alist = null;
    public static LinkedList<CurrentIncidents> cilist = null;
    public static LinkedList<CurrentIncidents> prlist = null;
    public static LinkedList<CurrentIncidents> rlist = null;

    Boolean loaded = false;
    private View mainView;
    private String msg = "Loading Data...";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_main, container, false);
        startButton = (Button)view.findViewById(R.id.startButton);
        startButton2 = (Button)view.findViewById(R.id.startButton2) ;
        startButton3 = (Button)view.findViewById(R.id.startButton3) ;
        startButton.setOnClickListener(this);
        startButton2.setOnClickListener(this);
        startButton3.setOnClickListener(this);
        pb = (ProgressBar)view.findViewById(R.id.pbHeaderProgress);
        tv = (TextView)view.findViewById(R.id.pBar);
        salutation = (TextView)view.findViewById(R.id.salutation);
        urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

        result = "";
        //mainView = (View)findViewById(R.id.a);
        //mainView.setBackgroundColor(getResources().getColor(R.color.silver,null));

        pb.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.INVISIBLE);
        MainActivity.currentFragment = 0;
        if (MainActivity.darkMode) {
            DarkMode();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.startButton:
            {
                urlSource = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
                if (prlist == null) {
                    result = "";

                    new MyAsyncTasks().execute("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx");

                }
                else {
                    alist = prlist;
                    loadFragment(new FirstFragment());
                }



            }
            break;
            case R.id.startButton2:
            {
                urlSource = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
                if (cilist == null) {
                    result = "";

                    new MyAsyncTasks().execute("https://trafficscotland.org/rss/feeds/currentincidents.aspx");


                }
                else {
                    alist = cilist;
                    loadFragment(new FirstFragment());
                }
            }
            break;
            case R.id.startButton3:
            {
                urlSource = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
                if (rlist == null) {
                    result = "";


                    new MyAsyncTasks().execute("https://trafficscotland.org/rss/feeds/roadworks.aspx");

                }
                else {
                    alist = rlist;
                    loadFragment(new FirstFragment());
                }

            }




        }
    }


// Handles fetching, parsing and storing the xml file. Displays the user a small progress wheel
    private class MyAsyncTasks extends AsyncTask<String, Void, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";
            Boolean haveStarted = false;
            CurrentIncidents item = null;
            alist  = new LinkedList<CurrentIncidents>();




            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(strings[0]);

                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                //This removes the first 2 header lines

                System.out.println("End document");
                while ((inputLine = in.readLine()) != null)
                {
                    if(inputLine!=null){
                        result = result + inputLine;

                    }

                    //Log.e("MyTag",inputLine);

                }





                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader(result));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {


                    if(eventType == XmlPullParser.START_TAG){
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            //Log.e("MyTag","Item Start Tag found");
                            item = new CurrentIncidents();
                            haveStarted = true;

                        }
                        else if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            if (haveStarted)
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                item.setTitle(temp);
                                Log.e("MyTag","Title is " + item.getTitle());
                            }

                        }
                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            if (haveStarted) {
                                String temp = xpp.nextText();
                                // Do something with text
                                item.setDescription(temp);
                                Log.e("MyTag", "Description is " + item.getDescription());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            if (haveStarted) {
                                String temp = xpp.nextText();
                                // Do something with text
                                item.setLink(temp);
                                Log.e("MyTag", "Link is " + item.getLink());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("georss:point"))
                        {
                            if (haveStarted) {
                                String temp = xpp.nextText();
                                // Do something with text
                                item.setGeo(temp);
                                Log.e("MyTag", "georss is " + item.getGeo());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("author"))
                        {
                            if (haveStarted) {
                                String temp = xpp.nextText();
                                // Do something with text
                                item.setAuthore(temp);
                                Log.e("MyTag", "author is " + item.getAuthor());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("comments"))
                        {
                            if (haveStarted) {
                                String temp = xpp.nextText();
                                // Do something with text
                                item.setComments(temp);
                                Log.e("MyTag", "comments are " + item.getComments());
                            }
                        }
                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            if (haveStarted) {
                                String temp = xpp.nextText();

                                // Do something with text
                                item.setPubDate(temp);
                                Log.e("MyTag", "pubDate is " + item.getPubDate());
                            }
                        }



                    }else if(eventType == XmlPullParser.TEXT) {
                        Log.e("MyTag",xpp.getText());
                    }else if(eventType == XmlPullParser.END_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item"))
                        {
                            Log.e("MyTag","we here dawg-----------------------------------");
                            alist.addLast(item);
                            int s = alist.size();

                            Log.e("MyTag",Integer.toString(s));
                        }
                    }
                    eventType = xpp.next();
                }
                in.close();
            }catch (XmlPullParserException e){
                e.printStackTrace();
                Log.e("MyTag","xmlpullparser");
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception");
            }

            loaded = true;








            return null;
        }

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            tv.setVisibility(View.VISIBLE);
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (urlSource == "https://trafficscotland.org/rss/feeds/currentincidents.aspx"){
                cilist = alist;
            }
            else if (urlSource == "https://trafficscotland.org/rss/feeds/roadworks.aspx")
            {
                rlist = alist;
            }
            else {
                prlist = alist;
            }
            pb.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
            MainActivity.currentFragment = 1;
            loadFragment(new FirstFragment());
        }
    }

    //Loads up the requested fragment to go from one screen to the other
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();


    }


// Prepares components for Dark Mode
    void DarkMode() {
        tv.setTextColor(Color.WHITE);
        salutation.setTextColor(Color.WHITE);

    }

    //Starts a new thread to update lists automatically (called by Timer Task)
    void updateLists(String url) {
        urlSource = url;
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        Boolean haveStarted = false;
        CurrentIncidents item = null;
        alist  = new LinkedList<CurrentIncidents>();




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
            //This removes the first 2 header lines

            System.out.println("End document");
            while ((inputLine = in.readLine()) != null)
            {
                if(inputLine!=null){
                    result = result + inputLine;

                }



            }





            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader(result));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {


                if(eventType == XmlPullParser.START_TAG){
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        //Log.e("MyTag","Item Start Tag found");
                        item = new CurrentIncidents();
                        haveStarted = true;

                    }
                    else if (xpp.getName().equalsIgnoreCase("title"))
                    {
                        if (haveStarted)
                        {
                            String temp = xpp.nextText();
                            item.setTitle(temp);
                            Log.e("MyTag","Title is " + item.getTitle());
                        }

                    }
                    else if (xpp.getName().equalsIgnoreCase("description"))
                    {
                        if (haveStarted) {
                            String temp = xpp.nextText();
                            item.setDescription(temp);
                            Log.e("MyTag", "Description is " + item.getDescription());
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("link"))
                    {
                        if (haveStarted) {
                            String temp = xpp.nextText();
                            item.setLink(temp);
                            Log.e("MyTag", "Link is " + item.getLink());
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("georss:point"))
                    {
                        if (haveStarted) {
                            String temp = xpp.nextText();
                            item.setGeo(temp);
                            Log.e("MyTag", "georss is " + item.getGeo());
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("author"))
                    {
                        if (haveStarted) {
                            String temp = xpp.nextText();
                            item.setAuthore(temp);
                            Log.e("MyTag", "author is " + item.getAuthor());
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("comments"))
                    {
                        if (haveStarted) {
                            String temp = xpp.nextText();
                            item.setComments(temp);
                            Log.e("MyTag", "comments are " + item.getComments());
                        }
                    }
                    else if (xpp.getName().equalsIgnoreCase("pubDate"))
                    {
                        if (haveStarted) {
                            String temp = xpp.nextText();
                            item.setPubDate(temp);
                            Log.e("MyTag", "pubDate is " + item.getPubDate());
                        }
                    }



                }else if(eventType == XmlPullParser.TEXT) {
                    Log.e("MyTag",xpp.getText());
                }else if(eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","we here dawg-----------------------------------");
                        alist.addLast(item);
                        int s = alist.size();

                        Log.e("MyTag",Integer.toString(s));
                    }
                }
                eventType = xpp.next();
            }
            in.close();
        }catch (XmlPullParserException e){
            e.printStackTrace();
            Log.e("MyTag","xmlpullparser");
        }
        catch (IOException ae)
        {
            Log.e("MyTag", "ioexception");
        }

        loaded = true;



        if (urlSource.equals("https://trafficscotland.org/rss/feeds/currentincidents.aspx") ){
            cilist = alist;
            result = "";
        }
        else if (urlSource.equals("https://trafficscotland.org/rss/feeds/roadworks.aspx"))
        {
            rlist = alist;
            result = "";
        }
        else if (urlSource.equals("https://trafficscotland.org/rss/feeds/plannedroadworks.aspx")) {
            prlist = alist;
            result = "";
        }





    }

}
