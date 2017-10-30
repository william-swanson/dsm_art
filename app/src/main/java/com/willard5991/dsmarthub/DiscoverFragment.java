package com.willard5991.dsmarthub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment {

    private LinearLayout ll_top;
    private LinearLayout ll_bottom;
//    private HorizontalScrollView horiz_top;
    private MainActivity mainActivity;
    private RecyclerView recycler;
    private RecyclerView recyclerPopular;
    private Realm realm;

    public DiscoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover, container, false);

        mainActivity = (MainActivity) this.getActivity();
        realm = Realm.getDefaultInstance();

        //recycler replaces the horizontal list view
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerPopular = (RecyclerView) view.findViewById(R.id.recycler_popular);

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

        //logging the size of the array
        Log.v("nclosest", String.valueOf(nClosest.size()));

        //replaced with a RecyclerAdapter
        exhibit test = new exhibit();
        test.setName("test");
        test.setArtist("test artist");
        nClosest.add(test);
        exhibit test2 = new exhibit();
        test2.setName("test2");
        test2.setArtist("test2 artist");
        nClosest.add(test2);
        nClosest.add(test2);
        nClosest.add(test2);
        DiscoverRecyclerAdapter adapter = new DiscoverRecyclerAdapter(nClosest);
        recycler.setAdapter(adapter);

//        bulldogList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
//                final Bulldog bulldog = (Bulldog) adapterView.getItemAtPosition(i);
//                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
//                intent.putExtra("bulldog",bulldog.getId());
//                startActivity(intent);
//            }
//        });
        recycler.getLayoutManager();

        return view;
    }

    public ArrayList<exhibit> getExhibits(){
        ArrayList<exhibit> exhibits = new ArrayList<exhibit>();

        RealmResults<exhibit> realmExhibits = realm.where(exhibit.class).findAll();
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
