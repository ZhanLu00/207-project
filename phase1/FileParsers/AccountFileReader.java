package FileParsers;

import ATM.BankAccounts.AssetAccounts.ChequingAccount;
import ATM.BankAccounts.AssetAccounts.SavingsAccount;
import ATM.BankAccounts.BankAccount;
import ATM.BankAccounts.DebtAccounts.CreditCardsAccount;
import ATM.BankAccounts.DebtAccounts.LineOfCreditAccount;
import ATM.TimeManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AccountFileReader {
    private ArrayList<BankAccount> accounts;

    public AccountFileReader(String fileName) throws IOException {

        accounts = new ArrayList<>();

        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String accountInfo;
        while((accountInfo = br.readLine()) != null){
            createAccount(accountInfo);
        }
    }

    private void createAccount(String accountInfo) {
        String[] seperated = accountInfo.split(",");
        String type = seperated[0].replace(",","");
        double balence = Double.parseDouble(seperated[1].replace(",",""));
        Date date = TimeManager.dateFromString(seperated[2].replace(",",""));

        if (type.equals(BankAccount.CHEQUING)) {
            accounts.add(new ChequingAccount(date,balence));
        }
        else if (type.equals(BankAccount.SAVINGS)) {
            accounts.add(new SavingsAccount(date,balence));
        }
        else if (type.equals(BankAccount.CREDIT_CARD)) {
            accounts.add(new CreditCardsAccount(date,balence));
        }
        else if (type.equals(BankAccount.LINE_OF_CREDIT)) {
            accounts.add(new LineOfCreditAccount(date,balence));
        }

    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
}
