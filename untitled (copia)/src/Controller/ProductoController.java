package Controller;

import Model.Producto;
import dao.ProductoDAO;
import dao.impl.ProductoDAOImpl;

import java.util.List;

public class ProductoController {

    private ProductoDAO productoDAO = new ProductoDAOImpl();

    public boolean registrar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            System.out.println("Error: El nombre del producto es obligatorio.");
            return false;
        }
        if (producto.getPrecio() <= 0) {
            System.out.println("Error: El precio debe ser mayor a 0.");
            return false;
        }
        if (producto.getStock() < 0) {
            System.out.println("Error: El stock no puede ser negativo.");
            return false;
        }
        return productoDAO.insertar(producto);
    }

    public List<Producto> listarTodos() {
        return productoDAO.listarTodos();
    }

    public List<Producto> listarPorCategoria(String categoria) {
        return productoDAO.listarPorCategoria(categoria);
    }

    public List<Producto> listarDisponibles() {
        return productoDAO.listarDisponibles();
    }

    public Producto buscarPorId(int id) {
        return productoDAO.buscarPorId(id);
    }

    public boolean actualizar(Producto producto) {
        return productoDAO.actualizar(producto);
    }

    public boolean eliminar(int id) {
        // En el DAO lo que hace es ponerlo en 'AGOTADO'
        return productoDAO.eliminar(id);
    }
}