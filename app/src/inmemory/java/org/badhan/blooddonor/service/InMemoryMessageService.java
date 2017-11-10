package org.badhan.blooddonor.service;

import com.squareup.otto.Subscribe;

import org.badhan.blooddonor.core.MyApplication;
import org.badhan.blooddonor.entity.Message;
import org.badhan.blooddonor.entity.UserDetails;
import org.badhan.blooddonor.service.Message.DeleteMessage;
import org.badhan.blooddonor.service.Message.SearchMessages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class InMemoryMessageService extends BaseInMemoryService {
    public InMemoryMessageService(MyApplication application) {
        super(application);
    }

    @Subscribe
    public void deleteMessage(DeleteMessage.Request request){
        DeleteMessage.Response response = new DeleteMessage.Response();
        response.messageId = request.messageId;

    }

    @Subscribe
    public void searchMessages(SearchMessages.Request request){
        SearchMessages.Response response = new SearchMessages.Response();
        response.messages = new ArrayList<>();

        UserDetails[] userDetails = new UserDetails[10];
        for (int i = 0; i< userDetails.length; i++){
            String stringId = Integer.toString(i);
            userDetails[i] = new UserDetails(
                    i,
                    true,
                    "User "+ stringId,
                    "user_"+stringId,
                    "https://lorempixel.com/64/64/people/"+stringId
            );
        }


        Random random = new Random();
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, -100); //100days before today

        for (int i = 0; i < 100; i++){
            boolean isFromMe;
            if (request.includeSentMessages && request.includeReceivedMessages){
                isFromMe = random.nextBoolean();
            }else {
                isFromMe = !request.includeReceivedMessages;
            }

            date.set(Calendar.MINUTE, random.nextInt(60*24));

            String numberString = Integer.toString(i);
            response.messages.add(new Message(
                     i,
                    (Calendar) date.clone(),
                    "Short message "+ numberString,
                    "Long message " + numberString,
                    "",
                    userDetails[random.nextInt(userDetails.length)],
                    isFromMe,
                    i>4
            ));

            postDelayed(response, 2000);
        }

    }
}
