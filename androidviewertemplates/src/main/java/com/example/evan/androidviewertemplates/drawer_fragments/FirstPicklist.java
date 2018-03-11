package com.example.evan.androidviewertemplates.drawer_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.team_details.FirstPicklistAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Teo on 2/1/18.
 */
public class FirstPicklist extends Fragment {
    DatabaseReference dref;
    FirebaseDatabase dataBase;
    public Integer counterValue = 0;
    ArrayList<String> teamList = new ArrayList<>();
    ArrayList<Integer> sortedTeamList = new ArrayList<>();
    ArrayList<DataSnapshot> list = new ArrayList<>();
    Context context;
    public Map<String, Integer> teams = new HashMap<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        dataBase = FirebaseDatabase.getInstance();
        dref = dataBase.getReference();
        dref.child("Teams").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> teamObject = (Map<String, Object>)dataSnapshot.getValue();
                String teamNumber = teamObject.get("number").toString();
                Integer teamPicklistPosition = Integer.parseInt(teamObject.get("picklistPosition").toString());
                teams.put(teamNumber, teamPicklistPosition);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private Map<String, Integer> sortByValue(Map<String, Integer> teams) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(teams.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.firstpicklist, container, false);
        Log.e("Checkpoint","onCreateView reached");
        ListView listView = (ListView)rootView.findViewById(R.id.listview);
        FirstPicklistAdapter adapter = new FirstPicklistAdapter(context, sortByValue(teams));
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e("Checkpoint","onViewCreated reached");
    }
}

