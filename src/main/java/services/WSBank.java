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