package Abarrotes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.JobAttributes;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

public class DectVenpro extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField cantidad;
	private JTextField cVen;
	Conexion cx = new Conexion();
	private JTextField idprod;
	
	public java.sql.Date Conversor(java.util.Date dato) {
		return new java.sql.Date(dato.getTime());
	}

	public DectVenpro(int cVenv) {
		setBounds(100, 100, 450, 197);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbid = new JLabel("ID Venta:");
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
		
		cantidad = new JTextField();
		cantidad.setFont(new Font("Gargi", Font.PLAIN, 12));
		cantidad.setColumns(10);
		cantidad.setBounds(128, 87, 310, 25);
		contentPanel.add(cantidad);
		
		cVen = new JTextField();
		cVen.setFont(new Font("Gargi", Font.PLAIN, 12));
		cVen.setEnabled(false);
		cVen.setColumns(10);
		cVen.setBounds(128, 19, 310, 25);
		cVen.setText(cVenv+"");
		contentPanel.add(cVen);
		
		idprod = new JTextField();
		idprod.setFont(new Font("Gargi", Font.PLAIN, 12));
		idprod.setColumns(10);
		idprod.setBounds(128, 53, 310, 25);
		contentPanel.add(idprod);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<String> elementos = new ArrayList<String>();
					elementos.add(cVen.getText());
					if (!idprod.getText().isEmpty())
						elementos.add(idprod.getText());
					else
						elementos.add("0");
					if (!cantidad.getText().isEmpty())
						elementos.add(cantidad.getText());
					else
						elementos.add("0");
					int ban = cx.AltaDetVent(elementos);
					if (ban == 1) {
						JOptionPane.showMessageDialog(null, "LA CVE DE LA VENTA ESTA VACIA");
					} else if (ban == 2) {
						JOptionPane.showMessageDialog(null, "LA CVE DE LA VENTA NO EXISTE, MODIFICALA");
					} else if (ban == 3) {
						JOptionPane.showMessageDialog(null, "LA CVE DEL PRODUCTO NO DEBE ESTAR VACIA");
					} else if (ban == 4) {
						JOptionPane.showMessageDialog(null, "LA CVE DEL PRODUCTO NO EXISTE, MODIFICALA");
					} else if (ban == 5) {
						JOptionPane.showMessageDialog(null, "LA CANTIDAD VENDIDA ESTA VACIA O ES MENOR A 1");
					} else if (ban == 6) {
						JOptionPane.showMessageDialog(null, "ESTE PRODUCTO HA SIDO DADO DE BAJA");
					} else if (ban == 7) {
						JOptionPane.showMessageDialog(null,
								"LA CANTIDAD SOLICITADA EXCEDE LAS EXISTENCIAS DE ESTE PRODUCTO");
					} else if (ban == 8) {
						JOptionPane.showMessageDialog(null, "LA CANTIDAD SOLICITADA EXCEDE LAS EXISTENCIAS DE ESTE PRODUCTO");
					} else if (ban == 0) {
						JOptionPane.showMessageDialog(null, "DETALLE INGRESADO DE FORMA SATISFACTORIA");
						setVisible(false);
						cx.desconectar();
						dispose();
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "ERROR DE INSERCION");
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
