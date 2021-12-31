package com.example.astro2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Moon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Moon extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AstroViewModel astroViewModel;
    private TextView moonRise,wane,newMoon,fullMoon,moonPhase,synodicDay;

    String refresh,latitud,longitude;

    private AstroCalculator.MoonInfo moonInfo;

    public Moon() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Moon.
     */
    // TODO: Rename and change types and number of parameters
    public static Moon newInstance(String param1, String param2) {
        Moon fragment = new Moon();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_moon, container, false);

        moonRise=view.findViewById(R.id.moonRise_lay);
        wane=view.findViewById(R.id.wane_lay);
        newMoon=view.findViewById(R.id.newMoon_lay);
        fullMoon=view.findViewById(R.id.fullMoon_lay);
        moonPhase=view.findViewById(R.id.moonPhase_lay);
        synodicDay=view.findViewById(R.id.synodicDay_lay);
        refresh=new String("1");
        latitud=new String("0");
        longitude=new String("0");
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        astroViewModel=new ViewModelProvider(requireActivity()).get(AstroViewModel.class);

        astroViewModel.getAstro().observe(getViewLifecycleOwner(),item ->
        {
            moonInfo=astroViewModel.getAstro().getValue().getMoonInfo();


            moonRise.setText(moonInfo.getMoonrise().toString());
            wane.setText(moonInfo.getMoonset().toString());
            newMoon.setText(moonInfo.getNextNewMoon().toString());
            fullMoon.setText(moonInfo.getNextFullMoon().toString());
            moonPhase.setText(Double.toString(Math.round(moonInfo.getIllumination()*100))+"%");
            synodicDay.setText(Double.toString(Math.round(moonInfo.getAge()*10000)/10000));


        });

    }
}