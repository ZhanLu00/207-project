package ATM;
import ATM.Users.BankManager;
import ATM.Users.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class Atm {
    UserManager userManager;
    TimeManager timeManager;

    public Atm (UserManager userManager,TimeManager timeManager){
        this.userManager = userManager;
        this.timeManager = timeManager;
    }
    public User getUser(String username, String password){
        return userManager.getUser(username, password);
    }
    public void setUser(User user){
        userManager.addUser(user);
    }

    public void printText() throws IOException {

        BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter username: ");
        String username = kbd.readLine();
        System.out.println("Enter password: ");
        String password = kbd.readLine();
        User user = userManager.getUser(username, password);
        if (user != null) {
//            user.getText();
        } else{
            System.out.println("Incorrect username or password.");
            printText();
        }
    }

    public void save() {
        this.getTimeManager().save();
        this.getUserManager().save();
    }
}
