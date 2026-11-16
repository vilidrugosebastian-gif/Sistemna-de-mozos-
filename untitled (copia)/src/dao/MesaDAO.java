package dao;

import Model.Mesa;
import java.util.List;

public interface MesaDAO {

    boolean insertar(Mesa mesa);

    List<Mesa> listarTodas();

    Mesa buscarPorId(int idMesa);

    List<Mesa> listarDisponibles();// Útil para el mozo

    boolean actualizar(Mesa mesa);

    boolean eliminar(int idMesa);
}