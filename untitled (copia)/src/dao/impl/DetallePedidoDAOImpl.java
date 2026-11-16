package dao.impl;

import dao.DetallePedidoDAO;
import Model.DetallePedido;
import Services.DataBaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAOImpl implements DetallePedidoDAO {

    @Override
    public List<DetallePedido> listarPorPedido(int idPedido) {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM DETALLES_PEDIDO WHERE ID_PEDIDO = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearDetalle(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public DetallePedido buscarPorId(int idDetalle) {
        String sql = "SELECT * FROM DETALLES_PEDIDO WHERE ID_DETALLE = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idDetalle);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearDetalle(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar detalle: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean eliminarPorPedido(int idPedido) {
        String sql = "DELETE FROM DETALLES_PEDIDO WHERE ID_PEDIDO = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalles: " + e.getMessage());
            return false;
        }
    }

    private DetallePedido mapearDetalle(ResultSet rs) throws SQLException {
        return new DetallePedido(
                rs.getInt("ID_DETALLE"),
                rs.getInt("ID_PEDIDO"),
                rs.getInt("ID_PRODUCTO"),
                rs.getInt("CANTIDAD"),
                rs.getDouble("PRECIO"),
                rs.getDouble("SUBTOTAL"),
                rs.getString("COMENTARIO")
        );
    }
}