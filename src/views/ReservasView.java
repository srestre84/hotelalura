package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import javax.swing.JComboBox;

import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Properties;

@SuppressWarnings("serial")
public class ReservasView extends JFrame {

    private JPanel contentPane;
    public static JTextField txtValor;
    public static JDateChooser txtFechaEntrada;
    public static JDateChooser txtFechaSalida;
    public static JComboBox<FormaPago> txtFormaPago; // Cambio el tipo de JComboBox
    int xMouse, yMouse;
    private JLabel labelExit;
    private JLabel labelAtras;
   

    
    // por ser mi primer ejercicio en JDBC no usare el pool de conexiones ni el factory, 
    // para ejercicios posteriores si lo hare. 
    // en este ejemplo uso las propiedades del C3P0 primero inicializando la propiedades del sistema
    // luego se ejecuta su respaldo
    // luego se le da la propiedad para que no genere mensajes de registro por defecto
    // luego ingreso los datos de conexion como url, user, y contraseña.
    // finalmente uso el driver de JDBC
    private class MyConnection {

        public java.sql.Connection getConnection() throws SQLException {

            Properties p = new Properties(System.getProperties());
            p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
            p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "OFF"); // Off or any other level
            System.setProperties(p);
            String dbUrl = "jdbc:mysql://localhost:3306/hotel_alura?useTimeZone=true&serverTimeZone=UTC";
            String dbUser = "root";
            String dbPassword = "";

            Properties properties = new Properties();
            properties.setProperty("user", dbUser);
            properties.setProperty("password", dbPassword);

