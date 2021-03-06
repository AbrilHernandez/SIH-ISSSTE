
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class Conexion_BD{
    
         
    public static Connection conexion()
    {
        Connection con=null;
     
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/sihissste_db", "vmcc", "sih-issste");
            System.out.println("Conexion exitosa");
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error1 en la Conexión con la BD "
                    +ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            con=null;
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error2 en la Conexión con la BD "+
                    ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            con=null;
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex, "Error3 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            con=null;
        }
        finally
        {
            return con;
        }
    }
    
    public ResultSet obtenerDatosporIDEstudioLaboratorio(int id){
        Connection cn = conexion();
        Statement st;
        ResultSet rs = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery("SELECT Caracter_EstuLab, Tipo_EstuLab FROM EstudioLaboratorio WHERE id_EstuLab = " + id +"");
            
        }catch(SQLException ex){
            System.out.println("Error al obtener registro de estudio de laboratorio " + ex.getMessage());
        }
        
        return rs;
    }
    
    public ResultSet obtenerExpedienteClinico(String RFC){
        Connection cn = conexion();
        Statement st;
        ResultSet rs = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery("SELECT id_ExpedienteClinico FROM ExpedienteClinico WHERE RFC_Derechoh = '" + RFC +"'");
            
        }catch(SQLException ex){
            System.out.println("Error al obtener el expediente clinico " + ex.getMessage());
        }
        
        return rs;
    }
    
    public boolean insertarNuevoAnalisisClinicoDoctor(String fecha, String caracter, String tipo, int numExp){
        try{
            Connection cn = conexion();
            Statement st = cn.createStatement();
            st.executeUpdate("INSERT INTO EstudioLaboratorio (Estado_EstuLab, FechaOrden_EstuLab, Caracter_EstuLab, Tipo_EstuLab, id_ExpedienteClinico) "
                    + "VALUES ('P','" + fecha +"', '"+ caracter +"', '"+ tipo +"', 1)");
            cn.close();
            return true;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public void prueba(){
        System.out.println("Hola");
        boolean estado = false;
    }
    
    public ResultSet SeleccionarTodoMedicamento(){
        Connection cn = conexion();
        Statement st;
        ResultSet rs = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM Medicamentos;");
        }catch(SQLException ex){
            System.out.println("Error al llenar la tabla de medicamentos");
        }
        return rs;
    }
    
    public ResultSet buscarPorNombreMedicamento(String cadena){
        Connection cn = conexion();
        Statement st;
        ResultSet rs = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM Medicamentos WHERE NombreGen_Medicamentos LIKE '"+ cadena +"%'");
        }catch(SQLException ex){
            System.out.println("Error" + ex.getMessage());
        }
        return rs;
    }
    
    
    
    public ResultSet buscarMedicamento(String id){
        Connection cn = conexion();
        Statement st;
        ResultSet rs = null;
        try{
            st = cn.createStatement();
            rs = st.executeQuery("SELECT * FROM Medicamentos WHERE id_Medicamentos = '"+ id +"'");
        }catch(SQLException ex){
            System.out.println("Error al realiza consulta");
        }
        return rs;
    }
    
    
    public int actualizarMedicamentos(String idAct, String nombreAct, String descripAct, int cantAct, String presenAct, int id){
    int resultado = 0;
    String SQL = "UPDATE Medicamentos SET id_Medicamentos = '"+ idAct +"', NombreGen_Medicamentos = '"+ nombreAct +"', Descripcion_Medicamentos = '"+ descripAct +"',Cantidad_Medicamento = "+ cantAct +", Presentacion = '"+ presenAct +"' WHERE id_Medicamento = "+ id +"";
    try{
            Connection cn = conexion();
            Statement comando=cn.createStatement();
            resultado = comando.executeUpdate(SQL);
        }catch(Exception e){}
        
        return resultado;
    }
    
    public int actualizarCantidadMedicamento(int cantidad, String id){
        int resultado = 0;
        String SQL = "UPDATE Medicamentos SET Cantidad_Medicamento = "+ cantidad +" WHERE id_Medicamentos = '"+ id +"'";
        try{
            Connection cn = conexion();
            Statement comando=cn.createStatement();
            resultado = comando.executeUpdate(SQL);
        }catch(Exception e){}
        
        return resultado;
    }
    
    public boolean InsertarMedicamentos(String codigo, String nombre, String descripcion, int cantidad, String presentacion){
        Connection cn = conexion();
        String SQL = "INSERT INTO Medicamentos VALUES (?,?,?,?,?);";
        try{
            PreparedStatement pst = cn.prepareStatement(SQL);
            
            pst.setString(1, codigo);
            pst.setString(2, nombre);
            pst.setString(3, descripcion);
            pst.setInt(4, cantidad);
            pst.setString(5, presentacion);
            
            int n = pst.executeUpdate();
            
            if(n != 0){
               return true;
            }else{
                return false;
            }
            
        }catch(SQLException ex){
            System.out.println(ex);
        }
        
        return false;
    }
}