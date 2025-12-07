import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SistemaReciclajeGUI extends JFrame {

    private JTextField txtRut, txtNombre, txtCorreo, txtDireccion;
    private JTextField txtMaterialDesc, txtMaterialPeso;
    private JTextArea txtAreaMateriales;
    private JButton btnAgregarMaterial, btnCrearSolicitud;

    private java.util.List<DetalleMaterial> materiales = new ArrayList<>();
    private ControladorSolicitud controladorSol = new ControladorSolicitud();

    public SistemaReciclajeGUI() {
        setTitle("Sistema de Reciclaje");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelHogar = new JPanel(new GridLayout(5, 2, 5, 5));
        panelHogar.setBorder(BorderFactory.createTitledBorder("Datos del Hogar"));

        txtRut = new JTextField();
        txtNombre = new JTextField();
        txtCorreo = new JTextField();
        txtDireccion = new JTextField();

        panelHogar.add(new JLabel("RUT:"));
        panelHogar.add(txtRut);
        panelHogar.add(new JLabel("Nombre:"));
        panelHogar.add(txtNombre);
        panelHogar.add(new JLabel("Correo:"));
        panelHogar.add(txtCorreo);
        panelHogar.add(new JLabel("Dirección:"));
        panelHogar.add(txtDireccion);

        JPanel panelMaterial = new JPanel(new GridLayout(3, 2, 5, 5));
        panelMaterial.setBorder(BorderFactory.createTitledBorder("Agregar Materiales"));

        txtMaterialDesc = new JTextField();
        txtMaterialPeso = new JTextField();

        panelMaterial.add(new JLabel("Descripción:"));
        panelMaterial.add(txtMaterialDesc);
        panelMaterial.add(new JLabel("Peso (kg):"));
        panelMaterial.add(txtMaterialPeso);

        btnAgregarMaterial = new JButton("Agregar Material");
        panelMaterial.add(btnAgregarMaterial);

        //area materiales añadidos
        txtAreaMateriales = new JTextArea(8, 40);
        txtAreaMateriales.setEditable(false);

        //boton crear solicitud
        btnCrearSolicitud = new JButton("Crear Solicitud");

        add(panelHogar, BorderLayout.NORTH);
        add(panelMaterial, BorderLayout.CENTER);
        add(new JScrollPane(txtAreaMateriales), BorderLayout.SOUTH);
        add(btnCrearSolicitud, BorderLayout.PAGE_END);

        // agregar material
        btnAgregarMaterial.addActionListener(e -> agregarMaterial());

        //crear solicitud 
        btnCrearSolicitud.addActionListener(e -> crearSolicitud());

        setVisible(true);
    }

    private void agregarMaterial() {
        try {
            String desc = txtMaterialDesc.getText();
            float peso = Float.parseFloat(txtMaterialPeso.getText());

            materiales.add(new DetalleMaterial(desc, peso));

            txtAreaMateriales.append("Material: " + desc + " - " + peso + " kg\n");

            txtMaterialDesc.setText("");
            txtMaterialPeso.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos del material.");
        }
    }

    private void crearSolicitud() {
        try {
            Hogar hogar = new Hogar(
                1,
                txtRut.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtDireccion.getText(),
                -36.8f, -73.0f
            );

            SolicitudDeRecoleccion sol = controladorSol.crearSolicitud(hogar, materiales);

            JOptionPane.showMessageDialog(this,
                    "Solicitud creada con ID: " + sol.getSolicitudID() +
                    "\nPeso total: " + sol.calcularPesoSol() + " kg"
            );

            //crear un reciclador fijo ejemplo
            Reciclador reciclador = new Reciclador(
                2, "98765432-1", "Constanza Cristinich", "conicris@mail.com",
                "AB-1234", 200f, "doc.com", "Cooperativa", -36.8f, -73.0f
            );

            Comprobante comp = reciclador.generarComprobante(sol.getSolicitudID());

            JOptionPane.showMessageDialog(this,
                    "Reciclador disponible: " + reciclador.getNombre() +
                    "\nComprobante generado."
            );

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear solicitud.");
        }
    }

    public static void main(String[] args) {
        new SistemaReciclajeGUI();
    }
}

