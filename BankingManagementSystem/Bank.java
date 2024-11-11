import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(int accountNumber, String accountHolder, double initialBalance) {
        accounts.add(new Account(accountNumber, accountHolder, initialBalance));
    }

    public Account findAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    public boolean processTransaction(int accountNumber, Transaction transaction) {
        Account account = findAccount(accountNumber);
        if (account == null) {
            return false;
        }

        switch (transaction.getType().toLowerCase()) {
            case "deposit":
                account.deposit(transaction.getAmount());
                return true;
            case "withdraw":
                return account.withdraw(transaction.getAmount());
            default:
                return false;
        }
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getAccountReport() {
        StringBuilder report = new StringBuilder();
        for (Account account : accounts) {
            report.append(account.toString()).append("\n");
        }
        return report.toString();
    }
}

