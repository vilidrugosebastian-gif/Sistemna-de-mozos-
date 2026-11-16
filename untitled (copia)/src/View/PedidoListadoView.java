package View;

import Controller.PedidoController;
import Controller.MozoController;
import Controller.ClienteController;
import Model.Pedido;
import Model.Mozo;
import Model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PedidoListadoView extends JFrame {

    private PedidoController pedidoController = new PedidoController();
    private MozoController mozoController = new MozoController();
    private ClienteController clienteController = new ClienteController();

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    public PedidoListadoView() {
        setTitle("Historial de Pedidos - Pollería");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Pedidos Registrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        // Columnas más amigables
        String[] columnas = {"ID Pedido", "Fecha", "Mozo", "Cliente", "Tipo", "Estado", "Método Pago", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPedidos = new JTable(modeloTabla);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar Lista");
        JButton btnCobrar = new JButton("Cobrar Pedido (Cerrar)");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnActualizar);
        panelBotones.add(btnCobrar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarTabla());

        btnCobrar.addActionListener(e -> {
            int filaSeleccionada = tablaPedidos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un pedido de la tabla para cobrar.");
                return;
            }

            int idPedido = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String estadoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 5);

            if (estadoActual.equals("CERRADO")) {
                JOptionPane.showMessageDialog(this, "Este pedido ya fue cobrado y cerrado.");
                return;
            }

            String[] opciones = {"EFECTIVO", "TARJETA", "YAPE/PLIN"};
            int seleccion = JOptionPane.showOptionDialog(this,
                    "Seleccione el método de pago para el Pedido #" + idPedido,
                    "Cobrar Pedido", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);

            if (seleccion >= 0) {
                String metodoPago = opciones[seleccion];
                if (pedidoController.cobrarPedido(idPedido, metodoPago)) {
                    JOptionPane.showMessageDialog(this, "Pedido #" + idPedido + " cobrado con éxito vía " + metodoPago);
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al cobrar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> dispose());
        cargarTabla();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Pedido> lista = pedidoController.listarTodos();

        for (Pedido p : lista) {
            // Buscamos los nombres usando los controllers que ya existen
            Mozo m = mozoController.buscarPorId(p.getIdMozo());
            Cliente c = clienteController.buscarPorId(p.getIdCliente());

            String nombreMozo = (m != null) ? m.getNombre() + " " + m.getApellido() : "ID: " + p.getIdMozo();
            String nombreCliente = (c != null) ? c.getNombre() + " " + c.getApellido() : "ID: " + p.getIdCliente();

            Object[] fila = {
                    p.getIdPedido(),
                    p.getFechaPedido(),
                    nombreMozo,
                    nombreCliente,
                    p.getTipoPedido(),
                    p.getEstado(),
                    p.getMetodoPago() == null ? "PENDIENTE" : p.getMetodoPago(),
                    String.format("S/ %.2f", p.getTotal())
            };
            modeloTabla.addRow(fila);
        }
    }
}