package com.example.evan.androidviewertemplates.drawer_fragments;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.team_details.TeamDetailsActivity;
import com.example.evan.androidviewertemplates.utils.PasswordDialogFragment;
import com.example.evan.androidviewertools.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Created by Teo on 2/1/18.
 */
public class FirstPicklistFragmentOldBackup extends Fragment
                           implements PasswordDialogFragment.PasswordDialogListener {
    public static Boolean picklistValue = false;
    DatabaseReference dref;
    FirebaseDatabase dataBase;
    Context context;
    Boolean authenticated;
    public Map<Integer, String> teams = new HashMap<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myLayout = inflater.inflate(R.layout.firstpicklist, null);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.firstpicklist, null);
        final Button picklistEditButton = (Button) myLayout.findViewById(R.id.picklistEditButton);
        dataBase = FirebaseDatabase.getInstance();
        dref = dataBase.getReference();
        dref.child("picklist").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String teamNumber = dataSnapshot.getValue().toString();
                Integer teamPicklistPosition = Integer.parseInt(dataSnapshot.getKey());
                teams.put(teamPicklistPosition, teamNumber);
                Constants.picklistMap.put(teamPicklistPosition, teamNumber.toString());
                String key = new String();
                saveMap(context, key, Constants.picklistMap);
                if (Constants.picklistMap.size() >= 65) {
                    FirstPicklistAdapter adapter = new FirstPicklistAdapter(context, sortByValue(Constants.picklistMap));
                    listView.setAdapter(adapter);
                    picklistEditButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    });
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                            Integer teamNumberClicked = Integer.parseInt(Constants.picklistMap.get(position));
                            Intent teamDetailsViewIntent = getTeamDetailsActivityIntent();
                            teamDetailsViewIntent.putExtra("teamNumber", teamNumberClicked);
                            teamDetailsViewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(teamDetailsViewIntent);
                        return true;
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }
            public Intent getTeamDetailsActivityIntent(){
                return new Intent(getActivity(), TeamDetailsActivity.class);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirstPicklistAdapter adapter = new FirstPicklistAdapter(context, sortByValue(Constants.picklistMap));
                listView.setAdapter(adapter);
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

        PasswordDialogFragment passwordDialogFragment = new PasswordDialogFragment();
        ListView listView = (ListView) myLayout.findViewById(R.id.listview);
        listView.setAdapter(new FirstPicklistAdapter());




        return myLayout;
    }
    private Map<Integer, String> sortByValue(Map<Integer, String> teams) {
        List<Map.Entry<Integer, String>> list = new LinkedList<Map.Entry<Integer, String>>(teams.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
            public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Map<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();
        for (Map.Entry<Integer, String> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    public static <Integer, String> Integer getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Constants.picklistMap.clear();
    }
    public static void saveMap(Context context, String key, Map<Integer, String> inputMap) {
        SharedPreferences pSharedPref = context.getSharedPreferences("MyPREF", Context.MODE_PRIVATE);
        if (pSharedPref != null) {
            Gson gson = new Gson();
            String hashMapString = gson.toJson(inputMap);
            //save in shared prefs
            pSharedPref.edit().putString(key, hashMapString).apply();
        }
    }

}
