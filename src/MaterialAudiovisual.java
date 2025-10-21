public abstract class MaterialAudiovisual extends Material {
    protected int duracion;
    protected int unidadesDisponibles;
    protected String genero;

    public MaterialAudiovisual(String codigo, String titulo, int duracion, int unidadesDisponibles, String genero) {
        super(codigo, titulo);
        this.duracion = duracion;
        this.unidadesDisponibles = unidadesDisponibles;
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public String getGenero() {
        return genero;
    }
}