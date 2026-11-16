package Model;

import java.time.LocalDate;

public class Pedido {
    private int idPedido;
    private int idMozo;
    private int idMesa;
    private int idCliente;
    private LocalDate fechaPedido;
    private String tipoPedido;
    private String estado;
    private String metodoPago;
    private double total;

    public Pedido(int idPedido, int idMozo, int idMesa, int idCliente, LocalDate fechaPedido,
                  String tipoPedido, String estado, String metodoPago, double total) {
        this.idPedido = idPedido;
        this.idMozo = idMozo;
        this.idMesa = idMesa;
        this.idCliente = idCliente;
        this.fechaPedido = fechaPedido;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.total = total;
    }

    public int getIdPedido() { return idPedido; }
    public int getIdMozo() { return idMozo; }
    public int getIdMesa() { return idMesa; }
    public int getIdCliente() { return idCliente; }
    public LocalDate getFechaPedido() { return fechaPedido; }
    public String getTipoPedido() { return tipoPedido; }
    public String getEstado() { return estado; }
    public String getMetodoPago() { return metodoPago; }
    public double getTotal() { return total; }

    public void setTipoPedido(String tipoPedido) { this.tipoPedido = tipoPedido; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public void setTotal(double total) { this.total = total; }
}