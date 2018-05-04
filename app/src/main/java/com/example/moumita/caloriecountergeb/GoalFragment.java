package com.example.moumita.caloriecountergeb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adapter.GoalRecyclerAdapter;
import goaldatabase.Goal;
import goaldatabase.GoalOperations;

public class GoalFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private GoalOperations goalData;
    List<String> goalName, goalDes, goalStatus;
    List<Integer> goalImages,goalDuration,goalStreak;
    private TextView goalHeaderText;


    public GoalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goal, container, false);

        goalHeaderText = view.findViewById(R.id.goal_header);

        goalName = new ArrayList<>();
        goalDes = new ArrayList<>();
        goalDuration = new ArrayList<>();
        goalImages = new ArrayList<>();
        goalStreak = new ArrayList<>();
        goalStatus = new ArrayList<>();

        goalData = new GoalOperations(getContext());

        goalData.open();

        final List<Goal> goalList = goalData.getAllGoals();

        for(Goal a: goalList) {
            goalName.add(a.getGoal_name());
            goalDes.add(a.getGoal_description());
            goalImages.add(ImageID(a.getGoal_image()));
            goalDuration.add((int) a.getGoal_duration());
            goalStreak.add((int)a.getMy_goal_streak());
            goalStatus.add(a.getGoal_status());

        }

        goalData.close();

        recyclerView = view.findViewById(R.id.goal_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new GoalRecyclerAdapter(goalName, goalDes, goalImages, goalDuration, goalStreak, goalStatus);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public int ImageID(String image_name) {
        int resID = this.getResources().getIdentifier(image_name, "drawable", getActivity().getPackageName());
        return resID;
    }

}
