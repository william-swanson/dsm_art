package com.willard5991.dsmarthub;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    private LinearLayout ll_top;
    private LinearLayout ll_bottom;
    private HorizontalScrollView horiz_top;
    private MainActivity mainActivity;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        mainActivity = (MainActivity) this.getActivity();

        ll_top = (LinearLayout) view.findViewById(R.id.linear_layout_top);
        horiz_top = (HorizontalScrollView) view.findViewById(R.id.horiz_top);
        ll_bottom = (LinearLayout) view.findViewById(R.id.linear_layout_bottom);

        ArrayList<exhibit> allExhibits = this.getExhibits();
        ArrayList<exhibit> nClosest = new ArrayList<exhibit>();

        //calculate closest exhibits within n miles
        double n = 3;
        double[] distances = new double[allExhibits.size()];
        for(int i = 0; i<allExhibits.size(); i++){
            distances[i] = allExhibits.get(i).calcCrowDistance(mainActivity.getLatitude(),mainActivity.getLongitude());
            if(distances[i] <= n){
                nClosest.add(allExhibits.get(i));
            }
        }
        nClosest = sortExhibits(nClosest);

        DiscoverArtworkAdapter adapterClosest = new DiscoverArtworkAdapter(this.getActivity(),nClosest);
        horiz_top.setAdapter(adapterClosest);

        return view;
    }

    public ArrayList<exhibit> getExhibits(){
        ArrayList<exhibit> exhibits = new ArrayList<exhibit>();

        RealmResults<exhibit> realmExhibits = mainActivity.realm.where(exhibit.class).findAll();
        for(exhibit ex : realmExhibits){
            exhibits.add(ex);
        }
        return exhibits;
    }

    public ArrayList<exhibit> sortExhibits(ArrayList<exhibit> exs){
        ArrayList<exhibit> sortedExs = new ArrayList<exhibit>();
        ArrayList<Double> distances = new ArrayList<Double>();

        //calculate the distances
        for(int i = 0; i<exs.size(); i++){
            distances.add(exs.get(i).calcCrowDistance(mainActivity.getLatitude(),mainActivity.getLongitude()));
        }

        while(exs.size() != 0){
            int minInd = 0;
            double minValue = distances.get(minInd);
            for(int i = 0; i< exs.size(); i++){
                if(distances.get(i) < minValue){
                    minValue = distances.get(i);
                    minInd = i;
                }
            }
            sortedExs.add(exs.get(minInd));
            exs.remove(minInd);
            distances.remove(minInd);
        }
        return sortedExs;
    }
}
