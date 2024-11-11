class Bank {
    private List<BankAccount> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void createAccount(int accountNumber, String holderName, double initialBalance) {
        BankAccount newAccount = new BankAccount(accountNumber, holderName, initialBalance);
        accounts.add(newAccount);
        mergeSort(accounts); // Sorting after each addition
    }

    public boolean processTransaction(int accountNumber, Transaction transaction) {
        BankAccount account = findAccount(accountNumber);
        if (account == null) {
            return false; // Account not found
        }

        if (transaction.getType().equals("deposit")) {
            account.balance += transaction.getAmount();
            return true;
        } else if (transaction.getType().equals("withdraw")) {
            if (account.balance >= transaction.getAmount()) {
                account.balance -= transaction.getAmount();
                return true;
            } else {
                return false; // Insufficient funds
            }
        }
        return false;
    }

    private BankAccount findAccount(int accountNumber) {
        int low = 0, high = accounts.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            BankAccount midAccount = accounts.get(mid);
            if (midAccount.accountNumber == accountNumber) {
                return midAccount;
            } else if (midAccount.accountNumber < accountNumber) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // Account not found
    }

    // Merge Sort implementation
    private void mergeSort(List<BankAccount> accounts) {
        if (accounts.size() <= 1) return;

        int mid = accounts.size() / 2;
        List<BankAccount> left = new ArrayList<>(accounts.subList(0, mid));
        List<BankAccount> right = new ArrayList<>(accounts.subList(mid, accounts.size()));

        mergeSort(left);
        mergeSort(right);

        merge(accounts, left, right);
    }

    private void merge(List<BankAccount> accounts, List<BankAccount> left, List<BankAccount> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).accountNumber < right.get(j).accountNumber) {
                accounts.set(k++, left.get(i++));
            } else {
                accounts.set(k++, right.get(j++));
            }
        }
        while (i < left.size()) {
            accounts.set(k++, left.get(i++));
        }
        while (j < right.size()) {
            accounts.set(k++, right.get(j++));
        }
    }

    public String getAccountReport() {
        StringBuilder report = new StringBuilder();
        for (BankAccount account : accounts) {
            report.append("Account No: ").append(account.accountNumber)
                  .append(", Holder: ").append(account.holderName)
                  .append(", Balance: ").append(account.balance).append("\n");
        }
        return report.toString();
    }
}
