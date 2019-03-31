package src.ATM;

import src.ATM.ActionHandler.BankManagerActionHandler;
import src.ATM.ActionHandler.ClientActionHandler;
import src.ATM.Users.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AtmApplication {


    // tet

    private static BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        // todo add data base reader
//        Atm atm = new Atm("BankUsers.txt", "BankAccounts.txt", "AtmInfo.txt");
//        atm.printText();
//        atm.save();

        // TODO add transaction & messages file
        //  delete acc request and client request bc bank manager will store it?
        Atm atm = new Atm("phase2/src/ATM/testfiles/BankUsers.txt","phase2/src/ATM/testfiles/BankAccounts.txt",
                "phase2/src/ATM/testfiles/AtmInfo.txt", "phase2/src/ATM/testfiles/alerts.txt",
                "phase2/src/ATM/testfiles/transactions.txt","phase2/src/ATM/testfiles/messages.txt", "", "");

        while(true) {
            String username = getStringFromUser("Username (-1 to exit): ");

            if (username.equals("-1")) {
                break;
            }

            String password = getStringFromUser("Password: ");

            User user = atm.getUser(username, password);

            if (user instanceof BankManager) {
                BankManagerActionHandler actionHandler = new BankManagerActionHandler(atm);
                actionHandler.displayCommandLineInterface();
            } else if (user instanceof  Client) {
                ClientActionHandler actionHandler = new ClientActionHandler((Client)user, atm);
                actionHandler.displayCommandLineInterface();
            }
        }

        atm.save();
    }

    private static int getIntFromUser(String display) throws IOException {
        System.out.print(display);
        return Integer.parseInt(kbd.readLine());
    }

    private static String getStringFromUser(String display) throws IOException {
        System.out.print(display);
        return kbd.readLine();
    }


}