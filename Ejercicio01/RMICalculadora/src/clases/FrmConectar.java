package clases;

import interfaces.OperacionInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.*;
import java.awt.*;

public class FrmConectar extends JFrame {
    
    private JTextField txtNum1, txtNum2, txtResultado;
    private JButton btnMayor, btnSumar, btnRestar, btnMultiplicar, btnDividir;
    private OperacionInterface servidor;
    
    public FrmConectar() {
        initComponents();
        conectarServidor();
    }
    
    private void initComponents() {
        setTitle("🧮 Calculadora RMI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Número 1
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Número 1:"), gbc);
        gbc.gridx = 1;
        txtNum1 = new JTextField(10);
        add(txtNum1, gbc);
        
        // Número 2
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Número 2:"), gbc);
        gbc.gridx = 1;
        txtNum2 = new JTextField(10);
        add(txtNum2, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnMayor = new JButton("Mayor");
        btnSumar = new JButton("Sumar");
        btnRestar = new JButton("Restar");
        btnMultiplicar = new JButton("Multiplicar");
        btnDividir = new JButton("Dividir");
        
        panelBotones.add(btnMayor);
        panelBotones.add(btnSumar);
        panelBotones.add(btnRestar);
        panelBotones.add(btnMultiplicar);
        panelBotones.add(btnDividir);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(panelBotones, gbc);
        
        // Resultado
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1;
        add(new JLabel("Resultado:"), gbc);
        gbc.gridx = 1;
        txtResultado = new JTextField(10);
        txtResultado.setEditable(false);
        add(txtResultado, gbc);
        
        // Event listeners
        btnMayor.addActionListener(e -> ejecutarOperacion("mayor"));
        btnSumar.addActionListener(e -> ejecutarOperacion("sumar"));
        btnRestar.addActionListener(e -> ejecutarOperacion("restar"));
        btnMultiplicar.addActionListener(e -> ejecutarOperacion("multiplicar"));
        btnDividir.addActionListener(e -> ejecutarOperacion("dividir"));
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void conectarServidor() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 3233);
            servidor = (OperacionInterface) registry.lookup("calculadora");
            JOptionPane.showMessageDialog(this, "✅ Conectado al servidor!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
        }
    }
    
    private void ejecutarOperacion(String operacion) {
        try {
            int num1 = Integer.parseInt(txtNum1.getText());
            int num2 = Integer.parseInt(txtNum2.getText());
            
            switch (operacion) {
                case "mayor":
                    txtResultado.setText(String.valueOf(servidor.calcularMayor(num1, num2)));
                    break;
                case "sumar":
                    txtResultado.setText(String.valueOf(servidor.sumar(num1, num2)));
                    break;
                case "restar":
                    txtResultado.setText(String.valueOf(servidor.restar(num1, num2)));
                    break;
                case "multiplicar":
                    txtResultado.setText(String.valueOf(servidor.multiplicar(num1, num2)));
                    break;
                case "dividir":
                    txtResultado.setText(String.format("%.2f", servidor.dividir(num1, num2)));
                    break;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmConectar().setVisible(true));
    }
}