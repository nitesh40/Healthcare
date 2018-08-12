package com.nitesh.firstapp.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitesh.firstapp.Homepage.Ambulance;
import com.nitesh.firstapp.Homepage.Doctor;
import com.nitesh.firstapp.Homepage.Hospital;
import com.nitesh.firstapp.Homepage.Pharmacy;
import com.nitesh.firstapp.Map.MapsActivity;
import com.nitesh.firstapp.R;

/**
 * Created by Nitesh on 8/11/2018.
 */

public class HomFragment extends Fragment {
    private static HomFragment INSTANCE = null;
    ImageView mhospital,mpharmacy,mdoctor,mambulance,mmap;
    TextView mhospitaltext,mpharmacytext,mdoctortext,mambulancetext,mmaptext;



    public HomFragment() {
        // Required empty public constructor
    }

    public static HomFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new HomFragment();
        return INSTANCE;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        mhospital=view.findViewById(R.id.hospitalfragment);
        mpharmacy=view.findViewById(R.id.pharmacyfragment);
        mdoctor=view.findViewById(R.id.doctorfragment);
        mambulance=view.findViewById(R.id.ambulancefragment);
        mmap=view.findViewById(R.id.mapfragnment);

        mhospitaltext=view.findViewById(R.id.texthospital);
        mpharmacytext=view.findViewById(R.id.textpharmacy);
        mdoctortext=view.findViewById(R.id.textdoctor);
        mambulancetext=view.findViewById(R.id.textambulance);
        mmaptext=view.findViewById(R.id.mapfragnmenttext);

        mhospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Hospital.class);
                startActivity(intent);
            }
        });

        mpharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Pharmacy.class);
                startActivity(intent);
            }
        });

        mdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Doctor.class);
                startActivity(intent);
            }
        });

        mambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Ambulance.class);
                startActivity(intent);
            }
        });

        mmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}
