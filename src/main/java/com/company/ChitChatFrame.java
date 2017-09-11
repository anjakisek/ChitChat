package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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

    private PrejetoRobot robot;

    public ChitChatFrame() {
        super();
        setTitle("ChitChat");
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Prednastavljeno je na javno sporočilo
        this.javno = true;


        // Zgornje okence za vnos uporabniskega imena
        this.vzdevek = new JPanel();
        JLabel napis = new JLabel("Vzdevek");
        this.vzdevekInput = new JTextField(System.getProperty("user.name"), 40);
        this.vzdevekInput.setEditable(true);
        FlowLayout vzdevekFlow = new FlowLayout();
        vzdevek.setLayout(vzdevekFlow);
        vzdevek.add(napis);
        vzdevek.add(vzdevekInput);
        GridBagConstraints vzdevekConstraint = new GridBagConstraints();
        vzdevekConstraint.gridx = 0;
        vzdevekConstraint.gridy = 0;
        pane.add(vzdevek, vzdevekConstraint);
        vzdevek.addKeyListener(this);


        //gumba prijava odjava zgoraj
        prijava = new JButton("Prijava");
        odjava = new JButton("Odjava");
        prijava.addActionListener(this);
        odjava.addActionListener(this);
        odjava.setEnabled(false);
        vzdevek.add(prijava);
        vzdevek.add(odjava);


        // seznam uporabnikov desno
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

        //Gumba javno in zasebno, kamor se vpise uporabnika za zasebno sporocilo
        javnoGumb = new JButton("Javno");
        zasebnoGumb = new JTextField("Tu vpisi prejemnika");
        javnoGumb.addActionListener(this);
        javnoGumb.setEnabled(false);
        zasebnoGumb.addKeyListener(this);
        zasebnoGumb.setEditable(false);
        uporabniki.add(zasebnoGumb);
        uporabniki.add(javnoGumb);




        //Glavno okno za izpis sporocil

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


        // Okno na dnu, kamor se vnasa sporocila za posiljanje
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
    // izpise sporocilo v glavno okno

    public void izpisiSporocilo(String person, String message) {
        String chat = this.output.getText();
        this.output.setText(chat + person + ": " + message + "\n");
    }

    // odziva se na klike na gumbe
    public void actionPerformed(ActionEvent e) {

        //PRIJAVA
        if (e.getSource() == prijava) {
            //Ce je prijava uspesna
            if (Server.prijava(this.vzdevekInput.getText())){
                if (prvicZagnan) {
                    robot.activate();
                    prvicZagnan=false;
                }
                this.prijava.setEnabled(false);
                this.odjava.setEnabled(true);
                this.input.setEditable(true);
                this.vzdevekInput.setEditable(false);
                this.javnoGumb.setEnabled(true);
                izpisiSporocilo(this.vzdevekInput.getText(), "Uspesno prijavljen!");
            } else {
                //ce prijava ni uspesna
                izpisiSporocilo(this.vzdevekInput.getText(), "Ni uspesno prijavljen!");
            }

            //ODJAVA
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

            //Ce je gumb javno ugasnjen, posiljamo sporocila javno, ce je onemogoceno pisanje prejemnika, vpisanemu prejemniku posljemo zasebno sporocilo
        } else if (e.getSource() == javnoGumb){
            javnoGumb.setEnabled(false);
            this.javno = true;
            zasebnoGumb.setEditable(true);
        }
        }


        // ob kliku na enter poslje sporocilo
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.input) {
            if (e.getKeyChar() == '\n') {
                if (this.javno == true) {
                    this.izpisiSporocilo(this.vzdevekInput.getText(), this.input.getText());

                } else if (this.javno == false){
                    this.izpisiSporocilo(this.vzdevekInput.getText() + " to " + this.uporabnikiOutput.getText(), this.input.getText());
                }

                this.input.setText("");
            }
        } else if (e.getSource() == this.zasebnoGumb){
            if (e.getKeyChar() == '\n'){
                this.javno = false;
                this.zasebnoGumb.setEditable(false);
                this.javnoGumb.setEnabled(true);
            }
        }
    }

    //prejeta sporocila prepozna in jih izpise na ekran
    public void sprejmiSporocilo(){
        ArrayList<PrejetoSporocilo> seznam = Server.prejeto(this.vzdevekInput.getText());
        for (PrejetoSporocilo posta : seznam) {
            boolean global = posta.getGlobal();
            String recipient = posta.getRecipient();
            String sender = posta.getSender();
            String text = posta.getText();
            String sendAt = posta.getSentAt();

            if (global){
                izpisiSporocilo(sender, text + " (send at " + sendAt + ")");
            } else {
                izpisiSporocilo(sender + " to " + recipient, text + " (send at " + sendAt + ")");
            }

    }}






    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    //danega uporabnika izpise na ekran
    public void izpisiUporabnika(Uporabnik oseba){
        String username = oseba.getUsername();
        String lastActive = oseba.getLastActive();
        String aktivni = this.uporabnikiOutput.getText();
        this.uporabnikiOutput.setText(aktivni + username + " (last active: " + lastActive + " ) \n");
        }



        //pobrise uporabnike na ekranu
    public void pobrisiUporabnike(){
        this.uporabnikiOutput.setText("");
    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}

