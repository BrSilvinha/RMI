package dao;

import database.Conexion;
import dto.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre, activo, created_at, updated_at FROM categorias ORDER BY nombre";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    categorias.add(mapearResultSet(rs));
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return categorias;
    }
    
    public List<Categoria> listarActivos() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre, activo, created_at, updated_at FROM categorias WHERE activo = 1 ORDER BY nombre";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                
                while (rs.next()) {
                    categorias.add(mapearResultSet(rs));
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return categorias;
    }
    
    public List<Categoria> buscar(String nombre) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT id, nombre, activo, created_at, updated_at FROM categorias WHERE nombre LIKE ? ORDER BY nombre";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + nombre + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        categorias.add(mapearResultSet(rs));
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return categorias;
    }
    
    public Categoria obtenerPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, activo, created_at, updated_at FROM categorias WHERE id = ?";
        
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
    
    public boolean insertar(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (nombre, activo) VALUES (?, ?)";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, categoria.getNombre());
                stmt.setBoolean(2, categoria.isActivo());
                
                int filasAfectadas = stmt.executeUpdate();
                
                if (filasAfectadas > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            categoria.setId(generatedKeys.getInt(1));
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
    
    public boolean actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET nombre = ?, activo = ? WHERE id = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria.getNombre());
                stmt.setBoolean(2, categoria.isActivo());
                stmt.setInt(3, categoria.getId());
                
                return stmt.executeUpdate() > 0;
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
    }
    
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM categorias WHERE id = ?";
        
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
        String sql = "UPDATE categorias SET activo = ? WHERE id = ?";
        
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
    
    public boolean existeNombre(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM categorias WHERE nombre = ?";
        
        Connection conn = null;
        try {
            conn = Conexion.obtenerConexion();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } finally {
            Conexion.liberarConexion(conn);
        }
        return false;
    }
    
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) FROM categorias";
        
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
    
    private Categoria mapearResultSet(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("id"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setActivo(rs.getBoolean("activo"));
        categoria.setCreatedAt(rs.getTimestamp("created_at"));
        categoria.setUpdatedAt(rs.getTimestamp("updated_at"));
        return categoria;
    }
}