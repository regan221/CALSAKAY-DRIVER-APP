package com.example.calsakay_driver;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ConversationRecViewAdapter extends RecyclerView.Adapter<ConversationRecViewAdapter.ViewHolder> {

    List<ConversationModel> convo;

    public void setConvo(List<ConversationModel> convo) {
        this.convo = convo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ConversationRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
        ConversationRecViewAdapter.ViewHolder holder = new ConversationRecViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationRecViewAdapter.ViewHolder holder, int position) {

        if (convo.get(position).getMessageType().contentEquals("outgoing")){
            holder.tvMessageSent.setText(convo.get(position).getMessageContent());
            holder.tvConversationSentTime.setText(convo.get(position).getMessageTimestamp());
            holder.cvConversationSent.setVisibility(View.VISIBLE);
            holder.cvConversationRecieved.setVisibility(View.GONE);
        } else {
            holder.tvMessageRecieved.setText(convo.get(position).getMessageContent());
            holder.tvConversationRecievedTime.setText(convo.get(position).getMessageTimestamp());
            holder.cvConversationRecieved.setVisibility(View.VISIBLE);
            holder.cvConversationSent.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return this.convo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvMessageRecieved, tvMessageSent, tvConversationRecievedTime, tvConversationSentTime;
        private MaterialCardView cvConversationRecieved, cvConversationSent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvMessageSent = itemView.findViewById(R.id.tvMessageSent);
            this.tvMessageRecieved = itemView.findViewById(R.id.tvMessageRecieved);
            this.tvConversationRecievedTime = itemView.findViewById(R.id.tvConversationRecievedTime);
            this.tvConversationSentTime = itemView.findViewById(R.id.tvConversationSentTime);
            this.cvConversationRecieved = itemView.findViewById(R.id.cvConversationRecieved);
            this.cvConversationSent = itemView.findViewById(R.id.cvConversationSend);
        }
    }
}


