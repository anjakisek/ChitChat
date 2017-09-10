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
    private JPanel uporabniki;
    private JTextPane uporabnikiOutput;
    private JButton javnoGumb;
    private JTextField zasebnoGumb;
    private boolean javno;
    private boolean zasebno;

    public ChitChatFrame() {
        super();
        setTitle("ChitChat");
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Prednastavljeno je na javno sporočilo
        this.javno = true;
        this.zasebno = false;


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
        odjava.setEnabled(false);
        vzdevek.add(prijava);
        vzdevek.add(odjava);


        // seznam uporabnikov
        this.uporabniki = new JPanel();
        JLabel napis2 = new JLabel ("Uporabniki: ");
        this.uporabnikiOutput = new JTextPane();
        uporabnikiOutput.setPreferredSize(new Dimension(100, 200));
        this.uporabnikiOutput.setEditable(false);

        uporabniki.setLayout(new BoxLayout(uporabniki, BoxLayout.Y_AXIS));
        uporabniki.add(napis2);
        uporabniki.add(uporabnikiOutput);
        uporabniki.setVisible(true);


        JScrollPane scrolPane = new JScrollPane(uporabniki);
        scrolPane.setPreferredSize(new Dimension(100, 300));

        GridBagConstraints uporabnikiCon = new GridBagConstraints();
        uporabnikiCon.fill = GridBagConstraints.BOTH;
        uporabnikiCon.weightx = 1.0;
        uporabnikiCon.weighty = 0.5;
        uporabnikiCon.gridx = 1;
        uporabnikiCon.gridy = 1;
        pane.add(uporabniki, uporabnikiCon);

        javnoGumb = new JButton("Javno");
        zasebnoGumb = new JTextField("Tu vpisi prejemnika");
        javnoGumb.addActionListener(this);
        javnoGumb.setEnabled(false);
        zasebnoGumb.addKeyListener(this);
        zasebnoGumb.setEditable(false);
        uporabniki.add(zasebnoGumb);
        uporabniki.add(javnoGumb);





        this.output = new JTextPane();
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setPreferredSize(new Dimension(350, 350));
        this.output.setEditable(false);
        GridBagConstraints outputConstraint = new GridBagConstraints();
        outputConstraint.fill = GridBagConstraints.BOTH;
        outputConstraint.weightx = 1.0;
        outputConstraint.weighty = 1.0;
        outputConstraint.gridx =0;
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
            //Ce je prijava uspesna
            if (Server.prijava(this.vzdevekInput.getText())){
                if (prvicZagnan) {
                    //preveriNovaSporocila.activate();
                    prvicZagnan=false;
                }
                this.prijava.setEnabled(false);
                this.odjava.setEnabled(true);
                this.input.setEditable(true);
                this.vzdevekInput.setEditable(false);
                this.javnoGumb.setEnabled(true);
                izpisiSporocilo(this.vzdevekInput.getText(), "Uspesno prijavljen!");
            } else {
                izpisiSporocilo(this.vzdevekInput.getText(), "Ni uspesno prijavljen!");
            }

        }else if(e.getSource() == odjava){
            if (Server.odjava(this.vzdevekInput.getText())){
                this.prijava.setEnabled(true);
                this.odjava.setEnabled(false);
                this.input.setEditable(false);
                this.vzdevekInput.setEditable(true);
                izpisiSporocilo(this.vzdevekInput.getText(), "Uspesno odjavljen!");

            } else {
                izpisiSporocilo(this.vzdevekInput.getText(), "Neuspesno odjavljen!");
            }

        } else if (e.getSource() == javnoGumb){
            javnoGumb.setEnabled(false);
            this.javno = true;
            this.zasebno = false;
            zasebnoGumb.setEditable(true);
        }
        }


    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.input) {
            if (e.getKeyChar() == '\n') {
                if (this.javno == true) {
                    this.izpisiSporocilo(this.vzdevekInput.getText(), this.input.getText());

                }

                this.input.setText("");
            }
        } else if (e.getSource() == this.zasebnoGumb){
            if (e.getKeyChar() == '\n'){
                this.zasebno = true;
                this.javno = false;
                this.zasebnoGumb.setEditable(false);
                this.javnoGumb.setEnabled(true);
            }
        }
    }

    public void sprejmiSporocilo(){
        //TODO
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


    public void izpisiUporabnika(String oseba){
        String aktivni = this.uporabnikiOutput.getText();
        this.uporabnikiOutput.setText(aktivni + oseba + "\n");
        }




    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}

