package Abarrotes;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.crypto.BadPaddingException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.GridLayout;

public class Main {

	private JFrame MainMenu;
	public String nombre = "";
	public int cvemp;
	Conexion conex = new Conexion();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.MainMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void inicio() {
		Sesion ini = new Sesion(MainMenu, true);
		ini.setVisible(true);
	}

	public Main() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		initialize();
	}

	private void initialize() {
		MainMenu = new JFrame();
		MainMenu.setBounds(100, 100, 720, 480);
		MainMenu.setLocationRelativeTo(null);
		MainMenu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		MainMenu.setResizable(false);
		MainMenu.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		inicio();
		conex.conectar();
		nombre = conex.getNombre();
		cvemp = conex.getCvemp(nombre);
		System.out.println(nombre);
		agregarmenu();
		JButton comprar = new JButton();
		comprar.setBackground(new Color(255, 165, 0));
		try {
			Image img = ImageIO.read(getClass().getResource("img/caja.png"));
			comprar.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		comprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ban[];
				ban = conex.Venauto(cvemp);
				if (ban[0] == 1) {
					JOptionPane.showMessageDialog(null, "LA CVE DEL EMPLEADO ESTA VACIA");
				} else if (ban[0] == 2) {
					JOptionPane.showMessageDialog(null, "LA CVE DEL EMPLEADO NO EXISTE");
				} else if (ban[0] == 3) {
					JOptionPane.showMessageDialog(null, "ESTE EMPLEADO SE ENCUENTRA DADO DE BAJA");
				} else if (ban[0] == 4) {
					JOptionPane.showMessageDialog(null,
							"LAS VENTAS SOLO PUEDEN SER COBRADAS POR LOS CAJEROS, ENC. DE TIENDA O ADMINISTRADORES");
				} else if (ban[0] == 0) {
					Ventas v = new Ventas(ban[1]);
					v.setVisible(true);
				}
			}
		});
		MainMenu.getContentPane().add(comprar);

		JLabel LeAtiende = new JLabel("Buenas tardes, le atiende " + nombre);
		LeAtiende.setFont(new Font("Gargi", Font.BOLD, 16));
		LeAtiende.setHorizontalAlignment(SwingConstants.CENTER);
		MainMenu.getContentPane().add(LeAtiende, BorderLayout.SOUTH);
	}

	public void agregarmenu() {
		JMenuBar barra = new JMenuBar();
		barra.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenu proveedor = new JMenu("Proveedor");
		proveedor.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem addProv = new JMenuItem("Anadir Proveedor");
		addProv.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem ediProv = new JMenuItem("Editar Proveedor");
		ediProv.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem delProv = new JMenuItem("Eliminar Proveedor");
		delProv.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem conProv = new JMenuItem("Consultar Proveedor");
		conProv.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem verProv = new JMenuItem("Lista de Proovedores");
		verProv.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenu productos = new JMenu("Productos");
		productos.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem addProd = new JMenuItem("Anadir Producto");
		addProd.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem ediProd = new JMenuItem("Editar Producto");
		ediProd.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem delProd = new JMenuItem("Eliminar Producto");
		delProd.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem conProd = new JMenuItem("Consultar Producto");
		conProd.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem verProd = new JMenuItem("Lista de Productos");
		verProd.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenu compras = new JMenu("Compras");
		compras.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem addComp = new JMenuItem("Realizar Compra");
		addComp.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem conComp = new JMenuItem("Consultar Compra");
		conComp.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem verCompras = new JMenuItem("Historial de compras");
		verCompras.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenu ventas = new JMenu("Ventas");
		ventas.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem addVentas = new JMenuItem("Realizar Venta");
		addVentas.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem conVentas = new JMenuItem("Consultar Venta");
		conVentas.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem verVentas = new JMenuItem("Historial de Ventas");
		verVentas.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenu op = new JMenu("Opciones");
		op.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem salir = new JMenuItem("Salir");
		salir.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem restore = new JMenuItem("Restaurar BD");
		restore.setFont(new Font("Gargi", Font.BOLD, 12));
		JMenuItem backup = new JMenuItem("Crear Backup");
		backup.setFont(new Font("Gargi", Font.BOLD, 12));

		barra.add(proveedor);
		barra.add(productos);
		barra.add(compras);
		barra.add(ventas);
		barra.add(op);
		proveedor.add(addProv);
		proveedor.add(ediProv);
		proveedor.add(delProv);
		proveedor.add(conProv);
		proveedor.add(verProv);
		productos.add(addProd);
		productos.add(ediProd);
		productos.add(delProd);
		productos.add(conProd);
		productos.add(verProd);
		compras.add(addComp);
		compras.add(conComp);
		compras.add(verCompras);
		ventas.add(addVentas);
		ventas.add(conVentas);
		ventas.add(verVentas);
		op.add(restore);
		op.add(backup);
		op.add(salir);

		MainMenu.setJMenuBar(barra);

		///////////////////////////////////////////////////////////
		/////                  OPTIONES                 ///////////
		///////////////////////////////////////////////////////////
		salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conex.CerrarSesion();
				conex.desconectar();
				System.exit(0);
			}
		});
		backup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccionado = new JFileChooser();
				FileNameExtensionFilter fil = new FileNameExtensionFilter("SQL", "sql");
				seleccionado.setFileFilter(fil);
				seleccionado.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
				if (seleccionado.showDialog(MainMenu, "Crear") == JFileChooser.APPROVE_OPTION) {
					if ((""+seleccionado.getSelectedFile().getAbsolutePath()).endsWith(".sql")) 
						conex.backup("" + seleccionado.getSelectedFile().getAbsolutePath());
					else
						conex.backup(("" + seleccionado.getSelectedFile().getAbsolutePath())+".sql");
				}
			}
		});
		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser seleccionado = new JFileChooser(); 
				FileNameExtensionFilter fil = new FileNameExtensionFilter("SQL", "sql");
				seleccionado.setFileFilter(fil);
				seleccionado.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if (seleccionado.showDialog(MainMenu, "Abrir") == JFileChooser.APPROVE_OPTION) {
					conex.restore("" + seleccionado.getSelectedFile().getAbsolutePath());
				}
			}
		});

		///////////////////////////////////////////////////////////
		/////             VENTAS OPTIONES               ///////////
		///////////////////////////////////////////////////////////
		addVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ban[];
				ban = conex.Venauto(cvemp);
				if (ban[0] == 1) {
					JOptionPane.showMessageDialog(null, "LA CVE DEL EMPLEADO ESTA VACIA");
				} else if (ban[0] == 2) {
					JOptionPane.showMessageDialog(null, "LA CVE DEL EMPLEADO NO EXISTE");
				} else if (ban[0] == 3) {
					JOptionPane.showMessageDialog(null, "ESTE EMPLEADO SE ENCUENTRA DADO DE BAJA");
				} else if (ban[0] == 4) {
					JOptionPane.showMessageDialog(null,
							"LAS VENTAS SOLO PUEDEN SER COBRADAS POR LOS CAJEROS, ENC. DE TIENDA O ADMINISTRADORES");
				} else if (ban[0] == 0) {
					Ventas v = new Ventas(ban[1]);
					v.setVisible(true);
				}
			}
		});
		
		conVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultaVentas v = new ConsultaVentas();
				v.setVisible(true);
			}
		});

		///////////////////////////////////////////////////////////
		///// COMPRAS OPTIONES ///////////
		///////////////////////////////////////////////////////////
		addComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectCve v = new SelectCve("Selecciona un proveedor:");
				v.setVisible(true);
			}
		});
		conComp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConsultaCompras v = new ConsultaCompras();
				v.setVisible(true);
			}
		});

		///////////////////////////////////////////////////////////
		///// CONSULTAS GENERALES ///////////
		///////////////////////////////////////////////////////////
		verProv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = conex.FillTable("SELECT * FROM PROVEEDORES");
				Ver v = new Ver(MainMenu, true, rs, "LISTA DE PROVEEDORES");
				v.setVisible(true);
			}
		});
		verCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = conex.FillTable("SELECT * FROM COMPRAS");
				Ver v = new Ver(MainMenu, true, rs, "LISTA DE COMPRAS");
				v.setVisible(true);
			}
		});
		verVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = conex.FillTable("SELECT * FROM VENTAS");
				Ver v = new Ver(MainMenu, true, rs, "LISTA DE VENTAS");
				v.setVisible(true);
			}
		});
		verProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet rs = conex.FillTable("SELECT * FROM PRODUCTOS");
				Ver v = new Ver(MainMenu, true, rs, "LISTA DE PRODUCTOS");
				v.setVisible(true);
			}
		});

		///////////////////////////////////////////////////////////
		///// OPCIONES PRODUCTOS ///////////
		///////////////////////////////////////////////////////////
		addProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Productos p = new Productos(1, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
		ediProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Productos p = new Productos(2, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
		delProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Productos p = new Productos(3, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
		conProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Productos p = new Productos(4, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});

		///////////////////////////////////////////////////////////
		///// OPCIONES PROOVEDOR ///////////
		///////////////////////////////////////////////////////////
		addProv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proovedores p = new Proovedores(1, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
		ediProv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proovedores p = new Proovedores(2, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
		delProv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proovedores p = new Proovedores(3, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
		conProv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proovedores p = new Proovedores(4, MainMenu, true, nombre);
				p.setVisible(true);
			}
		});
	}
}