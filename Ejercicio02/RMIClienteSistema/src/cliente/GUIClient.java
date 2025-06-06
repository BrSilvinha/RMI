package cliente;

import dto.Categoria;
import dto.Producto;
import interfaces.ICategoria;
import interfaces.IProducto;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class GUIClient extends JFrame {
    
    private static final String SERVIDOR_IP = "localhost";
    private static final int PUERTO = 3239;
    private static final String SERVICIO_CATEGORIA = "categoria";
    private static final String SERVICIO_PRODUCTO = "producto";
    
    private static ICategoria categoriaServicio;
    private static IProducto productoServicio;
    
    // Componentes principales
    private JLabel lblEstado;
    private CardLayout cardLayout;
    private JPanel panelPrincipal;
    
    public GUIClient() {
        initializeConnection();
        initializeGUI();
    }
    
    private void initializeConnection() {
        try {
            Registry registro = LocateRegistry.getRegistry(SERVIDOR_IP, PUERTO);
            categoriaServicio = (ICategoria) registro.lookup(SERVICIO_CATEGORIA);
            productoServicio = (IProducto) registro.lookup(SERVICIO_PRODUCTO);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error al conectar con el servidor RMI:\n" + e.getMessage(), 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private void initializeGUI() {
        setTitle("Sistema de Gestión de Almacén - Cliente RMI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Configurar Look and Feel (opcional, usar el por defecto)
        try {
            // Usar el Look and Feel del sistema o el por defecto
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Si hay error, usar el Look and Feel por defecto
        }
        
        // Panel principal con CardLayout
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        
        // Crear paneles
        panelPrincipal.add(createHomePanel(), "HOME");
        panelPrincipal.add(createCategoriasPanel(), "CATEGORIAS");
        panelPrincipal.add(createProductosPanel(), "PRODUCTOS");
        panelPrincipal.add(createEstadisticasPanel(), "ESTADISTICAS");
        
        // Barra de menú
        setJMenuBar(createMenuBar());
        
        // Barra de estado
        lblEstado = new JLabel("Conectado al servidor: " + SERVIDOR_IP + ":" + PUERTO);
        lblEstado.setBorder(new EmptyBorder(5, 10, 5, 10));
        lblEstado.setBackground(new Color(220, 255, 220));
        lblEstado.setOpaque(true);
        
        // Layout principal
        add(panelPrincipal, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);
        
        // Mostrar panel inicial
        cardLayout.show(panelPrincipal, "HOME");
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(240, 240, 240));
        
        // Menú Principal
        JMenu menuPrincipal = new JMenu("Inicio");
        JMenuItem itemHome = new JMenuItem("Ir al Inicio");
        itemHome.addActionListener(e -> cardLayout.show(panelPrincipal, "HOME"));
        menuPrincipal.add(itemHome);
        
        // Menú Categorías
        JMenu menuCategorias = new JMenu("Categorías");
        JMenuItem itemCategorias = new JMenuItem("Gestionar Categorías");
        itemCategorias.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "CATEGORIAS");
            refreshCategoriasTable();
        });
        menuCategorias.add(itemCategorias);
        
        // Menú Productos
        JMenu menuProductos = new JMenu("Productos");
        JMenuItem itemProductos = new JMenuItem("Gestionar Productos");
        itemProductos.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "PRODUCTOS");
            refreshProductosTable();
        });
        menuProductos.add(itemProductos);
        
        // Menú Estadísticas
        JMenu menuEstadisticas = new JMenu("Estadísticas");
        JMenuItem itemEstadisticas = new JMenuItem("Ver Estadísticas");
        itemEstadisticas.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "ESTADISTICAS");
            refreshEstadisticas();
        });
        menuEstadisticas.add(itemEstadisticas);
        
        menuBar.add(menuPrincipal);
        menuBar.add(menuCategorias);
        menuBar.add(menuProductos);
        menuBar.add(menuEstadisticas);
        
        return menuBar;
    }
    
    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Panel central con información
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Título principal
        JLabel titulo = new JLabel("Sistema de Gestión de Almacén");
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(new Color(50, 50, 150));
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(20, 0, 30, 0);
        centerPanel.add(titulo, gbc);
        
        // Subtítulo
        JLabel subtitulo = new JLabel("Sistema distribuido con RMI - Conectado exitosamente");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(Color.GRAY);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 40, 0);
        centerPanel.add(subtitulo, gbc);
        
        // Panel de botones principales
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JButton btnCategorias = createStyledButton("Gestionar Categorías", new Color(70, 130, 180));
        btnCategorias.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "CATEGORIAS");
            refreshCategoriasTable();
        });
        
        JButton btnProductos = createStyledButton("Gestionar Productos", new Color(60, 179, 113));
        btnProductos.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "PRODUCTOS");
            refreshProductosTable();
        });
        
        JButton btnEstadisticas = createStyledButton("Ver Estadísticas", new Color(255, 140, 0));
        btnEstadisticas.addActionListener(e -> {
            cardLayout.show(panelPrincipal, "ESTADISTICAS");
            refreshEstadisticas();
        });
        
        JButton btnSalir = createStyledButton("Salir", new Color(220, 20, 60));
        btnSalir.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(btnCategorias);
        buttonPanel.add(btnProductos);
        buttonPanel.add(btnEstadisticas);
        buttonPanel.add(btnSalir);
        
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 0, 0);
        centerPanel.add(buttonPanel, gbc);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 80));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    // Variables para tablas
    private JTable tableCategorias;
    private DefaultTableModel modelCategorias;
    private JTable tableProductos;
    private DefaultTableModel modelProductos;
    
    private JPanel createCategoriasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titulo = new JLabel("Gestión de Categorías");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Tabla de categorías
        String[] columnas = {"ID", "Nombre", "Estado", "Fecha Creación"};
        modelCategorias = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableCategorias = new JTable(modelCategorias);
        tableCategorias.setRowHeight(25);
        tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableCategorias.getTableHeader().setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(tableCategorias);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(60, 179, 113));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(this::agregarCategoria);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 140, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(this::editarCategoria);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(this::eliminarCategoria);
        
        JButton btnRefresh = new JButton("Actualizar");
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> refreshCategoriasTable());
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRefresh);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createProductosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titulo = new JLabel("Gestión de Productos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Tabla de productos
        String[] columnas = {"ID", "Nombre", "Descripción", "Categoría", "Estado", "Fecha Creación"};
        modelProductos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableProductos = new JTable(modelProductos);
        tableProductos.setRowHeight(25);
        tableProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableProductos.getTableHeader().setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(tableProductos);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBackground(new Color(60, 179, 113));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(this::agregarProducto);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(255, 140, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(this::editarProducto);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(this::eliminarProducto);
        
        JButton btnRefresh = new JButton("Actualizar");
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.addActionListener(e -> refreshProductosTable());
        
        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEditar);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnRefresh);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createEstadisticasPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("Estadísticas del Sistema");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(titulo, BorderLayout.NORTH);
        
        // Panel de estadísticas
        JPanel statsPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        statsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Crear paneles de estadísticas
        statsPanel.add(createStatCard("Total Categorías", "0", new Color(70, 130, 180)));
        statsPanel.add(createStatCard("Total Productos", "0", new Color(60, 179, 113)));
        statsPanel.add(createStatCard("Categorías Activas", "0", new Color(255, 140, 0)));
        statsPanel.add(createStatCard("Productos Activos", "0", new Color(147, 112, 219)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        // Botón de actualizar
        JButton btnRefresh = new JButton("Actualizar Estadísticas");
        btnRefresh.setBackground(new Color(70, 130, 180));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFont(new Font("Arial", Font.BOLD, 14));
        btnRefresh.addActionListener(e -> refreshEstadisticas());
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnRefresh);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createRaisedBevelBorder());
        card.setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(color);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(new EmptyBorder(10, 10, 5, 10));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 32));
        lblValue.setForeground(color);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblValue.setBorder(new EmptyBorder(5, 10, 10, 10));
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
    
    // Métodos para actualizar datos
    private void refreshCategoriasTable() {
        try {
            modelCategorias.setRowCount(0);
            List<Categoria> categorias = categoriaServicio.listar();
            
            for (Categoria categoria : categorias) {
                Object[] row = {
                    categoria.getId(),
                    categoria.getNombre(),
                    categoria.isActivo() ? "Activo" : "Inactivo",
                    categoria.getCreatedAt() != null ? categoria.getCreatedAt().toString().substring(0, 19) : "N/A"
                };
                modelCategorias.addRow(row);
            }
            
            lblEstado.setText("Categorías actualizadas - Total: " + categorias.size());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshProductosTable() {
        try {
            modelProductos.setRowCount(0);
            List<Producto> productos = productoServicio.listar();
            
            for (Producto producto : productos) {
                Object[] row = {
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getCategoriaNombre(),
                    producto.isActivo() ? "Activo" : "Inactivo",
                    producto.getCreatedAt() != null ? producto.getCreatedAt().toString().substring(0, 19) : "N/A"
                };
                modelProductos.addRow(row);
            }
            
            lblEstado.setText("Productos actualizados - Total: " + productos.size());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshEstadisticas() {
        try {
            int totalCategorias = categoriaServicio.contar();
            int totalProductos = productoServicio.contar();
            int categoriasActivas = categoriaServicio.listarActivos().size();
            int productosActivos = productoServicio.listarActivos().size();
            
            // Actualizar las tarjetas de estadísticas
            Component[] components = ((JPanel)((JPanel)panelPrincipal.getComponent(3)).getComponent(1)).getComponents();
            
            updateStatCard((JPanel)components[0], String.valueOf(totalCategorias));
            updateStatCard((JPanel)components[1], String.valueOf(totalProductos));
            updateStatCard((JPanel)components[2], String.valueOf(categoriasActivas));
            updateStatCard((JPanel)components[3], String.valueOf(productosActivos));
            
            lblEstado.setText("Estadísticas actualizadas");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar estadísticas: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateStatCard(JPanel card, String newValue) {
        JLabel valueLabel = (JLabel) card.getComponent(1);
        valueLabel.setText(newValue);
    }
    
    // Métodos para manejar categorías
    private void agregarCategoria(ActionEvent e) {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre de la categoría:", 
            "Nueva Categoría", JOptionPane.QUESTION_MESSAGE);
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            try {
                Categoria nuevaCategoria = new Categoria(nombre.trim());
                if (categoriaServicio.insertar(nuevaCategoria)) {
                    JOptionPane.showMessageDialog(this, "Categoría agregada exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    refreshCategoriasTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la categoría", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void editarCategoria(ActionEvent e) {
        int selectedRow = tableCategorias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría para editar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (Integer) modelCategorias.getValueAt(selectedRow, 0);
            String nombreActual = (String) modelCategorias.getValueAt(selectedRow, 1);
            
            String nuevoNombre = JOptionPane.showInputDialog(this, "Editar nombre:", nombreActual);
            
            if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                Categoria categoria = categoriaServicio.obtenerPorId(id);
                if (categoria != null) {
                    categoria.setNombre(nuevoNombre.trim());
                    if (categoriaServicio.actualizar(categoria)) {
                        JOptionPane.showMessageDialog(this, "Categoría actualizada exitosamente", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        refreshCategoriasTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar la categoría", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarCategoria(ActionEvent e) {
        int selectedRow = tableCategorias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (Integer) modelCategorias.getValueAt(selectedRow, 0);
            String nombre = (String) modelCategorias.getValueAt(selectedRow, 1);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar la categoría '" + nombre + "'?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (categoriaServicio.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Categoría eliminada exitosamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    refreshCategoriasTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la categoría", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Métodos para manejar productos
    private void agregarProducto(ActionEvent e) {
        try {
            List<Categoria> categorias = categoriaServicio.listarActivos();
            if (categorias.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay categorías disponibles. Cree una categoría primero.", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Crear diálogo personalizado
            JDialog dialog = new JDialog(this, "Nuevo Producto", true);
            dialog.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            
            JTextField txtNombre = new JTextField(20);
            JTextField txtDescripcion = new JTextField(20);
            JComboBox<Categoria> cmbCategoria = new JComboBox<>();
            
            for (Categoria categoria : categorias) {
                cmbCategoria.addItem(categoria);
            }
            
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;
            
            gbc.gridx = 0; gbc.gridy = 0;
            dialog.add(new JLabel("Nombre:"), gbc);
            gbc.gridx = 1;
            dialog.add(txtNombre, gbc);
            
            gbc.gridx = 0; gbc.gridy = 1;
            dialog.add(new JLabel("Descripción:"), gbc);
            gbc.gridx = 1;
            dialog.add(txtDescripcion, gbc);
            
            gbc.gridx = 0; gbc.gridy = 2;
            dialog.add(new JLabel("Categoría:"), gbc);
            gbc.gridx = 1;
            dialog.add(cmbCategoria, gbc);
            
            JPanel buttonPanel = new JPanel();
            JButton btnOK = new JButton("Guardar");
            JButton btnCancel = new JButton("Cancelar");
            
            btnOK.addActionListener(ev -> {
                String nombre = txtNombre.getText().trim();
                String descripcion = txtDescripcion.getText().trim();
                Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
                
                if (!nombre.isEmpty() && categoria != null) {
                    try {
                        Producto nuevoProducto = new Producto(nombre, descripcion, categoria.getId());
                        if (productoServicio.insertar(nuevoProducto)) {
                            JOptionPane.showMessageDialog(dialog, "✅ Producto agregado exitosamente");
                            refreshProductosTable();
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(dialog, "❌ Error al agregar el producto");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "El nombre es obligatorio");
                }
            });
            
            btnCancel.addActionListener(ev -> dialog.dispose());
            
            buttonPanel.add(btnOK);
            buttonPanel.add(btnCancel);
            
            gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
            dialog.add(buttonPanel, gbc);
            
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void editarProducto(ActionEvent e) {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (Integer) modelProductos.getValueAt(selectedRow, 0);
            Producto producto = productoServicio.obtenerPorId(id);
            
            if (producto != null) {
                String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:", producto.getNombre());
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    String nuevaDescripcion = JOptionPane.showInputDialog(this, "Nueva descripción:", producto.getDescripcion());
                    
                    producto.setNombre(nuevoNombre.trim());
                    if (nuevaDescripcion != null) {
                        producto.setDescripcion(nuevaDescripcion.trim());
                    }
                    
                    if (productoServicio.actualizar(producto)) {
                        JOptionPane.showMessageDialog(this, "Producto actualizado exitosamente");
                        refreshProductosTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al actualizar el producto");
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarProducto(ActionEvent e) {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int id = (Integer) modelProductos.getValueAt(selectedRow, 0);
            String nombre = (String) modelProductos.getValueAt(selectedRow, 1);
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de eliminar el producto '" + nombre + "'?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (productoServicio.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado exitosamente");
                    refreshProductosTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el producto");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new GUIClient().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}