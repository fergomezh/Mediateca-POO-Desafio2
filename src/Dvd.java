public class Dvd extends MaterialAudiovisual {
    private String director;

    public Dvd(String codigo, String titulo, int duracion, int unidadesDisponibles,
               String genero, String director) {
        super(codigo, titulo, duracion, unidadesDisponibles, genero);
        this.director = director;
    }

    @Override
    public String getTipo() {
        return "DVD";
    }

    public String getDirector() {
        return director;
    }
}