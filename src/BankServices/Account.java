package BankServices;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Account {

    private final int code;
    private final String ownerName;
    private int date;
    private double balance;

    private final List<Operation> operations = new ArrayList<>();
    private final List<Deposit> deposits = new ArrayList<>();
    private final List<Withdrawal> withdrawals = new ArrayList<>();


    public Account(int code, String ownerName, int date, double balance) {
        this.code = code;
        this.ownerName = ownerName;
        this.date = date;
        this.balance = balance;

        Deposit deposit = new Deposit(this.date, balance);
        operations.add(deposit);
        deposits.add(deposit);
    }

    public int getCode() {
        return code;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getDate() {
        return date;
    }

    public double getBalance() {
        return balance;
    }

    public List<Operation> getMovements() {
        operations.sort(new ByDateDescOperationComparator());
        return operations;
    }


    public List<Deposit> getDeposits() {
        Collections.sort(deposits, new ByDateDescDepositComparator());
        return deposits;
    }


    public List<Withdrawal> getWithdrawals() {
        withdrawals.sort(new ByDateDescWithdrawalComparator());
        return withdrawals;
    }

    public void deposit(int date, double amount) {
        if (date > this.date) this.date = date;
        balance += amount;
        Deposit deposit = new Deposit(this.date, amount);
        operations.add(deposit);
        deposits.add(deposit);
    }

    public void withdraw(int date, double amount) {
        if (date > this.date) this.date = date;
        balance -= amount;
        Withdrawal withdrawal = new Withdrawal(this.date, amount);
        operations.add(withdrawal);
        withdrawals.add(withdrawal);
    }

    public String toString() {
        return code + "," + ownerName + "," + date + "," + balance;
    }

    static class ByDateDescDepositComparator implements Comparator<Deposit> {

        @Override
        public int compare(Deposit o1, Deposit o2) {
            return o1.getDate() - o2.getDate();
        }
    }

    static class ByDateDescOperationComparator implements Comparator<Operation> {

        @Override
        public int compare(Operation o1, Operation o2) {
            return o1.getDate() - o2.getDate();
        }
    }

    static class ByDateDescWithdrawalComparator implements Comparator<Withdrawal> {

        @Override
        public int compare(Withdrawal o1, Withdrawal o2) {
            return o1.getDate() - o2.getDate();
        }
    }

}
