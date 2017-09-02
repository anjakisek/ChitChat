package com.company;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


import javax.swing.*;

public class ChitChatFrame extends JFrame implements ActionListener, KeyListener {

    private boolean prvicZagnan=true;
    private JTextPane output;
    private JTextField input;
    private JPanel vzdevek;
    private JTextField vzdevekInput;
    private JButton prijava;
    private JButton odjava;

    public ChitChatFrame() {
        super();
        setTitle("ChitChat");
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());

        this.vzdevek = new JPanel();
        JLabel napis = new JLabel("Vzdevek");
        this.vzdevekInput = new JTextField(System.getProperty("user.name"), 40);
        this.vzdevekInput.setEditable(false);
        FlowLayout vzdevekFlow = new FlowLayout();
        vzdevek.setLayout(vzdevekFlow);
        vzdevek.add(napis);
        vzdevek.add(vzdevekInput);
        GridBagConstraints vzdevekConstraint = new GridBagConstraints();
        vzdevekConstraint.gridx = 0;
        vzdevekConstraint.gridy = 0;
        pane.add(vzdevek, vzdevekConstraint);
        vzdevek.addKeyListener(this);

        //gumba prijava odjava
        prijava = new JButton("Prijava");
        odjava = new JButton("Odjava");
        prijava.addActionListener(this);
        odjava.addActionListener(this);
        odjava.setEnabled(true);
        vzdevek.add(prijava);
        vzdevek.add(odjava);





        this.output = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setPreferredSize(new Dimension(350, 350));
        this.output.setEditable(false);
        GridBagConstraints outputConstraint = new GridBagConstraints();
        outputConstraint.fill = GridBagConstraints.BOTH;
        outputConstraint.weightx = 1.0;
        outputConstraint.weighty = 1.0;
        outputConstraint.gridx = 0;
        outputConstraint.gridy = 1;
        pane.add(scrollPane, outputConstraint);


        this.input = new JTextField(40);
        this.input.setEditable(false);
        GridBagConstraints inputConstraint = new GridBagConstraints();
        inputConstraint.fill = GridBagConstraints.BOTH;
        inputConstraint.weightx = 1.0;
        inputConstraint.gridx =0;
        inputConstraint.gridy = 3;
        pane.add(input, inputConstraint);
        input.addKeyListener(this);

        //Ko se okno odpre, je fokus na polju za vnos sporoèila.
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                input.requestFocusInWindow();
            }
        });

    }

    /**
     * @param person - the person sending the message
     * @param message - the message content
     */

    public void izpisiSporocilo(String person, String message) {
        String chat = this.output.getText();
        this.output.setText(chat + person + ": " + message + "\n");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prijava) {
            try {
                URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                        .addParameter("username", this.vzdevekInput.getText()).build();
                HttpResponse response = Request.Post(uri).execute().returnResponse();
                InputStream responseBody = null;

                if (response.getStatusLine().getStatusCode()==200) {
                    //Ce je prijava uspesna
                    if (prvicZagnan) {
                        //preveriNovaSporocila.activate();
                        prvicZagnan=false;
                    }
                    this.prijava.setEnabled(false);
                    this.odjava.setEnabled(true);
                    this.input.setEditable(true);
                    this.vzdevekInput.setEditable(false);
                    //this.zasebno.setEditable(true);
                    responseBody=response.getEntity().getContent();
                }else if(response.getStatusLine().getStatusCode()==403){
                    //Neuspešna prijava
                    responseBody=response.getEntity().getContent();
                }
                this.izpisiSporocilo("", getStringFromInputStream(responseBody));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            //} catch (BadLocationException e1) {
             //   e1.printStackTrace();
            }

        }else if(e.getSource() == odjava){
            try {
                URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                        .addParameter("username", this.vzdevekInput.getText())
                        .build();
                String responseBody = Request.Delete(uri).execute().returnContent().asString();
                this.prijava.setEnabled(true);
                this.odjava.setEnabled(false);
                this.input.setEditable(false);
                this.vzdevekInput.setEditable(true);
                //this.zasebno.setEditable(false);
                this.izpisiSporocilo( "", responseBody );

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            //} catch (BadLocationException e1) {
             //   e1.printStackTrace();
            }

        }
        }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.input) {
            if (e.getKeyChar() == '\n') {
                this.izpisiSporocilo(this.vzdevekInput.getText(), this.input.getText());
                this.input.setText("");
            }
        }
    }




    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();}

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}

