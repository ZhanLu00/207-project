package ATM;


import ATM.ActionHandler.ActionHandler;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class AtmTesting {

    public static void main(String[] args) throws IOException {
        String userFileName = "phase2/resources/userfiles.txt";
        String accountFileName = "phase2/resources/accounts.txt";
        String atmFileName = "phase2/resources/Atminfo.txt";
        String alertsFileName = "phase2/resources/alerts.txt";
        String transactionsFileName = "phase2/resources/transactions.txt";
        String messagesFileName = "phase2/resources/messages.txt";
        String accountRequestFileName = "phase2/resources/accountRequests.txt";
        String clientRequestFileName = "phase2/resources//clientRequests.txt";

        Atm atm = new Atm(userFileName, accountFileName, atmFileName, alertsFileName, transactionsFileName,
                messagesFileName, accountRequestFileName, clientRequestFileName);

        ATMGUI gui = new ATMGUI();

        JFrame frame = new JFrame("src/ATM");
        frame.setResizable(false);
        frame.setLocation(500,300);
        frame.setSize(900, 500);
        frame.setContentPane(gui.root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gui.welcomePage.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    atm.save();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
            }
        });

        ActionHandler actionHandler = new ActionHandler(atm, gui);
        actionHandler.initViewer();
        actionHandler.initOperator();
    }
}
