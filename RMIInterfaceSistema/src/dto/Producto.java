package dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombre;
    private String descripcion;
    private int categoriaid;
    private String categoriaNombre; // Para mostrar en tabla
    private boolean activo;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
    // Constructor vacío
    public Producto() {
        this.activo = true;
    }
    
    // Constructor con parámetros
    public Producto(String nombre, String descripcion, int categoriaid) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoriaid = categoriaid;
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
    
    public String getDescripcion() { 
        return descripcion; 
    }
    
    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion; 
    }
    
    public int getCategoriaid() { 
        return categoriaid; 
    }
    
    public void setCategoriaid(int categoriaid) { 
        this.categoriaid = categoriaid; 
    }
    
    public String getCategoriaNombre() { 
        return categoriaNombre; 
    }
    
    public void setCategoriaNombre(String categoriaNombre) { 
        this.categoriaNombre = categoriaNombre; 
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
        return nombre;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return id == producto.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}