package views;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;

import java.awt.Toolkit;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class RegistroHuesped extends JFrame {

    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtTelefono;
    private JTextField txtReservaId; 
    private JDateChooser txtFechaN;
    private JComboBox<String> txtNacionalidad;
    private JLabel labelExit;
    private JLabel labelAtras;
    private int xMouse, yMouse;

 

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegistroHuesped frame = new RegistroHuesped();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RegistroHuesped() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(RegistroHuesped.class.getResource("/imagenes/lOGO-50PX.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 634);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.text);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setUndecorated(true);
        contentPane.setLayout(null);

        JPanel header = new JPanel();
        header.setBounds(0, 0, 910, 36);
        header.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                headerMouseDragged(e);
            }
        });
        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                headerMousePressed(e);
            }
        });
        header.setLayout(null);
        header.setBackground(SystemColor.text);
        header.setOpaque(false);
        header.setBounds(0, 0, 910, 36);
        contentPane.add(header);

        JPanel btnAtras = new JPanel();
        btnAtras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                volverAReservasView();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAtras.setBackground(Color.white);
                labelAtras.setForeground(Color.black);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAtras.setBackground(new Color(12, 138, 199));
                labelAtras.setForeground(Color.white);
            }
        });
        btnAtras.setLayout(null);
        btnAtras.setBackground(new Color(12, 138, 199));
        btnAtras.setBounds(0, 0, 53, 36);
        header.add(btnAtras);

        labelAtras = new JLabel("<");
        labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
        labelAtras.setForeground(Color.WHITE);
        labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
        labelAtras.setBounds(0, 0, 53, 36);
        btnAtras.add(labelAtras);

        txtNombre = createTextField(560, 135);
        txtApellido = createTextField(560, 204);
        txtFechaN = createFechaChooser(560, 278);
        txtNacionalidad = createNacionalidadComboBox(560, 350);
        txtTelefono = createTextField(560, 424);
        txtReservaId = createTextField(560, 495); 
        createSeparator(560, 170);
        createSeparator(560, 240);
        createSeparator(560, 314);
        createSeparator(560, 386);
        createSeparator(560, 457);
        createSeparator(560, 529);

        createGuardarButton();

        createHeaderComponents();

        createImagePanel();

        createExitButton();
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Roboto", Font.PLAIN, 16));
        textField.setBounds(x, y, 285, 33);
        textField.setBackground(Color.WHITE);
        textField.setColumns(10);
        textField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        contentPane.add(textField);
        return textField;
    }

    private JDateChooser createFechaChooser(int x, int y) {
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(x, y, 285, 36);
        dateChooser.getCalendarButton().setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/icon-reservas.png")));
        dateChooser.getCalendarButton().setBackground(SystemColor.textHighlight);
        dateChooser.setDateFormatString("yyyy-MM-dd");
        contentPane.add(dateChooser);
        return dateChooser;
    }

    private JComboBox<String> createNacionalidadComboBox(int x, int y) {
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setBounds(x, y, 289, 36);
        comboBox.setBackground(SystemColor.text);
        comboBox.setFont(new Font("Roboto", Font.PLAIN, 16));
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
            "afgano-afgana", "alemán-", "alemana", "árabe-árabe", "argentino-argentina", "australiano-australiana",
            "belga-belga", "boliviano-boliviana", "brasileño-brasileña", "camboyano-camboyana", "canadiense-canadiense",
            "chileno-chilena", "chino-china", "colombiano-colombiana", "coreano-coreana", "costarricense-costarricense",
            "cubano-cubana", "danés-danesa", "ecuatoriano-ecuatoriana", "egipcio-egipcia", "salvadoreño-salvadoreña",
            "escocés-escocesa", "español-española", "estadounidense-estadounidense", "estonio-estonia", "etiope-etiope",
            "filipino-filipina", "finlandés-finlandesa", "francés-francesa", "galés-galesa", "griego-griega",
            "guatemalteco-guatemalteca", "haitiano-haitiana", "holandés-holandesa", "hondureño-hondureña",
            "indonés-indonesa", "inglés-inglesa", "iraquí-iraquí", "iraní-iraní", "irlandés-irlandesa",
            "israelí-israelí", "italiano-italiana", "japonés-japonesa", "jordano-jordana", "laosiano-laosiana",
            "letón-letona", "letonés-letonesa", "malayo-malaya", "marroquí-marroquí", "mexicano-mexicana",
            "nicaragüense-nicaragüense", "noruego-noruega", "neozelandés-neozelandesa", "panameño-panameña",
            "paraguayo-paraguaya", "peruano-peruana", "polaco-polaca", "portugués-portuguesa",
            "puertorriqueño-puertorriqueño", "dominicano-dominicana", "rumano-rumana", "ruso-rusa", "sueco-sueca",
            "suizo-suiza", "tailandés-tailandesa", "taiwanes-taiwanesa", "turco-turca", "ucraniano-ucraniana",
            "uruguayo-uruguaya", "venezolano-venezolana", "vietnamita-vietnamita"
        }));
        contentPane.add(comboBox);
        return comboBox;
    }

    private void createSeparator(int x, int y) {
        JSeparator separator = new JSeparator();
        separator.setBounds(x, y, 289, 2);
        separator.setForeground(new Color(12, 138, 199));
        separator.setBackground(new Color(12, 138, 199));
        contentPane.add(separator);
    }

    private void createGuardarButton() {
        JPanel btnguardar = new JPanel();
        btnguardar.setBounds(723, 560, 122, 35);
        btnguardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guardarHuesped();
            }
        });
        btnguardar.setLayout(null);
        btnguardar.setBackground(new Color(12, 138, 199));
        contentPane.add(btnguardar);
        btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        JLabel labelGuardar = new JLabel("GUARDAR");
        labelGuardar.setHorizontalAlignment(SwingConstants.CENTER);
        labelGuardar.setForeground(Color.WHITE);
        labelGuardar.setFont(new Font("Roboto", Font.PLAIN, 18));
        labelGuardar.setBounds(0, 0, 122, 35);
        btnguardar.add(labelGuardar);
    }

    protected void guardarHuesped() {
        // Se obtienen los valores ingresados por el usuario
    	// En la clase reservasView obtuvimos un valor de la reserva y con este valor relacionamos la reserva con el huesped
    	// Por lo anterior no hubo necesidad de instanciar objeto ni crear clases dao por que no hicimos mas operaciones con estos
    	// Datos, solo los consultamos
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        Date fechaNacimiento = new Date(txtFechaN.getDate().getTime());
        String nacionalidad = (String) txtNacionalidad.getSelectedItem();
        String telefono = txtTelefono.getText();
        String reserva_id = txtReservaId.getText();

        // Realiza la conexión a la base de datos y guarda la información
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Reemplaza con el nombre del controlador
            String url = "jdbc:mysql://localhost:3306/hotel_alura?useTimeZone=true&serverTimeZone=UTC"; // Reemplaza con la URL de tu base de datos
            String usuario = "root"; // Reemplaza con tu usuario
            String contraseña = ""; // Reemplaza con tu contraseña

            // Usamos la conexion he ingresamos los valores en la tabla de MySQL usando el preparestatement
            Connection con = DriverManager.getConnection(url, usuario, contraseña);
            String query = "INSERT INTO huespedes (nombre, apellido, fecha, nacionalidad, telefono, reserva_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setDate(3, fechaNacimiento);
            pstmt.setString(4, nacionalidad);
            pstmt.setString(5, telefono);
            pstmt.setString(6, reserva_id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Huésped guardado exitosamente.");
                Busqueda busqueda = new Busqueda();
				busqueda.setVisible(true); // Luego de Guardar el huesped exitosamente abre la ventana de busqueda por si el usuario 
				//desea eliminar reservas o editar, vale la pena anotar que las reservas la pueden tomar varios usuarios y esto va a depender de la dispo-
				//bilidad de las habitaciones, si esto lo quisieramos calcular seria otro ejercicio y si necesitariamos las clases dao
                dispose();
                
            } else {
                JOptionPane.showMessageDialog(this, "Hubo un problema al guardar al huésped.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos.");
        }
    }


    private void createHeaderComponents() {
        JLabel lblNombre = createHeaderLabel("NOMBRE", 562, 119);
        JLabel lblApellido = createHeaderLabel("APELLIDO", 560, 189);
        JLabel lblFechaN = createHeaderLabel("FECHA DE NACIMIENTO", 560, 256);
        JLabel lblNacionalidad = createHeaderLabel("NACIONALIDAD", 560, 326);
        JLabel lblTelefono = createHeaderLabel("TELÉFONO", 562, 406);
        JLabel labelNumeroReserva = createHeaderLabel("RESERVA_ID ",560,466);
       
    }

    private JLabel createHeaderLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 255, 14);
        label.setForeground(SystemColor.textInactiveText);
        label.setFont(new Font("Roboto Black", Font.PLAIN, 18));
        contentPane.add(label);
        return label;
    }

    private void createImagePanel() {
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 489, 634);
        panel.setBackground(new Color(12, 138, 199));
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel imagenFondo = new JLabel("");
        imagenFondo.setBounds(0, 121, 479, 502);
        panel.add(imagenFondo);
        imagenFondo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/registro.png")));

        JLabel logo = new JLabel("");
        logo.setBounds(194, 39, 104, 107);
        panel.add(logo);
        logo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/Ha-100px.png")));
    }

    private void createExitButton() {
        JPanel btnexit = new JPanel();
        btnexit.setBounds(857, 0, 53, 36);
        contentPane.add(btnexit);
        btnexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                volverAMenuPrincipal();
            }

            private void volverAMenuPrincipal() {
				// TODO Auto-generated method stub
				
			}

			@Override
            public void mouseEntered(MouseEvent e) {
                btnexit.setBackground(Color.red);
                labelExit.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnexit.setBackground(Color.white);
                labelExit.setForeground(Color.black);
            }
        });
        btnexit.setLayout(null);
        btnexit.setBackground(Color.white);

        labelExit = new JLabel("X");
        labelExit.setBounds(0, 0, 53, 36);
        btnexit.add(labelExit);
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);
        labelExit.setForeground(SystemColor.black);
        labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
    }

    private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }

    private void volverAReservasView() {
        ReservasView reservas = new ReservasView();
        reservas.setVisible(true);
        dispose();
    }
}