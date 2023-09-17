package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.ImageIcon;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

    private JPanel contentPane;
    private JTextField txtBuscar;
    private JTable tbHuespedes;
    private JTable tbReservas;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloHuesped;
    private JLabel labelAtras;
    private JLabel labelExit;
    private int xMouse, yMouse;
    private Connection con;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Busqueda frame = new Busqueda();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Método para establecer la conexión a la base de datos
    private void conectarDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hotel_alura?useTimeZone=true&serverTimeZone=UTC"; //reemplaza tu url 
            String usuario = "root";//reemplaza tu usuario
            String contraseña = ""; // reemplaza tu contraseña
            con = DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores, muestra un mensaje al usuario o realiza otras acciones necesarias.
        }
    }

    // Método para buscar huéspedes por número de reserva
    private void buscarHuespedPorReservaId(String reservaId) {
        conectarDB(); // Establecer la conexión a la base de datos

        try {
            String query = "SELECT huespedes.id, huespedes.nombre, huespedes.apellido, huespedes.fecha, huespedes.nacionalidad, huespedes.telefono, huespedes.reserva_id, reservas.fechaEntrada, reservas.fechaSalida, reservas.valor, reservas.formaPago " +
                    "FROM huespedes " +
                    "INNER JOIN reservas ON huespedes.reserva_id = reservas.id " +
                    "WHERE huespedes.reserva_id = ?";
            
            // en el Query anterior uso inner Join para relacionar e imprimir en pantalla 
            //ambas tablas 
            // y el where para la relacionalas por el id 
            // las concateno

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, reservaId);

            ResultSet rs = pstmt.executeQuery();

            // Limpia los modelos de ambas tablas antes de agregar nuevos resultados
            modeloHuesped.setRowCount(0);
            modelo.setRowCount(0);

            // Procesa los resultados y agrega las filas a las tablas correspondientes con el metodo resulset
            // antes usamos el metodo en reservasView usamos prepareStatement
            while (rs.next()) {
                int huespedId = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String fechaNacimiento = rs.getString("fecha");
                String nacionalidad = rs.getString("nacionalidad");
                String telefono = rs.getString("telefono");
                String reserva_id = rs.getString("reserva_id");

                // Con el valor de la reserva obtengo los resultados obtenidos en la tabla registroHuesped y ReservasView que se relacionan 
                //Con llave id que viene siendo tambien el número de la reserva
                String fechaEntrada = rs.getString("fechaEntrada");
                String fechaSalida = rs.getString("fechaSalida");
                String valor = rs.getString("valor");
                String formaPago = rs.getString("formaPago");

                // Agrega los datos a las tablas correspondientes
                modeloHuesped.addRow(new Object[]{huespedId, nombre, apellido, fechaNacimiento, nacionalidad, telefono, reserva_id});
                modelo.addRow(new Object[]{fechaEntrada, fechaSalida, valor, formaPago});
            }

            // Cerrar recursos (ResultSet, PreparedStatement) y la conexión
            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores, muestra un mensaje al usuario o realiza otras acciones necesarias.
        }
    }

    public Busqueda() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 910, 571);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        setUndecorated(true);

        JLabel lblBuscarInfo = new JLabel("Buscar por número de reserva:");
        lblBuscarInfo.setBounds(536, 95, 193, 22);
        lblBuscarInfo.setForeground(new Color(12, 138, 199));
        lblBuscarInfo.setFont(new Font("Roboto", Font.PLAIN, 11));
        contentPane.add(lblBuscarInfo);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(536, 127, 193, 31);
        txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        contentPane.add(txtBuscar);
        txtBuscar.setColumns(10);

        JLabel lblBuscarAyuda = new JLabel("Nota: Si no recuerda su número de reserva, "
        + "por favor realice una nueva reserva.");
        lblBuscarAyuda.setBounds(536, 162, 400, 22);
        lblBuscarAyuda.setForeground(new Color(12, 138, 199));
        lblBuscarAyuda.setFont(new Font("Roboto", Font.PLAIN, 10));
        contentPane.add(lblBuscarAyuda);

        JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
        panel.setBackground(new Color(12, 138, 199));
        panel.setFont(new Font("Roboto", Font.PLAIN, 16));
        panel.setBounds(20, 169, 865, 328);
        contentPane.add(panel);

        tbReservas = new JTable();
        tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
        modelo = (DefaultTableModel) tbReservas.getModel();

        modelo.addColumn("fechaEntrada");
        modelo.addColumn("FechaSalida");
        modelo.addColumn("valor");
        modelo.addColumn("formaPago");
        JScrollPane scroll_table = new JScrollPane(tbReservas);
        panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
        scroll_table.setVisible(true);

        tbHuespedes = new JTable();
        tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
        modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();

        modeloHuesped.addColumn("ID");
        modeloHuesped.addColumn("Nombre");
        modeloHuesped.addColumn("Apellido");
        modeloHuesped.addColumn("Fecha de Nacimiento");
        modeloHuesped.addColumn("Nacionalidad");
        modeloHuesped.addColumn("Telefono");
        modeloHuesped.addColumn("Número de Reserva");
        JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
        panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
        scroll_tableHuespedes.setVisible(true);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
        lblNewLabel_2.setBounds(56, 51, 104, 107);
        contentPane.add(lblNewLabel_2);

        JPanel header = new JPanel();
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
        header.setBounds(0, 0, 910, 36);
        contentPane.add(header);

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
        labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
        labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
        labelAtras.setBounds(0, 0, 53, 36);
        btnAtras.add(labelAtras);

        JPanel btnexit = new JPanel();
        btnexit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuUsuario usuario = new MenuUsuario();
                usuario.setVisible(true);
                dispose();
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
        btnexit.setBackground(Color.WHITE);
        btnexit.setBounds(857, 0, 53, 36);
        header.add(btnexit);

        labelExit = new JLabel("X");
        labelExit.setHorizontalAlignment(SwingConstants.CENTER);
        labelExit.setForeground(Color.BLACK);
        labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
        labelExit.setBounds(0, 0, 53, 36);
        btnexit.add(labelExit);

        JSeparator separator_1_2 = new JSeparator();
        separator_1_2.setForeground(new Color(12, 138, 199));
        separator_1_2.setBackground(new Color(12, 138, 199));
        separator_1_2.setBounds(539, 159, 193, 2);
        contentPane.add(separator_1_2);

        JPanel btnbuscar = new JPanel();
        btnbuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String reservaId = txtBuscar.getText();
                if (!reservaId.isEmpty()) {
                    buscarHuespedPorReservaId(reservaId);
                } else {
                    // Muestra un mensaje de error si el campo de búsqueda está vacío
                    JOptionPane.showMessageDialog(contentPane, "Por favor, ingrese un número de reserva válido.");
                }
            }
        });
        btnbuscar.setLayout(null);
        btnbuscar.setBackground(new Color(12, 138, 199));
        btnbuscar.setBounds(748, 125, 122, 35);
        btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentPane.add(btnbuscar);

        JLabel lblBuscar = new JLabel("BUSCAR");
        lblBuscar.setBounds(0, 0, 122, 35);
        btnbuscar.add(lblBuscar);
        lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
        lblBuscar.setForeground(Color.WHITE);
        lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

        JPanel btnEditar = new JPanel();
        btnEditar.setLayout(null);
        btnEditar.setBackground(new Color(12, 138, 199));
        btnEditar.setBounds(635, 508, 122, 35);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentPane.add(btnEditar);

        JLabel lblEditar = new JLabel("EDITAR");
        lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEditar.setForeground(Color.WHITE);
        lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
        lblEditar.setBounds(0, 0, 122, 35);
        btnEditar.add(lblEditar);

        btnEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbHuespedes.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener el número de reserva del huésped seleccionado
                    String reservaId = tbHuespedes.getValueAt(selectedRow, 6).toString();
                     //creo una ventana nueva que me permite editar los campos del huesped
                    // Abre una ventana de edición de huésped y pasa el número de reserva para cargar la información actual
                    ventanaEdicion ventanaEdicion = new ventanaEdicion(reservaId);
                    ventanaEdicion.setVisible(true);
                }
            }
        });

        JPanel btnEliminar = new JPanel();
        btnEliminar.setLayout(null);
        btnEliminar.setBackground(new Color(12, 138, 199));
        btnEliminar.setBounds(767, 508, 122, 35);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        contentPane.add(btnEliminar);

        JLabel lblEliminar = new JLabel("ELIMINAR");
        lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
        lblEliminar.setForeground(Color.WHITE);
        lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
        lblEliminar.setBounds(0, 0, 122, 35);
        btnEliminar.add(lblEliminar);

        
        // Creo un metodo que me permite eliminar huespedes 
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tbHuespedes.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener el número de reserva y el ID del huésped seleccionado
                    String reservaId = tbHuespedes.getValueAt(selectedRow, 6).toString();
                    int huespedId = Integer.parseInt(tbHuespedes.getValueAt(selectedRow, 0).toString());

                    // Pregunta al usuario si realmente desea eliminar el registro
                    int dialogResult = JOptionPane.showConfirmDialog(contentPane, "¿Está seguro que desea eliminar este registro de huésped y su reserva asociada?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        eliminarHuespedYReserva(huespedId, reservaId);
                    }
                }
            }
        });

        setResizable(false);
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

    // Método para eliminar un huésped y, si es necesario, la reserva asociada
    private void eliminarHuespedYReserva(int huespedId, String reservaId) {
        conectarDB(); // Establecer la conexión a la base de datos

        try {
            // Eliminar el registro del huésped
            String deleteHuespedQuery = "DELETE FROM huespedes WHERE id = ?";
            PreparedStatement pstmt = con.prepareStatement(deleteHuespedQuery);
            pstmt.setInt(1, huespedId);
            pstmt.executeUpdate();

            // Verificar si la reserva asociada a este huésped ya no tiene otros huéspedes
            String checkReservaQuery = "SELECT COUNT(*) FROM huespedes WHERE reserva_id = ?";
            pstmt = con.prepareStatement(checkReservaQuery);
            pstmt.setString(1, reservaId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int numHuespedesEnReserva = rs.getInt(1);

            // Si no hay otros huéspedes en la misma reserva, eliminar la reserva
            if (numHuespedesEnReserva == 0) {
                String deleteReservaQuery = "DELETE FROM reservas WHERE id = ?";
                pstmt = con.prepareStatement(deleteReservaQuery);
                pstmt.setString(1, reservaId);
                pstmt.executeUpdate();
            }

            // Actualizar la tabla de huéspedes
            buscarHuespedPorReservaId(reservaId);

            // Cerrar recursos (PreparedStatement) y la conexión
            pstmt.close();
            con.close();

            JOptionPane.showMessageDialog(contentPane, "El registro de huésped y, si era necesario, la reserva asociada, han sido eliminados exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores, muestra un mensaje al usuario o realiza otras acciones necesarias.
            JOptionPane.showMessageDialog(contentPane, "Error al eliminar el registro de huésped y/o reserva.");
        }
    }
}
