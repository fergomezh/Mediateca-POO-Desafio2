import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PanelMaterialDisponible extends JPanel {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private MaterialDisponibleDAO dao = new MaterialDisponibleDAO();

    public PanelMaterialDisponible() {
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel(new String[]{
                "Código", "Título", "Tipo"
        }, 0);
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnActualizar = new JButton("Actualizar lista");
        btnActualizar.addActionListener(e -> listarMateriales());
        add(btnActualizar, BorderLayout.SOUTH);

        listarMateriales(); // carga inicial
    }

    private void listarMateriales() {
        try {
            modeloTabla.setRowCount(0);
            ArrayList<String[]> materiales = dao.listarMateriales();
            for (String[] fila : materiales) {
                modeloTabla.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al listar materiales: " + ex.getMessage());
        }
    }
}