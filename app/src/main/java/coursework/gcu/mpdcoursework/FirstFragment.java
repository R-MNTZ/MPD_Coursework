package coursework.gcu.mpdcoursework;
//Ramon Martinez Fernandez S1631216
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstFragment extends Fragment implements View.OnClickListener {


    View view;
    Button firstButton;
    Button searchDate, showAll;
    ImageButton imgBtn;
    EditText enterLoc, enterDate;
    CheckBox cb;
    TextView tv, tvTitle, tv2, tv3;
    static String searchTerm = " ";
    static String searchTerm2 = " ";
    static Boolean colourCode = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.search_page, container, false);
        firstButton = (Button) view.findViewById(R.id.button2);
        firstButton.setOnClickListener(this);
        showAll = (Button)view.findViewById(R.id.button6);
        imgBtn = (ImageButton) view.findViewById(R.id.imageButton);
        tv = view.findViewById(R.id.textView19);
        tv2 = view.findViewById(R.id.textView17);
        tv3 = view.findViewById(R.id.textView18);
        tvTitle = view.findViewById(R.id.textView16);
        showAll.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
        enterLoc = (EditText)view.findViewById(R.id.editText);
        enterDate = (EditText)view.findViewById(R.id.editText2);
        cb = (CheckBox)view.findViewById(R.id.checkBox);
        cb.setOnClickListener(this);
        if (SecondFragment.urlSource == "https://trafficscotland.org/rss/feeds/currentincidents.aspx")
        {
           cb.setVisibility(View.INVISIBLE);
            imgBtn.setVisibility(View.INVISIBLE);
        }
        MainActivity.currentFragment = 1;
        if (SecondFragment.urlSource == "https://trafficscotland.org/rss/feeds/roadworks.aspx") {
            tv.setText("Roadworks");
        } else if (SecondFragment.urlSource == "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx") {
            tv.setText("Planned Roadworks");
        } else {
            tv.setText("Current Incidents");
        }

        if (MainActivity.darkMode) {
            darkMode();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        colourCode = false;
        if (v == showAll){
            searchTerm="";
            searchTerm2="";
            MainActivity.currentFragment = 2;
            loadFragment(new Page1Fragment());
        }
        if (v == firstButton) {
            searchTerm2 = enterLoc.getText().toString();
            searchTerm = enterDate.getText().toString();
            if (!checkValidDate(enterDate.getText().toString()) && !searchTerm.equals("") ) {
                showtbDialog("Enter a valid Date in DD/MM or DD/MM/YYYY format");
                return;
           }
            MainActivity.currentFragment =  2;
            loadFragment(new Page1Fragment());
        }
        if (v == imgBtn){
            showtbDialog("Colour code incidents depending on the time left to complete from the date entered, or today's date if none is entered\nGreen:\nFinishes today\nYellow:\nFinishes in the next 7 days\nRed:\nWill take longer than a week");
        }

        if (cb.isChecked()) {
            colourCode = true;
        } else {
            colourCode = false;
        }
    }


    //Prepare components for Dark Mode
    void darkMode() {
        tv.setTextColor(Color.WHITE);
        tv2.setTextColor(Color.WHITE);
        tv3.setTextColor(Color.WHITE);
        tvTitle.setTextColor(Color.WHITE);
        enterDate.setHintTextColor(Color.WHITE);
        ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
        ViewCompat.setBackgroundTintList(enterDate, colorStateList);
        ViewCompat.setBackgroundTintList(enterLoc, colorStateList);
        enterLoc.setHintTextColor(Color.WHITE);
        enterLoc.setTextColor(Color.WHITE);
        enterDate.setTextColor(Color.WHITE);
        cb.setTextColor(Color.WHITE);
        int states[][] = {{android.R.attr.state_checked}, {}};
        int colors[] = {Color.WHITE, Color.WHITE};
        CompoundButtonCompat.setButtonTintList(cb, new ColorStateList(states, colors));


    }

    //Checks if date is valid, both in format and logic
    public Boolean checkValidDate(String date){
        if (date.contains("/")) {
            if (date.length() == 5) {
                String day = date.substring(0, 2);
                String month = date.substring(3, 5);
                if (Integer.valueOf(month) > 0 && Integer.valueOf(month) < 13) {
                    if (Integer.valueOf(day) > 0 && Integer.valueOf(day) < 32) {

                        if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
                            if (Integer.valueOf(day) > 30) {
                                return false;
                            }
                        } else if (month.equals("02")) {
                            if (Integer.valueOf(day) > 29) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            } else if (date.length() == 10) {
                String day = date.substring(0, 2);
                String month = date.substring(3, 5);
                String year = date.substring(6, 10);
                if (Integer.valueOf(month) > 0 && Integer.valueOf(month) < 13) {
                    if (Integer.valueOf(day) > 0 && Integer.valueOf(day) < 32) {
                        if (Integer.valueOf(year) > 2018 && Integer.valueOf(day) < 2022) {
                            if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
                                if (Integer.valueOf(day) > 30) {
                                    return false;
                                }
                            } else if (month.equals("02")) {
                                if (Integer.valueOf(day) > 29) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    //Shows dialog qith the string passed in as a parameter
    private void showtbDialog( String m)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(m);
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    //Loads up requested fragment
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

    }
}



