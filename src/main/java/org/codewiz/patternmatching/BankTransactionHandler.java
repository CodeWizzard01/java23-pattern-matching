package org.codewiz.patternmatching;

public class BankTransactionHandler {

    // Deposit, Withdrawal and Transfer
    public  sealed interface Transaction permits Deposit,Withdrawal,Transfer,FCYTransfer{}
    public record Deposit(String accountNumber,int amount) implements  Transaction{}
    public record Withdrawal(String accountNumber,int amount) implements  Transaction{}
    public record Transfer(String fromAccountNo,String toAccountNo,int amount) implements  Transaction{}
    public record FCYTransfer(Account fromAccount,Account toAccount,int amount) implements  Transaction{}

    public record Customer(String customerId, String name) {}
    public record Account(String accountNumber, Customer customer,String curr) {}

    /*public void handleTransaction(Transaction transaction) {
        if(transaction instanceof Deposit deposit){
            System.out.printf("Handling deposit of %d to account %s%n", deposit.amount(), deposit.accountNumber());
        }
        else if (transaction instanceof Withdrawal withdrawal) {
            System.out.printf("Handling withdrawal of %d from account %s%n", withdrawal.amount(), withdrawal.accountNumber());
        }else if (transaction instanceof Transfer transfer) {
            System.out.printf("Handling transfer of %d from account %s to account %s%n", transfer.amount(), transfer.fromAccountNo(), transfer.toAccountNo());
        } else {
            throw new IllegalArgumentException("Unknown transaction type: " + transaction);
        }
    }*/


    public void handleTransaction(Transaction transaction) {
        switch (transaction) {
            case Deposit(String accNo,int amt) ->
                    System.out.printf("Handling deposit of %d to account %s%n", amt, accNo);
            case Withdrawal(String accNo,int amt) when amt>10_000->
                    //send out alert
                    System.out.printf("Handling withdrawal of %d from account %s%n", amt, accNo);
            case Withdrawal(String accNo,int amt) ->
                    System.out.printf("Handling withdrawal of %d from account %s%n", amt, accNo);
            case Transfer transfer ->
                    System.out.printf("Handling transfer of %d from account %s to account %s%n", transfer.amount(), transfer.fromAccountNo(), transfer.toAccountNo());
            case FCYTransfer(Account(String fromAcct, _,_),
                             Account(String toAcct, _,_), int amount) ->
                    System.out.printf("Handling foreign transfer of %d from account %s to account %s%n", amount, fromAcct, toAcct);
        }
    }

    public String getType(Object obj) {
        return switch (obj) {
            case byte b -> "Byte";
            case int i -> "Integer";
            case long l -> "Long";
            default -> "Unknown";
        };
    }

    public String getTransactionDescription(Transaction transaction) {
        return switch (transaction) {
            case Deposit deposit -> String.format("Handling deposit of %d to account %s", deposit.amount(), deposit.accountNumber());
            case Withdrawal withdrawal -> String.format("Handling withdrawal of %d from account %s", withdrawal.amount(), withdrawal.accountNumber());
            case Transfer transfer -> String.format("Handling transfer of %d from account %s to account %s", transfer.amount(), transfer.fromAccountNo(), transfer.toAccountNo());
            case null, default -> throw new IllegalArgumentException("Unknown transaction type: " + transaction);
        };
    }



}

