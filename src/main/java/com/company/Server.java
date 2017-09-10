package com.company;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Anja on 7. 09. 2017.
 */
public class Server {

    public static boolean prijava (String uporabnik){
        try {
            URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                    .addParameter("username", uporabnik).build();
            HttpResponse response = Request.Post(uri).execute().returnResponse();
            InputStream responseBody = null;

            if (response.getStatusLine().getStatusCode()==200) {
                return true;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        return false;
    }

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



    private static void sentGlobal (String posiljatelj, String sporocilo){
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

    private static void sentPrivate (String posiljatelj, String prejemnik, String sporocilo ){
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
}
