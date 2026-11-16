package View;

import Controller.MozoController;
import Model.Mozo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MozoView extends JFrame {

    private MozoController mozoController = new MozoController();

    // Componentes de la interfaz
    private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtEmail;
    private JTextField txtCargo, txtUsuario, txtPassword;
    private JComboBox<String> cbEstado;
    private JTable tablaMozos;
    private DefaultTableModel modeloTabla;

    public MozoView() {
        // Configuración básica de la ventana
        setTitle("Gestión de Mozos - Pollería");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null); // Centra en la pantalla
        setLayout(new BorderLayout());

        // 1. Panel Superior (Formulario)
        JPanel panelFormulario = new JPanel(new GridLayout(5, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Mozo"));

        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);

        panelFormulario.add(new JLabel("Nombres:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Apellidos:"));
        txtApellido = new JTextField();
        panelFormulario.add(txtApellido);

        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Cargo:"));
        txtCargo = new JTextField("MOZO");
        panelFormulario.add(txtCargo);

        panelFormulario.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JTextField();
        panelFormulario.add(txtPassword);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"ACTIVO", "INACTIVO"});
        panelFormulario.add(cbEstado);

        JButton btnRegistrar = new JButton("Guardar Mozo");
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);

        // 2. Panel Central (Tabla)
        String[] columnas = {"ID", "DNI", "Nombre", "Apellido", "Cargo", "Usuario", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // No editable
        };
        tablaMozos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMozos);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel Inferior (Botones extra)
        JPanel panelBotones = new JPanel();
        JButton btnActualizarTabla = new JButton("Actualizar Lista");
        JButton btnVolver = new JButton("Volver al Menú");

        panelBotones.add(btnActualizarTabla);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // --- EVENTOS DE LOS BOTONES ---

        // Evento Guardar Mozo
        btnRegistrar.addActionListener(e -> {
            try {
                // Creamos el objeto Mozo (ID 0 porque Oracle lo genera)
                Mozo m = new Mozo(
                        0,
                        txtDni.getText(),
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtTelefono.getText(),
                        txtEmail.getText(),
                        txtCargo.getText(),
                        txtUsuario.getText(),
                        txtPassword.getText(),
                        cbEstado.getSelectedItem().toString()
                );

                // Llamamos al controlador
                if (mozoController.registrar(m)) {
                    JOptionPane.showMessageDialog(this, "Mozo registrado con éxito.");
                    limpiarCampos();
                    cargarTabla(); // Refrescamos la tabla
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar. Revise los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Evento Actualizar Tabla
        btnActualizarTabla.addActionListener(e -> cargarTabla());

        // Evento Volver (cierra la ventana)
        btnVolver.addActionListener(e -> this.dispose());

        // Cargar datos al abrir la ventana
        cargarTabla();
    }

    private void limpiarCampos() {
        txtDni.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtCargo.setText("MOZO");
        cbEstado.setSelectedIndex(0);
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0); // Limpiamos la tabla
        List<Mozo> lista = mozoController.listarTodos();
        for (Mozo m : lista) {
            Object[] fila = {
                    m.getIdMozo(),
                    m.getDni(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getCargo(),
                    m.getUsuario(),
                    m.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    }

    // Método main solo para probar esta ventana de forma individual
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MozoView().setVisible(true);
        });
    }
}