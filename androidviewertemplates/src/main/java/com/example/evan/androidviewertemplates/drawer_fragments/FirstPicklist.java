package com.example.evan.androidviewertemplates.drawer_fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//hello there teo
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.team_details.FirstPicklistAdapter;
import com.example.evan.androidviewertemplates.team_details.TeamDetailsActivity;
import com.example.evan.androidviewertemplates.utils.Util;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsAdapter;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsFragment;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jcodec.common.DictionaryCompressor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
    //Integer teamAmount = Integer.parseInt(dref.child("teamsList").toString());



    //oder from first to last picklist position
    //new arraylist with ordered
    //
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = getContext();

        dataBase = FirebaseDatabase.getInstance();

        dref = dataBase.getReference();
        dref.child("Teams").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.e("Datasnapshot", dataSnapshot.toString());

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



             /*   for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if(d != null) {
                        String name = d.child("number").getValue().toString();
                        teamList.add(name);
                    }
                }
                for(int i = 0; i < teamList.size(); i++) {
                    Integer name = Integer.parseInt(teamList.get(i));
                    sortedTeamList.add(name);
                }
                System.out.println(sortedTeamList);
                sortedTeamList = Arrays.sort(sortedTeamList); // <-- How come it won't work??
            }
*/
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

        /*
        //classic iterator example
        for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext(); ) {
            Map.Entry<String, Integer> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }*/


        return sortedMap;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.firstpicklist, container, false);
        Log.e("Checkpoint","onCreateView reached");

        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e("Checkpoint","onViewCreated reached");
        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, list);
        //listView.setAdapter(adapter);



}


}


    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.setAllSortConstantsFalse();
        setListAdapter(new FirstPickListAdapter(getActivity().getApplicationContext()));
    }

    public static class FirstPickListAdapter extends TeamRankingsAdapter {

        public FirstPickListAdapter(Context context) {

            super(context, "picklistPosition", "picklistPosition", true);
            Log.e("RankByNumber", String.valueOf(Constants.sortByTeamNumber));
            Constants.lastFourMatches = false;
            Constants.firstPickList = true;
        }
        @Override
        public Intent getTeamDetailsActivityIntent(){
            return new Intent(context, TeamDetailsActivity.class);
        }
    }
    @Override
    public Intent getTeamDetailsActivityIntent(){
        return new Intent(getActivity(), TeamDetailsActivity.class);
    }
}
*/
