package com.nitesh.firstapp.Homepage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nitesh.firstapp.R;

public class Doctor extends AppCompatActivity {

    //int[]IMAGES={};

    String[] NAMES={"Jenish", "Prantosh", "Bikash", "Bijay"};

    String[] TYPE={"Pagal", "Gofley", "BigAss", "Lamfo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        ListView listView=findViewById(R.id.listview);

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return NAMES.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.view,null);

            //ImageView image=findViewById(R.id.image);
            TextView name=findViewById(R.id.name);
            TextView type=findViewById(R.id.type);

            name.setText(NAMES[position]);
            type.setText(TYPE[position]);


            return convertView;
        }
    }

}
