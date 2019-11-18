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
                        try {
                            int sendStatus = conn.updateQuery(subSender);
                            int receiveStatus = conn.updateQuery(addReceiver);
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
                        status = "FAILED";
                    }
                } else {
                    status = "FAILED";
                }
            }
            else{
                status = "FAILED";
            }
        } catch (Exception e) {
        }
            //TODO: handle exception
        return status;
    }

    public int generateVA(int account){
        String queryAccount = "SELECT * FROM account WHERE no_rekening='"+account+"'";
        String queryMax = "SELECT MAX(no_virtual_account) AS max FROM virtual_account";
        int virtual_account = 0;

        
        try {
            ResultSet result = conn.getQuery(query);
            if(result.next()){
                ResultSet resultAccount = conn.getQuery(queryAccount);
                ResultSet resultMax = conn.getQuery(queryMax);
                int va = resultMax.getInt('max')+1;
                String queryInsertVA = "INSERT INTO no_virtual_account ('no_rekening', 'no_virtual_accouont') VALUES ('"+resultAccount.getInt('no_rekening')+"','"+va+"')";
                int updateVA = conn.updateQuery(queryInsertVA);
            } else {
                
            }
        } catch (Exception e) {
        }

        return va;
    }

}
