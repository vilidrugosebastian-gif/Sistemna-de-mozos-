package Controller;

import Model.Mesa;
import dao.MesaDAO;
import dao.impl.MesaDAOImpl;

import java.util.List;

public class MesaController {

    private MesaDAO mesaDAO = new MesaDAOImpl();

    public boolean registrar(Mesa mesa) {
        if (mesa.getNumero() <= 0) {
            System.out.println("Error: El número de mesa debe ser mayor a 0.");
            return false;
        }
        if (mesa.getCapacidad() <= 0) {
            System.out.println("Error: La capacidad debe ser mayor a 0.");
            return false;
        }
        return mesaDAO.insertar(mesa);
    }

    public List<Mesa> listarTodas() {
        return mesaDAO.listarTodas();
    }

    public List<Mesa> listarDisponibles() {
        return mesaDAO.listarDisponibles();
    }

    public Mesa buscarPorId(int id) {
        return mesaDAO.buscarPorId(id);
    }

    public boolean actualizar(Mesa mesa) {
        return mesaDAO.actualizar(mesa);
    }

    public boolean eliminar(int id) {
        return mesaDAO.eliminar(id);
    }

    // Método útil para cuando el cliente se va y la mesa vuelve a estar libre
    public boolean cambiarEstado(int idMesa, String nuevoEstado) {
        Mesa mesa = mesaDAO.buscarPorId(idMesa);
        if (mesa != null) {
            mesa.setEstado(nuevoEstado);
            return mesaDAO.actualizar(mesa);
        }
        return false;
    }
}