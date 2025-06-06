package clases;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.OperacionInterface;

public class OperacionServidor extends UnicastRemoteObject implements OperacionInterface {
    
    private static final long serialVersionUID = 1L;
    private static final int PUERTO = 3233;
    
    public OperacionServidor() throws RemoteException {
        super();
        System.out.println("✅ Servidor creado correctamente");
    }
    
    @Override
    public int calcularMayor(int num1, int num2) throws RemoteException {
        System.out.println("Ejecutando: calcularMayor(" + num1 + ", " + num2 + ")");
        int resultado = (num1 > num2) ? num1 : num2;
        System.out.println("Resultado: " + resultado);
        return resultado;
    }
    
    @Override
    public int sumar(int num1, int num2) throws RemoteException {
        System.out.println("Ejecutando: sumar(" + num1 + ", " + num2 + ")");
        int resultado = num1 + num2;
        System.out.println("Resultado: " + resultado);
        return resultado;
    }
    
    @Override
    public int restar(int num1, int num2) throws RemoteException {
        System.out.println("Ejecutando: restar(" + num1 + ", " + num2 + ")");
        int resultado = num1 - num2;
        System.out.println("Resultado: " + resultado);
        return resultado;
    }
    
    @Override
    public int multiplicar(int num1, int num2) throws RemoteException {
        System.out.println("Ejecutando: multiplicar(" + num1 + ", " + num2 + ")");
        int resultado = num1 * num2;
        System.out.println("Resultado: " + resultado);
        return resultado;
    }
    
    @Override
    public double dividir(int num1, int num2) throws RemoteException {
        System.out.println("Ejecutando: dividir(" + num1 + ", " + num2 + ")");
        if (num2 == 0) {
            throw new RemoteException("División por cero no permitida");
        }
        double resultado = (double) num1 / num2;
        System.out.println("Resultado: " + resultado);
        return resultado;
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("=== SERVIDOR RMI ===");
        new OperacionServidor().iniciarServidor();
    }
    
    public void iniciarServidor() {
        try {
            String dirIP = InetAddress.getLocalHost().toString();
            System.out.println("IP: " + dirIP + " Puerto: " + PUERTO);
            
            Registry registry = LocateRegistry.createRegistry(PUERTO);
            registry.bind("calculadora", this);
            
            System.out.println("🟢 SERVIDOR INICIADO CORRECTAMENTE");
            System.out.println("🔗 Servicio: 'calculadora'");
            System.out.println("⏳ Esperando clientes...");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}