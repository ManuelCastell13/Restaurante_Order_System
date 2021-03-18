
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class SQLUsuario {
    
    public boolean iniciarSesion(usuario usuario){
        Conexion con = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conexion = con.getConnection();
            
            ps = conexion.prepareStatement("select id, nombreUsuario, contraseña, nombre, correo, ultima_sesion, idTipo_usuario from usuario where nombreUsuario=?");
            ps.setString(1, usuario.getUsuario());
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                if(usuario.getContraseña().equals(rs.getString("contraseña"))){
                    return true;
                }
                else{
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
        public boolean agregarUsuario(usuario usuario){
        Conexion con = new Conexion();
        PreparedStatement ps = null;
        
        try {
            Connection conexion = con.getConnection();
            ps = conexion.prepareStatement("insert into usuario (nombreUsuario, contraseña, nombre, correo, idTipo_usuario) values (?,?,?,?,?)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getRol());
            ps.executeUpdate();
            
            return true;
        
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
        
         public boolean verificarUsuario(String usuario){
        Conexion con = new Conexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conexion = con.getConnection();
            ps = conexion.prepareStatement("select nombreUsuario from usuario where nombreUsuario=?");
            ps.setString(1, usuario);

            rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }
            return false;
        
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
    
}
