package dao;

import Model.DetallePedido;
import java.util.List;

public interface DetallePedidoDAO {
    List<DetallePedido> listarPorPedido(int idPedido);
    DetallePedido buscarPorId(int idDetalle);
    boolean eliminarPorPedido(int idPedido);
}