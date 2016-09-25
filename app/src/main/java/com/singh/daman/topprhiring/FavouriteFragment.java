package com.singh.daman.topprhiring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by daman on 25/9/16.
 */
public class FavouriteFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FavouriteAdapter mfavouriteAdapter;
    private EditText search;
    private DatabaseHandler handler;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> image = new ArrayList<>();
    ArrayList<String> category = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<String> experience = new ArrayList<>();
    ArrayList<String> favourite = new ArrayList<>();

    public FavouriteFragment() {
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        search = (EditText) rootView.findViewById(R.id.search);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favrecyclerview);
        mRecyclerView.setHasFixedSize(true);
        handler = new DatabaseHandler(getContext());
        mfavouriteAdapter = new FavouriteAdapter(getContext(), id, name, image, category, description, experience, favourite);
        mRecyclerView.setAdapter(mfavouriteAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);
        PopulateList();
        return rootView;
    }

    public void PopulateList() {
        ArrayList<Events> eventsArrayList = handler.getAllEvents();
        for (int i = 0; i < eventsArrayList.size(); i++) {
            Events events = eventsArrayList.get(i);
            System.out.println(events.getFavourite());
            if(events.getFavourite().equals("YES")) {
                id.add(events.getId());
                name.add(events.getName());
                image.add(events.getImage());
                category.add(events.getCategory());
                description.add(events.getDescription());
                experience.add(events.getExperience());
                favourite.add(events.getFavourite());
            }
        }
        mfavouriteAdapter.notifyDataSetChanged();
    }
}