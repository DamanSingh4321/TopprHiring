package com.singh.daman.topprhiring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerdetail, new DetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(this, SettingsActivity.class));
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public static class DetailFragment extends Fragment {

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_description, container, false);

            Bundle extras = getActivity().getIntent().getExtras();

            String image = extras.getString("EXTRA_IMAGE");
            String exp = extras.getString("EXTRA_EXP");
            String desc = extras.getString("EXTRA_DESC");
            String name = extras.getString("EXTRA_NAME");

            System.out.println("desc  "+desc);

            ((TextView) rootView.findViewById(R.id.desc_name))
                    .setText(name);
            ((TextView) rootView.findViewById(R.id.desc_exp))
                    .setText("Experience: "+exp);
            ((TextView) rootView.findViewById(R.id.description))
                    .setText("Description: \n"+desc);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.desc_image);
            Picasso.with(getContext()).load(image).fit().into(imageView);

            return rootView;
        }

    }
}
