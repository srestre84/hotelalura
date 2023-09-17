package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ventanaEdicion extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtFechaNacimiento;
    private JTextField txtNacionalidad;
    private JTextField txtTelefono;
    private String reservaId;
    private Connection con;

    public ventanaEdicion(String reservaId) {
        this.reservaId = reservaId;
        initialize();
    }

    private void initialize() {
        setTitle("Editar Huésped");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre:");
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        contentPane.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblApellido = new JLabel("Apellido:");
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        contentPane.add(txtApellido);
        txtApellido.setColumns(10);

        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        contentPane.add(lblFechaNacimiento);

        txtFechaNacimiento = new JTextField();
        contentPane.add(txtFechaNacimiento);
        txtFechaNacimiento.setColumns(10);

        JLabel lblNacionalidad = new JLabel("Nacionalidad:");
        contentPane.add(lblNacionalidad);

        txtNacionalidad = new JTextField();
        contentPane.add(txtNacionalidad);
        txtNacionalidad.setColumns(10);

        JLabel lblTelefono = new JLabel("Teléfono:");
        contentPane.add(lblTelefono);

        txtTelefono = new JTextField();
        contentPane.add(txtTelefono);
        txtTelefono.setColumns(10);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });
        contentPane.add(btnGuardar);

        cargarDatos(); // Cargar datos actuales del huésped desde la base de datos
    }

    private void conectarDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hotel_alura?useTimeZone=true&serverTimeZone=UTC";
            String usuario = "root";
            String contraseña = "";
            con = DriverManager.getConnection(url, usuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        conectarDB();

        try {
            String query = "SELECT nombre, apellido, fecha, nacionalidad, telefono FROM huespedes WHERE reserva_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, reservaId);

            // Ejecutar la consulta
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtApellido.setText(rs.getString("apellido"));
                txtFechaNacimiento.setText(rs.getString("fecha"));
                txtNacionalidad.setText(rs.getString("nacionalidad"));
                txtTelefono.setText(rs.getString("telefono"));
            }

            rs.close();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarCambios() {
        conectarDB();

        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String fecha = txtFechaNacimiento.getText();
            String nacionalidad = txtNacionalidad.getText();
            String telefono = txtTelefono.getText();

            String query = "UPDATE huespedes SET nombre = ?, apellido = ?, fecha = ?, nacionalidad = ?, telefono = ? WHERE reserva_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            pstmt.setString(3, fecha);
            pstmt.setString(4, nacionalidad);
            pstmt.setString(5, telefono);
            pstmt.setString(6, reservaId);

            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Cambios guardados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            pstmt.close();
            con.close();

            // Cierra la ventana de edición después de guardar los cambios
            dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar los cambios", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Agrega los métodos getNombre(), getApellido(), getFechaNacimiento(), getNacionalidad(), getTelefono() si aún no los tienes

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getApellido() {
        return txtApellido.getText();
    }

    public String getFechaNacimiento() {
        return txtFechaNacimiento.getText();
    }

    public String getNacionalidad() {
        return txtNacionalidad.getText();
    }

    public String getTelefono() {
        return txtTelefono.getText();
    }
}
