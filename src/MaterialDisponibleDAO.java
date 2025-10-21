import java.sql.*;
import java.util.ArrayList;

public class MaterialDisponibleDAO {

    public ArrayList<String[]> listarMateriales() throws SQLException {
        ArrayList<String[]> lista = new ArrayList<>();
        String sql = """
            SELECT m.codigo, m.titulo,
                CASE
                    WHEN m.codigo LIKE 'LIB%' THEN 'Libro'
                    WHEN m.codigo LIKE 'REV%' THEN 'Revista'
                    WHEN m.codigo LIKE 'CDA%' THEN 'CD de Audio'
                    WHEN m.codigo LIKE 'DVD%' THEN 'DVD'
                    ELSE 'Desconocido'
                END AS tipo
            FROM material m
        """;

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new String[]{
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("tipo")
                });
            }
        }

        return lista;
    }
}