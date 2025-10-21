import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelCdAudio extends JPanel {
    private JLabel lblCodigo;
    private JTextField txtTitulo, txtDuracion, txtUnidades, txtGenero, txtArtista, txtCanciones;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private CdAudioDAO dao = new CdAudioDAO();
    private String codigoActual = "";

    public PanelCdAudio() {
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(7, 2));
        lblCodigo = new JLabel("CDAxxxxx");
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
        txtArtista = new JTextField();
        formulario.add(new JLabel("Artista:"));
        formulario.add(txtArtista);
        txtCanciones = new JTextField();
        formulario.add(new JLabel("Número de canciones:"));
        formulario.add(txtCanciones);
        add(formulario, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnListar = new JButton("Listar");
        JButton btnEliminar = new JButton("Eliminar");
        botones.add(btnEliminar);
        btnEliminar.addActionListener(e -> eliminarCd());
        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnListar);
        add(botones, BorderLayout.CENTER);

        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Duración", "Unidades", "Género", "Artista", "Canciones"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarCd());
        btnModificar.addActionListener(e -> modificarCd());
        btnListar.addActionListener(e -> listarCds());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                codigoActual = tabla.getValueAt(fila, 0).toString();
                lblCodigo.setText(codigoActual);
                txtTitulo.setText(tabla.getValueAt(fila, 1).toString());
                txtDuracion.setText(tabla.getValueAt(fila, 2).toString());
                txtUnidades.setText(tabla.getValueAt(fila, 3).toString());
                txtGenero.setText(tabla.getValueAt(fila, 4).toString());
                txtArtista.setText(tabla.getValueAt(fila, 5).toString());
                txtCanciones.setText(tabla.getValueAt(fila, 6).toString());
            }
        });
    }

    private void agregarCd() {
        try {
            codigoActual = CodigoGenerator.generarCodigo("cd");
            lblCodigo.setText(codigoActual);

            CdAudio cd = new CdAudio(
                    codigoActual,
                    txtTitulo.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    Integer.parseInt(txtUnidades.getText()),
                    txtGenero.getText(),
                    txtArtista.getText(),
                    Integer.parseInt(txtCanciones.getText())
            );
            dao.insertarCd(cd);
            JOptionPane.showMessageDialog(this, "CD guardado en la base de datos.");
            listarCds();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void modificarCd() {
        try {
            CdAudio cd = new CdAudio(
                    codigoActual,
                    txtTitulo.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    Integer.parseInt(txtUnidades.getText()),
                    txtGenero.getText(),
                    txtArtista.getText(),
                    Integer.parseInt(txtCanciones.getText())
            );
            dao.actualizarCd(cd);
            JOptionPane.showMessageDialog(this, "CD modificado correctamente.");
            listarCds();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar CD: " + ex.getMessage());
        }
    }

    private void listarCds() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<CdAudio> cds = dao.listarCds();
            for (CdAudio cd : cds) {
                modeloTabla.addRow(new Object[]{
                        cd.getCodigo(), cd.getTitulo(), cd.getDuracion(),
                        cd.getUnidadesDisponibles(), cd.getGenero(),
                        cd.getArtista(), cd.getNumeroCanciones()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar CDs: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        lblCodigo.setText("CDAxxxxx");
        codigoActual = "";
        txtTitulo.setText("");
        txtDuracion.setText("");
        txtUnidades.setText("");
        txtGenero.setText("");
        txtArtista.setText("");
        txtCanciones.setText("");
    }

    private void eliminarCd() {
        if (codigoActual.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona un CD en la tabla para eliminar.");
            return;
        }
        try {
            dao.eliminarCd(codigoActual);
            JOptionPane.showMessageDialog(this, "CD eliminado correctamente.");
            listarCds();
            limpiarCampos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar CD: " + ex.getMessage());
        }
    }
}