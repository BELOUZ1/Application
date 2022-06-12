package com.example.application;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.bean.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messageitem, parent, false);
        return new MessageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.message.setText(message.getContent());

        if(message.getIdSender().equals(UserFactory.getUtilisateur().getId())){
            holder.layout.setGravity(Gravity.RIGHT);
        }else{
            holder.layout.setGravity(Gravity.LEFT);
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView message;
        private LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.message_item_tv);
            layout = view.findViewById(R.id.message_ll);
        }
    }
}
