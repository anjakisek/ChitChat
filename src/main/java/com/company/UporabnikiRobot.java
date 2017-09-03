package com.company;

import org.apache.http.client.fluent.Request;

import javax.swing.text.BadLocationException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Anja on 3. 09. 2017.
 */
public class UporabnikiRobot extends TimerTask {
    private ChitChatFrame chat;

    //Ko ga aktiviramo, zacne meriti cas
    public void activate() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(this, 4000, 4000);
    }

    public UporabnikiRobot(ChitChatFrame chat) {
        this.chat = chat;
    }

    @Override
    public void run() {
        try {String responseBody = Request.Get("http://chitchat.andrej.com/users")
                .execute()
                .returnContent()
                .asString();
            //Dobili smo seznam uporabnikov
            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new ISO8601DateFormat());

            TypeReference<List<Uporabnik>> t = new TypeReference<List<Uporabnik>>() { };
            List<Uporabnik> sodelujoci = mapper.readValue(responseBody, t);
            chat.pocistiSodelujoce();

            for (Uporabnik oseba : sodelujoci) {
                chat.zapisiSodelujocega(oseba);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }
}
