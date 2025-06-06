package servicio;

import dao.CategoriaDAO;
import dto.Categoria;
import interfaces.ICategoria;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoriaServicio extends UnicastRemoteObject implements ICategoria {
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(CategoriaServicio.class.getName());
    
    private final CategoriaDAO categoriaDAO;
    
    public CategoriaServicio() throws RemoteException {
        super();
        this.categoriaDAO = new CategoriaDAO();
    }
    
    @Override
    public List<Categoria> listar() throws RemoteException {
        try {
            return categoriaDAO.listar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar categorías", e);
            throw new RemoteException("Error al listar categorías: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Categoria> listarActivos() throws RemoteException {
        try {
            return categoriaDAO.listarActivos();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar categorías activas", e);
            throw new RemoteException("Error al listar categorías activas: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Categoria> buscar(String nombre) throws RemoteException {
        try {
            return categoriaDAO.buscar(nombre);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar categorías", e);
            throw new RemoteException("Error al buscar categorías: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Categoria obtenerPorId(int id) throws RemoteException {
        try {
            return categoriaDAO.obtenerPorId(id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener categoría por ID", e);
            throw new RemoteException("Error al obtener categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean insertar(Categoria categoria) throws RemoteException {
        try {
            if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
                throw new RemoteException("El nombre de la categoría es obligatorio");
            }
            
            if (categoriaDAO.existeNombre(categoria.getNombre())) {
                throw new RemoteException("Ya existe una categoría con ese nombre");
            }
            
            return categoriaDAO.insertar(categoria);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar categoría", e);
            throw new RemoteException("Error al insertar categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean actualizar(Categoria categoria) throws RemoteException {
        try {
            if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
                throw new RemoteException("El nombre de la categoría es obligatorio");
            }
            
            return categoriaDAO.actualizar(categoria);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar categoría", e);
            throw new RemoteException("Error al actualizar categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean eliminar(int id) throws RemoteException {
        try {
            return categoriaDAO.eliminar(id);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar categoría", e);
            throw new RemoteException("Error al eliminar categoría: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean cambiarEstado(int id, boolean activo) throws RemoteException {
        try {
            return categoriaDAO.cambiarEstado(id, activo);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar estado de categoría", e);
            throw new RemoteException("Error al cambiar estado: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean existeNombre(String nombre) throws RemoteException {
        try {
            return categoriaDAO.existeNombre(nombre);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar nombre de categoría", e);
            throw new RemoteException("Error al verificar nombre: " + e.getMessage(), e);
        }
    }
    
    @Override
    public int contar() throws RemoteException {
        try {
            return categoriaDAO.contar();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar categorías", e);
            throw new RemoteException("Error al contar categorías: " + e.getMessage(), e);
        }
    }
}