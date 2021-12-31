package com.example.astro2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText longitude,latitude,refresh;
    private AstroViewModel astroViewModel;
    Button button;



    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
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
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        latitude=view.findViewById(R.id.latitude);
        longitude=view.findViewById(R.id.longitude);
        refresh=view.findViewById(R.id.refresh);
        button=view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(latitude.getText().toString().length()==0||longitude.getText().toString().length()==0||refresh.getText().toString().length()==0)
                {
                    Toast.makeText(getActivity(),"Field must be set",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(longitude.getText().toString())>=180||Double.parseDouble(longitude.getText().toString())<=-180)
                {
                    Toast.makeText(getActivity(),"Value in Longitude must be between (-180,180)",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(latitude.getText().toString())>=90||Double.parseDouble(latitude.getText().toString())<=-90)
                {
                    Toast.makeText(getActivity(),"Value in Latitude must be between (-90,90)",Toast.LENGTH_SHORT).show();
                    return;
                }
                astroViewModel.setLongitude(Double.parseDouble(longitude.getText().toString()));
                astroViewModel.setLatitude(Double.parseDouble(latitude.getText().toString()));
                astroViewModel.setRefresh(Integer.parseInt(refresh.getText().toString()));

                Toast.makeText(getActivity(),"Settings saved",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        astroViewModel=new ViewModelProvider(requireActivity()).get(AstroViewModel.class);
    }
}