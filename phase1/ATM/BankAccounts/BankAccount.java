package src.ATM.BankAccounts;
import src.ATM.AccountManager;
import src.ATM.BankAccounts.AssetAccounts.AssetAccount;
import src.ATM.Transaction;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


/**
 * An abstract BankAccount class.
 */
public abstract class BankAccount {

    public static final String LINE_OF_CREDIT = "lineofcredit";
    public static final String CREDIT_CARD = "creditcard";
    public static final String SAVINGS = "savings";
    public static final String CHEQUING ="chequing";


    protected static int nextId = 0;

    protected int id;
    protected final Date DATE_CREATED;

    protected double balance = 0;
    protected Transaction lastTransaction;


    public BankAccount(Date date, double balance) {

        this.id = nextId;
        nextId += 1;

        this.DATE_CREATED = date;

        this.balance = balance;

    }

    public BankAccount(Date date, double balance, Transaction lastTransaction) {

        this.id = nextId;
        nextId += 1;

        this.DATE_CREATED = date;

        this.balance = balance;

        this.lastTransaction = lastTransaction;
    }

    /** Getters **/

    public abstract double getBalance();

    public Transaction getLastTransaction() {
        return this.lastTransaction;
    }

    /** Setters **/

    public void setLastTransaction(Transaction transaction) {
        this.lastTransaction = transaction;
    }

    /**
     * Deposits the given amount into an account.
     */
    public boolean deposit(double amount) {
        this.balance += amount;
        return true;
    }

    /**
     * Withdraws the given amount out of an account.
     */
    public abstract boolean withdraw(double amount);

    /**
     * Undoes the most recent transaction on an account.
     */
    public boolean undoTransaction(AccountManager accountManager) {
        if (this.lastTransaction != null) {

            double amount = this.lastTransaction.getAmount();

            BankAccount sender = accountManager.getAccount(this.lastTransaction.getReceiver());
            // receiver = this
            if(sender.withdraw(amount) && this.deposit(amount)) {
                this.lastTransaction = null;
                sender.lastTransaction = null;
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public Date getDATE_CREATED() {
        return DATE_CREATED;
    }

    /**
     * Pays a bill by transferring money out to a non-user's account.
     */
    public boolean payBill(double amount, String receiver) {
        if(this.withdraw(amount)) {
            try {
                String bill = amount + ", " + receiver;
                BufferedWriter writer = new BufferedWriter(new FileWriter("outgoing.txt"));
                writer.append(bill);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public int getId() {
        return id;
    }
}