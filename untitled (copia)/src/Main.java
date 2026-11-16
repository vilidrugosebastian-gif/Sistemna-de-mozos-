import Services.DataBaseInitializer;
import View.MenuView;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE LA POLLERÍA ===");

        // 1. Inicializar la base de datos (borra y crea las tablas)
        // Nota: Si no quieres que se borren los datos cada vez que entras,
        // comenta la siguiente línea después de la primera ejecución.
        DataBaseInitializer.inicializar();

        // 2. Abrir la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            MenuView menu = new MenuView();
            menu.setVisible(true);
        });

        System.out.println("=== SISTEMA LISTO ===");
    }
}