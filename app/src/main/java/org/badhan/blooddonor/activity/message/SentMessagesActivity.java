package org.badhan.blooddonor.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.R;
import org.badhan.blooddonor.adapter.MessagesAdapter;
import org.badhan.blooddonor.core.BaseAuthActivity;
import org.badhan.blooddonor.entity.Message;
import org.badhan.blooddonor.service.Message.SearchMessages;
import org.badhan.blooddonor.view.MainNavDrawer;

import java.util.ArrayList;


public class SentMessagesActivity extends BaseAuthActivity implements MessagesAdapter.OnMessageClickedListener {
    private static final int REQUEST_VIEW_MESSAGE = 1;

    private MessagesAdapter adapter;
    private ArrayList<Message> messages;
    private View progressBarFrame;

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_sent_messages);
        setNavDrawer(new MainNavDrawer(this));
        getSupportActionBar().setTitle("Sent Messages");

        adapter = new MessagesAdapter(this, this);
        messages = adapter.getMessages();

        progressBarFrame = findViewById(R.id.sent_messages_activity_progressBarFrame);

        RecyclerView recyclerView = findViewById(R.id.sent_messages_activity_message_list);
        recyclerView.setAdapter(adapter);

        if (isTablet)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        scheduler.postEveryMilliseconds(
                new SearchMessages.Request(true, false),
                1000*60*3);
    }

    @Override
    public void onMessageClicked(Message message) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(MessageActivity.EXTRA_MESSAGE, message);
        startActivityForResult(intent, REQUEST_VIEW_MESSAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_VIEW_MESSAGE
                || resultCode != MessageActivity.REQUEST_IMAGE_DELETED){
            return;
        }

        int messageId = data.getIntExtra(MessageActivity.RESULT_EXTRA_MESSAGE_ID, -1);
        if (messageId == -1)
            return;

        for (int i=0; i<messages.size(); i++){
            Message message = messages.get(i);
            if (message.getId() != messageId)
                continue;

            messages.remove(i);
            adapter.notifyItemRemoved(i);
            break;
        }
    }


    @Subscribe
    public void onMessagesLoaded(SearchMessages.Response response){
        response.showErrorToast(this);

        int oldMessagesSize = messages.size();
        messages.clear();
        adapter.notifyItemRangeRemoved(0, oldMessagesSize);

        messages.addAll(response.messages);
        adapter.notifyItemRangeInserted(0, messages.size());

        progressBarFrame.setVisibility(View.GONE);
    }
}
