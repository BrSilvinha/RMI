package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OperacionInterface extends Remote {
    public int calcularMayor(int num1, int num2) throws RemoteException;
    public int sumar(int num1, int num2) throws RemoteException;
    public int restar(int num1, int num2) throws RemoteException;
    public int multiplicar(int num1, int num2) throws RemoteException;
    public double dividir(int num1, int num2) throws RemoteException;
}