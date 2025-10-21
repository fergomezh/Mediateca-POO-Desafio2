public class CdAudio extends MaterialAudiovisual {
    private String artista;
    private int numeroCanciones;

    public CdAudio(String codigo, String titulo, int duracion, int unidadesDisponibles,
                   String genero, String artista, int numeroCanciones) {
        super(codigo, titulo, duracion, unidadesDisponibles, genero);
        this.artista = artista;
        this.numeroCanciones = numeroCanciones;
    }

    @Override
    public String getTipo() {
        return "CD de Audio";
    }

    public String getArtista() {
        return artista;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }
}