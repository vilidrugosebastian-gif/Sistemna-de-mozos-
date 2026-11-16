package dao.impl;

import dao.ProductoDAO;
import Model.Producto;
import Services.DataBaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO PRODUCTOS (NOMBRE, PRECIO, CATEGORIA, STOCK, ESTADO) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getCategoria());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS ORDER BY CATEGORIA, NOMBRE";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearProducto(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        String sql = "SELECT * FROM PRODUCTOS WHERE ID_PRODUCTO = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearProducto(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> listarPorCategoria(String categoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS WHERE CATEGORIA = ? ORDER BY NOMBRE";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, categoria);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar por categoría: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Producto> listarDisponibles() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS WHERE ESTADO = 'DISPONIBLE' AND STOCK > 0 ORDER BY NOMBRE";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearProducto(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar productos disponibles: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE PRODUCTOS SET NOMBRE=?, PRECIO=?, CATEGORIA=?, STOCK=?, ESTADO=? WHERE ID_PRODUCTO=?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setString(3, producto.getCategoria());
            ps.setInt(4, producto.getStock());
            ps.setString(5, producto.getEstado());
            ps.setInt(6, producto.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idProducto) {
        String sql = "UPDATE PRODUCTOS SET ESTADO = 'AGOTADO' WHERE ID_PRODUCTO = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al agotar producto: " + e.getMessage());
            return false;
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        return new Producto(
                rs.getInt("ID_PRODUCTO"),
                rs.getString("NOMBRE"),
                rs.getDouble("PRECIO"),
                rs.getString("CATEGORIA"),
                rs.getInt("STOCK"),
                rs.getString("ESTADO")
        );
    }
}