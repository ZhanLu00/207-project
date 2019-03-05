package ATM.BankAccounts.AssetAccounts;

import java.util.Date;


/**
 * A SavingsAccount class.
 */
public class SavingsAccount extends AssetAccount {

    public SavingsAccount(ATM.Users.Client client, Date date) {
        super(client, date);
    }

    public boolean withdraw(double amount) {
        if (this.balance >= amount) {
            this.balance += -amount;
            return true;
        } else {
            return false;
        }
    }

    public void collectInterest() {
        this.balance *= 1.001;
    }

}