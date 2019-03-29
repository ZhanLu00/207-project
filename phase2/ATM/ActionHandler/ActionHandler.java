package ATM.ActionHandler;

import ATM.*;
import ATM.Users.BankManager;
import ATM.Users.Client;
import ATM.Users.User;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActionHandler {

    /**
     * Private attributes
     */

    // data/model
    private AccountManager accountManager;

    private TransactionManager transactionManager;

    private UserManager userManager;

    private BillManager billManager;

    // viwer/display
    private ATMGUI viewer;

    // sub-action handler
    private BankInspectorActionHandler bankInspectorActionHandler;

    private BankManagerActionHandler bankManagerActionHandler;

    private ClientActionHandler clientActionHandler;


    // attributes for execution
    private String userType;

    private User currentUser;

    private int runStage;



    /**
     * Initialize attributes
     */
    public ActionHandler(Atm atm,  ATMGUI atmgui){
        this.accountManager = atm.getAccountManager();
        this.userManager = atm.getUserManager();
        this.billManager = atm.getBillManager();
        this.transactionManager = atm.getTransactionManager();
        this.viewer = atmgui;
    }

    /**
     * Initialize viewer
     */
    public void initViewer() throws IOException {
        // this will be the title page
        viewer.init();
    }

    /**
     * Initialize operator
     */
    public void initOperator(){
        this.runStage = 100;
        // basic on the current page, add listener
        viewer.newUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewer.changePage(viewer.welcomePage, viewer.newUserPage);
                runStage = 101;
            }
        });
        viewer.returningUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewer.changePage(viewer.welcomePage, viewer.returningUserPage);
                runStage = 102;
            }
        });

        // start from the main page


        // add listener to buttons, make sure to set the event as defined

        // log in page
        // add listener to buttons and input field

        // based on the user type, call different method for windows



    }


    /**
     * User Log in
     */
    public void login(){
        viewer.goBackReturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewer.changePage(viewer.returningUserPage, viewer.welcomePage);
            }
        });
        viewer.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccess = login(viewer.usernameText.getText(), viewer.passwordText.getText());
                if (loginSuccess){
                    if (userType == "client"){
                        viewer.changePage(viewer.returningUserPage, viewer.clientOptions);
                        currentUser = userManager.getUser(viewer.usernameText.getText());
                        clientOption();
                    }else if(userType.equals("bankManager")){
                        viewer.changePage(viewer.returningUserPage, viewer.managerOptions);
                    }else{
                        viewer.changePage(viewer.returningUserPage, viewer.inspectorOptions);
                    }
                }else{
                    viewer.usernameText.setText("");
                    viewer.passwordText.setText("");
                    JOptionPane.showMessageDialog(null, "Incorrect username/password. " +
                            "Please try again.");
                }
            }
        });
    }
    public boolean login(String userId, String pswd){
        if (userManager.userExists(userId)){
            if (userManager.getUser(userId).getPassword().equals(pswd)){
                currentUser = userManager.getUser(userId);
                // use type
                if (currentUser instanceof Client){
                    userType = "client";
                }else if (currentUser instanceof BankManager){
                    userType = "bankManager";
                }else{
                    userType = "bankInspector";
                }
                return true;
            }
        }
        return false;
    }


    /******************************************
     * client action handler
     */
    public void clientOption(){
        // add listener
        viewer.viewAccountSummaryButton.addActionListener(e->{
            viewer.changePage(viewer.clientOptions, viewer.summaryOfAccounts);
            accountSummary();
        });
        viewer.withdrawButton.addActionListener(e->{
            viewer.changePage(viewer.clientOptions, viewer.withdrawOption);
            withdraw();
        });
        viewer.transferButton.addActionListener(e->{
            viewer.changePage(viewer.clientOptions, viewer.transferOption);
        });
        viewer.payABillButton.addActionListener(e->{
            viewer.changePage(viewer.clientOptions, viewer.payBill);
        });
        viewer.depositMoneyButton.addActionListener(e->{
            viewer.changePage(viewer.clientOptions, viewer.depositOption);
        });
        viewer.goBackClient.addActionListener(e->{
            viewer.changePage(viewer.clientOptions, viewer.welcomePage);
        });
    }

    public void accountSummary(){

    }

    public void withdraw(){

        viewer.withdrawButton.addActionListener(e->{
            boolean inputOk = false;
            int withdrawAmount, accountNum;
            try{
                withdrawAmount = Integer.parseInt(viewer.withdrawAmt.getText());
                accountNum = Integer.parseInt(viewer.accNumWithdraw.getText());
                inputOk = true;
            }catch (Exception exp){
                JOptionPane.showMessageDialog(null, "Please check your input");
            }

            if (inputOk){
                boolean succeed = clientActionHandler.withdraw(accountManager.getAccount(accountNum), withdrawAmount);
                if (succeed){
                    JOptionPane.showMessageDialog(null, "withdraw succeed");
                }else{
                    JOptionPane.showMessageDialog(null, "You don't have that much money");
                }
            }
        });

        viewer.goBackWithdraw.addActionListener(e->{
            viewer.changePage(viewer.withdrawOption, viewer.clientOptions);
        });
    }

    public void transfer(){

    }

    public void bankManagerOption(){

    }

}
