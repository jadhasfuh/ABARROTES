package Abarrotes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Proovedores extends JDialog {

	Conexion cx = new Conexion();
	private final JPanel contentPanel = new JPanel();
	private JTextField nombre;
	private JTextField empresa;
	private JTextField dirprov;
	private JTextField diremp;
	private JTextField telefono;
	private JTextField estado;
	private JTextField cp;
	private JTextField localidad;
	private JTextField correo;
	private JTextField activo;
	private JButton consultaBtn, delButton, editarBtn;
	private JLabel lblId, labnombre, labemp, labdirpro, labdiremp, labtel, labest, lblProductos, labcp, labloc, labcorreo, labact;
	private JComboBox comboBox;
	
	public void clear() {
		nombre.setText("");
		empresa.setText("");
		dirprov.setText("");
		diremp.setText("");
		telefono.setText("");
		estado.setText("");
		cp.setText("");
		localidad.setText("");
		correo.setText("");
		activo.setText("");
	}
	
	public void desactivar() {
		labnombre.setEnabled(false);
		labcorreo.setEnabled(false);
		labemp.setEnabled(false);
		labdiremp.setEnabled(false);
		labdirpro.setEnabled(false);
		labtel.setEnabled(false);
		labest.setEnabled(false);
		labcorreo.setEnabled(false);
		labcp.setEnabled(false);
		labloc.setEnabled(false);
		nombre.setEnabled(false);
		correo.setEnabled(false);
		empresa.setEnabled(false);
		diremp.setEnabled(false);
		dirprov.setEnabled(false);
		telefono.setEnabled(false);
		estado.setEnabled(false);
		cp.setEnabled(false);
		localidad.setEnabled(false);
		correo.setEnabled(false);
		activo.setEnabled(false);
		labact.setEnabled(false);
		ArrayList<Integer> getId = cx.getId("SELECT CVEPROV FROM PROVEEDORES");
		for (int i = 0; i < getId.size(); i++) {
			comboBox.addItem(getId.get(i));
		}
	}

	public Proovedores(int Opcion, JFrame parent, boolean modal, String usuario) {
		super(parent, modal);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		switch (Opcion) {
		case 1:
			setTitle("Anadir Proveedor");
			break;
		case 2:
			setTitle("Editar Proveedor");
			break;
		case 3:
			setTitle("Eliminar Proveedor");
			break;
		case 4:
			setTitle("Consultar Proveedor");
			break;
		default:
			break;
		}
		setBackground(new Color(255, 165, 0));
		setBounds(100, 100, 470, 430);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblId = new JLabel("ID:");
		lblId.setFont(new Font("Gargi", Font.BOLD, 12));
		lblId.setBounds(12, 55, 70, 15);
		contentPanel.add(lblId);
		
		labnombre = new JLabel("Nombre:");
		labnombre.setFont(new Font("Gargi", Font.BOLD, 12));
		labnombre.setBounds(12, 80, 70, 15);
		contentPanel.add(labnombre);
		
		labemp = new JLabel("Empresa:");
		labemp.setFont(new Font("Gargi", Font.BOLD, 12));
		labemp.setBounds(12, 105, 84, 15);
		contentPanel.add(labemp);
		
		labdirpro = new JLabel("Direccion P:");
		labdirpro.setFont(new Font("Gargi", Font.BOLD, 12));
		labdirpro.setBounds(12, 130, 99, 15);
		contentPanel.add(labdirpro);
		
		labdiremp = new JLabel("Direccion E:");
		labdiremp.setFont(new Font("Gargi", Font.BOLD, 12));
		labdiremp.setBounds(12, 155, 112, 15);
		contentPanel.add(labdiremp);
		
		labtel = new JLabel("Telefono:");
		labtel.setFont(new Font("Gargi", Font.BOLD, 12));
		labtel.setBounds(12, 180, 84, 15);
		contentPanel.add(labtel);
		
		labest = new JLabel("Estado:");
		labest.setFont(new Font("Gargi", Font.BOLD, 12));
		labest.setBounds(12, 205, 84, 15);
		contentPanel.add(labest);
		
		nombre = new JTextField();
		nombre.setBounds(129, 72, 309, 25);
		contentPanel.add(nombre);
		nombre.setColumns(10);
		
		empresa = new JTextField();
		empresa.setColumns(10);
		empresa.setBounds(129, 97, 309, 25);
		contentPanel.add(empresa);
		
		dirprov = new JTextField();
		dirprov.setColumns(10);
		dirprov.setBounds(129, 122, 309, 25);
		contentPanel.add(dirprov);
		
		diremp = new JTextField();
		diremp.setColumns(10);
		diremp.setBounds(129, 147, 309, 25);
		contentPanel.add(diremp);
		
		telefono = new JTextField();
		telefono.setColumns(10);
		telefono.setBounds(129, 172, 309, 25);
		contentPanel.add(telefono);
		
		estado = new JTextField();
		estado.setColumns(10);
		estado.setBounds(129, 197, 309, 25);
		contentPanel.add(estado);
		
		comboBox = new JComboBox();
		comboBox.setBounds(129, 47, 100, 25);
		contentPanel.add(comboBox);
		
		lblProductos = new JLabel("PROVEEDORES");
		lblProductos.setFont(new Font("Gargi", Font.BOLD, 18));
		lblProductos.setBounds(12, 12, 150, 37);
		contentPanel.add(lblProductos);
		
		labcp = new JLabel("C P:");
		labcp.setFont(new Font("Gargi", Font.BOLD, 12));
		labcp.setBounds(12, 230, 84, 15);
		contentPanel.add(labcp);
		
		cp = new JTextField();
		cp.setColumns(10);
		cp.setBounds(129, 222, 309, 25);
		contentPanel.add(cp);
		
		labloc = new JLabel("Localidad:");
		labloc.setFont(new Font("Gargi", Font.BOLD, 12));
		labloc.setBounds(12, 255, 84, 15);
		contentPanel.add(labloc);
		
		localidad = new JTextField();
		localidad.setColumns(10);
		localidad.setBounds(129, 247, 309, 25);
		contentPanel.add(localidad);
		
		labcorreo = new JLabel("E-mail:");
		labcorreo.setFont(new Font("Gargi", Font.BOLD, 12));
		labcorreo.setBounds(12, 282, 84, 15);
		contentPanel.add(labcorreo);
		
		correo = new JTextField();
		correo.setColumns(10);
		correo.setBounds(129, 272, 309, 25);
		contentPanel.add(correo);
		
		labact = new JLabel("Activo:");
		labact.setFont(new Font("Gargi", Font.BOLD, 12));
		labact.setBounds(12, 305, 84, 15);
		contentPanel.add(labact);
		
		activo = new JTextField();
		activo.setColumns(10);
		activo.setBounds(129, 297, 309, 25);
		contentPanel.add(activo);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(SystemColor.control);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		switch (Opcion) {
		case 1: //ANADIR PROOVEDOR
			lblId.setEnabled(false);
			comboBox.setEnabled(false);
			activo.setEnabled(false);
			labact.setEnabled(false);
			break;
		case 2: //EDITAR PROVEEDOR
			lblId.setEnabled(false);
			activo.setEnabled(false);
			labact.setEnabled(false);
			
			ArrayList<Integer> getId = cx.getId("SELECT CVEPROV FROM PROVEEDORES");
			for (int i = 0; i < getId.size(); i++) {
				comboBox.addItem(getId.get(i));
			}
			break;
		case 3: // ELIMINAR PROVEEDOR
			desactivar();
			break;
		case 4: //CONSULTAR PROVEEDOR
			desactivar();
			break;
		default:
			break;
		}
		
		if (Opcion == 1) {
			JButton AltaBtn = new JButton();
			buttonPane.add(AltaBtn);
			try {
				Image img = ImageIO.read(getClass().getResource("img/add.png"));
				AltaBtn.setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
			}
			AltaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf, "Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (okCxl == JOptionPane.OK_OPTION) {
						String password = cx.contra(usuario);
						String givenPas = new String(pf.getPassword());
						if (password.equals(givenPas)) {
							cx.conectar();
							String cve = cx.getMaxProv(); 
							String datos [] = new String[10];
							datos[0] = nombre.getText().trim().toUpperCase();
							datos[1] = empresa.getText().trim().toUpperCase();
							datos[2] = dirprov.getText().trim().toUpperCase();
							datos[3] = diremp.getText().trim().toUpperCase();
							datos[4] = telefono.getText().trim().toUpperCase();
							datos[5] = estado.getText().trim().toUpperCase();
							datos[6] = cp.getText().trim().toUpperCase();
							datos[7] = localidad.getText().trim().toUpperCase();
							datos[8] = correo.getText().trim().toUpperCase();
							datos[9] = cve.trim();
							int ban = cx.AltaProvedor(datos);
							if (ban == 1) {
								JOptionPane.showMessageDialog(null, "EL NOMBRE DEL PROVEEDOR NO DEBE DE ESTAR VACIO");
							}else if(ban == 2) {
								JOptionPane.showMessageDialog(null, "El NOMBRE NO DEBE DE TENER CARACTERES NUMERICOS");
							}else if(ban == 3) {
								JOptionPane.showMessageDialog(null, "LA EMPRESA DEL PROVEEDOR NO DEBE DE ESTAR VACIA");
							}else if(ban == 4) {
								JOptionPane.showMessageDialog(null, "LA DIRECCION DE LA EMPRESA NO DEBE DE ESTAR VACIA");
							}else if(ban == 5) {
								JOptionPane.showMessageDialog(null, "EL TELEFONO DEL PROVEEDOR NO DEBE DE ESTAR VACIO");
							}else if(ban == 6) {
								JOptionPane.showMessageDialog(null, "EL TELEFONO TIENE LETRAS");
							}else if(ban == 7) {
								JOptionPane.showMessageDialog(null, "EL ESTADO NO DEBE DE ESTAR VACIO");
							}else if(ban == 8) {
								JOptionPane.showMessageDialog(null, "EL CODIGO POSTAL NO DEBE DE ESTAR VACIO");
							}else if(ban == 9) {
								JOptionPane.showMessageDialog(null, "EL CODIGO POSTAL TIENE LETRAS");
							}else if(ban == 10) {
								JOptionPane.showMessageDialog(null, "LA LOCALIDAD DEBE NO DEBE DE ESTAR VACIA");
							}else if(ban == 11) {
								JOptionPane.showMessageDialog(null, "EL CORREO DEL PROVEEDOR NO DEBE DE ESTAR VACIO");
							}else if(ban == 12) {
								JOptionPane.showMessageDialog(null, "EL NOMBRE DEL PROVEEDOR EXCEDE LOS 50 CARACTERES");
							}else if(ban == 13) {
								JOptionPane.showMessageDialog(null, "EL NOMBRE DE LA EMPRESA EXCEDE LOS 30 CARACTERES");
							}else if(ban == 14) {
								JOptionPane.showMessageDialog(null, "LA DIRECCION DEL PROVEEDOR EXCEDE LOS 50 CARACTERES");
							}else if(ban == 15) {
								JOptionPane.showMessageDialog(null, "LA DIRECCION DEL LA EMPRESA EXCEDE LOS 50 CARACTERES");
							}else if(ban == 16) {
								JOptionPane.showMessageDialog(null, "LA DIRECCION DEL CLIENTE EXCEDE LOS 15 CARACTERES");
							}else if(ban == 17) {
								JOptionPane.showMessageDialog(null, "EL ESTADO DE LA EMPRESA EXCEDE LOS 20 CARACTERES");
							}else if(ban == 18) {
								JOptionPane.showMessageDialog(null, "EL CODIGO POSTAL EXCEDE LOS 8 CARACTERES");
							}else if(ban == 19) {
								JOptionPane.showMessageDialog(null, "LA LOCALIDAD DEL PROVEEDOR EXCEDE LOS 30 CARACTERES");
							}else if(ban == 20) {
								JOptionPane.showMessageDialog(null, "EL CORREO DEL PROVEEDOR EXCEDE LOS 30 CARACTERES");
							}else if(ban == 21) {
								JOptionPane.showMessageDialog(null, "ESTE PROVEEDOR YA SE ENCUENTRA REGISTRADO EN LA BASE DE DATOS");
							}else if (ban == 0) {
								JOptionPane.showMessageDialog(null, "PROVEEDOR INSERTADO DE FORMA SATISFACTORIA");
								cx.backup("backup.sql");
								clear();
							}
						}else {
							JOptionPane.showMessageDialog(null, "WRONG PASSWORD");
						}
					}
				}
			});
		}else {
			consultaBtn = new JButton();
			buttonPane.add(consultaBtn);
			try {
				Image img = ImageIO.read(getClass().getResource("img/encontrar.png"));
				consultaBtn.setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
			}
		}
		
		if (Opcion == 2) {
			editarBtn = new JButton();
			buttonPane.add(editarBtn);
			try {
				Image img = ImageIO.read(getClass().getResource("img/lapiz.png"));
				editarBtn.setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
			}
			
			editarBtn.setEnabled(false);
			
			editarBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf, "Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (okCxl == JOptionPane.OK_OPTION) {
						String password = cx.contra(usuario);
						String givenPas = new String(pf.getPassword());
						if (password.equals(givenPas)) {
							String datos [] = new String[10];
							datos[0] = comboBox.getSelectedItem() + "";
							datos[1] = nombre.getText().trim().toUpperCase();
							datos[2] = empresa.getText().trim().toUpperCase();
							datos[3] = dirprov.getText().trim().toUpperCase();
							datos[4] = diremp.getText().trim().toUpperCase();
							datos[5] = telefono.getText().trim().toUpperCase();
							datos[6] = estado.getText().trim().toUpperCase();
							datos[7] = cp.getText().trim().toUpperCase();
							datos[8] = localidad.getText().trim().toUpperCase();
							datos[9] = correo.getText().trim().toUpperCase();
							int ban = cx.ModifProvedor(datos);
							if (ban == 1) {
								JOptionPane.showMessageDialog(null, "LA CLAVE DEL PROVEEDOR NO PUEDE ESTAR VACIA");
							}else if(ban == 2) {
								JOptionPane.showMessageDialog(null, "EL PROVEEDOR ES INEXISTENTE");
							}else if (ban == 0) {
								JOptionPane.showMessageDialog(null, "PROVEEDOR MODIFICADO DE FORMA SATISFACTORIA");
								cx.backup("backup.sql");
								clear();
								editarBtn.setEnabled(false);
								comboBox.setEnabled(true);
								consultaBtn.setEnabled(true);
							}
						}else {
							JOptionPane.showMessageDialog(null, "WRONG PASSWORD");
						}
					}
				}
			});
			
			consultaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Vector v = cx.consultaProveedor((Integer)comboBox.getSelectedItem());
					nombre.setText(v.elementAt(0) + "");
					empresa.setText(v.elementAt(1) + "");
					dirprov.setText(v.elementAt(2) + "");
					diremp.setText(v.elementAt(3) + "");
					telefono.setText(v.elementAt(4) + "");
					estado.setText(v.elementAt(5) + "");
					cp.setText(v.elementAt(6) + "");
					localidad.setText(v.elementAt(7) + "");
					correo.setText(v.elementAt(8) + "");
					activo.setText(v.elementAt(9) + "");
					editarBtn.setEnabled(true);
					comboBox.setEnabled(false);
					consultaBtn.setEnabled(false);
				}
			});
		}

		if (Opcion == 3) {
			delButton = new JButton();
			buttonPane.add(delButton);
			try {
				Image img = ImageIO.read(getClass().getResource("img/delete.png"));
				delButton.setIcon(new ImageIcon(img));
			} catch (Exception ex) {
				System.out.println(ex);
			}
			
			delButton.setEnabled(false);
			
			delButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf, "Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (okCxl == JOptionPane.OK_OPTION) {
						String password = cx.contra(usuario);
						String givenPas = new String(pf.getPassword());
						if (password.equals(givenPas)) {
							try {
							int ban = cx.BajaProvedor((Integer) comboBox.getSelectedItem());
							if (ban == 1) {
								JOptionPane.showMessageDialog(null, "LA CLAVE DEL PROVEEDOR NO PUEDE ESTAR VACIA");
							}else if(ban == 2) {
								JOptionPane.showMessageDialog(null, "AUN EXISTEN ARTICULOS DISPONIBLES DE ESTE PROVEEDOR");
							}else if(ban == 3) {
								JOptionPane.showMessageDialog(null, "LA CLAVE DEL PROVEEDOR NO SE ENCUENTRA REGISTRADA EN LA BASE DE DATOS");
							}else if (ban == 0) {
								JOptionPane.showMessageDialog(null, "PROVEEDOR ELIMINADO DE FORMA SATISFACTORIA");
								clear();
								cx.backup("backup.sql");
								delButton.setEnabled(false);
								comboBox.setEnabled(true);
								consultaBtn.setEnabled(true);
							}
							}catch (Exception e) {
								JOptionPane.showMessageDialog(null, "ESTE PROVEEDOR HA SIDO REMOVIDO");
							}
						}else {
							JOptionPane.showMessageDialog(null, "WRONG PASSWORD");
						}
					}
				}
			});
			
			consultaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Vector v = cx.consultaProveedor((Integer)comboBox.getSelectedItem());
					try {
					nombre.setText(v.elementAt(0) + "");
					empresa.setText(v.elementAt(1) + "");
					dirprov.setText(v.elementAt(2) + "");
					diremp.setText(v.elementAt(3) + "");
					telefono.setText(v.elementAt(4) + "");
					estado.setText(v.elementAt(5) + "");
					cp.setText(v.elementAt(6) + "");
					localidad.setText(v.elementAt(7) + "");
					correo.setText(v.elementAt(8) + "");
					activo.setText(v.elementAt(9) + "");
					delButton.setEnabled(true);
					comboBox.setEnabled(false);
					consultaBtn.setEnabled(false);
					}catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "ESTE PRODUCTO HA SIDO REMOVIDO");
					}
				}
			});
		}
		
		if (Opcion == 4) {
			consultaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Vector v = cx.consultaProveedor((Integer)comboBox.getSelectedItem());
					nombre.setText(v.elementAt(0) + "");
					empresa.setText(v.elementAt(1) + "");
					dirprov.setText(v.elementAt(2) + "");
					diremp.setText(v.elementAt(3) + "");
					telefono.setText(v.elementAt(4) + "");
					estado.setText(v.elementAt(5) + "");
					cp.setText(v.elementAt(6) + "");
					localidad.setText(v.elementAt(7) + "");
					correo.setText(v.elementAt(8) + "");
					activo.setText(v.elementAt(9) + "");
				}
			});
		}

		JButton cancelButton = new JButton("Volver");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Opcion == 1) {
					setVisible(false);
					dispose();
				}else {
					if (consultaBtn.isEnabled()) {
						setVisible(false);
						dispose();
					}else {
						if (Opcion == 2) {
							editarBtn.setEnabled(false);
							consultaBtn.setEnabled(true);
							comboBox.setEnabled(true);
							clear();
						}
						if (Opcion == 3) {
							consultaBtn.setEnabled(true);
							delButton.setEnabled(false);
							comboBox.setEnabled(true);
							clear();
						}
					}
				}
			}
		});
		cancelButton.setFont(new Font("Gargi", Font.BOLD, 12));
		try {
			Image img = ImageIO.read(getClass().getResource("img/regreso.png"));
			cancelButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		buttonPane.add(cancelButton);
	}
}