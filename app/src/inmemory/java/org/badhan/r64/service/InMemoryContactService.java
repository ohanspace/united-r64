package org.badhan.r64.service;

import com.squareup.otto.Subscribe;

import org.badhan.r64.core.MyApplication;
import org.badhan.r64.entity.ContactRequest;
import org.badhan.r64.entity.UserDetails;
import org.badhan.r64.service.contact.GetContactRequests;
import org.badhan.r64.service.contact.GetContacts;
import org.badhan.r64.service.contact.RespondToContactRequest;
import org.badhan.r64.service.contact.SendContactRequest;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class InMemoryContactService extends BaseInMemoryService {
    public InMemoryContactService(MyApplication application) {
        super(application);
    }


    @Subscribe
    public void getContacts(GetContacts.Request request){
        GetContacts.Response response = new GetContacts.Response();

        for (int i = 0; i<10; i++){
            response.contacts.add(createFakeUser(i, true));
        }

        postDelayed(response);
    }


    @Subscribe
    public void getContactRequests(GetContactRequests.Request request){
        GetContactRequests.Response response = new GetContactRequests.Response();
        response.requests = new ArrayList<>();

        for (int i = 0; i<3 ; i++){
            UserDetails userDetails = createFakeUser(i, false);
            ContactRequest contactRequest = new ContactRequest(i, request.fromMe, userDetails, new GregorianCalendar());
            response.requests.add(contactRequest);
        }

        postDelayed(response);
    }

    @Subscribe
    public void sendContactRequest(SendContactRequest.Request request){
        SendContactRequest.Response response = new SendContactRequest.Response();
        if (request.userId == 2){
            response.setOperationError("something bad happend for user 2");
        }

        postDelayed(response);
    }


    @Subscribe
    public void respondToContactRequest(RespondToContactRequest.Request request){
        RespondToContactRequest.Response response = new RespondToContactRequest.Response();
        postDelayed(response);

    }



    private UserDetails createFakeUser(int id, boolean isContact){
        String idString = Integer.toString(id);

        return  new UserDetails(
                id,
                isContact,
                "contact "+ idString,
                "contact"+idString,
                "http://lorempixel.com/64/64/people/"+idString
        );
    }
}
