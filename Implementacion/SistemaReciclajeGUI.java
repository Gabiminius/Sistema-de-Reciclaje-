import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SistemaReciclajeGUI extends JFrame {

    private ControladorSolicitud controladorSolicitud = new ControladorSolicitud();
    private ControladorRuta controladorRuta = new ControladorRuta();

    private JTextArea output;

    public SistemaReciclajeGUI() {
        setTitle("Sistema de Reciclaje");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        JButton btnRegistrarHogar = new JButton("Registrar Hogar");
        JButton btnCrearSolicitud = new JButton("Crear Solicitud");
        JButton btnVerSolicitudes = new JButton("Ver Solicitudes");
        JButton btnGenerarRuta = new JButton("Generar Ruta");
        JButton btnVerRutas = new JButton("Ver Rutas");

        output = new JTextArea();
        output.setEditable(false);
        JScrollPane scroll = new JScrollPane(output);

        panel.add(btnRegistrarHogar);
        panel.add(btnCrearSolicitud);
        panel.add(btnVerSolicitudes);
        panel.add(btnGenerarRuta);
        panel.add(btnVerRutas);

        add(panel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // ----- ACCIONES -----

        btnRegistrarHogar.addActionListener(e -> registrarHogar());
        btnCrearSolicitud.addActionListener(e -> crearSolicitud());
        btnVerSolicitudes.addActionListener(e -> mostrarSolicitudes());
        btnGenerarRuta.addActionListener(e -> generarRuta());
        btnVerRutas.addActionListener(e -> verRutas());

        setVisible(true);
    }

    private void registrarHogar() {
        try {
            int id = controladorSolicitud.getHogares().size() + 1;
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String rut = JOptionPane.showInputDialog("RUT:");
            String correo = JOptionPane.showInputDialog("Correo:");
            String direccion = JOptionPane.showInputDialog("Dirección:");

            Hogar h = new Hogar(id, rut, nombre, correo, direccion, 0, 0);
            controladorSolicitud.registrarHogar(h);
            controladorRuta.setHogares(controladorSolicitud.getHogares());

            output.append("✔ Hogar registrado: " + nombre + "\n");
        } catch (Exception ex) {
            output.append("Error registrando hogar.\n");
        }
    }

    private void crearSolicitud() {
        try {
            String hogarIDStr = JOptionPane.showInputDialog("ID del hogar:");
            int hogarID = Integer.parseInt(hogarIDStr);

            String desc = JOptionPane.showInputDialog("Descripción del material:");
            String pesoStr = JOptionPane.showInputDialog("Peso aproximado:");
            float peso = Float.parseFloat(pesoStr);

            java.util.List<DetalleMaterial> materiales = new java.util.ArrayList<>();
            materiales.add(new DetalleMaterial(desc, peso));
            SolicitudDeRecoleccion sol = controladorSolicitud.crearSolicitud(hogarID, materiales);


            if (sol != null) {
                output.append("✔ Solicitud creada (ID: " + sol.getSolicitudID() + ")\n");
            }
        } catch (Exception ex) {
            output.append("Error creando solicitud.\n");
        }
    }

    private void mostrarSolicitudes() {
        output.append("\n=== SOLICITUDES ===\n");
        for (SolicitudDeRecoleccion s : controladorSolicitud.getSolicitudes()) {
            output.append("ID: " + s.getSolicitudID() +
                    " | Hogar: " + s.getHogarID() +
                    " | Peso: " + s.calcularPesoSol() + "kg\n");
        }
    }

    private void generarRuta() {
        if (controladorSolicitud.getSolicitudes().isEmpty()) {
            output.append("No hay solicitudes para agrupar.\n");
            return;
        }

        RutaDeRecoleccion ruta = controladorRuta.generarPropuesta(
                "Zona X",
                controladorSolicitud.getSolicitudes()
        );

        output.append("✔ Ruta generada (ID: " + ruta.getRutaID() + ")\n");
    }

    private void verRutas() {
        output.append("\n=== RUTAS ===\n");
        for (RutaDeRecoleccion r : controladorRuta.getRutas()) {
            output.append("Ruta " + r.getRutaID() +
                    " | Solicitudes: " + r.getListaDeRecoleccion().size() +
                    " | Peso: " + r.calcularPesoRuta() + "kg\n");
        }
    }

    public static void main(String[] args) {
        new SistemaReciclajeGUI();
    }
}
