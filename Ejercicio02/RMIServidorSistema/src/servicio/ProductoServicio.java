package servicio;

import dao.ProductoDAO;
import dto.Producto;
import interfaces.IProducto;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductoServicio extends UnicastRemoteObject implements IProducto {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ProductoServicio.class.getName());
    
    private final ProductoDAO productoDAO;
    
    public ProductoServicio() throws RemoteException {
        super();
        this.productoDAO = new ProductoDAO();
    }
    
    @Override
    public List<Producto> listar() throws RemoteException {
        try {
            return productoDAO.listar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar productos", e);
            throw new RemoteException("Error al listar productos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Producto> listarActivos() throws RemoteException {
        try {
            return productoDAO.listarActivos();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar productos activos", e);
            throw new RemoteException("Error al listar productos activos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Producto> buscar(String nombre) throws RemoteException {
        try {
            return productoDAO.buscar(nombre);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar productos", e);
            throw new RemoteException("Error al buscar productos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Producto> listarPorCategoria(int categoriaid) throws RemoteException {
        try {
            return productoDAO.listarPorCategoria(categoriaid);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar productos por categoría", e);
            throw new RemoteException("Error al listar productos por categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Producto> listarActivosPorCategoria(int categoriaid) throws RemoteException {
        try {
            return productoDAO.listarActivosPorCategoria(categoriaid);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar productos activos por categoría", e);
            throw new RemoteException("Error al listar productos activos por categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Producto obtenerPorId(int id) throws RemoteException {
        try {
            return productoDAO.obtenerPorId(id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener producto por ID", e);
            throw new RemoteException("Error al obtener producto: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean insertar(Producto producto) throws RemoteException {
        try {
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                throw new RemoteException("El nombre del producto es obligatorio");
            }
            
            if (producto.getCategoriaid() <= 0) {
                throw new RemoteException("Debe seleccionar una categoría válida");
            }
            
            return productoDAO.insertar(producto);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar producto", e);
            throw new RemoteException("Error al insertar producto: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean actualizar(Producto producto) throws RemoteException {
        try {
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                throw new RemoteException("El nombre del producto es obligatorio");
            }
            
            if (producto.getCategoriaid() <= 0) {
                throw new RemoteException("Debe seleccionar una categoría válida");
            }
            
            return productoDAO.actualizar(producto);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar producto", e);
            throw new RemoteException("Error al actualizar producto: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminar(int id) throws RemoteException {
        try {
            return productoDAO.eliminar(id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar producto", e);
            throw new RemoteException("Error al eliminar producto: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean cambiarEstado(int id, boolean activo) throws RemoteException {
        try {
            return productoDAO.cambiarEstado(id, activo);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar estado de producto", e);
            throw new RemoteException("Error al cambiar estado: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int contarPorCategoria(int categoriaid) throws RemoteException {
        try {
            return productoDAO.contarPorCategoria(categoriaid);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar productos por categoría", e);
            throw new RemoteException("Error al contar productos: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int contar() throws RemoteException {
        try {
            return productoDAO.contar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar productos", e);
            throw new RemoteException("Error al contar productos: " + e.getMessage(), e);
        }
    }
}