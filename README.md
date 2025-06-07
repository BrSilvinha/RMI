
# 🚀 Ejercicios RMI en Java

Este repositorio contiene dos ejercicios prácticos de **RMI (Remote Method Invocation)** en Java que demuestran diferentes niveles de complejidad en sistemas distribuidos.

## 📋 Tabla de Contenidos

- [Ejercicio 1: Calculadora RMI](#ejercicio-1-calculadora-rmi)
- [Ejercicio 2: Sistema de Gestión de Almacén](#ejercicio-2-sistema-de-gestión-de-almacén)
- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instalación y Configuración](#instalación-y-configuración)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)

---

## 🧮 Ejercicio 1: Calculadora RMI

### Descripción
Una aplicación cliente-servidor simple que implementa una calculadora distribuida usando RMI. Permite realizar operaciones matemáticas básicas de forma remota.

### 🎯 Características
- ✅ **Operaciones Matemáticas**: Suma, resta, multiplicación, división
- ✅ **Función Mayor**: Encuentra el mayor entre dos números
- ✅ **Interfaz Gráfica**: GUI Swing amigable para el cliente
- ✅ **Comunicación RMI**: Puerto 3233
- ✅ **Manejo de Errores**: División por cero y validaciones

### 📁 Estructura del Proyecto
```
Ejercicio01/RMICalculadora/
├── src/
│   ├── interfaces/
│   │   └── OperacionInterface.java    # Interfaz remota
│   └── clases/
│       ├── OperacionServidor.java     # Implementación del servidor
│       └── FrmConectar.java          # Cliente con GUI
├── build.xml                         # Configuración Ant
└── manifest.mf                       # Manifiesto JAR
```

### 🚀 Cómo Ejecutar

#### 1. Iniciar el Servidor
```bash
# Compilar y ejecutar el servidor
cd Ejercicio01/RMICalculadora
java -cp build/classes clases.OperacionServidor
```

#### 2. Ejecutar el Cliente
```bash
# En otra terminal
java -cp build/classes clases.FrmConectar
```

### 🔧 Configuración
- **Servidor**: `localhost:3233`
- **Servicio RMI**: `calculadora`
- **Interfaz**: `OperacionInterface`

### 📸 Funcionalidades
- **Calculadora Visual**: Interfaz gráfica con botones para cada operación
- **Conexión Automática**: Se conecta automáticamente al servidor al iniciar
- **Feedback Visual**: Mensajes de éxito/error con íconos
- **Validación de Entrada**: Verifica que los datos sean numéricos válidos

---

## 🏬 Ejercicio 2: Sistema de Gestión de Almacén

### Descripción
Un sistema completo de gestión de almacén con base de datos MySQL que permite administrar categorías y productos de forma distribuida usando RMI.

### 🎯 Características
- ✅ **Gestión de Categorías**: CRUD completo
- ✅ **Gestión de Productos**: CRUD completo con relaciones
- ✅ **Base de Datos MySQL**: Persistencia de datos con XAMPP
- ✅ **Pool de Conexiones**: Manejo eficiente de conexiones
- ✅ **Interfaz Moderna**: GUI avanzada con CardLayout
- ✅ **Estadísticas**: Dashboard con métricas del sistema
- ✅ **Validaciones**: Reglas de negocio implementadas

### 📁 Estructura del Proyecto
```
Ejercicio02/
├── RMIInterfaceSistema/              # Interfaces y DTOs compartidos
│   └── src/
│       ├── dto/
│       │   ├── Categoria.java        # Entidad Categoria
│       │   └── Producto.java         # Entidad Producto
│       └── interfaces/
│           ├── ICategoria.java       # Interfaz remota Categoria
│           └── IProducto.java        # Interfaz remota Producto
├── RMIServidorSistema/               # Servidor RMI
│   └── src/
│       ├── dao/
│       │   ├── CategoriaDAO.java     # Acceso a datos Categoria
│       │   └── ProductoDAO.java      # Acceso a datos Producto
│       ├── database/
│       │   └── Conexion.java         # Pool de conexiones
│       └── servicio/
│           ├── CategoriaServicio.java # Servicio Categoria
│           ├── ProductoServicio.java  # Servicio Producto
│           └── ServidorSistema.java   # Servidor principal
└── RMIClienteSistema/                # Cliente GUI
    └── src/
        └── cliente/
            └── GUIClient.java        # Interfaz gráfica avanzada
```

### 🗄️ Base de Datos

#### Esquema MySQL
```sql
-- Base de datos: dbalmacendda22

CREATE TABLE categorias (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    categoriaid INT,
    activo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (categoriaid) REFERENCES categorias(id)
);
```

### 🚀 Cómo Ejecutar

#### 1. Configurar XAMPP
```bash
# Iniciar XAMPP Control Panel
# Activar Apache y MySQL
# Crear base de datos 'dbalmacendda22'
# Ejecutar el script SQL de las tablas
```

#### 2. Iniciar el Servidor
```bash
cd Ejercicio02/RMIServidorSistema
java -cp build/classes servicio.ServidorSistema
```

#### 3. Ejecutar el Cliente
```bash
cd Ejercicio02/RMIClienteSistema
java -cp build/classes cliente.GUIClient
```

### 🔧 Configuración
- **Servidor**: `localhost:3239`
- **Servicios RMI**: `categoria`, `producto`
- **Base de Datos**: MySQL en XAMPP
- **Usuario DB**: `root` (sin contraseña)

### 📊 Funcionalidades del Cliente

#### 🏠 Panel Principal
- Dashboard con acceso rápido a todas las funciones
- Botones estilizados con efectos hover
- Información de conexión en tiempo real

#### 📂 Gestión de Categorías
- Listar todas las categorías con estado
- Agregar nuevas categorías
- Editar categorías existentes
- Eliminar categorías
- Filtros y búsquedas

#### 📦 Gestión de Productos
- CRUD completo de productos
- Asignación de categorías
- Visualización con información completa
- Relaciones con categorías

#### 📈 Panel de Estadísticas
- Total de categorías y productos
- Elementos activos vs inactivos
- Métricas en tiempo real
- Tarjetas visuales con colores

---

## 💻 Requisitos del Sistema

### Software Necesario
- ☕ **Java JDK 24** o superior
- 🐘 **XAMPP** (para MySQL)
- 🏗️ **NetBeans IDE** (recomendado)
- 🔧 **Apache Ant** (para compilación)

### Dependencias
- **MySQL Connector/J**: Driver JDBC para MySQL
- **Swing**: Para interfaces gráficas
- **RMI**: Incluido en Java SE

---

## 🛠️ Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone [URL_DEL_REPOSITORIO]
cd rmi-exercises
```

### 2. Configurar XAMPP
1. Descargar e instalar [XAMPP](https://www.apachefriends.org/)
2. Iniciar XAMPP Control Panel
3. Activar **Apache** y **MySQL**
4. Abrir **phpMyAdmin** (http://localhost/phpmyadmin)
5. Crear base de datos `dbalmacendda22`
6. Ejecutar scripts SQL para crear tablas

### 3. Configurar NetBeans
1. Abrir cada proyecto en NetBeans
2. Verificar que el JDK esté configurado correctamente
3. Compilar los proyectos (Build)

### 4. Configurar MySQL Driver
```bash
# Descargar MySQL Connector/J
# Agregar JAR al classpath de los proyectos
```

---

## 🛡️ Manejo de Errores

### Ejercicio 1
- ✅ Validación de entrada numérica
- ✅ División por cero controlada
- ✅ Reconexión automática
- ✅ Mensajes de error descriptivos

### Ejercicio 2
- ✅ Pool de conexiones robusto
- ✅ Transacciones de base de datos
- ✅ Validaciones de reglas de negocio
- ✅ Logging completo con niveles
- ✅ Recuperación de fallos de conexión

---

## 🎨 Tecnologías Utilizadas

| Tecnología | Ejercicio 1 | Ejercicio 2 | Descripción |
|------------|-------------|-------------|-------------|
| **Java RMI** | ✅ | ✅ | Comunicación remota |
| **Swing** | ✅ | ✅ | Interfaz gráfica |
| **MySQL** | ❌ | ✅ | Base de datos |
| **XAMPP** | ❌ | ✅ | Stack de desarrollo |
| **DAO Pattern** | ❌ | ✅ | Patrón de acceso a datos |
| **Pool Conexiones** | ❌ | ✅ | Gestión de recursos |

---

## 📞 Soporte y Contacto

### Problemas Comunes

#### 🔴 Error de Conexión RMI
```bash
# Verificar que el puerto esté disponible
netstat -an | grep :3233    # Ejercicio 1
netstat -an | grep :3239    # Ejercicio 2
```

#### 🔴 Error de Base de Datos
```bash
# Verificar que MySQL esté ejecutándose
# Verificar credenciales en Conexion.java
# Verificar que la base de datos existe
```

#### 🔴 Error de Compilación
```bash
# Verificar JDK version
java -version
javac -version

# Limpiar y recompilar
ant clean
ant compile
```

---

## 📄 Licencia

Este proyecto es con fines educativos y está disponible bajo licencia MIT.

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📚 Referencias

- [Oracle RMI Tutorial](https://docs.oracle.com/javase/tutorial/rmi/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Swing Documentation](https://docs.oracle.com/javase/tutorial/uiswing/)
- [XAMPP Documentation](https://www.apachefriends.org/docs/)

---

*Desarrollado con ❤️ para aprender RMI en Java*
