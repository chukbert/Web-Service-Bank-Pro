package services;

public class Transaction {
    int idTransaksi;
    String type;
    int amount;
    int account_number;
    String time;

    public Transaction(int idTransaksi, String type, int amount, int account_number, String time) { 
        this.idTransaksi = idTransaksi;
        this.type = type;
        this.amount = amount;
        this.account_number = account_number;
        this.time = time;
    }
}