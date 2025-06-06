package dao;

import database.Conexion;
import dto.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nombre, p.descripcion, p.categoriaid, p.activo, 
                   p.created_at, p.updated_at, c.nombre as categoria_nombre
            FROM productos p 
            LEFT JOIN categorias c ON p.categoriaid = c.id 
            ORDER BY p.nombre
            """;
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    productos.add(mapearResultSet(rs));
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return productos;
    }
    
    public List<Producto> listarActivos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nombre, p.descripcion, p.categoriaid, p.activo, 
                   p.created_at, p.updated_at, c.nombre as categoria_nombre
            FROM productos p 
            LEFT JOIN categorias c ON p.categoriaid = c.id 
            WHERE p.activo = 1 
            ORDER BY p.nombre
            """;
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    productos.add(mapearResultSet(rs));
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return productos;
    }
    
    public List<Producto> buscar(String nombre) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nombre, p.descripcion, p.categoriaid, p.activo, 
                   p.created_at, p.updated_at, c.nombre as categoria_nombre
            FROM productos p 
            LEFT JOIN categorias c ON p.categoriaid = c.id 
            WHERE p.nombre LIKE ? 
            ORDER BY p.nombre
            """;
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + nombre + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        productos.add(mapearResultSet(rs));
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return productos;
    }
    
    public List<Producto> listarPorCategoria(int categoriaid) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nombre, p.descripcion, p.categoriaid, p.activo, 
                   p.created_at, p.updated_at, c.nombre as categoria_nombre
            FROM productos p 
            LEFT JOIN categorias c ON p.categoriaid = c.id 
            WHERE p.categoriaid = ? 
            ORDER BY p.nombre
            """;
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, categoriaid);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        productos.add(mapearResultSet(rs));
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return productos;
    }
    
    public List<Producto> listarActivosPorCategoria(int categoriaid) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nombre, p.descripcion, p.categoriaid, p.activo, 
                   p.created_at, p.updated_at, c.nombre as categoria_nombre
            FROM productos p 
            LEFT JOIN categorias c ON p.categoriaid = c.id 
            WHERE p.categoriaid = ? AND p.activo = 1 
            ORDER BY p.nombre
            """;
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, categoriaid);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        productos.add(mapearResultSet(rs));
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return productos;
    }
    
    public Producto obtenerPorId(int id) throws SQLException {
        String sql = """
            SELECT p.id, p.nombre, p.descripcion, p.categoriaid, p.activo, 
                   p.created_at, p.updated_at, c.nombre as categoria_nombre
            FROM productos p 
            LEFT JOIN categorias c ON p.categoriaid = c.id 
            WHERE p.id = ?
            """;
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearResultSet(rs);
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return null;
    }
    
    public boolean insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, categoriaid, activo) VALUES (?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, producto.getNombre());
                stmt.setString(2, producto.getDescripcion());
                stmt.setInt(3, producto.getCategoriaid());
                stmt.setBoolean(4, producto.isActivo());
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            producto.setId(generatedKeys.getInt(1));
                        }
                    }
                    return true;
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return false;
    }
    
    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, categoriaid = ?, activo = ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, producto.getNombre());
                stmt.setString(2, producto.getDescripcion());
                stmt.setInt(3, producto.getCategoriaid());
                stmt.setBoolean(4, producto.isActivo());
                stmt.setInt(5, producto.getId());
                
                return stmt.executeUpdate() > 0;
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
    }
    
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
    }
    
    public boolean cambiarEstado(int id, boolean activo) throws SQLException {
        String sql = "UPDATE productos SET activo = ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setBoolean(1, activo);
                stmt.setInt(2, id);
                return stmt.executeUpdate() > 0;
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
    }
    
    public int contarPorCategoria(int categoriaid) throws SQLException {
        String sql = "SELECT COUNT(*) FROM productos WHERE categoriaid = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, categoriaid);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return 0;
    }
    
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) FROM productos";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return 0;
    }
    
    private Producto mapearResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setCategoriaid(rs.getInt("categoriaid"));
        producto.setCategoriaNombre(rs.getString("categoria_nombre"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setCreatedAt(rs.getTimestamp("created_at"));
        producto.setUpdatedAt(rs.getTimestamp("updated_at"));
        return producto;
    }
}