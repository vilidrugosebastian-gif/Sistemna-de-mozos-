package Model;

public class Mozo {
    private int idMozo;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String cargo;
    private String usuario;
    private String password;
    private String estado;

    public Mozo(int idMozo, String dni, String nombre, String apellido, String telefono,
                String email, String cargo, String usuario, String password, String estado) {
        this.idMozo = idMozo;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.cargo = cargo;
        this.usuario = usuario;
        this.password = password;
        this.estado = estado;
    }

    public int getIdMozo() { return idMozo; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public String getCargo() { return cargo; }
    public String getUsuario() { return usuario; }
    public String getPassword() { return password; }
    public String getEstado() { return estado; }

    public void setDni(String dni) { this.dni = dni; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setPassword(String password) { this.password = password; }
    public void setEstado(String estado) { this.estado = estado; }
}
