package services;
 
import javax.jws.WebService;
import javax.jws.WebMethod;
import java.sql.*;

@WebService()
public class Connector {

    Statement st;

    public Connector(){
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:8889/ws-bank", "engima", "123");
            // Connection conn = DriverManager.getConnection("jdbc:mariadb://35.240.201.66:3306/ws-bank", "engima", "123");
            st = conn.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public ResultSet getQuery(String query){
        try {
            ResultSet result = st.executeQuery(query);
            return result;
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return null;
    }

    public int updateQuery(String query){
        try { 
            int result = st.executeUpdate(query);
            return result;
        } catch (SQLException s) {
            s.printStackTrace();
        }
        return -1;
    }
}