package si.fmf.chitchat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anja on 3. 09. 2017.
 */
public class Uporabnik {
    //Vsak uporabnik je svoj objekt z atributoma username in lastActive
    private String username;
    private String last_active;

    public Uporabnik() {

    }

    ;

    //ustvari uporabnika
    public Uporabnik(String username, String last_active) {
        this.username = username;
        this.last_active = last_active;

    }

    public String toString() {
        return username + " (last active " + new SimpleDateFormat("hh:mm").format(getDateActive()) + ")";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("username")
    public String getUsername() {
        return this.username;
    }

    public void setLastActive(String last_active) {
        this.last_active = last_active;
    }

    @JsonProperty("last_active")
    public String getLastActive() {
        return this.last_active;
    }


    public Date getDateActive(){
        ISO8601DateFormat df = new ISO8601DateFormat();
        try {
            return df.parse(last_active);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
