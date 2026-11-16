package dao;

import Model.Mozo;
import java.util.List;

public interface MozoDAO {

    // Recibe el objeto sin ID (se genera solo en Oracle)
    boolean insertar(Mozo mozo);

    // Devuelve todos los mozos registrados
    List<Mozo> listarTodos();

    // Busca un mozo por su ID único
    Mozo buscarPorId(int idMozo);

    // Busca un mozo por su nombre de usuario (útil para el Login)
    Mozo buscarPorUsuario(String usuario);

    // Actualiza los datos de un mozo existente
    boolean actualizar(Mozo mozo);

    // Elimina un mozo (o cambia su estado a INACTIVO)
    boolean eliminar(int idMozo);
}