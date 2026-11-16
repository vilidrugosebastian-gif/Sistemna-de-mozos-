package Model;

public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private String categoria;
    private int stock;
    private String estado;

    public Producto(int idProducto, String nombre, double precio,
                    String categoria, int stock, String estado) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.stock = stock;
        this.estado = estado;
    }

    public int getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getCategoria() { return categoria; }
    public int getStock() { return stock; }
    public String getEstado() { return estado; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setStock(int stock) { this.stock = stock; }
    public void setEstado(String estado) { this.estado = estado; }
}