            return DriverManager.getConnection(dbUrl, properties);
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ReservasView frame = new ReservasView();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ReservasView() {
        super("Reserva");
        setIconImage(Toolkit.getDefaultToolkit().getImage(ReservasView.class.getResource("/imagenes/aH-40px.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 560);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBorder(null);
        panel.setBackground(Color.WHITE);
        panel.setBounds(0, 0, 910, 560);
        contentPane.add(panel);
        panel.setLayout(null);

        // Código que crea los elementos de la interfaz gráfica

        JSeparator separator_1_2 = new JSeparator();
        separator_1_2.setForeground(SystemColor.textHighlight);
        separator_1_2.setBounds(68, 195, 289, 2);
        separator_1_2.setBackground(SystemColor.textHighlight);
        panel.add(separator_1_2);

        JSeparator separator_1_3 = new JSeparator();
        separator_1_3.setForeground(SystemColor.textHighlight);
        separator_1_3.setBackground(SystemColor.textHighlight);
        separator_1_3.setBounds(68, 453, 289, 2);
        panel.add(separator_1_3);

        JSeparator separator_1_1 = new JSeparator();
        separator_1_1.setForeground(SystemColor.textHighlight);
        separator_1_1.setBounds(68, 281, 289, 11);
        separator_1_1.setBackground(SystemColor.textHighlight);
        panel.add(separator_1_1);

        JLabel lblCheckIn = new JLabel("FECHA DE CHECK IN");
        lblCheckIn.setForeground(SystemColor.textInactiveText);
        lblCheckIn.setBounds(68, 136, 169, 14);
        lblCheckIn.setFont(new Font("Roboto Black", Font.PLAIN, 18));
        panel.add(lblCheckIn);

        JLabel lblCheckOut = new JLabel("FECHA DE CHECK OUT");
        lblCheckOut.setForeground(SystemColor.textInactiveText);
        lblCheckOut.setBounds(68, 221, 187, 14);
        lblCheckOut.setFont(new Font("Roboto Black", Font.PLAIN, 18));
        panel.add(lblCheckOut);

        JLabel lblFormaPago = new JLabel("FORMA DE PAGO");
        lblFormaPago.setForeground(SystemColor.textInactiveText);
        lblFormaPago.setBounds(68, 382, 187, 24);
        lblFormaPago.setFont(new Font("Roboto Black", Font.PLAIN, 18));
        panel.add(lblFormaPago);

        JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
        lblTitulo.setBounds(109, 60, 219, 42);
        lblTitulo.setForeground(new Color(12, 138, 199));
        lblTitulo.setFont(new Font("Roboto", Font.BOLD, 20));
        panel.add(lblTitulo);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(428, 0, 482, 560);
        panel_1.setBackground(new Color(12, 138, 199));
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel logo = new JLabel("");
        logo.setBounds(197, 68, 104, 107);
        panel_1.add(logo);
        logo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/Ha-100px.png")));

        JLabel imagenFondo = new JLabel("");
        imagenFondo.setBounds(0, 140, 500, 409);
        panel_1.add(imagenFondo);
        imagenFondo.setBackground(Color.WHITE);
        imagenFondo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/reservas-img-3.png")));

        JLabel lblValor = new JLabel("VALOR DE LA RESERVA");
        lblValor.setForeground(SystemColor.textInactiveText);
        lblValor.setBounds(72, 303, 196, 14);
        lblValor.setFont(new Font("Roboto Black", Font.PLAIN, 18));
        panel.add(lblValor);

        
        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(SystemColor.textHighlight);
        separator_1.setBounds(68, 362, 289, 2);
        separator_1.setBackground(SystemColor.textHighlight);
        panel.add(separator_1);

        // Componentes para dejar la interfaz con estilo Material Design
        JPanel btnexit = new JPanel();
        btnexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuPrincipal principal = new MenuPrincipal();
                principal.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnexit.setBackground(Color.red);
                labelExit.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnexit.setBackground(new Color(12, 138, 199));
                labelExit.setForeground(Color.white);
            }
        });
        btnexit.setLayout(null);
        btnexit.setBackground(new Color(12, 138, 199));
        btnexit.setBounds(429, 0, 53, 36);
        panel_1.add(btnexit);

        labelExit = new JLabel("X");
        labelExit.setForeground(Color.WHITE);
        labelExit.setBounds(0, 0, 53, 36);
        btnexit.add(labelExit);
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);
        labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));

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
        header.setBackground(Color.WHITE);
        panel.add(header);

        JPanel btnAtras = new JPanel();
        btnAtras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuUsuario usuario = new MenuUsuario();
                usuario.setVisible(true);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAtras.setBackground(new Color(12, 138, 199));
                labelAtras.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAtras.setBackground(Color.white);
                labelAtras.setForeground(Color.black);
            }
        });
        btnAtras.setLayout(null);
        btnAtras.setBackground(Color.WHITE);
        btnAtras.setBounds(0, 0, 53, 36);
        header.add(btnAtras);

        labelAtras = new JLabel("<");
        labelAtras.setBounds(0, 0, 53, 36);
        btnAtras.add(labelAtras);
        labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
        labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));

        JLabel lblSiguiente = new JLabel("SIGUIENTE");
        lblSiguiente.setHorizontalAlignment(SwingConstants.CENTER);
        lblSiguiente.setForeground(Color.WHITE);
        lblSiguiente.setFont(new Font("Roboto", Font.PLAIN, 18));
        lblSiguiente.setBounds(0, 0, 122, 35);

        // Campos que guardaremos en la base de datos
        txtFechaEntrada = new JDateChooser();
        txtFechaEntrada.getCalendarButton().setBackground(SystemColor.textHighlight);
        txtFechaEntrada.getCalendarButton().setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/icon-reservas.png")));
        txtFechaEntrada.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
        txtFechaEntrada.setBounds(68, 161, 289, 35);
        txtFechaEntrada.getCalendarButton().setBounds(268, 0, 21, 33);
        txtFechaEntrada.setBackground(Color.WHITE);
        txtFechaEntrada.setBorder(new LineBorder(SystemColor.window));
        txtFechaEntrada.setDateFormatString("yyyy-MM-dd");
        txtFechaEntrada.setFont(new Font("Roboto", Font.PLAIN, 18));
        panel.add(txtFechaEntrada);
        
        //uso el metodo propertychange para llamar al evento del mouse a usar este valor para realizar un calculo
        txtFechaEntrada.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    calcularValorReserva();
                }
            }
        });
        txtFechaSalida = new JDateChooser();
        txtFechaSalida.getCalendarButton().setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/icon-reservas.png")));
        txtFechaSalida.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
        txtFechaSalida.setBounds(68, 246, 289, 35);
        txtFechaSalida.getCalendarButton().setBounds(267, 1, 21, 31);
        txtFechaSalida.setBackground(Color.WHITE);
        txtFechaSalida.setFont(new Font("Roboto", Font.PLAIN, 18));
        txtFechaSalida.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // Activa el evento, después del usuario seleccionar las fechas se debe calcular el valor de la reserva
            }
        });
        txtFechaSalida.setDateFormatString("yyyy-MM-dd");
        txtFechaSalida.getCalendarButton().setBackground(SystemColor.textHighlight);
        txtFechaSalida.setBorder(new LineBorder(new Color(255, 255, 255), 0));
        panel.add(txtFechaSalida);

        txtValor = new JTextField();
        txtValor.setBackground(SystemColor.text);
        txtValor.setHorizontalAlignment(SwingConstants.CENTER);
        txtValor.setForeground(Color.BLACK);
        txtValor.setBounds(78, 328, 43, 33);
        txtValor.setEditable(false);
        txtValor.setFont(new Font("Roboto Black", Font.BOLD, 17));
        txtValor.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel.add(txtValor);
        txtValor.setColumns(10);

        // Crear el JComboBox para tipos de pago utilizando el enum FormaPago
        txtFormaPago = new JComboBox<>(FormaPago.values()); // Cambiado a FormaPago
        txtFormaPago.setBounds(68, 417, 289, 38);
        txtFormaPago.setBackground(SystemColor.text);
        txtFormaPago.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
        txtFormaPago.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.add(txtFormaPago);

        txtFormaPago.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FormaPago FormaPagoSeleccionada = (FormaPago) txtFormaPago.getSelectedItem();
                String FormaPagoLabel = FormaPagoSeleccionada.getLabel();
                // relacionamos el enum con la seleccion de la forma de pago del JcomboBox, se crea clase con enum
            }
        });

        JPanel btnsiguiente = new JPanel();
        btnsiguiente.setLayout(null);
        btnsiguiente.setBackground(SystemColor.textHighlight);
        btnsiguiente.setBounds(238, 493, 122, 35);
        panel.add(btnsiguiente);
        btnsiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnsiguiente.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (txtFechaEntrada.getDate() != null && txtFechaSalida.getDate() != null) {
                    FormaPago FormaPagoSeleccionada = (FormaPago) txtFormaPago.getSelectedItem();
                    Connection con = null;
                    
                    // si los datos fueron ingresados por el usuario se procede con la conexion

                    try {
                        // Obtenemos una conexion con la base de datos 
                        MyConnection myConnection = new MyConnection();
                        con = myConnection.getConnection();

                        // Guardar la reserva en la base de datos y obtener el ID generado
                        int reservaId = guardarReserva(con, FormaPagoSeleccionada);

                        if (reservaId != -1) {
                            // La reserva se guardó con éxito, muestra el ID al usuario
                            JOptionPane.showMessageDialog(null, "Reserva guardada con éxito. Número de reserva: " + reservaId + " Debe anotar o recordar este número por que si no debera volver a tomar otra reserva");

                            // Crear una instancia de RegistroHuesped y se muestra
                            RegistroHuesped registroHuesped = new RegistroHuesped();
                            registroHuesped.setVisible(true);

                            // Cierra la ventana actual (ReservasView)
                            dispose();
                        } else {
                            // Ocurrió un error al guardar la reserva, muestra un mensaje de error
                            JOptionPane.showMessageDialog(null, "No se pudo guardar la reserva.");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Manejo de errores de SQL aquí
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes llenar todos los campos.");
                }
            }
        });


        txtFechaSalida.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    calcularValorReserva();
                }
            }
        });
    }
