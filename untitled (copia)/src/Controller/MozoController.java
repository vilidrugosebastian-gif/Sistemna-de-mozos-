package Controller;

import Model.Mozo;
import dao.MozoDAO;
import dao.impl.MozoDAOImpl;

import java.util.List;

public class MozoController {

    // Programamos contra la interfaz, no contra la implementación
    private MozoDAO mozoDAO = new MozoDAOImpl();

    public boolean registrar(Mozo mozo) {
        // Validación básica: que el DNI tenga 8 dígitos y no esté vacío
        if (mozo.getDni() == null || mozo.getDni().length() != 8) {
            System.out.println("Error: El DNI debe tener 8 dígitos.");
            return false;
        }
        // Validación: que el usuario no esté vacío
        if (mozo.getUsuario() == null || mozo.getUsuario().trim().isEmpty()) {
            System.out.println("Error: El usuario no puede estar vacío.");
            return false;
        }

        return mozoDAO.insertar(mozo);
    }

    public List<Mozo> listarTodos() {
        return mozoDAO.listarTodos();
    }

    public Mozo buscarPorId(int id) {
        return mozoDAO.buscarPorId(id);
    }

    public boolean actualizar(Mozo mozo) {
        if (mozo.getIdMozo() <= 0) {
            System.out.println("Error: ID de mozo inválido.");
            return false;
        }
        return mozoDAO.actualizar(mozo);
    }

    public boolean eliminar(int id) {
        return mozoDAO.eliminar(id);
    }

    // Método especial para el Login de la pollería
    public Mozo login(String usuario, String password) {
        Mozo mozo = mozoDAO.buscarPorUsuario(usuario);

        // Verificamos que exista, que la contraseña coincida y que esté activo
        if (mozo != null && mozo.getPassword().equals(password)) {
            if (mozo.getEstado().equals("ACTIVO")) {
                return mozo; // Login exitoso
            } else {
                System.out.println("El usuario está inactivo.");
            }
        }
        return null; // Login fallido
    }
}