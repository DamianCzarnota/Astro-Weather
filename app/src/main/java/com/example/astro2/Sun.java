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
 * Use the {@link Sun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Sun extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView sunrise,sunset,civilDown,civilTwilight,azymutWschod,azymutZachod;
    private AstroViewModel model;

    String refresh,latitud,longitude;
    private AstroCalculator.SunInfo sunInfo;

    public Sun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Sun.
     */
    // TODO: Rename and change types and number of parameters
    public static Sun newInstance(String param1, String param2) {
        Sun fragment = new Sun();
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
        View view= inflater.inflate(R.layout.fragment_sun, container, false);

        sunrise=view.findViewById(R.id.sunRise_lay);
        sunset=view.findViewById(R.id.sunSet_lay);
        azymutWschod=view.findViewById(R.id.azimuthRise_lay);
        azymutZachod=view.findViewById(R.id.azimuthSet_lay);
        civilDown=view.findViewById(R.id.civilDawn_lay);
        civilTwilight=view.findViewById(R.id.civilTwilight_lay);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model=new ViewModelProvider(requireActivity()).get(AstroViewModel.class);

        model.getAstro().observe(getViewLifecycleOwner(),item ->{
            sunInfo=model.getAstro().getValue().getSunInfo();

            sunrise.setText(sunInfo.getSunrise().toString());
            sunset.setText(sunInfo.getSunset().toString());
            azymutWschod.setText(Double.toString(sunInfo.getAzimuthRise()));
            azymutZachod.setText(Double.toString(sunInfo.getAzimuthSet()));
            civilDown.setText(sunInfo.getTwilightMorning().toString());
            civilTwilight.setText(sunInfo.getTwilightEvening().toString());

        });



    }
}