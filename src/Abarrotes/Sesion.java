package Abarrotes;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

class JPanelFondo extends JPanel {

	private static final long serialVersionUID = 1L;
	Image imagen;
	JButton in, ca, Cambiar;
	JPasswordField pass;
	JTextField usuario;
	JFrame V;
	JLabel im, empty;
	String password;
	Conexion BD;
	JLabel user;
	JLabel pas;

	public JPanelFondo(JDialog s) {

		this.setSize(350, 500);
		V = new JFrame("LogIn");
		V.setSize(350, 300);
		V.setResizable(false);
		V.setLocationRelativeTo(null);
		V.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		
		BD = new Conexion();
		BD.conectar();
		BD.CerrarSesion();
		
		usuario = new JTextField();
		usuario.setFont(new Font("Gargi", Font.BOLD, 16));
		in = new JButton("Entrar");
		in.setBackground(new Color(255, 165, 0));
		in.setFont(new Font("Gargi", Font.BOLD, 16));
		ca = new JButton("Salir");
		ca.setBackground(new Color(255, 165, 0));
		ca.setFont(new Font("Gargi", Font.BOLD, 16));
		im = new JLabel(new ImageIcon(getClass().getResource("img/entrada.png")));
		pass = new JPasswordField(20);
		pass.setFont(new Font("Gargi", Font.BOLD, 10));
		pass.setEchoChar('*');
		
		user = new JLabel("Usuario");
		user.setFont(new Font("Gargi", Font.BOLD, 10));
		pas = new JLabel("Contrasena");
		pas.setFont(new Font("Gargi", Font.BOLD, 10));
		
		setLayout(new GridLayout(2,1));
		add(im);
		JPanel botno = new JPanel(new GridLayout(6, 1));
		botno.add(user);
		botno.add(usuario);
		botno.add(pas);
		botno.add(pass);
		botno.add(in);
		botno.add(ca);

		add(botno);

		V.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		in.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				password = BD.contra(usuario.getText());
				if (pass.getText().equals(password)) {
					BD.IniciarSecion(usuario.getText().trim());
					V.dispose();
					setVisible(false);
					s.setVisible(false);
					//JOptionPane.showMessageDialog(null, "Bienvenido!");
				} else {
					JOptionPane.showMessageDialog(null, "Ingrese usuario y/o contrasena validos");
					pass.setText("");
				}
			}
		});

		ca.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				V.dispose();
				BD.desconectar();
				System.exit(0);
			}
		});
	}
	
}

public class Sesion extends JDialog {

	private static final long serialVersionUID = 1L;
	JButton in, ca;
	JLabel fondoGay;
	JPasswordField pass;

	public Sesion(JFrame padre, boolean modal) {

		super(padre, modal); 
			
		JPanelFondo fondo = new JPanelFondo(this);
		this.add(fondo, BorderLayout.CENTER);
		this.pack();

		Image usuario = new ImageIcon(getClass().getResource("img/user.png")).getImage();
		setIconImage(usuario);
		setTitle("LogIn");
		setSize(350, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	}

}