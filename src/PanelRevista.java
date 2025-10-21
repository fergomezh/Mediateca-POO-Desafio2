import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class PanelRevista extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtEditorial, txtUnidades, txtPeriodicidad, txtFecha;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private RevistaDAO dao = new RevistaDAO();
    private String codigoActual = "";

    public PanelRevista() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(6, 2));
        lblCodigo = new JLabel("REVxxxxx");
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
        txtPeriodicidad = new JTextField();
        formulario.add(new JLabel("Periodicidad:"));
        formulario.add(txtPeriodicidad);
        txtFecha = new JTextField();
        formulario.add(new JLabel("Fecha publicación (YYYY-MM-DD):"));
        formulario.add(txtFecha);
        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnEliminar);
        btnEliminar.addActionListener(e -> eliminarRevista());
        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnListar);
        add(botones, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Editorial", "Unidades", "Periodicidad", "Fecha"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarRevista());
        btnModificar.addActionListener(e -> modificarRevista());
        btnListar.addActionListener(e -> listarRevistas());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                codigoActual = tabla.getValueAt(fila, 0).toString();
                lblCodigo.setText(codigoActual);
                txtTitulo.setText(tabla.getValueAt(fila, 1).toString());
                txtEditorial.setText(tabla.getValueAt(fila, 2).toString());
                txtUnidades.setText(tabla.getValueAt(fila, 3).toString());
                txtPeriodicidad.setText(tabla.getValueAt(fila, 4).toString());
                txtFecha.setText(tabla.getValueAt(fila, 5).toString());
            }
        });
    }

    private void agregarRevista() {
        try {
            codigoActual = CodigoGenerator.generarCodigo("revista");
            lblCodigo.setText(codigoActual);

            Revista revista = new Revista(
                    codigoActual,
                    txtTitulo.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtUnidades.getText()),
                    txtPeriodicidad.getText(),
                    LocalDate.parse(txtFecha.getText())
            );
            dao.insertarRevista(revista);
            JOptionPane.showMessageDialog(this, "Revista guardada en la base de datos.");
            listarRevistas();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarRevista() {
        try {
            Revista revista = new Revista(
                    codigoActual,
                    txtTitulo.getText(),
                    txtEditorial.getText(),
                    Integer.parseInt(txtUnidades.getText()),
                    txtPeriodicidad.getText(),
                    LocalDate.parse(txtFecha.getText())
            );
            dao.actualizarRevista(revista);
            JOptionPane.showMessageDialog(this, "Revista modificada correctamente.");
            listarRevistas();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar revista: " + ex.getMessage());
        }
    }

    private void listarRevistas() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<Revista> revistas = dao.listarRevistas();
            for (Revista r : revistas) {
                modeloTabla.addRow(new Object[]{
                        r.getCodigo(), r.getTitulo(), r.getEditorial(),
                        r.getUnidadesDisponibles(), r.getPeriodicidad(),
                        r.getFechaPublicacion()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar revistas: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("REVxxxxx");
        codigoActual = "";
        txtTitulo.setText(""); txtEditorial.setText(""); txtUnidades.setText("");
        txtPeriodicidad.setText(""); txtFecha.setText("");
    }

    private void eliminarRevista() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona una revista en la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminarRevista(codigoActual);
            JOptionPane.showMessageDialog(this, "Revista eliminada correctamente.");
            listarRevistas();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar revista: " + ex.getMessage());
        }
    }
}