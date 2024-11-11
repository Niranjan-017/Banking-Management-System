import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class BankingSystemGUI extends Frame {
    private Bank bank;
    private TextField accountNumberField, holderNameField, amountField;
    private TextArea outputArea;
    private static final String FILE_NAME = "E:\\BankingManagementSystem\\bank.txt";  // Change path as needed

    public BankingSystemGUI() {
        bank = new Bank();

        // Set up the window
        setTitle("Banking Management System");
        setSize(400, 400);
        setLayout(new FlowLayout());

        // Add components
        Label accountLabel = new Label("Account Number: ");
        accountNumberField = new TextField(20);

        Label holderLabel = new Label("Account Holder: ");
        holderNameField = new TextField(20);

        Label amountLabel = new Label("Amount: ");
        amountField = new TextField(20);

        Button createAccountButton = new Button("Create Account");
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button generateReportButton = new Button("Generate Report");

        outputArea = new TextArea("", 10, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);

        // Add components to the frame
        add(accountLabel);
        add(accountNumberField);
        add(holderLabel);
        add(holderNameField);
        add(amountLabel);
        add(amountField);
        add(createAccountButton);
        add(depositButton);
        add(withdrawButton);
        add(generateReportButton);
        add(outputArea);

        // Action listeners for buttons
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumberText = accountNumberField.getText();
                String holderName = holderNameField.getText();
                String amountText = amountField.getText();

                if (accountNumberText.isEmpty() || holderName.isEmpty() || amountText.isEmpty()) {
                    outputArea.setText("Please fill in all fields.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberText);
                    double initialBalance = Double.parseDouble(amountText);
                    bank.createAccount(accountNumber, holderName, initialBalance);
                    outputArea.setText("Account created: " + holderName + " (Account No: " + accountNumber + ")");
                    
                    // Save to file
                    saveAccountToFile(accountNumber, holderName, initialBalance);
                    
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid number format. Please check the input.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumberText = accountNumberField.getText();
                String amountText = amountField.getText();

                if (accountNumberText.isEmpty() || amountText.isEmpty()) {
                    outputArea.setText("Please fill in all fields.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberText);
                    double depositAmount = Double.parseDouble(amountText);
                    Transaction transaction = new Transaction("deposit", depositAmount);
                    boolean success = bank.processTransaction(accountNumber, transaction);
                    if (success) {
                        outputArea.setText("Deposited " + depositAmount + " to Account No: " + accountNumber);
                        
                        // Save deposit to file
                        saveTransactionToFile(accountNumber, "deposit", depositAmount);
                    } else {
                        outputArea.setText("Account not found or invalid transaction.");
                    }
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid number format. Please check the input.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String accountNumberText = accountNumberField.getText();
                String amountText = amountField.getText();

                if (accountNumberText.isEmpty() || amountText.isEmpty()) {
                    outputArea.setText("Please fill in all fields.");
                    return;
                }

                try {
                    int accountNumber = Integer.parseInt(accountNumberText);
                    double withdrawAmount = Double.parseDouble(amountText);
                    Transaction transaction = new Transaction("withdraw", withdrawAmount);
                    boolean success = bank.processTransaction(accountNumber, transaction);
                    if (success) {
                        outputArea.setText("Withdrew " + withdrawAmount + " from Account No: " + accountNumber);
                        
                        // Save withdrawal to file
                        saveTransactionToFile(accountNumber, "withdraw", withdrawAmount);
                    } else {
                        outputArea.setText("Account not found or insufficient funds.");
                    }
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid number format. Please check the input.");
                }
            }
        });

        generateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String report = bank.getAccountReport();
                outputArea.setText(report);
            }
        });

        // Window Closing Event
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Method to save account creation details to the file
    private void saveAccountToFile(int accountNumber, String holderName, double initialBalance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write("Account Created: Account No: " + accountNumber + ", Holder: " + holderName + ", Initial Balance: " + initialBalance);
            writer.newLine();
        } catch (IOException e) {
            outputArea.setText("Error saving account details to file.");
        }
    }

    // Method to save transaction details to the file
    private void saveTransactionToFile(int accountNumber, String transactionType, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write("Transaction: " + transactionType + ", Account No: " + accountNumber + ", Amount: " + amount);
            writer.newLine();
        } catch (IOException e) {
            outputArea.setText("Error saving transaction details to file.");
        }
    }

    public static void main(String[] args) {
        new BankingSystemGUI();
    }
}
