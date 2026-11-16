package Services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnector {

    public static Connection conectar() {

        String url = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
        String usuario = "bd_mozos";
        String contraseña = "NuevaClave123";

        try {

            Connection conexion = DriverManager.getConnection(
                    url,
                    usuario,
                    contraseña
            );

            System.out.println("Conectado a Oracle correctamente");

            return conexion;

        } catch (Exception e) {

            System.out.println("Error de conexión");
            e.printStackTrace();

            return null;
        }
    }
}