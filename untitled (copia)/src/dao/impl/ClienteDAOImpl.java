package dao.impl;

import dao.ClienteDAO;
import Model.Cliente;
import Services.DataBaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO CLIENTES (DNI, NOMBRE, APELLIDO, TELEFONO, EMAIL, DIRECCION) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getDireccion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM CLIENTES ORDER BY APELLIDO, NOMBRE";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearCliente(rs));
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM CLIENTES WHERE ID_CLIENTE = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM CLIENTES WHERE DNI = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearCliente(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por DNI: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE CLIENTES SET DNI=?, NOMBRE=?, APELLIDO=?, TELEFONO=?, EMAIL=?, DIRECCION=? WHERE ID_CLIENTE=?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getTelefono());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getDireccion());
            ps.setInt(7, cliente.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM CLIENTES WHERE ID_CLIENTE = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("ID_CLIENTE"),
                rs.getString("DNI"),
                rs.getString("NOMBRE"),
                rs.getString("APELLIDO"),
                rs.getString("TELEFONO"),
                rs.getString("EMAIL"),
                rs.getString("DIRECCION")
        );
    }
}