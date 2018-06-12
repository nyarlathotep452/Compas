import java.sql.*;

public class conexion {

    public static Connection getConexion(){
        
         Connection cn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost/db",
                    "root", "");
            
        } catch (Exception e) { System.out.print("Error de Conexion: "+e); }
        return cn;
        
    }
    
    
    public static void main(String[] args) {
        conexion.getConexion();
    }
    
}