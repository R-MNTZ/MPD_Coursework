package coursework.gcu.mpdcoursework;
//Ramon Martinez Fernandez S1631216
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class IndViewerFragment extends Fragment implements View.OnClickListener {

    private TextView h1, h2, h3, h4, h5, h6, h7, c1, c2, c3, c4, c5, c6, c7, title;
    private Button mapsBtn;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.currentFragment = 3;

        view = inflater.inflate(R.layout.ind_view, container, false);
        title = view.findViewById(R.id.textView);
        c1 = (TextView) view.findViewById(R.id.textView3);
        c2 = (TextView) view.findViewById(R.id.textView5);

        c4 = (TextView) view.findViewById(R.id.textView9);
        c5 = (TextView) view.findViewById(R.id.textView11);
        c6 = (TextView) view.findViewById(R.id.textView13);
        c7 = (TextView) view.findViewById(R.id.textView15);
        h1 = (TextView) view.findViewById(R.id.textView2);
        h2 = (TextView) view.findViewById(R.id.textView4);

        h4 = (TextView) view.findViewById(R.id.textView8);
        h5 = (TextView) view.findViewById(R.id.textView10);
        h6 = (TextView) view.findViewById(R.id.textView12);
        h7 = (TextView) view.findViewById(R.id.textView14);
        c1.setText(Page1Fragment.item.getTitle());
        c2.setText(Page1Fragment.item.getDescription());

        c4.setText(Page1Fragment.item.getGeo());
        c5.setText(Page1Fragment.item.getAuthor());
        c6.setText(Page1Fragment.item.getComments());
        c7.setText(Page1Fragment.item.getPubDate());
        mapsBtn = (Button) view.findViewById(R.id.button7);
        mapsBtn.setOnClickListener(this);

        if (MainActivity.darkMode) {
            darkMode();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mapsBtn) {
            Uri gmmIntentUri = Uri.parse("geo:" + Page1Fragment.item.getGeo());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    // Prepare components for dark mode
    void darkMode() {
        c1.setTextColor(Color.WHITE);
        c2.setTextColor(Color.WHITE);

        c4.setTextColor(Color.WHITE);
        c5.setTextColor(Color.WHITE);
        c6.setTextColor(Color.WHITE);
        c7.setTextColor(Color.WHITE);
        h1.setTextColor(Color.WHITE);
        h2.setTextColor(Color.WHITE);

        h4.setTextColor(Color.WHITE);
        h5.setTextColor(Color.WHITE);
        h6.setTextColor(Color.WHITE);
        h7.setTextColor(Color.WHITE);
        title.setTextColor(Color.WHITE);

    }
}
