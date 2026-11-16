package Model;

public class Cliente {
    private int idCliente;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;

    public Cliente(int idCliente, String dni, String nombre, String apellido,
                   String telefono, String email, String direccion) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    public int getIdCliente() { return idCliente; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }

    public void setDni(String dni) { this.dni = dni; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}