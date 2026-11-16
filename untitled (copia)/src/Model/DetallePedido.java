package Model;

public class DetallePedido {
    private int idDetalle;
    private int idPedido;
    private int idProducto;
    private int cantidad;
    private double precio;
    private double subtotal;
    private String comentario;

    public DetallePedido(int idDetalle, int idPedido, int idProducto, int cantidad,
                         double precio, double subtotal, String comentario) {
        this.idDetalle = idDetalle;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.comentario = comentario;
    }

    public int getIdDetalle() { return idDetalle; }
    public int getIdPedido() { return idPedido; }
    public int getIdProducto() { return idProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    public double getSubtotal() { return subtotal; }
    public String getComentario() { return comentario; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}