package com.example.bollywood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Button mCreateRoom;
    private Button mJoinRoom;
    private DatabaseReference mDatabaseRef;
    private String mCurrentUserName;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor myEdit;
    private String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCreateRoom=findViewById(R.id.main_create_room);
        mJoinRoom=findViewById(R.id.main_join_room);

        mDatabaseRef= FirebaseDatabase.getInstance().getReference();

        mSharedPreferences = getPreferences(Context.MODE_PRIVATE);

        mCurrentUserName=mSharedPreferences.getString("UserName","");
        if(mCurrentUserName.equals("")){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Your Name");
            final EditText input = new EditText(getApplicationContext());
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(input.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_LONG);
                    }
                    else{
                        myEdit=mSharedPreferences.edit();
                        mCurrentUserName=input.getText().toString();
                        myEdit.putString("UserName",mCurrentUserName);
                        myEdit.apply();
                    }
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        //When User clicks on create room
        mCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Player>players=new ArrayList<>();
                players.add(new Player("Player 1",0));

                RandomString randomString=new RandomString(5);
                roomName=randomString.nextString();

                Room room = new Room(roomName,players,"",0);

//                Log.d("okk",room.getPlayers().elementAt(0).getName());

                DatabaseReference ref=mDatabaseRef.child("Rooms").child(roomName);
                ref.setValue(room);
            }
        });


        // When user clicks on join room
        mJoinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Ask User For the Room Name
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Enter Room Id");
                final EditText input = new EditText(getApplicationContext());
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(input.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),"Please Enter Room Id",Toast.LENGTH_LONG);
                        }
                        else{
                            roomName=input.getText().toString();


                             final ValueEventListener valueEventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Room room = dataSnapshot.getValue(Room.class);

                                    Log.d("room",room.getRoomName());

                                    if(room==null){
                                        Toast.makeText(getApplicationContext(),"Check your room id",Toast.LENGTH_LONG);
                                    }
                                    else{
                                        mDatabaseRef.child("Rooms").removeEventListener(this);
                                        Player player = new Player(mCurrentUserName,0);
                                        ArrayList<Player> players=room.getPlayers();
                                        players.add(player);
                                        room.setPlayers(players);
                                        mDatabaseRef.child("Rooms").child(roomName).setValue(room).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),"Joined the room",Toast.LENGTH_LONG);
                                            }
                                        });
                                    }
                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };
                            mDatabaseRef.child("Rooms").child(roomName).addListenerForSingleValueEvent(valueEventListener);
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

//
//                //Get the current user name
//                String UserName = "Current User Name";
//
//
//                mDatabaseRef.child("Rooms").child(roomName).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Room room = dataSnapshot.getValue(Room.class);
//
//                        Log.d("room",room.getRoomName());
//
//                        if(room==null){
//                            Toast.makeText(getApplicationContext(),"Check your room id",Toast.LENGTH_LONG);
//                        }
//                        else{
//                            Player player = new Player(mCurrentUserName,0);
//                            Vector<Player> players=room.getPlayers();
//                            players.add(player);
//                            room.setPlayers(players);
//                            mDatabaseRef.child("Rooms").child(roomName).setValue(room);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

            }
        });


    }
}