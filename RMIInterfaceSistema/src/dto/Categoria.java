package dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombre;
    private boolean activo;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructor vacío
    public Categoria() {
        this.activo = true;
    }
    
    // Constructor con parámetros
    public Categoria(String nombre) {
        this.nombre = nombre;
        this.activo = true;
    }
    
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.activo = true;
    }
    
    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public boolean isActivo() { 
        return activo; 
    }
    
    public void setActivo(boolean activo) { 
        this.activo = activo; 
    }
    
    public Timestamp getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Timestamp createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public Timestamp getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(Timestamp updatedAt) { 
        this.updatedAt = updatedAt; 
    }
    
    @Override
    public String toString() {
        return nombre; // Para ComboBox
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return id == categoria.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}