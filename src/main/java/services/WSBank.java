package services;

import java.sql.*;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService()

public class WSBank{
    Connector conn = new Connector();
    @WebMethod
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