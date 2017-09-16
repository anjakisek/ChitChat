package com.company;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Anja on 3. 09. 2017.
 */

// robot bo vsake toliko sprožil preverjanje, ali imamo kakšno novo sporočilo
public class PrejetoRobot extends TimerTask {

    private ChitChatFrame chat;
    private Timer timer;

    //Ko ga aktiviramo, zacne meriti cas
    public void activate() {
        timer.scheduleAtFixedRate(this, 3000, 3000);
    }

    public void deactivate(){
        timer.cancel();
    }

    public PrejetoRobot(ChitChatFrame chat) {
        this.chat = chat;
        this.timer = new Timer();

    }

    public void run(){
        chat.sprejmiSporocilo();

    }
}
