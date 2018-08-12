package com.nitesh.firstapp.Homepage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nitesh.firstapp.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HospitalList extends AppCompatActivity {
    private TextView chat_conversation;

    private String user_name,room_name;
    private DatabaseReference root ;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        chat_conversation = (TextView) findViewById(R.id.textViewH);

//        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle(" Room - "+room_name);

        root = FirebaseDatabase.getInstance().getReferenceFromUrl("https://firstapp-1966b.firebaseio.com/Element/Hospital").child(room_name);

        Map<String,Object> map = new HashMap<String, Object>();
        root.updateChildren(map);

        DatabaseReference message_root = root.child(room_name);
        message_root.updateChildren(map);


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                append_chat_conversation(dataSnapshot);

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
    private String catname,elementaddress,elementclose,elemnetopen,elementdesc,elementid,elementphone,elementname;

    private void append_chat_conversation(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()){

            catname = (String) ((DataSnapshot)i.next()).getValue();
            elementaddress = (String) ((DataSnapshot)i.next()).getValue();
            elementclose = (String) ((DataSnapshot)i.next()).getValue();
            elementdesc = (String) ((DataSnapshot)i.next()).getValue();
            elementid = (String) ((DataSnapshot)i.next()).getValue();
            elemnetopen = (String) ((DataSnapshot)i.next()).getValue();
            elementphone = (String) ((DataSnapshot)i.next()).getValue();
            elementname = (String) ((DataSnapshot)i.next()).getValue();



            chat_conversation.append(catname +" :  "+elementname +" \n");
//                    "" + "\n "+elementphone +" \n "
//                    +elemnetopen +" \n "+elementid +" \n "+elementdesc +" \n "+elementaddress +" \n "
//                    +elementclose +"\n");
        }


    }
}
