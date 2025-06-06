package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    // Configuración para XAMPP
    private static final String URL = "jdbc:mysql://localhost:3306/dbalmacendda22";
    private static final String USUARIO = "root";
    private static final String PASSWORD = ""; // XAMPP por defecto sin contraseña
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Pool de conexiones simple
    private static final int MAX_CONEXIONES = 10;
    private static Connection[] pool = new Connection[MAX_CONEXIONES];
    private static boolean[] disponible = new boolean[MAX_CONEXIONES];
    
    static {
        try {
            Class.forName(DRIVER);
            // Inicializar pool
            for (int i = 0; i < MAX_CONEXIONES; i++) {
                pool[i] = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                disponible[i] = true;
            }
            System.out.println("✅ Pool de conexiones inicializado: " + MAX_CONEXIONES + " conexiones");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Error: Driver MySQL no encontrado", e);
        } catch (SQLException e) {
            throw new RuntimeException("❌ Error: No se puede conectar a la base de datos", e);
        }
    }
    
    public static synchronized Connection obtenerConexion() throws SQLException {
        for (int i = 0; i < MAX_CONEXIONES; i++) {
            if (disponible[i]) {
                disponible[i] = false;
                if (pool[i].isClosed()) {
                    pool[i] = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                }
                return pool[i];
            }
        }
        // Si no hay conexiones disponibles, crear una nueva
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
    
    public static synchronized void liberarConexion(Connection conexion) {
        if (conexion != null) {
            for (int i = 0; i < MAX_CONEXIONES; i++) {
                if (pool[i] == conexion) {
                    disponible[i] = true;
                    return;
                }
            }
            // Si no es del pool, cerrarla
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
    
    public static boolean probarConexion() {
        try (Connection conn = obtenerConexion()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            return false;
        }
    }
}