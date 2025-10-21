import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {

    public void insertarLibro(Libro libro) throws SQLException {
        String sqlMaterial = "INSERT INTO material (codigo, titulo) VALUES (?, ?)";
        String sqlEscrito = "INSERT INTO material_escrito (codigo, editorial, unidades_disponibles) VALUES (?, ?, ?)";
        String sqlLibro = "INSERT INTO libro (codigo, autor, numero_paginas, isbn, anio_publicacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlEscrito);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlLibro)) {

                stmt1.setString(1, libro.getCodigo());
                stmt1.setString(2, libro.getTitulo());
                stmt1.executeUpdate();

                stmt2.setString(1, libro.getCodigo());
                stmt2.setString(2, libro.getEditorial());
                stmt2.setInt(3, libro.getUnidadesDisponibles());
                stmt2.executeUpdate();

                stmt3.setString(1, libro.getCodigo());
                stmt3.setString(2, libro.getAutor());
                stmt3.setInt(3, libro.getNumeroPaginas());
                stmt3.setString(4, libro.getIsbn());
                stmt3.setInt(5, libro.getAnioPublicacion());
                stmt3.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public ArrayList<Libro> listarLibros() throws SQLException {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = """
            SELECT m.codigo, m.titulo, me.editorial, me.unidades_disponibles,
                   l.autor, l.numero_paginas, l.isbn, l.anio_publicacion
            FROM libro l
            JOIN material_escrito me ON l.codigo = me.codigo
            JOIN material m ON me.codigo = m.codigo
        """;

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getString("codigo"),
                        rs.getString("titulo"),
                        rs.getString("editorial"),
                        rs.getInt("unidades_disponibles"),
                        rs.getString("autor"),
                        rs.getInt("numero_paginas"),
                        rs.getString("isbn"),
                        rs.getInt("anio_publicacion")
                );
                lista.add(libro);
            }
        }

        return lista;
    }

    public void actualizarLibro(Libro libro) throws SQLException {
        String sqlMaterial = "UPDATE material SET titulo = ? WHERE codigo = ?";
        String sqlEscrito = "UPDATE material_escrito SET editorial = ?, unidades_disponibles = ? WHERE codigo = ?";
        String sqlLibro = "UPDATE libro SET autor = ?, numero_paginas = ?, isbn = ?, anio_publicacion = ? WHERE codigo = ?";

        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sqlMaterial);
                 PreparedStatement stmt2 = conn.prepareStatement(sqlEscrito);
                 PreparedStatement stmt3 = conn.prepareStatement(sqlLibro)) {

                stmt1.setString(1, libro.getTitulo());
                stmt1.setString(2, libro.getCodigo());
                stmt1.executeUpdate();

                stmt2.setString(1, libro.getEditorial());
                stmt2.setInt(2, libro.getUnidadesDisponibles());
                stmt2.setString(3, libro.getCodigo());
                stmt2.executeUpdate();

                stmt3.setString(1, libro.getAutor());
                stmt3.setInt(2, libro.getNumeroPaginas());
                stmt3.setString(3, libro.getIsbn());
                stmt3.setInt(4, libro.getAnioPublicacion());
                stmt3.setString(5, libro.getCodigo());
                stmt3.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void eliminarLibro(String codigo) throws SQLException {
        try (Connection conn = ConexionBD.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt3 = conn.prepareStatement("DELETE FROM libro WHERE codigo = ?");
                 PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM material_escrito WHERE codigo = ?");
                 PreparedStatement stmt1 = conn.prepareStatement("DELETE FROM material WHERE codigo = ?")) {

                stmt3.setString(1, codigo);
                stmt3.executeUpdate();
                stmt2.setString(1, codigo);
                stmt2.executeUpdate();
                stmt1.setString(1, codigo);
                stmt1.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}