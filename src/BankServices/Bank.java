package BankServices;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {
    private String name;

    private List<Account> accounts = new ArrayList<>();
    private List<Account> deletedAccounts = new ArrayList<>();

    public Bank(String n) {
        name = n;
    }

    private static void sortByCodeAsc(List<Account> accounts) {
//        for (int i = 0; i < accounts.size() - 1; i++) {
//            int currentMinIndex = i;
//            for (int j = i + 1; j < accounts.size(); j++) {
//                Account account1 = accounts.get(j);
//                Account account2 = accounts.get(currentMinIndex);
//                if (account1.getCode() < account2.getCode()) {
//                    currentMinIndex = j;
//                }
//            }
//            if (i != currentMinIndex) {
//                Account temp = accounts.get(i);
//                accounts.set(i, accounts.get(currentMinIndex));
//                accounts.set(currentMinIndex, temp);
//            }
//        }
        accounts.stream().sorted(Comparator.comparing(Account::getCode)).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public int createAccount(String name, int date, double initial) {
        Account account = new Account(accounts.size() + 1, name, date, initial);
        accounts.add(account);
        return accounts.size();
    }

    public Account deleteAccount(int code, int date) throws InvalidCode {
        Account account = getAccount(code);
        account.withdraw(date, account.getBalance());
        accounts.remove(account);
        deletedAccounts.add(account);
        return account;
    }

    public Account getAccount(int code) throws InvalidCode {
        try {
            Account account = accounts.get(code - 1);
            return account;
        } catch (Exception e) {
            throw new InvalidCode();
        }

    }

    public void deposit(int code, int date, double value) throws InvalidCode {
        Account account = getAccount(code);
        account.deposit(date, value);
    }

    public void withdraw(int code, int date, double value) throws InvalidCode, InvalidValue {
        Account account = getAccount(code);
        if (value > account.getBalance()) throw new InvalidValue();
        account.withdraw(date, value);
    }

    public void transfer(int fromCode, int toCode, int date, double value) throws InvalidCode, InvalidValue {
        withdraw(fromCode, date, value);
        deposit(toCode, date, value);
    }

    public double getTotalDeposit() {
//		double sum = 0;
//		for (int i = 0; i < accounts.size(); i++) {
//			Account account = accounts.get(i);
//			sum += account.getBalance();
//		}
        return accounts.stream().mapToDouble(Account::getBalance).sum();
    }

    public List<Account> getAccounts() {
        sortByCodeAsc(accounts);
        return accounts;
    }

    public List<Account> getZeroAccounts() {
        return deletedAccounts;
    }

    public List<Account> getAccountsByBalance(double low, double high) {

//		List<Account> accountsByBalance = new ArrayList<>();
//		for (int i = 0; i < accounts.size(); i++) {
//			Account account = accounts.get(i);
//			if (account.getBalance() >= low && account.getBalance() <= high) {
//				accountsByBalance.add(account);
//			}
//		}
//		sortByBalanceDesc(accountsByBalance);
//        return accountsByBalance;
        List<Account> result = new ArrayList<>();
        accounts.stream().filter(account -> account.getBalance() >= low && account.getBalance() <= high).forEach(System.out::println);
        return result;
    }

    public long getNumberHigher(double min) {
//        long count = 0;
//        for (int i = 0; i < accounts.size(); i++) {
//            Account account = accounts.get(i);
//            if (account.getBalance() >= min) {
//                count++;
//            }
//        } return count;

        var count = accounts.stream().filter(account -> account.getBalance() >= min).count();
        return count;
    }

    private void sortByBalanceDesc(List<Account> accounts) {
//        for (int i = 0; i < accounts.size() - 1; i++) {
//            int currentMinIndex = i;
//            for (int j = i + 1; j < accounts.size(); j++) {
//                if (accounts.get(j).getBalance() > accounts.get(currentMinIndex).getBalance()) {
//                    currentMinIndex = j;
//                }
//            }
//            if (i != currentMinIndex) {
//                Account temp = accounts.get(i);
//                accounts.set(i, accounts.get(currentMinIndex));
//                accounts.set(currentMinIndex, temp);
//            }
//        }

        accounts.stream().sorted(Comparator.comparing(Account::getBalance)).collect(Collectors.toList());
    }
}
