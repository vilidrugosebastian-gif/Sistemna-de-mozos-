package View;

import Controller.ClienteController;
import Controller.MozoController;
import Controller.PedidoController;
import Controller.ProductoController;
import Controller.MesaController;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoView extends JFrame {

    private PedidoController pedidoController = new PedidoController();
    private ClienteController clienteController = new ClienteController();
    private ProductoController productoController = new ProductoController();
    private MesaController mesaController = new MesaController();
    private MozoController mozoController = new MozoController();

    // Componentes de la cabecera (Pedido)
    private JComboBox<Cliente> cbClientes;
    private JComboBox<Mesa> cbMesas;
    private JComboBox<Mozo> cbMozos;
    private JComboBox<String> cbTipoPedido;

    // Componentes para agregar productos (Detalle)
    private JComboBox<Producto> cbProductos;
    private JTextField txtCantidad;
    private JTextField txtComentario;

    // Tabla de detalles
    private JTable tablaDetalles;
    private DefaultTableModel modeloTabla;
    private List<DetallePedido> listaDetalles = new ArrayList<>();

    // Total
    private JLabel lblTotal;

    public PedidoView() {
        setTitle("Registrar Pedido - Mozo");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. PANEL SUPERIOR (Cabecera del Pedido) ---
        JPanel panelCabecera = new JPanel(new GridLayout(2, 4, 10, 10));
        panelCabecera.setBorder(BorderFactory.createTitledBorder("Datos del Pedido"));

        panelCabecera.add(new JLabel("Cliente:"));
        cbClientes = new JComboBox<>();
        cargarClientes();
        panelCabecera.add(cbClientes);

        panelCabecera.add(new JLabel("Mesa:"));
        cbMesas = new JComboBox<>();
        cargarMesas();
        panelCabecera.add(cbMesas);

        panelCabecera.add(new JLabel("Mozo:"));
        cbMozos = new JComboBox<>();
        cargarMozos();
        panelCabecera.add(cbMozos);

        panelCabecera.add(new JLabel("Tipo:"));
        cbTipoPedido = new JComboBox<>(new String[]{"SALON", "LLEVAR", "DELIVERY"});
        panelCabecera.add(cbTipoPedido);

        add(panelCabecera, BorderLayout.NORTH);

        // --- 2. PANEL CENTRAL (Agregar Productos y Tabla) ---
        JPanel panelCentro = new JPanel(new BorderLayout());

        // Formulario para agregar un producto
        JPanel panelAgregar = new JPanel(new FlowLayout());
        panelAgregar.add(new JLabel("Producto:"));
        cbProductos = new JComboBox<>();
        cargarProductos();
        panelAgregar.add(cbProductos);

        panelAgregar.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField(5);
        panelAgregar.add(txtCantidad);

        panelAgregar.add(new JLabel("Comentario:"));
        txtComentario = new JTextField(15);
        panelAgregar.add(txtComentario);

        JButton btnAgregar = new JButton("Agregar al Ticket");
        panelAgregar.add(btnAgregar);

        panelCentro.add(panelAgregar, BorderLayout.NORTH);

        // Tabla de detalles
        String[] columnas = {"Producto", "Cantidad", "Precio", "Subtotal", "Comentario"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaDetalles = new JTable(modeloTabla);
        panelCentro.add(new JScrollPane(tablaDetalles), BorderLayout.CENTER);

        add(panelCentro, BorderLayout.CENTER);

        // --- 3. PANEL INFERIOR (Total y Botones) ---
        JPanel panelInferior = new JPanel(new BorderLayout());

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("TOTAL: S/ 0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 18));
        panelTotal.add(lblTotal);
        panelInferior.add(panelTotal, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        JButton btnRegistrarPedido = new JButton("Registrar Pedido y Enviar a Cocina");
        JButton btnLimpiar = new JButton("Limpiar Ticket");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnRegistrarPedido);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // --- EVENTOS ---

        // Evento Agregar Producto
        btnAgregar.addActionListener(e -> {
            try {
                Producto p = (Producto) cbProductos.getSelectedItem();
                if (p == null) return;

                int cantidad = Integer.parseInt(txtCantidad.getText());
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
                    return;
                }

                // Validar stock
                if (p.getStock() < cantidad) {
                    JOptionPane.showMessageDialog(this, "No hay suficiente stock. Stock actual: " + p.getStock());
                    return;
                }

                double subtotal = cantidad * p.getPrecio();
                String comentario = txtComentario.getText();

                // Creamos el detalle temporal (ID 0 porque aún no se guarda en Oracle)
                DetallePedido d = new DetallePedido(0, 0, p.getIdProducto(), cantidad, p.getPrecio(), subtotal, comentario);
                listaDetalles.add(d);

                // Agregamos a la tabla visual
                modeloTabla.addRow(new Object[]{p.getNombre(), cantidad, p.getPrecio(), subtotal, comentario});

                // Actualizamos el total
                actualizarTotal();

                // Limpiamos los campos
                txtCantidad.setText("");
                txtComentario.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
            }
        });

        // Evento Registrar Pedido
        btnRegistrarPedido.addActionListener(e -> {
            if (listaDetalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El ticket está vacío. Agregue productos.");
                return;
            }

            Cliente c = (Cliente) cbClientes.getSelectedItem();
            Mesa m = (Mesa) cbMesas.getSelectedItem();
            Mozo z = (Mozo) cbMozos.getSelectedItem();
            String tipo = cbTipoPedido.getSelectedItem().toString();

            if (c == null || z == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente y un mozo.");
                return;
            }

            // Si es para llevar, la mesa puede ser nula (0)
            int idMesa = (m != null && tipo.equals("SALON")) ? m.getIdMesa() : 0;

            // Creamos el Pedido (fecha actual)
            Pedido pedido = new Pedido(0, z.getIdMozo(), idMesa, c.getIdCliente(),
                    LocalDate.now(), tipo, "ABIERTO", "", 0.0);

            // El Controlador calculará el total y llamará al DAO
            if (pedidoController.registrar(pedido, listaDetalles)) {
                JOptionPane.showMessageDialog(this, "¡Pedido registrado con éxito! Enviado a cocina.");
                limpiarTodo();
                cargarProductos(); // Refrescamos stock en el combo
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento Limpiar
        btnLimpiar.addActionListener(e -> limpiarTodo());

        // Evento Volver
        btnVolver.addActionListener(e -> dispose());
    }

    private void actualizarTotal() {
        double total = 0;
        for (DetallePedido d : listaDetalles) {
            total += d.getSubtotal();
        }
        lblTotal.setText(String.format("TOTAL: S/ %.2f", total));
    }

    private void limpiarTodo() {
        listaDetalles.clear();
        modeloTabla.setRowCount(0);
        lblTotal.setText("TOTAL: S/ 0.00");
        txtCantidad.setText("");
        txtComentario.setText("");
    }

    // Métodos para cargar los ComboBox
    private void cargarClientes() {
        cbClientes.removeAllItems();
        for (Cliente c : clienteController.listarTodos()) {
            cbClientes.addItem(c);
        }
        // Personalizar cómo se ve el cliente en el combo
        cbClientes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    Cliente c = (Cliente) value;
                    setText(c.getNombre() + " " + c.getApellido());
                }
                return this;
            }
        });
    }

    private void cargarMesas() {
        cbMesas.removeAllItems();
        for (Mesa m : mesaController.listarDisponibles()) {
            cbMesas.addItem(m);
        }
        cbMesas.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Mesa) {
                    Mesa m = (Mesa) value;
                    setText("Mesa " + m.getNumero() + " (" + m.getUbicacion() + ")");
                }
                return this;
            }
        });
    }

    private void cargarMozos() {
        cbMozos.removeAllItems();
        for (Mozo m : mozoController.listarTodos()) {
            cbMozos.addItem(m);
        }
        cbMozos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Mozo) {
                    Mozo m = (Mozo) value;
                    setText(m.getNombre() + " " + m.getApellido());
                }
                return this;
            }
        });
    }

    private void cargarProductos() {
        cbProductos.removeAllItems();
        for (Producto p : productoController.listarDisponibles()) {
            cbProductos.addItem(p);
        }
        cbProductos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto) {
                    Producto p = (Producto) value;
                    setText(p.getNombre() + " - S/ " + p.getPrecio());
                }
                return this;
            }
        });
    }
}