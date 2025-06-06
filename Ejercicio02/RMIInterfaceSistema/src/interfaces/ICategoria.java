package interfaces;

import dto.Categoria;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICategoria extends Remote {
    
    // Operaciones CRUD básicas
    List<Categoria> listar() throws RemoteException;
    List<Categoria> buscar(String nombre) throws RemoteException;
    Categoria obtenerPorId(int id) throws RemoteException;
    boolean insertar(Categoria categoria) throws RemoteException;
    boolean actualizar(Categoria categoria) throws RemoteException;
    boolean eliminar(int id) throws RemoteException;
    
    // Operaciones adicionales
    List<Categoria> listarActivos() throws RemoteException;
    boolean cambiarEstado(int id, boolean activo) throws RemoteException;
    boolean existeNombre(String nombre) throws RemoteException;
    int contar() throws RemoteException;
}