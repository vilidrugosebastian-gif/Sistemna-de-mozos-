package dao;

import Model.Producto;
import java.util.List;

public interface ProductoDAO {
    boolean insertar(Producto producto);
    List<Producto> listarTodos();
    Producto buscarPorId(int idProducto);
    List<Producto> listarPorCategoria(String categoria); // Útil para la carta
    List<Producto> listarDisponibles(); // Solo los que tienen stock
    boolean actualizar(Producto producto);
    boolean eliminar(int idProducto);
}