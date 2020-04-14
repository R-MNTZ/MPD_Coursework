package coursework.gcu.mpdcoursework;
//Ramon Martinez Fernandez S1631216
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Page1Fragment extends Fragment implements View.OnClickListener{

    View view;

    private TextView titleDisplay, subTitle, tv2;

    public static CurrentIncidents item;
    private int results;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.page_1, container, false);
        titleDisplay = (TextView) view.findViewById(R.id.textView2);
        subTitle = (TextView) view.findViewById(R.id.textView20);
        tv2 = view.findViewById(R.id.textView21);
        results = 0;
        setUp();
        MainActivity.currentFragment = 2;
        if (MainActivity.darkMode) {
            darkMode();
        }
        return view;
    }


    @Override
    public void onClick(View v) {

    }

    void darkMode() {
        titleDisplay.setTextColor(Color.WHITE);
        subTitle.setTextColor(Color.WHITE);
        tv2.setTextColor(Color.WHITE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    //Creates and displays all the buttons in the screen
    public void setUp () {

        if (SecondFragment.urlSource == "https://trafficscotland.org/rss/feeds/roadworks.aspx") {
            titleDisplay.setText("Roadworks");
        } else if (SecondFragment.urlSource == "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx") {
            titleDisplay.setText("Planned Roadworks");
        } else {
            titleDisplay.setText("Current Incidents");
        }
        for (int i = 0; i < SecondFragment.alist.size(); i++) {
            String display = "";
            for (int j = 0; j < SecondFragment.alist.getLast().getItem().length; j++) {
                display = display + SecondFragment.alist.get(i).getItem()[j].toString() + "\n";
            }
            if (checkDateRange(SecondFragment.alist.get(i).getDescription(), FirstFragment.searchTerm) && display.toLowerCase().contains(FirstFragment.searchTerm2.toLowerCase())) {

                final String ph = display;
                final Button myButton = new Button(getActivity());
                final TextView tv = new TextView(getActivity());
                myButton.setId(i);
                results++;
                myButton.setText(SecondFragment.alist.get(i).getTitle());
                myButton.setMinWidth(800);
                if (titleDisplay.getText() != "Current Incidents") {
                    if (FirstFragment.colourCode == true)
                    {

                        if (getEndDate(SecondFragment.alist.get(i).getDescription()) < 1) {
                            myButton.setBackgroundColor((getResources().getColor(R.color.green, null)));
                        } else if (getEndDate(SecondFragment.alist.get(i).getDescription()) < 8) {
                            myButton.setBackgroundColor((getResources().getColor(R.color.yellow, null)));
                        } else {
                            myButton.setBackgroundColor((getResources().getColor(R.color.DarkRed, null)));
                        }
                    }
                }


                LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ll.addView(myButton, lp);
                ll.addView(tv, lp);
                final int ph2 = i;

                myButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        item = SecondFragment.alist.get(ph2);
                        loadFragment(new IndViewerFragment());
                    }
                });
            }
        }
        if (SecondFragment.alist.size() == 0) {
            tv2.setText("No incidents to be displayed currently!");
            tv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv2.setTextSize(20);



        }
        else if (results == 0) {
            tv2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tv2.setText("Your search had no results!");
            tv2.setTextSize(20);
        }
        else {
            tv2.setVisibility(View.GONE);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    //Returns the difference between end date and current date
    public int getEndDate (String desc) {
        if (desc.length() > 0) {
            int index = desc.indexOf("End Date");
            String endDate = desc.substring(index + 10);
            String StartDate = desc.substring(0, index -1);
            if (endDate.contains("\n")) {
                index = endDate.indexOf("\n");
                endDate = endDate.substring(0, index);
            }

            index = endDate.indexOf(",");
            endDate = endDate.substring(index + 1);
            index = endDate.indexOf("-");
            endDate = endDate.substring(1, index - 1);
            String Date1Day = endDate.substring(0, 2);
            String Date1Year = endDate.substring(endDate.length() - 4);
            String Date1Month = endDate.substring(3, endDate.length() - 5);

            switch (Date1Month) {
                case "January":
                    Date1Month = "01";
                    break;
                case "February":
                    Date1Month = "02";
                    break;

                case "March":
                    Date1Month = "03";
                    break;

                case "April":
                    Date1Month = "04";
                    break;

                case "May":
                    Date1Month = "05";
                    break;

                case "June":
                    Date1Month = "06";
                    break;

                case "July":
                    Date1Month = "07";
                    break;

                case "August":
                    Date1Month = "08";
                    break;

                case "September":
                    Date1Month = "09";
                    break;

                case "October":
                    Date1Month = "10";
                    break;

                case "November":
                    Date1Month = "11";
                    break;

                case "December":
                    Date1Month = "12";
                    break;

            }


            String Date2Day;
            String Date2Month;
            String Date2Year;
            if (FirstFragment.searchTerm != "" )
            {
                String date = FirstFragment.searchTerm;
                if(date.length() == 5){
                    date = date + "/2020";
                }
                Date2Day = date.substring(0,2);
                Date2Month = date.substring(3,5);
                Date2Year = date.substring(date.length() - 4);
            }
            else
            {
                String currentDate = java.time.LocalDate.now().toString();
                Date2Day = currentDate.substring(8);
                Date2Month = currentDate.substring(5, 7);
                Date2Year = currentDate.substring(0, 4);
            }


            int daysFromJan1stD2 = (Integer.valueOf(Date1Month) - 1) * 30 + (Integer.valueOf(Date1Day)) + 365 * (Integer.valueOf(Date1Year) - 2020);
            int daysFromJan1stD1 = (Integer.valueOf(Date2Month) - 1) * 30 + (Integer.valueOf(Date2Day)) + 365 * (Integer.valueOf(Date2Year) - 2020);
            if (Integer.valueOf(Date1Month) > 4) {
                daysFromJan1stD2 += 1;
            } if (Integer.valueOf(Date1Month) > 6) {
                daysFromJan1stD2 += 1;
            }if (Integer.valueOf(Date1Month) > 8) {
                daysFromJan1stD2 += 1;
            }if (Integer.valueOf(Date1Month) > 10) {
                daysFromJan1stD2 += 1;
            } if(Integer.valueOf(Date1Year) > 2020) {
                daysFromJan1stD2 += 1;
            }

            if (Integer.valueOf(Date2Month) > 4) {
                daysFromJan1stD1 += 1;
            } if (Integer.valueOf(Date2Month) > 6) {
                daysFromJan1stD1 += 1;
            }if (Integer.valueOf(Date2Month) > 8) {
                daysFromJan1stD1 += 1;
            }if (Integer.valueOf(Date2Month) > 10) {
                daysFromJan1stD1 += 1;
            } if(Integer.valueOf(Date2Year) > 2020) {
                daysFromJan1stD1 += 1;
            }
            int diff = daysFromJan1stD2 - daysFromJan1stD1;
            return diff;
        } else {
            int ret = 0;
            return ret;
        }
    }

    // Checks whether entered date is within roadwork start-end date range
    public boolean checkDateRange(String desc, String date) {
        if (date.equals("")){
            return true;
        }
        if(date.length() == 5){
            date = date + "/2020";
        }
        int index = desc.indexOf("End Date");
        String endDate = desc.substring(index + 10);
        String startDate = desc.substring(0, index -1);
        if (endDate.contains("\n")) {
            index = endDate.indexOf("\n");
            endDate = endDate.substring(0, index);
        }

        index = startDate.indexOf(",");
        startDate = startDate.substring(index + 1);
        index = startDate.indexOf("-");
        startDate = startDate.substring(1, index - 1);
        String startDateDay = startDate.substring(0, 2);
        String startDateYear = startDate.substring(startDate.length() - 4);
        String startDateMonth = startDate.substring(3, startDate.length() - 5);

        switch (startDateMonth) {
            case "January":
                startDateMonth = "01";
                break;
            case "February":
                startDateMonth = "02";
                break;

            case "March":
                startDateMonth = "03";
                break;

            case "April":
                startDateMonth = "04";
                break;

            case "May":
                startDateMonth = "05";
                break;

            case "June":
                startDateMonth = "06";
                break;

            case "July":
                startDateMonth = "07";
                break;

            case "August":
                startDateMonth = "08";
                break;

            case "September":
                startDateMonth = "09";
                break;

            case "October":
                startDateMonth = "10";
                break;

            case "November":
                startDateMonth = "11";
                break;

            case "December":
                startDateMonth = "12";
                break;



        }


        index = endDate.indexOf(",");
        endDate = endDate.substring(index + 1);
        index = endDate.indexOf("-");
        endDate = endDate.substring(1, index - 1);
        String endDateDay = endDate.substring(0, 2);
        String endDateYear = endDate.substring(endDate.length() - 4);
        String endDateMonth = endDate.substring(3, endDate.length() - 5);

        switch (endDateMonth) {
            case "January":
                endDateMonth = "01";
                break;
            case "February":
                endDateMonth = "02";
                break;

            case "March":
                endDateMonth = "03";
                break;

            case "April":
                endDateMonth = "04";
                break;

            case "May":
                endDateMonth = "05";
                break;

            case "June":
                endDateMonth = "06";
                break;

            case "July":
                endDateMonth = "07";
                break;

            case "August":
                endDateMonth = "08";
                break;

            case "September":
                endDateMonth = "09";
                break;

            case "October":
                endDateMonth = "10";
                break;

            case "November":
                endDateMonth = "11";
                break;

            case "December":
                endDateMonth = "12";
                break;



        }
        String dateDay = "";
        try{
            dateDay = date.substring(0,2);
        }
        catch (StringIndexOutOfBoundsException e){
            Log.e("ff", "dd");
        }
        //String dateDay = date.substring(0,2);
        String dateMonth = date.substring(3,5);
        String dateYear = date.substring(date.length() - 4);
        int daysFromJan1stED = (Integer.valueOf(endDateMonth) - 1) * 30 + (Integer.valueOf(endDateDay)) + 365 * (Integer.valueOf(endDateYear) - 2020);
        int daysFromJan1stSD = (Integer.valueOf(startDateMonth) - 1) * 30 + (Integer.valueOf(startDateDay)) + 365 * (Integer.valueOf(startDateYear) - 2020);
        int daysFromJan1stGD = (Integer.valueOf(dateMonth) - 1) * 30 + (Integer.valueOf(dateDay)) + 365 * (Integer.valueOf(dateYear) - 2020);
        if ( daysFromJan1stGD >= daysFromJan1stSD && daysFromJan1stGD <= daysFromJan1stED ) {
            return true;
        }
        return false;
    }

// Loads up requested fragment
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}
