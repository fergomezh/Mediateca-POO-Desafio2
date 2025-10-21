public abstract class Material {
    protected String codigo;
    protected String titulo;

    public Material(String codigo, String titulo) {
        this.codigo = codigo;
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public abstract String getTipo();
}