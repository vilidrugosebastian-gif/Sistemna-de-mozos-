package View;

import Controller.MesaController;
import Model.Mesa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MesaView extends JFrame {
    private MesaController mesaController = new MesaController();
    private JTextField txtNumero, txtCapacidad, txtUbicacion;
    private JComboBox<String> cbEstado;
    private JTable tablaMesas;
    private DefaultTableModel modeloTabla;

    public MesaView() {
        setTitle("Gestión de Mesas");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelFormulario = new JPanel(new GridLayout(2, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar Mesa"));

        panelFormulario.add(new JLabel("Número:"));
        txtNumero = new JTextField();
        panelFormulario.add(txtNumero);

        panelFormulario.add(new JLabel("Capacidad:"));
        txtCapacidad = new JTextField();
        panelFormulario.add(txtCapacidad);

        panelFormulario.add(new JLabel("Ubicación:"));
        txtUbicacion = new JTextField("SALON PRINCIPAL");
        panelFormulario.add(txtUbicacion);

        panelFormulario.add(new JLabel("Estado:"));
        cbEstado = new JComboBox<>(new String[]{"DISPONIBLE", "OCUPADA", "POR LIMPIAR"});
        panelFormulario.add(cbEstado);

        JButton btnRegistrar = new JButton("Guardar Mesa");
        panelFormulario.add(btnRegistrar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Número", "Capacidad", "Ubicación", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaMesas = new JTable(modeloTabla);
        add(new JScrollPane(tablaMesas), BorderLayout.CENTER);

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
                Mesa m = new Mesa(0, Integer.parseInt(txtNumero.getText()),
                        Integer.parseInt(txtCapacidad.getText()), txtUbicacion.getText(), cbEstado.getSelectedItem().toString());
                if (mesaController.registrar(m)) {
                    JOptionPane.showMessageDialog(this, "Mesa registrada.");
                    limpiar(); cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Número y Capacidad deben ser números.");
            }
        });
        btnActualizar.addActionListener(e -> cargarTabla());
        btnVolver.addActionListener(e -> dispose());

        cargarTabla();
    }

    private void limpiar() {
        txtNumero.setText(""); txtCapacidad.setText(""); txtUbicacion.setText("SALON PRINCIPAL");
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Mesa> lista = mesaController.listarTodas();
        for (Mesa m : lista) {
            modeloTabla.addRow(new Object[]{m.getIdMesa(), m.getNumero(), m.getCapacidad(), m.getUbicacion(), m.getEstado()});
        }
    }
}