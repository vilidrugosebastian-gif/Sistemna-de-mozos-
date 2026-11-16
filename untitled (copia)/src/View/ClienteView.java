package View;

import Controller.ClienteController;
import Model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JFrame {
    private ClienteController clienteController = new ClienteController();
    private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtEmail, txtDireccion;
    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;

    public ClienteView() {
        setTitle("Gestión de Clientes");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Cliente"));

        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);

        JButton btnRegistrar = new JButton("Guardar Cliente");
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "DNI", "Nombres", "Apellidos", "Teléfono", "Dirección"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaClientes = new JTable(modeloTabla);
        add(new JScrollPane(tablaClientes), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Lista");
        JButton btnVolver = new JButton("Volver");
        panelBotones.add(btnActualizar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(e -> {
            Cliente c = new Cliente(0, txtDni.getText(), txtNombre.getText(), txtApellido.getText(),
                    txtTelefono.getText(), txtEmail.getText(), txtDireccion.getText());
            if (clienteController.registrar(c)) {
                JOptionPane.showMessageDialog(this, "Cliente registrado.");
                limpiar(); cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnActualizar.addActionListener(e -> cargarTabla());
        btnVolver.addActionListener(e -> dispose());

        cargarTabla();
    }

    private void limpiar() {
        txtDni.setText(""); txtNombre.setText(""); txtApellido.setText("");
        txtTelefono.setText(""); txtEmail.setText(""); txtDireccion.setText("");
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Cliente> lista = clienteController.listarTodos();
        for (Cliente c : lista) {
            modeloTabla.addRow(new Object[]{c.getIdCliente(), c.getDni(), c.getNombre(), c.getApellido(), c.getTelefono(), c.getDireccion()});
        }
    }
}