// calculo del valor de la reserva
    private void calcularValorReserva() {
        java.util.Date fechaEntrada = txtFechaEntrada.getDate();
        java.util.Date fechaSalida = txtFechaSalida.getDate();

        if (fechaEntrada != null && fechaSalida != null) {
            long diferenciaEnMillis = fechaSalida.getTime() - fechaEntrada.getTime();
            int dias = (int) TimeUnit.DAYS.convert(diferenciaEnMillis, TimeUnit.MILLISECONDS);
            // la anterior formula uso la formula de dias y cambio la diferencia a esta unidad , con esta funcio se hace para obtener el valor en dias 
            //y aproximar sin tener este problema luego con este valor de dias multiplico por el valor del dia
            double valorPorDia = 100; // Uso el valor de 100 usd sin especificarlo para evitar confución el dia 
            double valorTotal = dias * valorPorDia;

            txtValor.setText(String.format("%.2f", valorTotal));
        } else {
            double valorPorDefecto = 100; // Tambien uso el valor por defecto en caso de que el usuario no seleccione valor de entrada ni saliuda se le cobrara un dia
            txtValor.setText(String.format("%.2f", valorPorDefecto));
            //el %.2f se usa como una cada de texto con dos decimales para volver estos valores universales en la operacion que realizamos
        }
    }
// Luego ingresamos los valores a la tabla de Mysql con las opciones de Sql de JDBC Ingresando con throw cada uno de los valores 
    private int guardarReserva(Connection con, FormaPago formaPagoSeleccionada) throws SQLException {
        if (con == null) {
            throw new IllegalArgumentException("Connection is null");
        }

        String sql = "INSERT INTO RESERVAS (fechaEntrada, fechaSalida, valor, FormaPago) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new java.sql.Date(txtFechaEntrada.getDate().getTime()));
            statement.setDate(2, new java.sql.Date(txtFechaSalida.getDate().getTime()));

            BigDecimal valor = new BigDecimal(txtValor.getText().replace(",", "."));
            statement.setString(3, valor.toString());

            statement.setString(4, formaPagoSeleccionada.getLabel());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        return id;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    // Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
    private void headerMousePressed(java.awt.event.MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }

    public Connection getConnection1() {
        // TODO Auto-generated method stub
        return null;
    }

    public static JDateChooser getTxtFechaSalida() {
        // TODO Auto-generated method stub
        return null;
    }

    public static JTextComponent getTxtValor() {
        // TODO Auto-generated method stub
        return null;
    }

    public static JDateChooser getTxtFechaEntrada() {
        // TODO Auto-generated method stub
        return null;
    }

    public JComboBox<String> getTxtFormaPago() {
        // TODO Auto-generated method stub
        return null;
    }
} 