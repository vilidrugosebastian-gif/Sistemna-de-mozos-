package Services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseInitializer {

    public static void inicializar() {
        Connection conexion = DataBaseConnector.conectar();
        if (conexion == null) {
            System.out.println("No se pudo inicializar: la conexión es nula.");
            return;
        }
        try {
            Statement st = conexion.createStatement();

            // 1. Verificamos si la tabla MOZOS ya existe
            boolean tablasExisten = false;
            try {
                // Intentamos hacer una consulta rápida a la tabla
                ResultSet rs = st.executeQuery("SELECT 1 FROM MOZOS WHERE ROWNUM = 1");
                rs.close();
                tablasExisten = true;
            } catch (Exception e) {
                // Si entra aquí, significa que la tabla NO existe (Error ORA-00942)
                tablasExisten = false;
            }

            if (tablasExisten) {
                System.out.println("--- Las tablas ya existen. No se modificó la base de datos. ---");
                st.close();
                conexion.close();
                return; // Salimos del método para conservar los datos
            }

            // 2. Si no existen, procedemos a crearlas
            System.out.println("--- Primera ejecución detectada. Creando tablas ---");

            // 1. MOZOS
            st.execute("CREATE TABLE MOZOS (" +
                    "ID_MOZO NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "DNI VARCHAR2(15) NOT NULL, " +
                    "NOMBRE VARCHAR2(50) NOT NULL, " +
                    "APELLIDO VARCHAR2(50) NOT NULL, " +
                    "TELEFONO VARCHAR2(20), " +
                    "EMAIL VARCHAR2(100), " +
                    "CARGO VARCHAR2(30) DEFAULT 'MOZO', " +
                    "USUARIO VARCHAR2(50) UNIQUE NOT NULL, " +
                    "PASSWORD VARCHAR2(50) NOT NULL, " +
                    "ESTADO VARCHAR2(20) DEFAULT 'ACTIVO')");
            System.out.println("Tabla creada: MOZOS");

            // 2. MESAS
            st.execute("CREATE TABLE MESAS (" +
                    "ID_MESA NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "NUMERO NUMBER NOT NULL, " +
                    "CAPACIDAD NUMBER NOT NULL, " +
                    "UBICACION VARCHAR2(50) DEFAULT 'SALON PRINCIPAL', " +
                    "ESTADO VARCHAR2(20) DEFAULT 'DISPONIBLE')");
            System.out.println("Tabla creada: MESAS");

            // 3. CLIENTES
            st.execute("CREATE TABLE CLIENTES (" +
                    "ID_CLIENTE NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "DNI VARCHAR2(15), " +
                    "NOMBRE VARCHAR2(50) NOT NULL, " +
                    "APELLIDO VARCHAR2(50) NOT NULL, " +
                    "TELEFONO VARCHAR2(20), " +
                    "EMAIL VARCHAR2(100), " +
                    "DIRECCION VARCHAR2(200))");
            System.out.println("Tabla creada: CLIENTES");

            // 4. PRODUCTOS
            st.execute("CREATE TABLE PRODUCTOS (" +
                    "ID_PRODUCTO NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "NOMBRE VARCHAR2(100) NOT NULL, " +
                    "PRECIO NUMBER(10,2) NOT NULL, " +
                    "CATEGORIA VARCHAR2(50), " +
                    "STOCK NUMBER DEFAULT 0, " +
                    "ESTADO VARCHAR2(20) DEFAULT 'DISPONIBLE')");
            System.out.println("Tabla creada: PRODUCTOS");

            // 5. PEDIDOS
            st.execute("CREATE TABLE PEDIDOS (" +
                    "ID_PEDIDO NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "ID_MOZO NUMBER NOT NULL, " +
                    "ID_MESA NUMBER, " +
                    "ID_CLIENTE NUMBER NOT NULL, " +
                    "FECHA_PEDIDO DATE DEFAULT SYSDATE, " +
                    "TIPO_PEDIDO VARCHAR2(20) DEFAULT 'SALON', " +
                    "ESTADO VARCHAR2(20) DEFAULT 'ABIERTO', " +
                    "METODO_PAGO VARCHAR2(30), " +
                    "TOTAL NUMBER(10,2) DEFAULT 0, " +
                    "CONSTRAINT FK_PEDIDOS_MOZO FOREIGN KEY (ID_MOZO) REFERENCES MOZOS(ID_MOZO), " +
                    "CONSTRAINT FK_PEDIDOS_MESA FOREIGN KEY (ID_MESA) REFERENCES MESAS(ID_MESA), " +
                    "CONSTRAINT FK_PEDIDOS_CLIENTE FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTES(ID_CLIENTE))");
            System.out.println("Tabla creada: PEDIDOS");

            // 6. DETALLES_PEDIDO
            st.execute("CREATE TABLE DETALLES_PEDIDO (" +
                    "ID_DETALLE NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " +
                    "ID_PEDIDO NUMBER NOT NULL, " +
                    "ID_PRODUCTO NUMBER NOT NULL, " +
                    "CANTIDAD NUMBER NOT NULL, " +
                    "PRECIO NUMBER(10,2) NOT NULL, " +
                    "SUBTOTAL NUMBER(10,2) NOT NULL, " +
                    "COMENTARIO VARCHAR2(200), " +
                    "CONSTRAINT FK_DETALLE_PEDIDO FOREIGN KEY (ID_PEDIDO) REFERENCES PEDIDOS(ID_PEDIDO), " +
                    "CONSTRAINT FK_DETALLE_PRODUCTO FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTOS(ID_PRODUCTO))");
            System.out.println("Tabla creada: DETALLES_PEDIDO");

            st.close();
            conexion.close();
            System.out.println("--- Base de datos de la Pollería inicializada correctamente ---");

        } catch (Exception e) {
            System.out.println("Error al crear las tablas");
            e.printStackTrace();
        }
    }

    // --- MÉTODO TEMPORAL PARA BORRAR TABLAS MANUALMENTE ---
    // Ejecuta este método desde el Main si necesitas reiniciar la base de datos.
    public static void reiniciarBaseDeDatos() {
        Connection conexion = DataBaseConnector.conectar();
        if (conexion == null) return;
        try {
            Statement st = conexion.createStatement();
            System.out.println("--- Eliminando tablas (Reinicio manual) ---");
            borrarTablas(st);
            st.close();
            conexion.close();
            System.out.println("--- Base de datos limpia. Vuelve a ejecutar el sistema. ---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void borrarTablas(Statement st) {
        String[] tablas = {"DETALLES_PEDIDO", "PEDIDOS", "PRODUCTOS", "CLIENTES", "MESAS", "MOZOS"};
        for (String tabla : tablas) {
            try {
                st.execute("DROP TABLE " + tabla + " CASCADE CONSTRAINTS");
                System.out.println("Tabla eliminada: " + tabla);
            } catch (Exception e) {
                System.out.println("La tabla " + tabla + " no existía (se omite).");
            }
        }
    }
}