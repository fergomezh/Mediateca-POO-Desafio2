import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelLibro extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtEditorial, txtUnidades, txtAutor, txtPaginas, txtISBN, txtAnio;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private LibroDAO dao = new LibroDAO();
    private String codigoActual = "";

    public PanelLibro() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(8, 2));
        lblCodigo = new JLabel("LIBxxxxx");
        formulario.add(new JLabel("Código generado:"));
        formulario.add(lblCodigo);
        txtTitulo = new JTextField();
        formulario.add(new JLabel("Título:"));
        formulario.add(txtTitulo);
        txtEditorial = new JTextField();
        formulario.add(new JLabel("Editorial:"));
        formulario.add(txtEditorial);
        txtUnidades = new JTextField();
        formulario.add(new JLabel("Unidades disponibles:"));
        formulario.add(txtUnidades);
        txtAutor = new JTextField();
        formulario.add(new JLabel("Autor:"));
        formulario.add(txtAutor);
        txtPaginas = new JTextField();
        formulario.add(new JLabel("Número de páginas:"));
        formulario.add(txtPaginas);
        txtISBN = new JTextField();
        formulario.add(new JLabel("ISBN:"));
        formulario.add(txtISBN);
        txtAnio = new JTextField();
        formulario.add(new JLabel("Año de publicación:"));
        formulario.add(txtAnio);
        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnEliminar);
        btnEliminar.addActionListener(e -> eliminarLibro());
        botones.add(btnAgregar); botones.add(btnModificar); botones.add(btnListar);
        add(botones, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Autor", "Páginas", "ISBN", "Año"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarLibro());
        btnModificar.addActionListener(e -> modificarLibro());
        btnListar.addActionListener(e -> listarLibros());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                codigoActual = tabla.getValueAt(fila, 0).toString();
                lblCodigo.setText(codigoActual);
                txtTitulo.setText(tabla.getValueAt(fila, 1).toString());
                txtEditorial.setText(tabla.getValueAt(fila, 2).toString());
                txtUnidades.setText(tabla.getValueAt(fila, 3).toString());
                txtAutor.setText(tabla.getValueAt(fila, 4).toString());
                txtPaginas.setText(tabla.getValueAt(fila, 5).toString());
                txtISBN.setText(tabla.getValueAt(fila, 6).toString());
                txtAnio.setText(tabla.getValueAt(fila, 7).toString());
            }
        });
    }

    private void agregarLibro() {
        try {
            codigoActual = CodigoGenerator.generarCodigo("libro");
            lblCodigo.setText(codigoActual);

            Libro libro = new Libro(
                    codigoActual,
                    txtTitulo.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtUnidades.getText()),
                    txtAutor.getText(),
                    Integer.parseInt(txtPaginas.getText()),
                    txtISBN.getText(),
                    Integer.parseInt(txtAnio.getText())
            );
            dao.insertarLibro(libro);
            JOptionPane.showMessageDialog(this, "Libro guardado en la base de datos.");
            listarLibros();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarLibro() {
        try {
            Libro libro = new Libro(
                    codigoActual,
                    txtTitulo.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtUnidades.getText()),
                    txtAutor.getText(),
                    Integer.parseInt(txtPaginas.getText()),
                    txtISBN.getText(),
                    Integer.parseInt(txtAnio.getText())
            );
            dao.actualizarLibro(libro);
            JOptionPane.showMessageDialog(this, "Libro modificado correctamente.");
            listarLibros();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar libro: " + ex.getMessage());
        }
    }

    private void listarLibros() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<Libro> libros = dao.listarLibros();
            for (Libro libro : libros) {
                modeloTabla.addRow(new Object[]{
                        libro.getCodigo(), libro.getTitulo(), libro.getEditorial(),
                        libro.getUnidadesDisponibles(), libro.getAutor(),
                        libro.getNumeroPaginas(), libro.getIsbn(), libro.getAnioPublicacion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar libros: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("LIBxxxxx");
        codigoActual = "";
        txtTitulo.setText(""); txtEditorial.setText(""); txtUnidades.setText("");
        txtAutor.setText(""); txtPaginas.setText(""); txtISBN.setText(""); txtAnio.setText("");
    }

    private void eliminarLibro() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro en la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminarLibro(codigoActual);
            JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
            listarLibros();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar libro: " + ex.getMessage());
        }
    }
}