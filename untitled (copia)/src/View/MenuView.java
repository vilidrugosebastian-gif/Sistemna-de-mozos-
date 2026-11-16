package View;

import javax.swing.*;
import java.awt.*;

public class MenuView extends JFrame {

    public MenuView() {
        // Configuración básica de la ventana principal
        setTitle("Sistema de Servicio - Pollería");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra toda la aplicación
        setLocationRelativeTo(null); // Centra en la pantalla
        setLayout(new BorderLayout());

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("POLLERÍA EL BUEN SABOR", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // --- PANEL DE BOTONES ---
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(6, 1, 10, 10)); // 6 filas, 1 columna
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton btnMozos = new JButton("Gestión de Mozos");
        JButton btnMesas = new JButton("Gestión de Mesas");
        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnProductos = new JButton("Gestión de Productos");
        JButton btnPedidos = new JButton("Tomar / Registrar Pedido");
        JButton btnSalir = new JButton("Salir del Sistema");

        panelBotones.add(btnMozos);
        panelBotones.add(btnMesas);
        panelBotones.add(btnClientes);
        panelBotones.add(btnProductos);
        panelBotones.add(btnPedidos);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // --- EVENTOS DE LOS BOTONES ---

        // Abrir Mozos
        btnMozos.addActionListener(e -> {
            new MozoView().setVisible(true);
        });

        // Abrir Mesas
        btnMesas.addActionListener(e -> {
            new MesaView().setVisible(true);
        });

        // Abrir Clientes
        btnClientes.addActionListener(e -> {
            new ClienteView().setVisible(true);
        });

        // Abrir Productos
        btnProductos.addActionListener(e -> {
            new ProductoView().setVisible(true);
        });

        // Abrir Pedidos (Lo haremos en el siguiente paso)
        btnPedidos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Módulo de Pedidos en construcción. ¡Ya casi!");
        });

        // Salir
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Salir", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}