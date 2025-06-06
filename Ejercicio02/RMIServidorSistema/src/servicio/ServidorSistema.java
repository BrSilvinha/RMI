package servicio;

import database.Conexion;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorSistema {
    
    private static final Logger LOGGER = Logger.getLogger(ServidorSistema.class.getName());
    private static final int PUERTO = 3239;
    private static final String SERVICIO_CATEGORIA = "categoria";
    private static final String SERVICIO_PRODUCTO = "producto";
    
    public static void main(String[] args) {
        try {
            // Verificar conexión a XAMPP
            System.out.println("🔄 Verificando conexión a XAMPP...");
            if (!Conexion.probarConexion()) {
                System.err.println("❌ No se pudo conectar a XAMPP MySQL");
                System.err.println("   Verifique que XAMPP esté ejecutándose");
                System.err.println("   Verifique que MySQL esté iniciado en XAMPP");
                return;
            }
            
            System.out.println("✅ Conexión a XAMPP MySQL establecida");
            
            // Obtener la dirección IP local
            String direccionIP = InetAddress.getLocalHost().getHostAddress();
            System.out.println("🌐 Dirección IP del servidor: " + direccionIP);
            
            // Crear el registro RMI
            System.out.println("🚀 Iniciando servidor RMI...");
            Registry registro = LocateRegistry.createRegistry(PUERTO);
            System.out.println("📋 Registro RMI creado en puerto: " + PUERTO);
            
            // Crear e implementar los servicios
            System.out.println("⚙️ Creando servicios...");
            CategoriaServicio categoriaServicio = new CategoriaServicio();
            ProductoServicio productoServicio = new ProductoServicio();
            
            // Registrar los servicios en el registro RMI
            registro.rebind(SERVICIO_CATEGORIA, categoriaServicio);
            registro.rebind(SERVICIO_PRODUCTO, productoServicio);
            
            System.out.println("✅ Servidor RMI iniciado correctamente");
            System.out.println("🔗 Servicios registrados:");
            System.out.println("   - " + SERVICIO_CATEGORIA);
            System.out.println("   - " + SERVICIO_PRODUCTO);
            System.out.println("📡 Escuchando en: " + direccionIP + ":" + PUERTO);
            System.out.println("🔄 Servidor listo para recibir peticiones...");
            System.out.println();
            System.out.println("📋 Para detener el servidor presione Ctrl+C");
            System.out.println("📊 Estado: ACTIVO");
            
            // Mantener el servidor ejecutándose
            while (true) {
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fatal al iniciar el servidor RMI", e);
            System.err.println("❌ Error fatal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}