import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("Sistema de Mediateca");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Libros", new PanelLibro());
        tabs.addTab("Revistas", new PanelRevista());
        tabs.addTab("CDs de Audio", new PanelCdAudio());
        tabs.addTab("DVDs", new PanelDvd());
        tabs.addTab("Materiales disponibles", new PanelMaterialDisponible());

        add(tabs);
        setVisible(true);
    }
}