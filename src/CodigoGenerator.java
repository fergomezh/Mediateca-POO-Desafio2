import java.sql.*;

public class CodigoGenerator {

    public static String generarCodigo(String tipo) throws SQLException {
        String prefijo = switch (tipo.toLowerCase()) {
            case "libro" -> "LIB";
            case "revista" -> "REV";
            case "cd" -> "CDA";
            case "dvd" -> "DVD";
            default -> throw new IllegalArgumentException("Tipo desconocido: " + tipo);
        };

        String sql = "SELECT MAX(codigo) AS ultimo FROM material WHERE codigo LIKE ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prefijo + "%");
            ResultSet rs = stmt.executeQuery();

            int siguiente = 1;
            if (rs.next() && rs.getString("ultimo") != null) {
                String ultimo = rs.getString("ultimo");
                String numero = ultimo.substring(3); // extrae los 5 dígitos
                siguiente = Integer.parseInt(numero) + 1;
            }

            return String.format("%s%05d", prefijo, siguiente);
        }
    }
}