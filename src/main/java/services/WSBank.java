package services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.WebParam;
import javax.xml.ws.Holder;

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

    public void getDataNasabah(
        @WebParam(name = "namaNasabah", mode = WebParam.Mode.OUT) Holder<String> namaNasabah,
        @WebParam(name = "no_rekening", mode = WebParam.Mode.INOUT) Holder<Integer> no_rekening,
        @WebParam(name = "balance", mode = WebParam.Mode.OUT) Holder<Integer> balance,
        @WebParam(name = "transaksi", mode = WebParam.Mode.OUT)
        Holder<List<Transaction>> transaksi) {

        final String queryAkun = "SELECT * FROM account WHERE no_rekening = " + no_rekening.value;
        final ResultSet rsData = conn.getQuery(queryAkun);
        
        try {
            if (rsData.next()) {
                namaNasabah.value = rsData.getString("nama");
                balance.value = rsData.getInt("balance");
            }
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        final String queryTransaction = "SELECT * FROM transaction WHERE id_account = "+ no_rekening.value;
        final ResultSet rsTransaction = conn.getQuery(queryTransaction);

        try {
            List<Transaction> trs = new ArrayList<Transaction>();
            while (rsTransaction.next()) {
                Transaction t = new Transaction(
                    rsTransaction.getInt("id"),
                    rsTransaction.getString("type"),
                    rsTransaction.getInt("amount"),
                    rsTransaction.getInt("account_number"),
                    rsTransaction.getString("time")
                );

                
                trs.add(t);
            }
            for (Transaction i : trs) {
                i.printClass();
            }
            transaksi.value = trs;
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    
    public String transfer(int sender, int receiver, int amount){
        String querySender = "SELECT * FROM account WHERE no_rekening='"+sender+"'";
        String queryReceiver = "SELECT * FROM account WHERE no_rekening='"+receiver+"'";
        String name = "";
        String status = "FAILED";
        try {
            ResultSet senderResult = conn.getQuery(querySender);
            ResultSet receiverResult = conn.getQuery(queryReceiver);
            if(senderResult.next()){
                // name = result.getString("nama");
                if(receiverResult.next()){
                    int senderBalance = senderResult.getInt("balance");
                    int receiverBalance = receiverResult.getInt("balance");
                    if(senderBalance >= amount){
                        String subSender = "UPDATE account SET balance='"+(senderBalance-amount)+"' WHERE no_rekening='"+sender+"'";
                        String addReceiver = "UPDATE account SET balance='"+(receiverBalance+amount)+"' WHERE no_rekening='"+receiver+"'";
                        String transSender = "INSERT INTO transaction (id, id_account, type, amount, account_number, time) VALUES (NULL, '"+sender+"', 'DEBIT', '"+amount+"', '"+receiver+"', CURRENT_TIMESTAMP)";
                        String transReceiver = "INSERT INTO transaction (id, id_account, type, amount, account_number, time) VALUES (NULL, '"+receiver+"', 'CREDIT', '"+amount+"', '"+sender+"', CURRENT_TIMESTAMP)";
                        try {
                            int sendStatus = conn.updateQuery(subSender);
                            int receiveStatus = conn.updateQuery(addReceiver);
                            int tsStatus = conn.updateQuery(transSender);
                            int trStatus = conn.updateQuery(transReceiver);
                            System.out.println("STATUS TRANSAKSI");
                            System.out.println(sendStatus);
                            System.out.println(receiveStatus);
                            if(sendStatus==1 && receiveStatus==1){
                                status = "SUCCEED";
                            } else {
                                status = "FALSE";
                            }
                        } catch (Exception e) {
                            //TODO: handle exception
                        }
                    } else {
                        status = "FAILED1";
                    }
                } else {
                    status = "FAILED2";
                }
            }
            else{
                status = "FAILED3";
            }
        } catch (Exception e) {
        }
            //TODO: handle exception
        return status;
    }

    public int generateVA(int account){
        String queryAccount = "SELECT * FROM account WHERE no_rekening='"+account+"'";
        String queryMax = "SELECT MAX(no_virtual_account) AS max FROM virtual_account";
        //first va default
        int va = 9000;

        
        try {
            ResultSet result = conn.getQuery(queryAccount);
            if(result.next()){
                ResultSet resultMax = conn.getQuery(queryMax);
                resultMax.next();
                System.out.println("max");
                System.out.println(resultMax.getInt("max"));
                if(resultMax.getInt("max")>=9000){
                    va = resultMax.getInt("max") + 1;
                }
                String queryInsertVA = "INSERT INTO virtual_account (no_rekening, no_virtual_account) VALUES ('"+result.getInt("no_rekening")+"','"+va+"')";
                System.out.println(queryInsertVA);
                int updateVA = conn.updateQuery(queryInsertVA);
                if(updateVA != 1){
                    va = 0;
                }
            } else {
                
            }
        } catch (Exception e) {
        }

        return va;
    }

    public boolean checkTransaction(int account, int amount, String start, String end){
        String queryTransaction = "SELECT * FROM transaction WHERE (account_number = '"+account+"' OR id_account = '"+account+"') AND (time>='"+start+"' AND time<='"+end+"') AND (amount ='"+amount+"')";
        String queryAccount = "SELECT * FROM transaction WHERE account_number='"+account+"'";
        String queryTime = "SELECT time FROM transaction WHERE account_number='"+account+"'";
        boolean exist = false;

        try {
            ResultSet result = conn.getQuery(queryTransaction);
            if(result.next()){
                exist = true;
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        // try {
        //     ResultSet result = conn.getQuery(queryAccount);
        //     if(result.next()){
        //         ResultSet resultTime = conn.getQuery(queryTime);
        //         resultTime.next();
        //         System.out.println("time");
        //         System.out.println(resultTime.getTimestamp("time"));
        //         Timestamp transTime = resultTime.getTimestamp("time");
        //         if((transTime.after(start)||transTime.equals(start)) && (transTime.before(end)||transTime.equals(end))){
        //             exist = true;
        //         } else {
        //             exist = false;
        //         }
        //     } else {
                
        //     }
        // } catch (Exception e) {
        // }

        return exist;
    }


}
