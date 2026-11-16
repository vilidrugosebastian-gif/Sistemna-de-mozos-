package Controller;

import Model.Pedido;
import Model.DetallePedido;
import dao.PedidoDAO;
import dao.DetallePedidoDAO;
import dao.impl.PedidoDAOImpl;
import dao.impl.DetallePedidoDAOImpl;

import java.util.List;

public class PedidoController {

    private PedidoDAO pedidoDAO = new PedidoDAOImpl();
    private DetallePedidoDAO detalleDAO = new DetallePedidoDAOImpl();

    public boolean registrar(Pedido pedido, List<DetallePedido> detalles) {

        // 1. Validaciones de la cabecera
        if (pedido.getIdMozo() <= 0) {
            System.out.println("Error: Debe asignar un mozo al pedido.");
            return false;
        }
        if (pedido.getIdCliente() <= 0) {
            System.out.println("Error: Debe asignar un cliente al pedido.");
            return false;
        }
        if (detalles == null || detalles.isEmpty()) {
            System.out.println("Error: El pedido debe tener al menos un producto.");
            return false;
        }

        // 2. Calcular el total sumando los subtotales
        double totalCalculado = 0;
        for (DetallePedido d : detalles) {
            totalCalculado += d.getSubtotal();
        }
        pedido.setTotal(totalCalculado);

        // NOTA: La fecha NO se modifica aquí. Se asume que el objeto 'pedido'
        // ya fue creado con LocalDate.now() en la Vista o al inicio del flujo.

        // 3. Llamar al DAO para guardar todo (con transacción)
        return pedidoDAO.registrarPedido(pedido, detalles);
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }

    public Pedido buscarPorId(int id) {
        return pedidoDAO.buscarPorId(id);
    }

    public boolean cobrarPedido(int idPedido, String metodoPago) {
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);
        if (pedido != null) {
            pedido.setEstado("CERRADO");
            pedido.setMetodoPago(metodoPago);
            return pedidoDAO.actualizar(pedido);
        }
        return false;
    }

    public boolean cancelarPedido(int idPedido) {
        return pedidoDAO.actualizarEstado(idPedido, "CANCELADO");
    }
}