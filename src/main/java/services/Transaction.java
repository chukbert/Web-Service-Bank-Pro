package services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
    public int idTransaksi;
    public String type;
    public int amount;
    public int account_number;
    public String time;

    public Transaction() {
        this.idTransaksi = 0;
        this.type = "DEBIT";
        this.amount = 0;
        this.account_number = 0;
        this.time = "DEF";
    }
    
    public Transaction(int idTransaksi, String type, int amount, int account_number, String time) { 
        this.idTransaksi = idTransaksi;
        this.type = type;
        this.amount = amount;
        this.account_number = account_number;
        this.time = time;
    }

    public void printClass() {
        System.out.println(this.idTransaksi);
        System.out.println(this.type);
        System.out.println(this.amount);
        System.out.println(this.account_number);
        System.out.println(this.time);
    }
}