package Model;

public class Mesa {
    private int idMesa;
    private int numero;
    private int capacidad;
    private String ubicacion;
    private String estado;

    public Mesa(int idMesa, int numero, int capacidad, String ubicacion, String estado) {
        this.idMesa = idMesa;
        this.numero = numero;
        this.capacidad = capacidad;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public int getIdMesa() { return idMesa; }
    public int getNumero() { return numero; }
    public int getCapacidad() { return capacidad; }
    public String getUbicacion() { return ubicacion; }
    public String getEstado() { return estado; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setEstado(String estado) { this.estado = estado; }
}