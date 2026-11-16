package dao.impl;

import dao.MesaDAO;
import Model.Mesa;
import Services.DataBaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaDAOImpl implements MesaDAO {

    @Override
    public boolean insertar(Mesa mesa) {
        String sql = "INSERT INTO MESAS (NUMERO, CAPACIDAD, UBICACION, ESTADO) VALUES (?, ?, ?, ?)";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getUbicacion());
            ps.setString(4, mesa.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al insertar mesa: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Mesa> listarTodas() {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM MESAS ORDER BY NUMERO";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearMesa(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar mesas: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Mesa buscarPorId(int idMesa) {
        String sql = "SELECT * FROM MESAS WHERE ID_MESA = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearMesa(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar mesa: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Mesa> listarDisponibles() {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM MESAS WHERE ESTADO = 'DISPONIBLE' ORDER BY NUMERO";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearMesa(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar mesas disponibles: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Mesa mesa) {
        String sql = "UPDATE MESAS SET NUMERO=?, CAPACIDAD=?, UBICACION=?, ESTADO=? WHERE ID_MESA=?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, mesa.getNumero());
            ps.setInt(2, mesa.getCapacidad());
            ps.setString(3, mesa.getUbicacion());
            ps.setString(4, mesa.getEstado());
            ps.setInt(5, mesa.getIdMesa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar mesa: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idMesa) {
        // Para mesas, sí podemos borrar directamente si ya no existen físicamente
        String sql = "DELETE FROM MESAS WHERE ID_MESA = ?";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, idMesa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar mesa: " + e.getMessage());
            return false;
        }
    }

    private Mesa mapearMesa(ResultSet rs) throws SQLException {
        return new Mesa(
                rs.getInt("ID_MESA"),
                rs.getInt("NUMERO"),
                rs.getInt("CAPACIDAD"),
                rs.getString("UBICACION"),
                rs.getString("ESTADO")
        );
    }
}