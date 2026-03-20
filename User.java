import java.util.*;

public class User {
    private String accountNo;
    private int pin;
    private double balance;
    private List<String> miniStatement = new ArrayList<>();

    public User(String accountNo, int pin, double balance) {
        this.accountNo = accountNo;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getMiniStatement() {
        return miniStatement;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void deposit(double amt) {
        balance += amt;
        miniStatement.add("Deposit: +" + amt);
    }

    public void withdraw(double amt) {
        balance -= amt;
        miniStatement.add("Withdraw: -" + amt);
    }
}