package dao.impl;

import dao.PedidoDAO;
import Model.Pedido;
import Model.DetallePedido;
import Services.DataBaseConnector;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {

    @Override
    public boolean registrarPedido(Pedido pedido, List<DetallePedido> detalles) {
        String sqlPedido = "INSERT INTO PEDIDOS (ID_MOZO, ID_MESA, ID_CLIENTE, FECHA_PEDIDO, TIPO_PEDIDO, ESTADO, METODO_PAGO, TOTAL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO DETALLES_PEDIDO (ID_PEDIDO, ID_PRODUCTO, CANTIDAD, PRECIO, SUBTOTAL, COMENTARIO) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlStock = "UPDATE PRODUCTOS SET STOCK = STOCK - ? WHERE ID_PRODUCTO = ?";

        Connection cn = null;
        try {
            cn = DataBaseConnector.conectar();
            cn.setAutoCommit(false); // Iniciamos la transacción

            // 1. Insertar la cabecera del pedido y obtener el ID generado
            int idPedidoGenerado = 0;
            try (PreparedStatement ps = cn.prepareStatement(sqlPedido, new String[]{"ID_PEDIDO"})) {
                if (pedido.getIdMesa() > 0) {
                    ps.setInt(2, pedido.getIdMesa());
                } else {
                    ps.setNull(2, java.sql.Types.INTEGER); // Si es DELIVERY no hay mesa
                }
                ps.setInt(1, pedido.getIdMozo());
                ps.setInt(3, pedido.getIdCliente());
                ps.setDate(4, Date.valueOf(pedido.getFechaPedido()));
                ps.setString(5, pedido.getTipoPedido());
                ps.setString(6, pedido.getEstado());
                ps.setString(7, pedido.getMetodoPago());
                ps.setDouble(8, pedido.getTotal());

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) idPedidoGenerado = rs.getInt(1);
                }
            }

            // 2. Insertar los detalles y descontar stock
            try (PreparedStatement psDetalle = cn.prepareStatement(sqlDetalle);
                 PreparedStatement psStock = cn.prepareStatement(sqlStock)) {

                for (DetallePedido d : detalles) {
                    psDetalle.setInt(1, idPedidoGenerado);
                    psDetalle.setInt(2, d.getIdProducto());
                    psDetalle.setInt(3, d.getCantidad());
                    psDetalle.setDouble(4, d.getPrecio());
                    psDetalle.setDouble(5, d.getSubtotal());
                    psDetalle.setString(6, d.getComentario());
                    psDetalle.executeUpdate();

                    // Descontar stock
                    psStock.setInt(1, d.getCantidad());
                    psStock.setInt(2, d.getIdProducto());
                    psStock.executeUpdate();
                }
            }

            cn.commit(); // Si todo sale bien, guardamos los cambios
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar pedido. Rollback...: " + e.getMessage());
            if (cn != null) {
                try { cn.rollback(); } catch (SQLException ex) { }
            }
            return false;
        } finally {
            if (cn != null) {
                try { cn.setAutoCommit(true); cn.close(); } catch (SQLException ex) { }
            }
        }
    }

    @Override
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM PEDIDOS ORDER BY ID_PEDIDO DESC";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearPedido(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Pedido buscarPorId(int idPedido) {
        String sql = "SELECT * FROM PEDIDOS WHERE ID_PEDIDO = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearPedido(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar pedido: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Pedido pedido) {
        String sql = "UPDATE PEDIDOS SET ESTADO=?, METODO_PAGO=?, TOTAL=? WHERE ID_PEDIDO=?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, pedido.getEstado());
            ps.setString(2, pedido.getMetodoPago());
            ps.setDouble(3, pedido.getTotal());
            ps.setInt(4, pedido.getIdPedido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        String sql = "UPDATE PEDIDOS SET ESTADO = ? WHERE ID_PEDIDO = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        int idMesa = rs.getInt("ID_MESA");
        if (rs.wasNull()) idMesa = 0; // Si es nulo, asignamos 0 para Delivery

        return new Pedido(
                rs.getInt("ID_PEDIDO"),
                rs.getInt("ID_MOZO"),
                idMesa,
                rs.getInt("ID_CLIENTE"),
                rs.getDate("FECHA_PEDIDO").toLocalDate(),
                rs.getString("TIPO_PEDIDO"),
                rs.getString("ESTADO"),
                rs.getString("METODO_PAGO"),
                rs.getDouble("TOTAL")
        );
    }
}