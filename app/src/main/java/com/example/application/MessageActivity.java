package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.application.bean.Message;
import com.example.application.bean.Utilisateur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private TextView profile;
    private EditText messageET;
    private ImageView sendIMG;
    private RecyclerView listMessage;
    private List<Message> messages = new ArrayList<>();
    private MessageAdapter adapter;
    private DatabaseReference ref;
    private DatabaseReference refIDTransaction;
    private String transactionID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        profile = findViewById(R.id.profile_tv);
        listMessage = findViewById(R.id.messages_rv);
        sendIMG = findViewById(R.id.send_img);
        messageET = findViewById(R.id.message_et);

        ref = FirebaseDatabase.getInstance().getReference("Message");
        refIDTransaction = FirebaseDatabase.getInstance().getReference("Transaction");

        Intent intent = getIntent();

        String userName = intent.getStringExtra("USER_NAME");
        String userID = intent.getStringExtra("USER_ID");

        profile.setText(userName);

        adapter = new MessageAdapter(messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listMessage.setLayoutManager(mLayoutManager);
        listMessage.setItemAnimator(new DefaultItemAnimator());
        listMessage.setAdapter(adapter);


        //String chatID = UserFactory.getUtilisateur().getId().concat("-").concat(userID);
        getIdTransaction(UserFactory.getUtilisateur().getId(), userID);


        sendIMG.setOnClickListener(view -> {

            String message = messageET.getText().toString().trim();

            if (!message.isEmpty() && transactionID != null){
                String id = ref.push().getKey();
                Message mMessage = new Message();
                mMessage.setId(id);
                mMessage.setContent(message);
                mMessage.setIdSender(UserFactory.getUtilisateur().getId());
                messages.add(mMessage);
                ref.child(transactionID).setValue(messages);
                messageET.setText("");
            }

        });

    }

    private void getMessages(String idUser){

        ref.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                messages.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    messages.add(message);

                }

                adapter.notifyDataSetChanged();
                listMessage.smoothScrollToPosition(adapter.getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getIdTransaction(String id1, String id2){
        refIDTransaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean existe = false;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String transaction = postSnapshot.getValue(String.class);

                    if(transaction.equals(id1.concat("-").concat(id2)) || transaction.equals(id2.concat("-").concat(id1))){
                        transactionID = transaction;
                        existe = true;
                        break;
                    }

                }

                if(!existe){
                    String id = refIDTransaction.push().getKey();
                    transactionID = id1.concat("-").concat(id2);
                    refIDTransaction.child(id).setValue(transactionID);
                }

                getMessages(transactionID);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}