
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
    public static final String URL = "jdbc:mysql://localhost:3306/restaurante?serverTimezone=UTC";
	public static final String usuario = "root";
	public static final String contraseña = "1234";
	
	public Connection getConnection() {
	Connection conexion = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(URL, usuario, contraseña);
			//JOptionPane.showMessageDialog(null, "Conexion exitosa");
			
		}catch(Exception e) {
			System.err.println(e);
		}
		return conexion;	
	}
}
