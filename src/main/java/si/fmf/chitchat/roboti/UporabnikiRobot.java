package si.fmf.chitchat.roboti;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.apache.http.client.fluent.Request;
import si.fmf.chitchat.ChitChatFrame;
import si.fmf.chitchat.Uporabnik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        timer.scheduleAtFixedRate(this, 3000, 3000);
    }

    public UporabnikiRobot(ChitChatFrame chat) {
        this.chat = chat;
    }

    @Override
    public void run() {
        try {
            String responseBody = Request.Get("http://chitchat.andrej.com/users")
                    .execute()
                    .returnContent()
                    .asString();

            //Dobili smo seznam uporabnikov

            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new ISO8601DateFormat());

            TypeReference<List<Uporabnik>> t = new TypeReference<List<Uporabnik>>() {
            };
            ArrayList<Uporabnik> uporabniki = mapper.readValue(responseBody, t);

            chat.posodobiUporabnike(uporabniki);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
