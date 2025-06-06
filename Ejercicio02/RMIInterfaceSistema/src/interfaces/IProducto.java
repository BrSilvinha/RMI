package interfaces;

import dto.Producto;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IProducto extends Remote {
    
    // Operaciones CRUD básicas
    List<Producto> listar() throws RemoteException;
    List<Producto> buscar(String nombre) throws RemoteException;
    List<Producto> listarPorCategoria(int categoriaid) throws RemoteException;
    Producto obtenerPorId(int id) throws RemoteException;
    boolean insertar(Producto producto) throws RemoteException;
    boolean actualizar(Producto producto) throws RemoteException;
    boolean eliminar(int id) throws RemoteException;
    
    // Operaciones adicionales
    List<Producto> listarActivos() throws RemoteException;
    List<Producto> listarActivosPorCategoria(int categoriaid) throws RemoteException;
    boolean cambiarEstado(int id, boolean activo) throws RemoteException;
    int contarPorCategoria(int categoriaid) throws RemoteException;
    int contar() throws RemoteException;
}