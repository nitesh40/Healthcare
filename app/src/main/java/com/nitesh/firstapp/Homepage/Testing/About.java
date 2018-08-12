package com.nitesh.firstapp.Homepage.Testing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitesh.firstapp.R;

import java.util.ArrayList;
import java.util.List;

public class About extends AppCompatActivity {
    private static final String SECTOR_KEY ="sector";
    private static final String TAG ="12" ;
    //Button maddnew;
    DatabaseReference databaseReference;


    RecyclerView recyclerView;
    Myadapter myadapter;
    List<Form> listdata;
    FirebaseDatabase FDB;
    //DatabaseReference DBR;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
       // maddnew=findViewById(R.id.addnew);



        recyclerView = findViewById(R.id.recyclerView1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));

        listdata=new ArrayList<>();
        myadapter=new Myadapter(listdata);
        FDB=FirebaseDatabase.getInstance();

        //// progress dialog

        progressDialog = new ProgressDialog(About.this);
        progressDialog.setMessage("Loading ....");
        progressDialog.setCancelable(true);
        progressDialog.show();


        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://firstapp-1966b.firebaseio.com/FirstAid");

//        maddnew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addcate();
//            }
//        });

        //getcate();
        getdatafirebase();


    }


    void getdatafirebase(){

        databaseReference=FDB.getReferenceFromUrl("https://firstapp-1966b.firebaseio.com/FirstAid");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Form data=dataSnapshot.getValue(Form.class);
                listdata.add(data);
                recyclerView.setAdapter(myadapter);
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{
        //Context context;
        List<Form>listarray;

        public Myadapter(List<Form> listarray) {
            this.listarray = listarray;
        }

        @NonNull
        @Override
        public Myadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reclycer_view_form,parent,false);
            return new Myadapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Myadapter.MyViewHolder holder, int position) {

            Form data=listarray.get(position);


            holder.txt_item_name.setText(data.getMname());
            holder.txt_item_desc.setText(data.getMid());
            holder.txt_item_description.setText(data.getMdescription());

//            holder.deleteitem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Toast.makeText(About.this,"Recently not in use",Toast.LENGTH_LONG).show();
//
//                }
//            });
//
//            holder.edititem.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(About.this,"Recently not in use",Toast.LENGTH_LONG).show();
//                }
//            });

        }

        @Override
        public int getItemCount() {
            return listarray.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_item_desc,txt_item_name,txt_item_description;
//            Button deleteitem,edititem;

            public MyViewHolder(View itemView) {
                super(itemView);

                txt_item_desc = itemView.findViewById(R.id.itemid);
                txt_item_name = itemView.findViewById(R.id.itemname);
                txt_item_description=itemView.findViewById(R.id.itemdesc);
//                deleteitem=itemView.findViewById(R.id.delete);
//                edititem=itemView.findViewById(R.id.edit);
            }
        }
    }



//    private void addcate() {
//
//        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Add Sector");  // 6796
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View maddcate = inflater.inflate(R.layout.form, null);
//
//        final EditText mname=maddcate.findViewById(R.id.nnmae);
//        final Button msubmit=maddcate.findViewById(R.id.submit);
////        final EditText mdescription=maddcate.findViewById(R.id.ddscription);
//
//        //final Button mcancel=maddcate.findViewById(R.id.cancel);
//
//        msubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mname.getText().toString().trim().isEmpty()) {
//                    mname.setError("Sector name required !!");
//                    mname.requestFocus();
//                    return;
//                }
//
////                if (mdescription.getText().toString().trim().isEmpty()) {
////                    mdescription.setError("Sector name required !!");
////                    mdescription.requestFocus();
////                    return;
////                }
//
//
//
//                String id = databaseReference.push().getKey();
//                Form form=new Form();
//                form.setName(mname.getText().toString());
//                form.setId(id);
////                form.setDescription(mdescription.getText().toString());
//                databaseReference.child(id).setValue(form);
//
//                Toast.makeText(About.this,"Sector Created",Toast.LENGTH_SHORT).show();
//
//                Intent inte=new Intent(About.this,About.class);
//                startActivity(inte);
//
//
//            }
//        });
//        dialog.setView(maddcate);
//
//        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
}
