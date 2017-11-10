package org.badhan.blooddonor.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.core.BaseActivity;
import org.badhan.blooddonor.entity.Message;

import java.util.ArrayList;


public class MessagesAdapter extends RecyclerView.Adapter<MessageViewHolder> implements View.OnClickListener {
    private final LayoutInflater inflater;
    private final BaseActivity activity;
    private final OnMessageClickedListener listener;
    private final ArrayList<Message> messages;

    public MessagesAdapter(BaseActivity activity, OnMessageClickedListener listener) {
        this.activity = activity;
        this.listener = listener;

        messages = new ArrayList<>();
        inflater = activity.getLayoutInflater();

    }

    public ArrayList getMessages(){
        return messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_message, parent, false);
        view.setOnClickListener(this);
        return  new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.populate(activity, messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Message){
            Message message = (Message) view.getTag();
            listener.onMessageClicked(message);
        }
    }


    public interface OnMessageClickedListener{
        void onMessageClicked(Message message);
    }
}
