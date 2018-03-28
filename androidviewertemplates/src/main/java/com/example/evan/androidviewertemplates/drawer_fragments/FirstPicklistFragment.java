package com.example.evan.androidviewertemplates.drawer_fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.firebase_classes.TeamTemplate;
import com.example.evan.androidviewertemplates.team_details.TeamDetailsActivity;
import com.example.evan.androidviewertemplates.utils.PasswordDialogFragment;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.example.evan.androidviewertemplates.utils.Util;
import com.example.evan.androidviewertools.firebase_classes.Team;
import com.example.evan.androidviewertools.search_view.SearchableFirebaseListAdapter;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsAdapter;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsFragment;
import com.example.evan.androidviewertools.utils.Constants;
import com.example.evan.androidviewertools.utils.firebase.FirebaseLists;

import java.util.List;
import java.util.Map;

/**
 * Created by Teo on 3/10/2018.
 */

public class FirstPicklistFragment extends TeamRankingsFragment
                                           implements PasswordDialogFragment.PasswordDialogListener {
    boolean authenticated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.setAllSortConstantsFalse();
        setListAdapter(new FirstPicklistAdapter(getActivity().getApplicationContext()));
    }

    /**
     * Created by citruscircuits on 1/27/16.
     */
    public static class FirstPicklistAdapter extends TeamRankingsAdapter {

        public FirstPicklistAdapter(Context context) {
            super(context, "calculatedData.firstPicklistRank", "calculatedData.firstPicklistRank", true);
        }

        @Override
        public void onClick(Integer teamNumber) {
            PasswordDialogFragment passwordDialogFragment = new PasswordDialogFragment();
            passwordDialogFragment.show();

        }

        @Override
        public Intent getTeamDetailsActivityIntent(){ // TODO Get rid of me
            return new Intent(context, TeamDetailsActivity.class);
        }
    }

    @Override
    public Intent getTeamDetailsActivityIntent(){
        return new Intent(getActivity(), TeamDetailsActivity.class);
    }

    @Override
    public boolean checkPassword(DialogFragment dialog) {
        return true; // TODO FIXME
    }

    @Override
    public void onPasswordCorrect(DialogFragment dialog) {
        this.authenticated = true;
    }

    @Override
    public void onPasswordFail(DialogFragment dialog) {
        // TODO Make a toast
    }
}