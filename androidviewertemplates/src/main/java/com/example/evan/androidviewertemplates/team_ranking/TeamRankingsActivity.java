package com.example.evan.androidviewertemplates.team_ranking;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evan.androidviewertemplates.MainActivity;
import com.example.evan.androidviewertemplates.R;
import com.example.evan.androidviewertemplates.utils.SpecificConstants;
import com.example.evan.androidviewertools.ViewerActivity;
import com.example.evan.androidviewertools.team_ranking.TeamRankingsFragment;
import com.example.evan.androidviewertools.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TeamRankingsActivity extends ViewerActivity {
    TeamRankingsFragment fragment;

    static List<String> menuSortOptions;
    static Set<String> shouldBeNotReversed;

    static {
        menuSortOptions = Arrays.asList("number", "calculatedData.actualSeed", "calculatedData.firstPick", "calculatedData.secondPick");

        shouldBeNotReversed = new HashSet<>();
        shouldBeNotReversed.add("number");
        shouldBeNotReversed.add("calculatedData.actualSeed");
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_team_rankings);

        this.fragment = getFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.teamRankingsActivityRelativeLayout, this.fragment, "").commit();

        setActionBarColor();
    }

    public void setActionBarColor(){
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#65C423"));
        if(actionBar!=null) {
            actionBar.setBackgroundDrawable(colorDrawable);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        for (int i = 0; i < menuSortOptions.size(); ++i) {
            String optionName = "By " + SpecificConstants.KEYS_TO_TITLES.get(this.menuSortOptions.get(i));
            menu.add(Menu.NONE, i, i, optionName);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String key = this.menuSortOptions.get(id);
        this.fragment.orderByField(key, shouldBeNotReversed.contains(key));
        return true;
    }

    public TeamRankingsFragment getFragment() {
        TeamRankingsFragment fragment = new TeamRankingsActivityFragment();
        Bundle arguments = new Bundle();
        arguments.putString("field", getIntent().getStringExtra("field"));
        Log.e("field",getIntent().getStringExtra("field"));
        arguments.putInt("team", getIntent().getIntExtra("team", 0));
        arguments.putBoolean("displayValueAsPercentage", getIntent().getBooleanExtra("displayValueAsPercentage", false));
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public Intent getMainActivityIntent() {
        return new Intent(this, MainActivity.class);
    }
}
