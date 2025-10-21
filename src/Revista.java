import java.time.LocalDate;

public class Revista extends MaterialEscrito {
    private String periodicidad;
    private LocalDate fechaPublicacion;

    public Revista(String codigo, String titulo, String editorial, int unidadesDisponibles,
                   String periodicidad, LocalDate fechaPublicacion) {
        super(codigo, titulo, editorial, unidadesDisponibles);
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    @Override
    public String getTipo() {
        return "Revista";
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }
}