import java.util.Scanner;
import java.util.ArrayList;

public class ATMSystem {
    public static void main(String[] args) {
        ATMInterface atmInterface = new ATMInterface();
        atmInterface.start();
    }
}

class ATMInterface {
    private UserAuthentication userAuthentication;
    private TransactionManager transactionManager;
    private Scanner scanner;

    public ATMInterface() {
        userAuthentication = new UserAuthentication();
        transactionManager = new TransactionManager();
        scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Welcome to the ATM!");

        boolean isAuthenticated = userAuthentication.authenticateUser();

        if (isAuthenticated) {
            int choice;
            do {
                displayMenu();
                choice = getUserChoice();
                switch (choice) {
                    case 1:
                        transactionManager.showTransactionHistory();
                        break;
                    case 2:
                        transactionManager.withdraw(scanner);
                        break;
                    case 3:
                        transactionManager.deposit(scanner);
                        break;
                    case 4:
                        transactionManager.transfer(scanner);
                        break;
                    case 5:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (choice != 5);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. View Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private int getUserChoice() {
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            choice = -1;
        }
        return choice;
    }
}

class UserAuthentication {
    private String userId = "Shiba";
    private String userPin = "1234";
    private Scanner scanner;

    public UserAuthentication() {
        scanner = new Scanner(System.in);
    }

    public boolean authenticateUser() {
        System.out.print("Enter your user ID: ");
        String inputId = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String inputPin = scanner.nextLine();

        return inputId.equals(userId) && inputPin.equals(userPin);
    }
}

class TransactionManager {
    private ArrayList<String> transactionHistory;
    private double balance;

    public TransactionManager() {
        transactionHistory = new ArrayList<>();
        balance = 0.0;
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void withdraw(Scanner scanner) {
        System.out.print("Enter the amount to withdraw: ");
        double amount = getValidAmount(scanner);
        if (amount > balance) {
            System.out.println("Insufficient funds. Your current balance is: INR " + balance);
        } else {
            balance -= amount;
            transactionHistory.add("Withdrawn: INR " + amount);
            System.out.println("Withdrawal successful. New balance: INR " + balance);
        }
    }

    public void deposit(Scanner scanner) {
        System.out.print("Enter the amount to deposit: ");
        double amount = getValidAmount(scanner);
        balance += amount;
        transactionHistory.add("Deposited: INR " + amount);
        System.out.println("Deposit successful. New balance: INR " + balance);
    }

    public void transfer(Scanner scanner) {
        System.out.print("Enter the amount to transfer: ");
        double amount = getValidAmount(scanner);
        if (amount > balance) {
            System.out.println("Insufficient funds. Your current balance is: INR " + balance);
        } else {
            System.out.print("Enter the recipient's account ID: ");
            String recipientId = scanner.nextLine();
            balance -= amount;
            transactionHistory.add("Transferred: INR " + amount + " to Account ID: " + recipientId);
            System.out.println("Transfer successful. New balance: INR " + balance);
        }
    }

    private double getValidAmount(Scanner scanner) {
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine());
            if (amount <= 0) {
                System.out.println("Amount must be positive. Transaction cancelled.");
                amount = 0;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Transaction cancelled.");
        }
        return amount;
    }
}
