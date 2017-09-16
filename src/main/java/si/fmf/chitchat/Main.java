package si.fmf.chitchat;

import si.fmf.chitchat.roboti.UporabnikiRobot;

public class Main {

    public static void main(String[] args) {
        ChitChatFrame chatFrame = new ChitChatFrame();
        UporabnikiRobot robot = new UporabnikiRobot(chatFrame);
        robot.activate();
        chatFrame.pack();
        chatFrame.setVisible(true);
    }
}