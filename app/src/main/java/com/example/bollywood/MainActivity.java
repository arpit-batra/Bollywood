package com.example.bollywood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mCreateRoom;
    private Button mJoinRoom;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreateRoom=findViewById(R.id.main_create_room);
        mJoinRoom=findViewById(R.id.main_join_room);

        mDatabaseRef= FirebaseDatabase.getInstance().getReference();

        //When User clicks on create room
        mCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Here I Have to get the name of the current User
                List<String> friends=new ArrayList<String>();
                friends.add("Current User ka naam");

                //Here I need to make a random name of the room
                String RoomName="Some Random String";

                Room room = new Room(RoomName,friends);

                DatabaseReference ref=mDatabaseRef.child("Rooms").push();
                ref.setValue(room);
            }
        });


        // When user clicks on join room
        mJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ask User For the Room Name
                String RoomName = "-M5kXVz8F9XNCRcdVs6d";

                //Get the current user name
                String UserName = "Current User Name";


                mDatabaseRef.child("Rooms").child(RoomName).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Room room = new Room();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }
}