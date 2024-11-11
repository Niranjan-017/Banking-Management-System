import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BankingSystemGUI extends Frame {
    private Bank bank;
    private TextField accountNumberField, holderNameField, amountField;
    private TextArea outputArea;

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
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                String holderName = holderNameField.getText();
                double initialBalance = Double.parseDouble(amountField.getText());
                bank.createAccount(accountNumber, holderName, initialBalance);
                outputArea.setText("Account created: " + holderName + " (Account No: " + accountNumber + ")");
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                double depositAmount = Double.parseDouble(amountField.getText());
                Transaction transaction = new Transaction("deposit", depositAmount);
                boolean success = bank.processTransaction(accountNumber, transaction);
                if (success) {
                    outputArea.setText("Deposited " + depositAmount + " to Account No: " + accountNumber);
                } else {
                    outputArea.setText("Account not found or invalid transaction.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                double withdrawAmount = Double.parseDouble(amountField.getText());
                Transaction transaction = new Transaction("withdraw", withdrawAmount);
                boolean success = bank.processTransaction(accountNumber, transaction);
                if (success) {
                    outputArea.setText("Withdrew " + withdrawAmount + " from Account No: " + accountNumber);
                } else {
                    outputArea.setText("Account not found or insufficient funds.");
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

    public static void main(String[] args) {
        new BankingSystemGUI();
    }
}
