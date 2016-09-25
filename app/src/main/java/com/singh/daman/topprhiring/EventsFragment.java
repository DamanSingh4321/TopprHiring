package com.singh.daman.topprhiring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daman on 24/9/16.
 */
public class EventsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private EventsAdapter mEventsAdapter;
    private EditText search;
    private DatabaseHandler handler;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> experience = new ArrayList<>();
    ArrayList<String> favourite = new ArrayList<>();

    public EventsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // inflater.inflate(R.menu.moviesfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
////        if (id == R.id.action_refresh) {
////            Data();
////            return true;
////        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        search = (EditText) rootView.findViewById( R.id.search);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.eventsrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        handler = new DatabaseHandler(getContext());
        mEventsAdapter = new EventsAdapter(getContext(), id, name, image, category, description, experience, favourite);
        mRecyclerView.setAdapter(mEventsAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        Data();
        PopulateList();
        addTextListener();
        return rootView;
    }

    public void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                ArrayList<String> sid = new ArrayList<>();
                ArrayList<String> sname = new ArrayList<>();
                ArrayList<String> simage = new ArrayList<>();
                ArrayList<String> scategory = new ArrayList<>();
                ArrayList<String> sdescription = new ArrayList<>();
                ArrayList<String> sexperience = new ArrayList<>();
                ArrayList<String> sfavourite = new ArrayList<>();


                for (int i = 0; i < name.size(); i++) {

                    final String searchname = name.get(i).toLowerCase();
                    final String searchcategory = category.get(i).toLowerCase();
                    if (searchname.contains(query) || searchcategory.contains(query) ) {
                        sname.add(name.get(i));
                        sid.add(id.get(i));
                        simage.add(image.get(i));
                        scategory.add(category.get(i));
                        sdescription.add(description.get(i));
                        sexperience.add(experience.get(i));
                        sfavourite.add(favourite.get(i));
                    }
                }

                mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mEventsAdapter = new EventsAdapter(getContext(), sid, sname, simage, scategory, sdescription, sexperience, sfavourite);
                mRecyclerView.setAdapter(mEventsAdapter);
                mEventsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void PopulateList(){
        ArrayList<Events> eventsArrayList = handler.getAllEvents();
        for (int i = 0; i < eventsArrayList.size(); i++){
            Events events = eventsArrayList.get(i);
            System.out.println(events);
            id.add(events.getId());
            name.add(events.getName());
            image.add(events.getImage());
            category.add(events.getCategory());
            description.add(events.getDescription());
            experience.add(events.getExperience());
            favourite.add(events.getFavourite());
        }mEventsAdapter.notifyDataSetChanged();
    }

    public void Data() {
        try {
            final String url = "https://hackerearth.0x10.info/api/toppr_events?type=json&query=list_events";
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String syncresponse = object.getString("websites");
                                JSONArray a1obj = new JSONArray(syncresponse);
                                for (int j = 0; j < a1obj.length(); j++) {
                                    JSONObject obj = a1obj.getJSONObject(j);
                                    Events events = new Events();
                                    events.setId(obj.getString("id"));
                                    events.setName(obj.getString("name"));
                                    events.setCategory(obj.getString("category"));
                                    events.setImage(obj.getString("image"));
                                    events.setDescription(obj.getString("description"));
                                    events.setExperience(obj.getString("experience"));
                                    events.setFavourite("NO");
                                    handler.addEvents(events);
                                    System.out.println("events:  " + events);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println("exper " + experience);
                            mEventsAdapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), "No internet connections!", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}