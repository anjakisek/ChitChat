package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpResponse;
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
    public static boolean prijava (String uporabnik) {
        try {
            URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                    .addParameter("username", uporabnik).build();
            HttpResponse response = Request.Post(uri).execute().returnResponse();
            System.out.println(response.getStatusLine());
            InputStream responseBody = null;

            if (response.getStatusLine().getStatusCode()==200) {
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
    public static boolean odjava (String uporabnik) {
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



    //POSILJANJE JAVNEGA SPOROCILA
    public static void sentGlobal (String posiljatelj, String sporocilo){
        try{
            URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", posiljatelj)
                    .build();
            String message = "{ \"global\" : true, \"text\" : \"" + sporocilo + "\" }";


            String responseBody = Request.Post(uri)
                    .bodyString(sporocilo, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    //POSILJANJE ZASEBNEGA SPOROCILA
    public static void sentPrivate (String posiljatelj, String prejemnik, String sporocilo ){
        try{
            URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", posiljatelj)
                    .build();
            String message = "{ \"global\" : false, \"recipient\" : " + prejemnik + ", \"text\" : \"" + sporocilo + "\" }";


            String responseBody = Request.Post(uri)
                    .bodyString(sporocilo, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();

            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    // SPREJME PREJETA SPOROCILA
    public static ArrayList<PrejetoSporocilo> prejeto(String prejemnik) {
        try{
            URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
                    .addParameter("username", prejemnik)
                    .build();

            String responseBody = Request.Get(uri)
                    .execute()
                    .returnContent()
                    .asString();

            ObjectMapper mapper = new ObjectMapper();
            mapper.setDateFormat(new ISO8601DateFormat());

            TypeReference<List<PrejetoSporocilo>> t = new TypeReference<List<PrejetoSporocilo>>() { };
            //ArrayList<PrejetoSporocilo> prejetaSporocila = mapper.readValue(responseBody, t);

            return mapper.readValue(responseBody, t);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;


    }}

