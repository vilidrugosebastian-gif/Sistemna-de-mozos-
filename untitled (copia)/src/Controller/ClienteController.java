package Controller;

import Model.Cliente;
import dao.ClienteDAO;
import dao.impl.ClienteDAOImpl;

import java.util.List;

public class ClienteController {

    private ClienteDAO clienteDAO = new ClienteDAOImpl();

    public boolean registrar(Cliente cliente) {
        // Validaciones para la pollería
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre es obligatorio.");
            return false;
        }

        // Si ingresa DNI, verificamos que tenga 8 dígitos (opcional si es consumidor final)
        if (cliente.getDni() != null && !cliente.getDni().isEmpty()) {
            if (cliente.getDni().length() != 8) {
                System.out.println("Error: El DNI debe tener 8 dígitos.");
                return false;
            }
            // Verificamos que no exista otro cliente con ese DNI
            if (clienteDAO.buscarPorDni(cliente.getDni()) != null) {
                System.out.println("Error: Ya existe un cliente registrado con ese DNI.");
                return false;
            }
        }

        return clienteDAO.insertar(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarPorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

    public Cliente buscarPorDni(String dni) {
        return clienteDAO.buscarPorDni(dni);
    }

    public boolean actualizar(Cliente cliente) {
        return clienteDAO.actualizar(cliente);
    }

    public boolean eliminar(int id) {
        return clienteDAO.eliminar(id);
    }
}