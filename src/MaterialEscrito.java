public abstract class MaterialEscrito extends Material {
    protected String editorial;
    protected int unidadesDisponibles;

    public MaterialEscrito(String codigo, String titulo, String editorial, int unidadesDisponibles) {
        super(codigo, titulo);
        this.editorial = editorial;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }
}