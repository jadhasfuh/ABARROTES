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
import java.util.Vector;
import java.awt.event.ActionEvent;

public class Productos extends JDialog {

	Conexion cx = new Conexion();
	private final JPanel contentPanel = new JPanel();
	private JTextField nombre;
	private JTextField descripcion;
	private JTextField pcompra;
	private JTextField pventa;
	private JTextField existencias;
	private JTextField estado;
	private JButton consultaBtn, delButton, editarBtn;
	private JLabel lid, lnombre, ldesc, lpcomp, lpvent, lexisten, lestado, lblProductos;
	private JComboBox comboBox;
	
	public Productos(int Opcion, JFrame parent, boolean modal, String usuario) {
		
		super(parent, modal);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		switch (Opcion) {
		case 1:
			setTitle("Anadir Producto");
			break;
		case 2:
			setTitle("Editar Producto");
			break;
		case 3:
			setTitle("Eliminar Producto");
			break;
		case 4:
			setTitle("Consultar Producto");
			break;
		default:
			break;
		}
		
		setBackground(new Color(255, 165, 0));
		setBounds(100, 100, 470, 330);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lid = new JLabel("ID:");
		lid.setFont(new Font("Gargi", Font.BOLD, 12));
		lid.setBounds(12, 55, 70, 15);
		contentPanel.add(lid);
		
		lnombre = new JLabel("Nombre:");
		lnombre.setFont(new Font("Gargi", Font.BOLD, 12));
		lnombre.setBounds(12, 80, 70, 15);
		contentPanel.add(lnombre);
		
		ldesc = new JLabel("Descripcion:");
		ldesc.setFont(new Font("Gargi", Font.BOLD, 12));
		ldesc.setBounds(12, 105, 84, 15);
		contentPanel.add(ldesc);
		
		lpcomp = new JLabel("P Compra:");
		lpcomp.setFont(new Font("Gargi", Font.BOLD, 12));
		lpcomp.setBounds(12, 130, 70, 15);
		contentPanel.add(lpcomp);
		
		lpvent = new JLabel("P Venta:");
		lpvent.setFont(new Font("Gargi", Font.BOLD, 12));
		lpvent.setBounds(12, 155, 70, 15);
		contentPanel.add(lpvent);
		
		lexisten = new JLabel("Existencias:");
		lexisten.setFont(new Font("Gargi", Font.BOLD, 12));
		lexisten.setBounds(12, 180, 84, 15);
		contentPanel.add(lexisten);
		
		lestado = new JLabel("Estado:");
		lestado.setFont(new Font("Gargi", Font.BOLD, 12));
		lestado.setBounds(12, 205, 84, 15);
		contentPanel.add(lestado);
		
		nombre = new JTextField();
		nombre.setBounds(129, 72, 309, 25);
		contentPanel.add(nombre);
		nombre.setColumns(10);
		
		descripcion = new JTextField();
		descripcion.setColumns(10);
		descripcion.setBounds(129, 97, 309, 25);
		contentPanel.add(descripcion);
		
		pcompra = new JTextField();
		pcompra.setColumns(10);
		pcompra.setBounds(129, 122, 309, 25);
		contentPanel.add(pcompra);
		
		pventa = new JTextField();
		pventa.setColumns(10);
		pventa.setBounds(129, 147, 309, 25);
		contentPanel.add(pventa);
		
		existencias = new JTextField();
		existencias.setColumns(10);
		existencias.setBounds(129, 172, 309, 25);
		contentPanel.add(existencias);
		
		estado = new JTextField();
		estado.setColumns(10);
		estado.setBounds(129, 197, 309, 25);
		contentPanel.add(estado);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Gargi", Font.BOLD, 12));
		comboBox.setBounds(129, 47, 100, 25);
		contentPanel.add(comboBox);
		
		lblProductos = new JLabel("PRODUCTOS");
		lblProductos.setFont(new Font("Gargi", Font.BOLD, 18));
		lblProductos.setBounds(12, 12, 150, 37);
		contentPanel.add(lblProductos);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(SystemColor.control);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		switch (Opcion) {
		case 1: //ANADIR PRODUCTO
			lid.setEnabled(false);
			comboBox.setEnabled(false);
			lpcomp.setEnabled(false);
			lpvent.setEnabled(false);
			pcompra.setEnabled(false);
			pventa.setEnabled(false);
			estado.setEnabled(false);
			lestado.setEnabled(false);
			existencias.setEnabled(false);
			lexisten.setEnabled(false);
			break;
		case 2://EDITAR PROVEEDOR
			lid.setEnabled(false);
			lpcomp.setEnabled(false);
			lpvent.setEnabled(false);
			pcompra.setEnabled(false);
			pventa.setEnabled(false);
			estado.setEnabled(false);
			lestado.setEnabled(false);
			existencias.setEnabled(false);
			lexisten.setEnabled(false);
			
			ArrayList<Integer> getId = cx.getId("SELECT CVEPROD FROM PRODUCTOS");
			for (int i = 0; i < getId.size(); i++) {
				comboBox.addItem(getId.get(i));
			}
			break;
		case 3: //ELIMINAR PRODUCTO
			desactivar();
			break;
		case 4: //CONSULTAR PRODUCTO
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
				public void actionPerformed(ActionEvent arg0) {
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf, "Password: ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (okCxl == JOptionPane.OK_OPTION) {
						String password = cx.contra(usuario);
						String givenPas = new String(pf.getPassword());
						if (password.equals(givenPas)) {
							cx.conectar();
							String cve = cx.getMaxProd(); 
							String datos [] = new String[3];
							datos[0] = nombre.getText().trim().toUpperCase();
							datos[1] = descripcion.getText().trim().toUpperCase();
							datos[2] = cve.trim();cx.backup("backup.sql");
							int ban = cx.AltaProducto(datos);
							if (ban == 1) {
								JOptionPane.showMessageDialog(null, "EL NOMBRE DEL PRODUCTO NO DEBE DE ESTAR VACIO");
							}else if(ban == 2) {
								JOptionPane.showMessageDialog(null, "LA DESCRIPCION NO DEBE DE ESTAR VACIA");
							}else if(ban == 3) {
								JOptionPane.showMessageDialog(null, "EL NOMBRE DEL PRODUCTO EXCEDE LOS 30 CARACTERES");
							}else if(ban == 4) {
								JOptionPane.showMessageDialog(null, "LA DESCRIPCION DEL PRODUCTO EXCEDE LOS 50 CARACTERES");
							}else if(ban == 5) {
								JOptionPane.showMessageDialog(null, "ESTE PRODUCTO YA SE ENCUENTRA REGISTRADO EN LA BASE DE DATOS");
							}else if (ban == 0) {
								JOptionPane.showMessageDialog(null, "PRODUCTO INSERTADO DE FORMA SATISFACTORIA");
								cx.backup("backup.sql");
								clear();
							}
						}else {
							JOptionPane.showMessageDialog(null, "WRONG PASSWORD");
						}
					}
				}
			});
		} else {
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
							String datos [] = new String[3];
							datos[0] = comboBox.getSelectedItem() + "";
							datos[1] = nombre.getText().trim().toUpperCase();
							datos[2] = descripcion.getText().trim().toUpperCase();
							int ban = cx.ModifProd(datos);
							if (ban == 1) {
								JOptionPane.showMessageDialog(null, "LA CVE DEL PRODUCTO NO DEBE DE ESTAR VACIA");
							}else if(ban == 2) {
								JOptionPane.showMessageDialog(null, "EL NOMBRE NO PUEDE ESTAR VACIA");
							}else if(ban == 3) {
								JOptionPane.showMessageDialog(null, "LA DESCRPICION NO PUEDE ESTAR VACIA");
							}else if(ban == 4) {
								JOptionPane.showMessageDialog(null, "ESTE PRODUCTO NO SE ENCUENTRA REGISTRADO EN LA BD");
							}else if (ban == 0) {
								JOptionPane.showMessageDialog(null, "PRODUCTO MODIFICADO DE FORMA SATISFACTORIA");
								clear();
								cx.backup("backup.sql");
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
					Vector v = cx.consultaProducto((Integer) comboBox.getSelectedItem());
					nombre.setText(v.elementAt(0) + "");
					descripcion.setText(v.elementAt(1) + "");
					pcompra.setText(v.elementAt(2) + "");
					pventa.setText(v.elementAt(3) + "");
					existencias.setText(v.elementAt(4) + "");
					estado.setText(v.elementAt(5) + "");
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
							int ban = cx.BajaProducto((Integer) comboBox.getSelectedItem());
							if (ban == 1) {
								JOptionPane.showMessageDialog(null, "LA CLAVE DEL PRODUCTO NO PUEDE ESTAR VACIA");
							} else if (ban == 2) {
								JOptionPane.showMessageDialog(null, "HAY EXISTENCIAS DENTRO DEL PRODUCTO, NO SE PUEDE DAR DE BAJA");
							} else if (ban == 3) {
								JOptionPane.showMessageDialog(null,"EL PRODUCTO TIENE UNA CADUCIDAD ASIGNADA");
							} else if (ban == 4) {
								JOptionPane.showMessageDialog(null,"EL PRODUCTO TIENE UNA VENTA ASIGNADA");
							} else if (ban == 5) {
								JOptionPane.showMessageDialog(null,"EL PRODUCTO TIENE UNA COMPRA ASIGNADA");
							} else if (ban == 6) {
								JOptionPane.showMessageDialog(null,"LA CLAVE DEL PRODUCTO NO EXISTE");
							}  else if (ban == 0) {
								JOptionPane.showMessageDialog(null, "PRODUCTO ELIMINADO DE FORMA SATISFACTORIA");
								clear();
								delButton.setEnabled(false);
								comboBox.setEnabled(true);
								consultaBtn.setEnabled(true);
							}
							}catch (Exception e) {
								JOptionPane.showMessageDialog(null, "ESTE PRODUCTO HA SIDO REMOVIDO");
								cx.backup("backup.sql");
							}
						} else {
							JOptionPane.showMessageDialog(null, "WRONG PASSWORD");
						}
					}
				}
			});

			consultaBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Vector v = cx.consultaProducto((Integer) comboBox.getSelectedItem());
					try {
					nombre.setText(v.elementAt(0) + "");
					descripcion.setText(v.elementAt(1) + "");
					pcompra.setText(v.elementAt(2) + "");
					pventa.setText(v.elementAt(3) + "");
					existencias.setText(v.elementAt(4) + "");
					estado.setText(v.elementAt(5) + "");
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
					Vector v = cx.consultaProducto((Integer)comboBox.getSelectedItem());
					nombre.setText(v.elementAt(0) + "");
					descripcion.setText(v.elementAt(1) + "");
					pcompra.setText(v.elementAt(2) + "");
					pventa.setText(v.elementAt(3) + "");
					existencias.setText(v.elementAt(4) + "");
					estado.setText(v.elementAt(5) + "");
				}
			});
		}

		JButton cancelButton = new JButton("Volver");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (Opcion == 1) {
					setVisible(false);
					dispose();
				} else {
					if (consultaBtn.isEnabled()) {
						setVisible(false);
						dispose();
					} else {
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
	
	public void clear() {
		nombre.setText("");
		descripcion.setText("");
		pcompra.setText("");
		pventa.setText("");
		existencias.setText("");
		estado.setText("");
	}
	
	public void desactivar() {
		lnombre.setEnabled(false);
		ldesc.setEnabled(false);
		lpcomp.setEnabled(false);
		lpvent.setEnabled(false);
		lestado.setEnabled(false);
		lexisten.setEnabled(false);
		nombre.setEnabled(false);;
		descripcion.setEnabled(false);
		pcompra.setEnabled(false);
		pventa.setEnabled(false);
		existencias.setEnabled(false);
		estado.setEnabled(false);
		ArrayList<Integer> getId = cx.getId("SELECT CVEPROD FROM PRODUCTOS");
		for (int i = 0; i < getId.size(); i++) {
			comboBox.addItem(getId.get(i));
		}
	}
}