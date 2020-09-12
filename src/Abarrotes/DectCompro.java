package Abarrotes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class DectCompro extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField precioventa;
	private JTextField preciocomp;
	private JDateChooser caducidad;
	private JTextField cantidad;
	private JTextField idcompra;
	Conexion cx = new Conexion();
	private JTextField idprod;
	private JTextField lote;
	
	public java.sql.Date Conversor(java.util.Date dato) {
		return new java.sql.Date(dato.getTime());
	}

	public DectCompro(int cveC) {
		setBounds(100, 100, 450, 333);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbid = new JLabel("ID Compra:");
		lbid.setFont(new Font("Gargi", Font.BOLD, 12));
		lbid.setBounds(12, 24, 70, 15);
		contentPanel.add(lbid);
		
		JLabel lblIdProducto = new JLabel("ID Producto:");
		lblIdProducto.setFont(new Font("Gargi", Font.BOLD, 12));
		lblIdProducto.setBounds(12, 58, 98, 15);
		contentPanel.add(lblIdProducto);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setFont(new Font("Gargi", Font.BOLD, 12));
		lblCantidad.setBounds(12, 92, 98, 15);
		contentPanel.add(lblCantidad);
		
		JLabel lblCaducidad = new JLabel("Caducidad:");
		lblCaducidad.setFont(new Font("Gargi", Font.BOLD, 12));
		lblCaducidad.setBounds(12, 126, 98, 15);
		contentPanel.add(lblCaducidad);
		
		JLabel lblPrecioCompra = new JLabel("Precio Compra:");
		lblPrecioCompra.setFont(new Font("Gargi", Font.BOLD, 12));
		lblPrecioCompra.setBounds(12, 160, 98, 15);
		contentPanel.add(lblPrecioCompra);
		
		JLabel lblPrecioDeVenta = new JLabel("Precio de Venta:");
		lblPrecioDeVenta.setFont(new Font("Gargi", Font.BOLD, 12));
		lblPrecioDeVenta.setBounds(12, 192, 138, 15);
		contentPanel.add(lblPrecioDeVenta);
		
		precioventa = new JTextField();
		precioventa.setFont(new Font("Gargi", Font.PLAIN, 12));
		precioventa.setBounds(128, 187, 310, 25);
		contentPanel.add(precioventa);
		precioventa.setColumns(10);
		
		preciocomp = new JTextField();
		preciocomp.setFont(new Font("Gargi", Font.PLAIN, 12));
		preciocomp.setColumns(10);
		preciocomp.setBounds(128, 155, 310, 25);
		contentPanel.add(preciocomp);
		
		caducidad = new JDateChooser();
		caducidad.setFont(new Font("Gargi", Font.PLAIN, 12));
		caducidad.setBounds(128, 121, 310, 25);
		contentPanel.add(caducidad);
		
		cantidad = new JTextField();
		cantidad.setFont(new Font("Gargi", Font.PLAIN, 12));
		cantidad.setColumns(10);
		cantidad.setBounds(128, 87, 310, 25);
		contentPanel.add(cantidad);
		
		idcompra = new JTextField();
		idcompra.setFont(new Font("Gargi", Font.PLAIN, 12));
		idcompra.setEnabled(false);
		idcompra.setColumns(10);
		idcompra.setBounds(128, 19, 310, 25);
		idcompra.setText(cveC+"");
		contentPanel.add(idcompra);
		
		idprod = new JTextField();
		idprod.setFont(new Font("Gargi", Font.PLAIN, 12));
		idprod.setColumns(10);
		idprod.setBounds(128, 53, 310, 25);
		contentPanel.add(idprod);
		
		JLabel lblote = new JLabel("Lote:");
		lblote.setFont(new Font("Gargi", Font.BOLD, 12));
		lblote.setBounds(12, 224, 138, 15);
		contentPanel.add(lblote);
		
		lote = new JTextField();
		lote.setFont(new Font("Gargi", Font.PLAIN, 12));
		lote.setColumns(10);
		lote.setBounds(128, 219, 310, 25);
		contentPanel.add(lote);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date fecha;
				try {
					fecha = Conversor(caducidad.getDate());
				}catch (Exception e1) {
					fecha = null;
				}
				if (fecha == null) {
					JOptionPane.showMessageDialog(null, "FECHA DE CADUCIDAD NO VALIDA");
				}else {
					ArrayList<String> elementos = new ArrayList<String>();
					elementos.add(idcompra.getText());
					if (!idprod.getText().isEmpty())
						elementos.add(idprod.getText());
					else
						elementos.add("0");
					if (!cantidad.getText().isEmpty())
						elementos.add(cantidad.getText());
					else
						elementos.add("0");
					elementos.add(fecha + "");
					if (!preciocomp.getText().isEmpty())
						elementos.add(preciocomp.getText());
					else
						elementos.add("0");
					if (!precioventa.getText().isEmpty())
						elementos.add(precioventa.getText());
					else
						elementos.add("0");
					if (lote.getText().length() > 9 ) {
						JOptionPane.showMessageDialog(null, "EL LOTE EXCEDE LOS CARACTERES PERMITIDOS");
						lote.setText("");
					}
					elementos.add(lote.getText());
					
					int ban = cx.AltaDetComp(elementos, fecha);
					if (ban == 1) {
						JOptionPane.showMessageDialog(null, "LA CLAVE DE COMPRA NO DEBE DE ESTAR VACIA");
					} else if (ban == 2) {
						JOptionPane.showMessageDialog(null, "LA CLAVE DE COMPRA NO EXISTE");
					} else if (ban == 3) {
						JOptionPane.showMessageDialog(null, "LA CLAVE DEL PRODUCTO ESTA VACIA");
					} else if (ban == 4) {
						JOptionPane.showMessageDialog(null, "LA CLAVE DEL PRODUCTO NO EXISTE");
					} else if (ban == 5) {
						JOptionPane.showMessageDialog(null, "LA CANTIDAD COMPRADA ESTA VACIA O ES INFERIOR A 1");
					} else if (ban == 6) {
						JOptionPane.showMessageDialog(null, "LA FECHA DE CAD DEL PRODUCTO NO DEBE DE ESTAR VACIA");
					} else if (ban == 7) {
						JOptionPane.showMessageDialog(null, "LA FECHA DE CAD DEL PRODUCTO YA CADUCO O ESTA PROXIMO A CADUCAR");
					} else if (ban == 8) {
						JOptionPane.showMessageDialog(null, "EL PRECIO DE COMPRA NO DEBE DE ESTAR VACIO NI CERO");
					} else if (ban == 9) {
						JOptionPane.showMessageDialog(null, "EL PRECIO DE VENTA NO DEBE DE ESTAR VACIO NI CERO");
					} else if (ban == 10) {
						JOptionPane.showMessageDialog(null, "EL LOTE NO DEBE DE ESTAR VACIO");
					} else if (ban == 11) {
						JOptionPane.showMessageDialog(null, "EL LOTE EXCEDE LOS 10 CARACTERES");
					}  else if (ban == 0) {
						JOptionPane.showMessageDialog(null, "DETALLE INGRESADO DE FORMA SATISFACTORIA");
						setVisible(false);
						cx.desconectar();
						dispose();
					}
				}
			}
		});
		
		try {
			Image img = ImageIO.read(getClass().getResource("img/comprar.png"));
			okButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cx.desconectar();
				setVisible(false);
				dispose();
			}
		});
		try {
			Image img = ImageIO.read(getClass().getResource("img/delete.png"));
			cancelButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		buttonPane.add(cancelButton);
	}
}

