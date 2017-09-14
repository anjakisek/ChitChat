package com.company;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anja on 7. 09. 2017.
 */
public class Server {

    //PRIJAVA
    public static boolean prijava(String uporabnik) {
        try {
            URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                    .addParameter("username", uporabnik).build();
            HttpResponse response = Request.Post(uri).execute().returnResponse();
            System.out.println(response.getStatusLine());
            InputStream responseBody = null;

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("vse je ok");
                return true;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        System.out.println("neka napaka je");
        return false;
    }

    //ODJAVA
    public static boolean odjava(String uporabnik) {
        try {
            URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                    .addParameter("username", uporabnik)
                    .build();
            HttpResponse response = Request.Delete(uri).execute().returnResponse();
            InputStream responseBody = null;

            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }


        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        return false;
    }


    //POSILJANJE ZASEBNEGA SPOROCILA
    public static void sendPrivateMessage(String sender, String receiver, String content) {
        ObjectMapper mapper = new ObjectMapper();

        URI uri = null;
        String responseBody = null;
        try {
            uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", sender)
                    .build();

            String message = mapper.writeValueAsString(new PoslanoSporocilo(receiver, content));

            responseBody = Request.Post(uri)
                    .bodyString(message, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    //POSILJANJE JAVNEGA SPOROCILA
    public static void sendGlobalMessage(String sender, String content) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new ISO8601DateFormat());

        URI uri = null;
        String responseBody = "";
        try {
            uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", sender)
                    .build();
            String message = mapper.writeValueAsString(new PoslanoSporocilo(content));

            responseBody = Request.Post(uri)
                    .bodyString(message, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static ArrayList<PrejetoSporocilo> prejeto(String me) throws URISyntaxException, ClientProtocolException, IOException {

        URI uri = null;
        uri = new URIBuilder("http://chitchat.andrej.com/messages")
                .addParameter("username", me)
                .build();
        String responseBody = Request.Get(uri)
                .execute()
                .returnContent()
                .asString();
        return vSeznamSporocil(responseBody);

    }


    private static ArrayList<PrejetoSporocilo> vSeznamSporocil(String neurejenoSporocilo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new ISO8601DateFormat());

        TypeReference<List<PrejetoSporocilo>> t = new TypeReference<List<PrejetoSporocilo>>() {
        };
        ArrayList<PrejetoSporocilo> prejetaSporocila = mapper.readValue(neurejenoSporocilo, t);

        return prejetaSporocila;
    }
}




