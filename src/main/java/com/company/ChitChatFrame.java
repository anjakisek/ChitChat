package com.company;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.*;

public class ChitChatFrame extends JFrame implements ActionListener, KeyListener {

    private JTextPane output;
    private JTextField input;
    private JPanel vzdevek;
    private JTextField vzdevekInput;

    public ChitChatFrame() {
        super();
        setTitle("ChitChat");
        Container pane = this.getContentPane();
        pane.setLayout(new GridBagLayout());

        this.vzdevek = new JPanel();
        JLabel napis = new JLabel("Vzdevek");
        this.vzdevekInput = new JTextField(System.getProperty("user.name"), 40);
        FlowLayout vzdevekFlow = new FlowLayout();
        vzdevek.setLayout(vzdevekFlow);
        vzdevek.add(napis);
        vzdevek.add(vzdevekInput);
        GridBagConstraints vzdevekConstraint = new GridBagConstraints();
        vzdevekConstraint.gridx = 0;
        vzdevekConstraint.gridy = 0;
        pane.add(vzdevek, vzdevekConstraint);
        vzdevek.addKeyListener(this);



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
        //this.input.setEditable(false);
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
    public void addMessage(String person, String message) {
        String chat = this.output.getText();
        this.output.setText(chat + person + ": " + message + "\n");
    }

    public void actionPerformed(ActionEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == this.input) {
            if (e.getKeyChar() == '\n') {
                this.addMessage(this.vzdevekInput.getText(), this.input.getText());
                this.input.setText("");
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}

