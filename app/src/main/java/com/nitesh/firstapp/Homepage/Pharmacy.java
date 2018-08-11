package com.nitesh.firstapp.Homepage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.nitesh.firstapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Pharmacy extends AppCompatActivity {

    ListView msearch;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        msearch=findViewById(R.id.search);

        ArrayList<String> arraySearch= new ArrayList<>();
        arraySearch.addAll(Arrays.asList(getResources().getStringArray(R.array.search)));

        adapter=new ArrayAdapter<String>(
          Pharmacy.this,android.R.layout.simple_list_item_1,arraySearch
        );

        msearch.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView= (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String C) {
                adapter.getFilter().filter(C);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
