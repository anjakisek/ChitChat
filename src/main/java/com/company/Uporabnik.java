package com.company;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Anja on 3. 09. 2017.
 */
public class Uporabnik {
    //Vsak uporabnik je svoj objekt z atributoma username in lastActive
    private String username;
    private String lastActive;

    //ustvari uporabnika
    public Uporabnik(String username, String lastActive){
        this.username = username;
        this.lastActive = lastActive;

    }

    public String toString(){
        return "Uporabnik username " + username + " last active " + lastActive;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @JsonProperty("username")
    public String getUsername(){
        return this.username;
    }

    public void setLastActive(String lastActive){
        this.lastActive = lastActive;
    }

    @JsonProperty("lastActive")
    public String getLastActive(){
        return this.lastActive;
    }






}
