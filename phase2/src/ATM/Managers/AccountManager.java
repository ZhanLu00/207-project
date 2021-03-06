package ATM.Managers;

import ATM.BankAccounts.AssetAccounts.ChequingAccount;
import ATM.BankAccounts.ExtraAccounts.ForeignCurrencyAccount;
import ATM.BankAccounts.ExtraAccounts.LotteryAccount;
import ATM.BankAccounts.AssetAccounts.SavingsAccount;
import ATM.BankAccounts.BankAccount;
import ATM.BankAccounts.DebtAccounts.CreditCardsAccount;
import ATM.BankAccounts.DebtAccounts.LineOfCreditAccount;
import ATM.Transaction;
import java.util.*;
import java.util.function.Consumer;

/**
 * An AccountManager class responsible for storing accounts.
 */
public class AccountManager implements Iterable<BankAccount> {

    private ArrayList<BankAccount> accounts;
    private ArrayList<String[]> accountRequests;
    private CurrencyManager currencyManager;
    private Date dateCreated;

    public AccountManager(ArrayList<BankAccount> accounts,
                          ArrayList<String[]> accountRequests,
                          CurrencyManager currencyManager,
                          Date dateCreated) {

        this.accounts = accounts;
        this.accountRequests = accountRequests;
        this.currencyManager = currencyManager;
        this.dateCreated = dateCreated;

    }

    public void runUpdateCycle() {
        for (BankAccount account : accounts) {
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).collectInterest();
            }
            else if (account instanceof LotteryAccount) {
                ((LotteryAccount) account).play();
            }
        }
    }

    /**
     * Adds an account to the list of accounts.
     */
    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    /**
     * Takes an *id* and returns the BankAccount with corresponding id, if it exists.
     */
    public BankAccount getAccount(int id) {
        if (id < accounts.size()) {
            return accounts.get(id);
        } else {
            return null;
        }
    }

    /**
     * check the existence of an account
     */
    public boolean accountExist(int act){
        for (BankAccount account: getAccounts()){
            if (account.getId() == act){
                return true;
            }
        }
        return false;
    }


    /**
     * Attempts to transfer *amount* from one account to another.
     *
     * Returns the Transaction if successful, and null otherwise.
     */
    public Transaction transfer(double amount, int senderId, int receiverId) {
        BankAccount sender = this.getAccount(senderId);
        BankAccount receiver = this.getAccount(receiverId);
        if(sender.withdraw(amount) && receiver.deposit(amount)) {
            return new Transaction(amount, senderId, receiverId, "transfer");
        } else {
            return null;
        }
    }

    /** Getters **/
    public ArrayList<String[]> getAccountRequests() {
        return accountRequests;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    /**
     * Creates and returns an account by accountType.
     */
    public BankAccount createAccount(String accountType) {
        switch (accountType) {
            case "CHEQUING_ACCOUNT":
                return new ChequingAccount(dateCreated,0);
            case "SAVINGS_ACCOUNT":
                return new SavingsAccount(dateCreated,0);
            case "LOTTERY_ACCOUNT":
                return new LotteryAccount(dateCreated,0);
            case "FOREIGN_CURRENCY_ACCOUNT":
                String currencyType = "USD";
                double rate = currencyManager.getRate(currencyType);
                return new ForeignCurrencyAccount(dateCreated, 0, currencyType, rate);
            case "CREDIT_CARD_ACCOUNT":
                return new CreditCardsAccount(dateCreated,0);
            case "LINE_OF_CREDIT_ACCOUNT":
                return new LineOfCreditAccount(dateCreated,0);
            default:
                return null;
        }
    }

    /**
     * An AccountManagerIterator class.
     */
    private class AccountManagerIterator implements Iterator<BankAccount> {
        private ArrayList<BankAccount> bankAccounts;
        int i;

        AccountManagerIterator(ArrayList<BankAccount> bankAccounts) {
            this.bankAccounts = bankAccounts;
            this.i = 0;
        }

        @Override
        public boolean hasNext() {
            return i < bankAccounts.size();
        }

        @Override
        public BankAccount next() {
            i+=1;
            return bankAccounts.get(i-1);
        }
    }

    @Override
    public Iterator<BankAccount> iterator() {
        return new AccountManagerIterator(accounts);
    }

    @Override
    public void forEach(Consumer<? super BankAccount> action) {

    }

    @Override
    public Spliterator<BankAccount> spliterator() {
        return null;
    }
}
