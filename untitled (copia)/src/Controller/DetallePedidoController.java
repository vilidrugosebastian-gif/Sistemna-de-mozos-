package Controller;

import Model.DetallePedido;
import dao.DetallePedidoDAO;
import dao.impl.DetallePedidoDAOImpl;

import java.util.List;

public class DetallePedidoController {

    private DetallePedidoDAO detalleDAO = new DetallePedidoDAOImpl();

    public List<DetallePedido> listarPorPedido(int idPedido) {
        return detalleDAO.listarPorPedido(idPedido);
    }

    public DetallePedido buscarPorId(int idDetalle) {
        return detalleDAO.buscarPorId(idDetalle);
    }

    public boolean eliminarPorPedido(int idPedido) {
        return detalleDAO.eliminarPorPedido(idPedido);
    }
}