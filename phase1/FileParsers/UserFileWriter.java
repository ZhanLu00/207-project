package src.FileParsers;

import src.ATM.BankAccounts.BankAccount;
import src.ATM.TimeManager;
import src.ATM.Users.BankManager;
import src.ATM.Users.Client;
import src.ATM.Users.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class UserFileWriter {

    private String fileName;
    private ArrayList<User> users;

    public UserFileWriter(String fileName, ArrayList<User> users) {
        this.fileName = fileName;
        this.users = users;
    }

    public void write() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write("");
        StringBuilder fileOut = new StringBuilder();

        for (User user : users) {
            String type = getType(user);
            String username = user.getUsername();
            String password = user.getPassword();
            StringBuilder accounts = new StringBuilder();
            for (Integer accountNum : user.getAccounts()) {
                accounts.append(accountNum.toString()).append(",");
            }

            String accountsString;
            if (accounts.length() == 0) {
                accountsString = "";
            }
            else {
                accountsString = accounts.toString().substring(0, accounts.length() - 1);
            }
            fileOut.append(String.format("%s,%s,%s,%s\n",type,username,password,accountsString));
        }

        writer.append(fileOut);
        writer.close();
    }

    private String getType(User user) {
        if (user instanceof Client) {
            return "client";
        }
        else if (user instanceof BankManager) {
            return "manager";
        }

        return "user";
    }

}
