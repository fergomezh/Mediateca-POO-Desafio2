import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelDvd extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtDuracion, txtUnidades, txtGenero, txtDirector;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private DvdDAO dao = new DvdDAO();
    private String codigoActual = "";

    public PanelDvd() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(6, 2));
        lblCodigo = new JLabel("DVDxxxxx");
        formulario.add(new JLabel("Código generado:"));
        formulario.add(lblCodigo);
        txtTitulo = new JTextField();
        formulario.add(new JLabel("Título:"));
        formulario.add(txtTitulo);
        txtDuracion = new JTextField();
        formulario.add(new JLabel("Duración (min):"));
        formulario.add(txtDuracion);
        txtUnidades = new JTextField();
        formulario.add(new JLabel("Unidades disponibles:"));
        formulario.add(txtUnidades);
        txtGenero = new JTextField();
        formulario.add(new JLabel("Género:"));
        formulario.add(txtGenero);
        txtDirector = new JTextField();
        formulario.add(new JLabel("Director:"));
        formulario.add(txtDirector);
        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnEliminar);
        btnEliminar.addActionListener(e -> eliminarDvd());
        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnListar);
        add(botones, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Duración", "Unidades", "Género", "Director"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarDvd());
        btnModificar.addActionListener(e -> modificarDvd());
        btnListar.addActionListener(e -> listarDvds());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                codigoActual = tabla.getValueAt(fila, 0).toString();
                lblCodigo.setText(codigoActual);
                txtTitulo.setText(tabla.getValueAt(fila, 1).toString());
                txtDuracion.setText(tabla.getValueAt(fila, 2).toString());
                txtUnidades.setText(tabla.getValueAt(fila, 3).toString());
                txtGenero.setText(tabla.getValueAt(fila, 4).toString());
                txtDirector.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
    }

    private void agregarDvd() {
        try {
            codigoActual = CodigoGenerator.generarCodigo("dvd");
            lblCodigo.setText(codigoActual);

            Dvd dvd = new Dvd(
                    codigoActual,
                    txtTitulo.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    Integer.parseInt(txtUnidades.getText()),
                    txtGenero.getText(),
                    txtDirector.getText()
            );
            dao.insertarDvd(dvd);
            JOptionPane.showMessageDialog(this, "DVD guardado en la base de datos.");
            listarDvds();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarDvd() {
        try {
            Dvd dvd = new Dvd(
                    codigoActual,
                    txtTitulo.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    Integer.parseInt(txtUnidades.getText()),
                    txtGenero.getText(),
                    txtDirector.getText()
            );
            dao.actualizarDvd(dvd);
            JOptionPane.showMessageDialog(this, "DVD modificado correctamente.");
            listarDvds();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar DVD: " + ex.getMessage());
        }
    }

    private void listarDvds() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<Dvd> dvds = dao.listarDvds();
            for (Dvd dvd : dvds) {
                modeloTabla.addRow(new Object[]{
                        dvd.getCodigo(), dvd.getTitulo(), dvd.getDuracion(),
                        dvd.getUnidadesDisponibles(), dvd.getGenero(), dvd.getDirector()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar DVDs: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("DVDxxxxx");
        codigoActual = "";
        txtTitulo.setText(""); txtDuracion.setText(""); txtUnidades.setText("");
        txtGenero.setText(""); txtDirector.setText("");
    }

    private void eliminarDvd() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un DVD en la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminarDvd(codigoActual);
            JOptionPane.showMessageDialog(this, "DVD eliminado correctamente.");
            listarDvds();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar DVD: " + ex.getMessage());
        }
    }
}