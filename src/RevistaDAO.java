import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class RevistaDAO {

    public void insertarRevista(Revista revista) throws SQLException {
        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlEscrito = "INSERT INTO material_escrito (codigo, editorial, unidades_disponibles) VALUES (?, ?, ?)";
        String sqlRevista = "INSERT INTO revista (codigo, periodicidad, fecha_publicacion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlEscrito);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlRevista)) {

                stmt1.setString(1, revista.getCodigo());
                stmt1.setString(2, revista.getTitulo());
                stmt1.executeUpdate();

                stmt2.setString(1, revista.getCodigo());
                stmt2.setString(2, revista.getEditorial());
                stmt2.setInt(3, revista.getUnidadesDisponibles());
                stmt2.executeUpdate();

                stmt3.setString(1, revista.getCodigo());
                stmt3.setString(2, revista.getPeriodicidad());
                stmt3.setDate(3, Date.valueOf(revista.getFechaPublicacion()));
                stmt3.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public ArrayList<Revista> listarRevistas() throws SQLException {
        ArrayList<Revista> lista = new ArrayList<>();
        String sql = """
            SELECT m.codigo, m.titulo, me.editorial, me.unidades_disponibles,
                   r.periodicidad, r.fecha_publicacion
            FROM revista r
            JOIN material_escrito me ON r.codigo = me.codigo
            JOIN material m ON me.codigo = m.codigo
        """;

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Revista revista = new Revista(
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("editorial"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("periodicidad"),
                        rs.getDate("fecha_publicacion").toLocalDate()
                );
                lista.add(revista);
            }
        }

        return lista;
    }

    public void actualizarRevista(Revista revista) throws SQLException {
        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlEscrito = "UPDATE material_escrito SET editorial = ?, unidades_disponibles = ? WHERE codigo = ?";
        String sqlRevista = "UPDATE revista SET periodicidad = ?, fecha_publicacion = ? WHERE codigo = ?";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlEscrito);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlRevista)) {

                stmt1.setString(1, revista.getTitulo());
                stmt1.setString(2, revista.getCodigo());
                stmt1.executeUpdate();

                stmt2.setString(1, revista.getEditorial());
                stmt2.setInt(2, revista.getUnidadesDisponibles());
                stmt2.setString(3, revista.getCodigo());
                stmt2.executeUpdate();

                stmt3.setString(1, revista.getPeriodicidad());
                stmt3.setDate(2, Date.valueOf(revista.getFechaPublicacion()));
                stmt3.setString(3, revista.getCodigo());
                stmt3.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void eliminarRevista(String codigo) throws SQLException {
        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM revista WHERE codigo = ?");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM material_escrito WHERE codigo = ?");
                 PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM material WHERE codigo = ?")) {

                stmt3.setString(1, codigo); stmt3.executeUpdate();
                stmt2.setString(1, codigo); stmt2.executeUpdate();
                stmt1.setString(1, codigo); stmt1.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}