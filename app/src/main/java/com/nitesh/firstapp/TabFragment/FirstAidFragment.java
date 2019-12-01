package com.nitesh.firstapp.TabFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitesh.firstapp.Map.MapsActivity;
import com.nitesh.firstapp.R;
import com.nitesh.firstapp.TabFragment.ContentOfFirstAidFragment.Disease;
import com.nitesh.firstapp.TabFragment.ContentOfFirstAidFragment.Firstaid;
import com.nitesh.firstapp.TabFragment.ContentOfFirstAidFragment.HealthTips;
import com.nitesh.firstapp.TabFragment.ContentOfFirstAidFragment.Medicine;

/**
 * Created by Nitesh on 8/11/2018.
 */

public class FirstAidFragment extends Fragment  {
    private static FirstAidFragment INSTANCE = null;
    ImageView mfirstaid,mmedicine,msolution,mdiseaseinfo,mmap;
    TextView mfirsttext,mmedicnetext,msolutiontext,mdiseaseinfotext,mmaptext1;


    public FirstAidFragment() {
        // Required empty public constructor
    }

    public static FirstAidFragment getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new FirstAidFragment();
        return INSTANCE;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_firstaid, container, false);

        mfirstaid=view.findViewById(R.id.firstaid);
        mmedicine=view.findViewById(R.id.medicine);
        msolution=view.findViewById(R.id.solution);
        mdiseaseinfo=view.findViewById(R.id.info);
        mmap=view.findViewById(R.id.mapfragnment1);

        mfirsttext=view.findViewById(R.id.textfirstaid);
        mmedicnetext=view.findViewById(R.id.textmedicine);
        msolutiontext=view.findViewById(R.id.textsolution);
        mdiseaseinfotext=view.findViewById(R.id.textinfo);
        mmaptext1=view.findViewById(R.id.mapfragnmenttext1);

        mfirstaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Firstaid.class);
                startActivity(intent);
            }
        });

        mmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Medicine.class);
                startActivity(intent);
            }
        });

        msolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),HealthTips.class);
                startActivity(intent);
            }
        });

        mdiseaseinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Disease.class);
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
