import java.util.*;

public class ATMSystem {

    static Scanner sc = new Scanner(System.in);
    static Map<String, User> users = new HashMap<>();
    static int totalTransactions = 0;

    public static void main(String[] args) {

        
        users.put("123456", new User("123456", 1234, 5000));
        users.put("111111", new User("111111", 1111, 3000));

        while (true) {
            System.out.println("\n=== ATM SYSTEM ===");
            System.out.println("1.Admin Login");
            System.out.println("2.User Login");
            int choice = sc.nextInt();

            if (choice == 1) adminLogin();
            else userLogin();
        }
    }

    
    static void adminLogin() {
        System.out.print("Enter Admin PIN: ");
        int pin = sc.nextInt();

        if (pin != 9999) {
            System.out.println("Wrong Admin PIN!");
            return;
        }

        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1.Deposit to Account");
            System.out.println("2.Check Balance");
            System.out.println("3.Total Transactions");
            System.out.println("4.Logout");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> adminDeposit();
                case 2 -> checkBalance();
                case 3 -> System.out.println("Total Transactions: " + totalTransactions);
                case 4 -> { return; }
            }
        }
    }

    static void adminDeposit() {
        System.out.print("Enter Account No: ");
        String acc = sc.next();

        User user = users.get(acc);
        if (user == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt % 100 != 0) {
            System.out.println("Only ₹100, ₹200, ₹500 notes allowed!");
            return;
        }

        user.deposit(amt);
        user.getMiniStatement().add("Admin Deposit: +" + amt);
        totalTransactions++;

        System.out.println("Deposited Successfully!");
    }

    
    static void userLogin() {
        System.out.print("Enter Account No: ");
        String acc = sc.next();

        User user = users.get(acc);
        if (user == null) {
            System.out.println("Invalid Account!");
            return;
        }

        System.out.print("Enter PIN: ");
        int pin = sc.nextInt();

        if (user.getPin() != pin) {
            System.out.println("Wrong PIN!");
            return;
        }

        while (true) {
            System.out.println("\n--- USER MENU ---");
            System.out.println("1.Deposit");
            System.out.println("2.Withdraw");
            System.out.println("3.Balance Check");
            System.out.println("4.Change PIN");
            System.out.println("5.Transfer");
            System.out.println("6.Mini Statement");
            System.out.println("7.Logout");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> deposit(user);
                case 2 -> withdraw(user);
                case 3 -> System.out.println("Balance: " + user.getBalance());
                case 4 -> changePin(user);
                case 5 -> transfer(user);
                case 6 -> miniStatement(user);
                case 7 -> { return; }
            }
        }
    }

    
    static void deposit(User user) {
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt % 100 != 0) {
            System.out.println("Only ₹100, ₹200, ₹500 notes allowed!");
            return;
        }

        user.deposit(amt);
        totalTransactions++;

        System.out.println("Deposit Successful!");
    }

    static void withdraw(User user) {
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt % 100 != 0) {
            System.out.println("Only ₹100, ₹200, ₹500 notes allowed!");
            return;
        }

        if (amt > user.getBalance()) {
            System.out.println("Insufficient Balance!");
            return;
        }

        user.withdraw(amt);
        totalTransactions++;

        System.out.println("Withdraw Successful!");

       
        int amount = (int) amt;
        int notes500 = amount / 500;
        amount %= 500;
        int notes200 = amount / 200;
        amount %= 200;
        int notes100 = amount / 100;

        System.out.println("Dispensed:");
        if (notes500 > 0) System.out.println("500 x " + notes500);
        if (notes200 > 0) System.out.println("200 x " + notes200);
        if (notes100 > 0) System.out.println("100 x " + notes100);
    }

    static void changePin(User user) {
        System.out.print("Enter New PIN: ");
        user.setPin(sc.nextInt());
        System.out.println("PIN Changed!");
    }

    static void transfer(User user) {
        System.out.print("Enter Receiver Account: ");
        String acc = sc.next();

        User receiver = users.get(acc);
        if (receiver == null) {
            System.out.println("Invalid Account!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();

        if (amt % 100 != 0) {
            System.out.println("Only multiples of 100 allowed!");
            return;
        }

        if (amt > user.getBalance()) {
            System.out.println("Insufficient Balance!");
            return;
        }

        user.withdraw(amt);
        receiver.deposit(amt);

        user.getMiniStatement().add("Transfer to " + acc + ": -" + amt);
        receiver.getMiniStatement().add("Received from " + user.getAccountNo() + ": +" + amt);

        totalTransactions++;

        System.out.println("Transfer Successful!");
    }

    static void miniStatement(User user) {
        System.out.println("\n--- MINI STATEMENT ---");
        for (String s : user.getMiniStatement()) {
            System.out.println(s);
        }
    }

    static void checkBalance() {
        System.out.print("Enter Account No: ");
        String acc = sc.next();

        User user = users.get(acc);
        if (user != null) {
            System.out.println("Balance: " + user.getBalance());
        } else {
            System.out.println("Account not found!");
        }
    }
}