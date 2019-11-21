package services;

import java.sql.*;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()

public class WSBank{
    Connector conn = new Connector();
    @WebMethod
    public boolean validate(int account){
        String query = "SELECT * FROM account WHERE no_rekening='"+account+"'";
        boolean exist = false;
        try {
            ResultSet result = conn.getQuery(query);
            if(result.next()){
                exist = true;
            } else {
                exist = false;
            }
        } catch (Exception e) {
        }
            //TODO: handle exception
        return exist;
    }

    public void getAccountData(
        @WebParam(name = "name", mode = WebParam.Mode.OUT) Holder<String> name,
        @WebParam(name = "account_number", mode = WebParam.Mode.INOUT) Holder<Integer> accountNumber,
        @WebParam(name = "balance", mode = WebParam.Mode.OUT) Holder<Integer> balance,
        @WebParam(name = "transactions", mode = WebParam.Mode.OUT)
        Holder<List<Transaction>> transactions) {

        final String dataQuery = "SELECT * FROM account WHERE account_number = " + accountNumber.value;
        final ResultSet rsData = conn.getResultQuery(dataQuery);
        
        try {
            if (rsData.next()) {
                name.value = rsData.getString("account_name");
                balance.value = rsData.getInt("account_balance");
            }
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        final String transactionsQuery = "SELECT * FROM transaction WHERE account_number = "+ accountNumber.value;
        final ResultSet rsTransaction = conn.getResultQuery(transactionsQuery);

        try {
            List<Transaction> trs = new ArrayList<Transaction>();
            while (rsTransaction.next()) {
                trs.add(
                    new Transaction(
                        rsTransaction.getInt("transaction_id"),
                        rsTransaction.getString("transaction_type"),
                        rsTransaction.getInt("target_account_number"),
                        rsTransaction.getInt("amount"),
                        rsTransaction.getString("transaction_time")
                    )
                );
            }
            transactions.value = trs;
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public String transfer(int sender, int receiver){
        String query = "SELECT * FROM account WHERE no_rekening='"+sender+"''";
        String name = "";
        try {
            ResultSet result = conn.getQuery(query);
            if(result.next()){
                name = result.getString("nama");
            }
        } catch (Exception e) {
        }
            //TODO: handle exception
        return name;
    }

}