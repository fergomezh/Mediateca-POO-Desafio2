public class Libro extends MaterialEscrito {
    private String autor;
    private int numeroPaginas;
    private String isbn;
    private int anioPublicacion;

    public Libro(String codigo, String titulo, String editorial, int unidadesDisponibles,
                 String autor, int numeroPaginas, String isbn, int anioPublicacion) {
        super(codigo, titulo, editorial, unidadesDisponibles);
        this.autor = autor;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.anioPublicacion = anioPublicacion;
    }

    @Override
    public String getTipo() {
        return "Libro";
    }

    public String getAutor() {
        return autor;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }
}