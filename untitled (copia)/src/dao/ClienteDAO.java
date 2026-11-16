package dao;

import Model.Cliente;
import java.util.List;

public interface ClienteDAO {
    boolean insertar(Cliente cliente);
    List<Cliente> listarTodos();
    Cliente buscarPorId(int idCliente);
    Cliente buscarPorDni(String dni); // Muy útil para facturación
    boolean actualizar(Cliente cliente);
    boolean eliminar(int idCliente);
}