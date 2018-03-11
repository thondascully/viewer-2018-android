package com.example.evan.androidviewertemplates.team_details;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.evan.androidviewertemplates.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Teo on 3/10/2018.
 */

public class FirstPicklistAdapter extends BaseAdapter {
    Context context;
    Map<String,Integer> teams;

    public FirstPicklistAdapter(Context context, Map<String,Integer> teams) {
        super();
        this.context = context;
        this.teams = teams;


    }
    @Override
    public int getCount(){
        return teams.size();
    }


    @Override
    public Object getItem(int position) {
        return getItem(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Message message = myArrayList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View myLayout = inflater.inflate(R.layout.firstpicklistcelllayout, null);
        TextView teamNumber = (TextView) myLayout.findViewById(R.id.teamNumber);
        String team = teams.get(position).toString();
        teamNumber.setText(team);

        TextView rankNumber = (TextView) myLayout.findViewById(R.id.rankNumber);

        rankNumber.setText("4343"); // <--- SET POSITION ON FIREBASE - PICKLIST
        convertView = myLayout;

        return convertView;
    }
}
















   /* @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.firstpicklistcelllayout, null);
        }

        ClipData.Item p = getItem(position);

        if (p != null) {
            Button upButton = (Button) v.findViewById(R.id.upButton);
            Button downButton = (Button) v.findViewById(R.id.downButton);
            TextView rankPlace = (TextView) v.findViewById(R.id.rankPlace);






            if (upButton != null) {
                upButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //int position=(Integer)view.getTag();

                    }
                });
            }

            if (downButton != null) {
                downButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //int position=(Integer)view.getTag();

                    }
                });
            }

            if (rankPlace != null) {
                rankPlace.setText(position);
            }
        }

        return v;
    }

    public int getPositionId(int position) {
        return position;
    }
*/
