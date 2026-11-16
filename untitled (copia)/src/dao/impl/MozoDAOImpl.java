package dao.impl;

import dao.MozoDAO;
import Model.Mozo;
import Services.DataBaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MozoDAOImpl implements MozoDAO {

    @Override
    public boolean insertar(Mozo mozo) {
        // No insertamos el ID porque Oracle lo genera solo (IDENTITY)
        String sql = "INSERT INTO MOZOS (DNI, NOMBRE, APELLIDO, TELEFONO, EMAIL, CARGO, USUARIO, PASSWORD, ESTADO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, mozo.getDni());
            ps.setString(2, mozo.getNombre());
            ps.setString(3, mozo.getApellido());
            ps.setString(4, mozo.getTelefono());
            ps.setString(5, mozo.getEmail());
            ps.setString(6, mozo.getCargo());
            ps.setString(7, mozo.getUsuario());
            ps.setString(8, mozo.getPassword());
            ps.setString(9, mozo.getEstado());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar mozo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Mozo> listarTodos() {
        List<Mozo> lista = new ArrayList<>();
        String sql = "SELECT * FROM MOZOS ORDER BY ID_MOZO";

        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearMozo(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar mozos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Mozo buscarPorId(int idMozo) {
        String sql = "SELECT * FROM MOZOS WHERE ID_MOZO = ?";

        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idMozo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMozo(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar mozo por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Mozo buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM MOZOS WHERE USUARIO = ?";

        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearMozo(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar mozo por usuario: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Mozo mozo) {
        String sql = "UPDATE MOZOS SET DNI=?, NOMBRE=?, APELLIDO=?, TELEFONO=?, EMAIL=?, CARGO=?, USUARIO=?, PASSWORD=?, ESTADO=? " +
                "WHERE ID_MOZO=?";

        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, mozo.getDni());
            ps.setString(2, mozo.getNombre());
            ps.setString(3, mozo.getApellido());
            ps.setString(4, mozo.getTelefono());
            ps.setString(5, mozo.getEmail());
            ps.setString(6, mozo.getCargo());
            ps.setString(7, mozo.getUsuario());
            ps.setString(8, mozo.getPassword());
            ps.setString(9, mozo.getEstado());
            ps.setInt(10, mozo.getIdMozo());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar mozo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int idMozo) {
        // En un sistema real, no borramos al mozo, lo pasamos a INACTIVO
        String sql = "UPDATE MOZOS SET ESTADO = 'INACTIVO' WHERE ID_MOZO = ?";

        try (Connection cn = DataBaseConnector.conectar();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idMozo);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al inactivar mozo: " + e.getMessage());
            return false;
        }
    }

    // Método auxiliar para evitar repetir código al leer un ResultSet
    private Mozo mapearMozo(ResultSet rs) throws SQLException {
        return new Mozo(
                rs.getInt("ID_MOZO"),
                rs.getString("DNI"),
                rs.getString("NOMBRE"),
                rs.getString("APELLIDO"),
                rs.getString("TELEFONO"),
                rs.getString("EMAIL"),
                rs.getString("CARGO"),
                rs.getString("USUARIO"),
                rs.getString("PASSWORD"),
                rs.getString("ESTADO")
        );
    }
}