# 🏢 Sistema de Gestión de Almacén - RMI

Un sistema distribuido desarrollado en Java que utiliza **RMI (Remote Method Invocation)** para la gestión de un almacén, permitiendo administrar categorías y productos a través de una arquitectura cliente-servidor.

![Java](https://img.shields.io/badge/Java-24-orange?logo=java)
![RMI](https://img.shields.io/badge/RMI-Distributed-blue)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql)
![Swing](https://img.shields.io/badge/Swing-GUI-green)

## 📋 Descripción

Este proyecto implementa un sistema de gestión de almacén utilizando tecnologías de computación distribuida. El sistema permite:

- ✅ **Gestión de Categorías**: Crear, editar, eliminar y listar categorías de productos
- ✅ **Gestión de Productos**: Administrar productos con sus respectivas categorías
- ✅ **Interfaz Gráfica**: Cliente con interfaz Swing intuitiva y moderna
- ✅ **Arquitectura Distribuida**: Comunicación cliente-servidor mediante RMI
- ✅ **Base de Datos**: Persistencia de datos en MySQL
- ✅ **Pool de Conexiones**: Gestión eficiente de conexiones a la base de datos

## 🏗️ Arquitectura del Sistema

```
┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
│   RMI Client    │  ────>  │   RMI Server    │  ────>  │   MySQL DB      │
│  (GUI Swing)    │         │  (Servicios)    │         │  (XAMPP)        │
└─────────────────┘         └─────────────────┘         └─────────────────┘
```

### Componentes:

1. **RMIInterfaceSistema**: Define contratos (interfaces) y DTOs compartidos
2. **RMIServidorSistema**: Implementa servicios remotos y lógica de negocio
3. **RMIClienteSistema**: Cliente con interfaz gráfica para usuarios finales

## 🛠️ Tecnologías Utilizadas

- **Java 24**: Lenguaje de programación principal
- **RMI**: Para comunicación distribuida
- **Swing**: Framework para la interfaz gráfica
- **MySQL**: Base de datos relacional
- **JDBC**: Conectividad con base de datos
- **NetBeans**: IDE de desarrollo

## 📋 Requisitos del Sistema

### Software Necesario:
- ☑️ **Java JDK 24** o superior
- ☑️ **XAMPP** (Apache + MySQL + PHP)
- ☑️ **NetBeans IDE** (recomendado)
- ☑️ **MySQL Connector/J 9.3.0**

### Especificaciones Mínimas:
- **RAM**: 4GB mínimo, 8GB recomendado
- **Espacio en Disco**: 500MB
- **Sistema Operativo**: Windows 10/11, Linux, macOS

## 🚀 Instalación y Configuración

### 1. Configurar la Base de Datos

1. **Instalar XAMPP**:
   - Descargar desde [https://www.apachefriends.org/](https://www.apachefriends.org/)
   - Instalar y ejecutar XAMPP Control Panel
   - Iniciar los servicios **Apache** y **MySQL**

2. **Crear la Base de Datos**:
   ```sql
   CREATE DATABASE dbalmacendda22;
   USE dbalmacendda22;

   CREATE TABLE categorias (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nombre VARCHAR(100) NOT NULL UNIQUE,
       activo BOOLEAN DEFAULT TRUE,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );

   CREATE TABLE productos (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nombre VARCHAR(100) NOT NULL,
       descripcion TEXT,
       categoriaid INT NOT NULL,
       activo BOOLEAN DEFAULT TRUE,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       FOREIGN KEY (categoriaid) REFERENCES categorias(id)
   );
   ```

### 2. Configurar el Proyecto

1. **Clonar el Repositorio**:
   ```bash
   git clone https://github.com/tuusuario/sistema-rmi-almacen.git
   cd sistema-rmi-almacen
   ```

2. **Configurar MySQL Connector**:
   - Descargar MySQL Connector/J desde [MySQL Official Site](https://dev.mysql.com/downloads/connector/j/)
   - Agregar el JAR al classpath de ambos proyectos (Servidor y Cliente)

3. **Verificar Configuración de Conexión**:
   ```java
   // En RMIServidorSistema/src/database/Conexion.java
   private static final String URL = "jdbc:mysql://localhost:3306/dbalmacendda22";
   private static final String USUARIO = "root";
   private static final String PASSWORD = ""; // XAMPP por defecto
   ```

## 📱 Uso del Sistema

### 1. Iniciar el Servidor RMI

1. Abrir NetBeans IDE
2. Abrir el proyecto **RMIServidorSistema**
3. Ejecutar la clase `servicio.ServidorSistema`
4. Verificar que aparezca el mensaje:
   ```
   ✅ Servidor RMI iniciado correctamente
   📡 Escuchando en: [IP]:[PUERTO]
   ```

### 2. Ejecutar el Cliente

1. Abrir el proyecto **RMIClienteSistema**
2. Ejecutar la clase `cliente.GUIClient`
3. La interfaz gráfica se abrirá automáticamente

### 3. Funcionalidades Disponibles

#### 🏷️ Gestión de Categorías:
- **Agregar**: Crear nuevas categorías
- **Editar**: Modificar categorías existentes
- **Eliminar**: Remover categorías (si no tienen productos)
- **Listar**: Ver todas las categorías con filtros

#### 📦 Gestión de Productos:
- **Agregar**: Crear productos asociados a categorías
- **Editar**: Modificar información de productos
- **Eliminar**: Remover productos del sistema
- **Búsqueda**: Filtrar productos por nombre o categoría

#### 📊 Dashboard:
- **Estadísticas**: Contadores en tiempo real
- **Estado**: Información del servidor y conexión

## 📁 Estructura del Proyecto

```
sistema-rmi-almacen/
├── RMIInterfaceSistema/           # Interfaces y DTOs compartidos
│   ├── src/
│   │   ├── dto/
│   │   │   ├── Categoria.java     # Modelo de datos de Categoría
│   │   │   └── Producto.java      # Modelo de datos de Producto
│   │   └── interfaces/
│   │       ├── ICategoria.java    # Interfaz remota para Categorías
│   │       └── IProducto.java     # Interfaz remota para Productos
│   └── dist/
│       └── RMIInterfaceSistema.jar
│
├── RMIServidorSistema/            # Servidor RMI
│   ├── src/
│   │   ├── dao/
│   │   │   ├── CategoriaDAO.java  # Acceso a datos de Categorías
│   │   │   └── ProductoDAO.java   # Acceso a datos de Productos
│   │   ├── database/
│   │   │   └── Conexion.java      # Pool de conexiones MySQL
│   │   ├── servicio/
│   │   │   ├── CategoriaServicio.java  # Servicio RMI de Categorías
│   │   │   ├── ProductoServicio.java   # Servicio RMI de Productos
│   │   │   └── ServidorSistema.java    # Servidor principal
│   └── dist/
│
└── RMIClienteSistema/             # Cliente con GUI
    ├── src/
    │   └── cliente/
    │       └── GUIClient.java     # Interfaz gráfica principal
    └── dist/
```

## ⚙️ Configuración Avanzada

### Cambiar Puerto RMI
```java
// En ServidorSistema.java y GUIClient.java
private static final int PUERTO = 3239; // Cambiar por el puerto deseado
```

### Configurar Base de Datos Remota
```java
// En Conexion.java
private static final String URL = "jdbc:mysql://IP_REMOTA:3306/dbalmacendda22";
private static final String USUARIO = "tu_usuario";
private static final String PASSWORD = "tu_password";
```

## 🐛 Solución de Problemas

### Error de Conexión a MySQL
```
❌ No se pudo conectar a XAMPP MySQL
```
**Solución**:
1. Verificar que XAMPP esté ejecutándose
2. Comprobar que MySQL esté iniciado
3. Verificar credenciales en `Conexion.java`

### Error de Puerto RMI
```
❌ Error: Puerto ya en uso
```
**Solución**:
1. Cambiar el puerto en `PUERTO` (servidor y cliente)
2. O terminar procesos que usen el puerto 3239

### Error de ClassNotFoundException
```
❌ Error: Driver MySQL no encontrado
```
**Solución**:
1. Agregar MySQL Connector/J al classpath
2. Verificar que el JAR esté en las librerías del proyecto

## 🤝 Contribución

1. **Fork** el repositorio
2. Crear una **rama feature** (`git checkout -b feature/nueva-funcionalidad`)
3. **Commit** los cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. **Push** a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear un **Pull Request**

### Estándares de Código
- Usar **camelCase** para variables y métodos
- Documentar métodos públicos con **Javadoc**
- Manejar excepciones apropiadamente
- Seguir principios **SOLID**

## 📝 TODO / Mejoras Futuras

- [ ] 🔐 Implementar autenticación y autorización
- [ ] 🔍 Agregar búsqueda avanzada con filtros
- [ ] 📊 Dashboard con gráficos y reportes
- [ ] 🌐 API REST complementaria
- [ ] 📱 Cliente web con tecnologías modernas
- [ ] 🐳 Containerización con Docker
- [ ] ⚡ Cache distribuido con Redis
- [ ] 📧 Sistema de notificaciones

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.


## 🙏 Agradecimientos

- Oracle por la tecnología RMI
- MySQL team por el sistema de base de datos
- Apache Friends por XAMPP
- NetBeans community
