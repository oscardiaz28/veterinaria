package pe.edu.utp.model;

public class Usuario {

    //Atributos
    private Integer id;
    private String email;
    private String contra;
    private String token;
    private String estado;

    //Constructor
    public Usuario() {
    }

    //Constructor para listar usuarios
    public Usuario(Integer id, String email, String contra, String token, String estado) {
        this.id = id;
        this.email = email;
        this.contra = contra;
        this.token = token;
        this.estado = estado;
    }

    //Constructor para registrar usuario
    public Usuario(String email, String contra, String estado) {
        this.email = email;
        this.contra = contra;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", contra='" + contra + '\'' +
                ", token='" + token + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

}
