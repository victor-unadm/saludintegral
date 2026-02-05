package mx.unadmexico.saludintegral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SistemaDeCitas extends JFrame {

    // Componentes de UI
    private final JPanel panelInicio = new JPanel(new GridBagLayout());
    private final JPanel panelAgendarCita = new JPanel(new GridBagLayout());
    private final JLabel lblInicio = new JLabel("Sistema de Gestión Hospitalaria");
    private final JLabel lblTituloRegistroCita = new JLabel("Clínica Salud Integral - Registro de Cita");
    private final JMenu mnuInicio = new JMenu("Inicio");
    private final JMenu mnuPacientes = new JMenu("Pacientes");
    private final JMenu mnuCitas = new JMenu("Citas");
    private final JMenu mnuMedicos = new JMenu("Medicos");;
    private final JMenu mnuSalir = new JMenu("Salir");
    private final JMenuBar barraMenu = new JMenuBar();
    private final JTextField txtNombrePaciente = new JTextField(20);
    private JComboBox<String> cmbTipoConsulta;
    private final JTextArea txtObservacionesMedicas = new JTextArea(5, 20);
    private final JCheckBox chkDatosCorrectos = new JCheckBox("Confirmo que los datos proporcionados son correctos");
    private final JButton btnAgendarCita = new JButton("Agendar Cita");;
    
    public SistemaDeCitas() {
        
        // Configuración de la ventana principal
        setTitle("Sistema de Citas");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del menú
        barraMenu.add(mnuInicio);
        barraMenu.add(mnuPacientes);
        barraMenu.add(mnuCitas);
        barraMenu.add(mnuMedicos);
        barraMenu.add(mnuSalir);
        setJMenuBar(barraMenu);

        // Etiqueta del panel inicial
        estilizarEtiqueta(lblInicio);
        panelInicio.add(lblInicio);

        agregarEventos();
        
        // Agrega panel inicial al JFrame
        add(panelInicio);
    }
    
    private void agregarEventos() {
        // Click en menú Citas
        mnuCitas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarPanelCitas();
            }
        });
  
        // Click en menú Salir
        mnuSalir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        // Click en botón de Agendar Cita
        btnAgendarCita.addActionListener(e -> agendarCita());
    }
    
    private void cargarPanelCitas() {
        // Si el panel ya está cargado, no volverlo a cargar
        if (this.getContentPane().isAncestorOf(panelAgendarCita)) {
            return;
        }

        getContentPane().removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        estilizarEtiqueta(lblTituloRegistroCita);
        panelAgendarCita.add(lblTituloRegistroCita, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panelAgendarCita.add(new JLabel("Nombre del Paciente:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panelAgendarCita.add(txtNombrePaciente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelAgendarCita.add(new JLabel("Tipo de Consulta:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        String[] tiposConsulta = { "General", "Especialidad", "Urgencia" };
        cmbTipoConsulta = new JComboBox<>(tiposConsulta);
        panelAgendarCita.add(cmbTipoConsulta, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 2, 10);
        panelAgendarCita.add(new JLabel("Observaciones Médicas:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(2, 10, 1, 10);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelAgendarCita.add(new JScrollPane(txtObservacionesMedicas), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        panelAgendarCita.add(chkDatosCorrectos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panelAgendarCita.add(btnAgendarCita, gbc);

        add(panelAgendarCita);
        
        revalidate();
        repaint();
    }

    private void agendarCita() {
        ArrayList<String> advertencias = new ArrayList<>();

        if (txtNombrePaciente.getText().isEmpty()) {
            advertencias.add("El nombre del paciente es obligatorio.");
        }

        if (!chkDatosCorrectos.isSelected()) {
            advertencias.add("Debe confirmar que los datos proporcionados son correctos.");
        }

        if (!advertencias.isEmpty()) {
            String mensajeAdvertencia = String.join("\n", advertencias);
            mostrarMensaje(mensajeAdvertencia, JOptionPane.WARNING_MESSAGE);
            return;
        }

        mostrarMensaje(
                "Los datos para agendar la cita son correctos.\n" +
                        "Paciente: " + txtNombrePaciente.getText() + "\n" +
                        "Tipo de Consulta: " + cmbTipoConsulta.getSelectedItem(),
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarMensaje(String message, int tipoMensaje) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Agendar Cita",
                tipoMensaje);
    }
    
    private void estilizarEtiqueta(JLabel label) {
        Font fuente = label.getFont();
        Font fuenteGrande = fuente.deriveFont(Font.BOLD, 28f);
        label.setFont(fuenteGrande);
    }

    public static void main(String[] args) throws Exception {
        try {
            SistemaDeCitas sistema = new SistemaDeCitas();
            sistema.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error al iniciar el sistema de citas: " + e.getMessage());
        }
    }
}
