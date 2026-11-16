package View;

import Controller.ProductoController;
import Model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoView extends JFrame {
    private ProductoController productoController = new ProductoController();
    private JTextField txtNombre, txtPrecio, txtCategoria, txtStock;
    private JComboBox<String> cbEstado;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public ProductoView() {
        setTitle("Gestión de Productos (Menú)");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Producto"));

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"DISPONIBLE", "AGOTADO"});
        panelFormulario.add(cbEstado);

        JButton btnRegistrar = new JButton("Guardar Producto");
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Precio", "Categoría", "Stock", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaProductos = new JTable(modeloTabla);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Lista");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(e -> {
            try {
                Producto p = new Producto(0, txtNombre.getText(), Double.parseDouble(txtPrecio.getText()),
                        txtCategoria.getText(), Integer.parseInt(txtStock.getText()), cbEstado.getSelectedItem().toString());
                if (productoController.registrar(p)) {
                    JOptionPane.showMessageDialog(this, "Producto registrado.");
                    limpiar(); cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Precio y Stock deben ser números.");
            }
        });
        btnActualizar.addActionListener(e -> cargarTabla());
        btnVolver.addActionListener(e -> dispose());

        cargarTabla();
    }

    private void limpiar() {
        txtNombre.setText(""); txtPrecio.setText(""); txtCategoria.setText(""); txtStock.setText("");
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Producto> lista = productoController.listarTodos();
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{p.getIdProducto(), p.getNombre(), p.getPrecio(), p.getCategoria(), p.getStock(), p.getEstado()});
        }
    }
}