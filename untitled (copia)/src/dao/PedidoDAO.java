package dao;

import Model.Pedido;
import Model.DetallePedido;
import java.util.List;

public interface PedidoDAO {
    // Método estrella: registra la cabecera y los detalles en una sola transacción
    boolean registrarPedido(Pedido pedido, List<DetallePedido> detalles);

    List<Pedido> listarTodos();
    Pedido buscarPorId(int idPedido);
    boolean actualizar(Pedido pedido);
    boolean actualizarEstado(int idPedido, String nuevoEstado);
}