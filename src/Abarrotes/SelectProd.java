package Abarrotes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.JobAttributes;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;

public class SelectProd extends JDialog {

	private final JPanel contentPanel = new JPanel();
	Conexion cx = new Conexion();
	String seleccion = "", idP[];
	
	public SelectProd(String title, String accion, String cve) {
		setBounds(100, 100, 219, 165);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		if (accion.equals("DETCOMPRO")) {  //compras
			seleccion = "CVECOMP";
		}
		
		if (accion.equals("DETVTAPRO")) { //ventas
			seleccion = "CVEVTA";
		}
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Gargi", Font.BOLD, 12));
		comboBox.setBounds(12, 36, 183, 34);
		contentPanel.add(comboBox);

		try {
			ArrayList<String> getId = cx.getIdP("SELECT PRODUCTOS.CVEPROD,PRODUCTOS.NOMP FROM PRODUCTOS INNER JOIN " + accion + " ON PRODUCTOS.CVEPROD"
					+ " = " + accion + ".CVEPROD WHERE "+accion+"."+seleccion+" = "+cve);
			System.out.println(getId);
			for (int i = 0; i < getId.size(); i++) {
				comboBox.addItem(getId.get(i)+"");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "NO HAY PRODUCTOS CARGADOS");
		}
		
		JLabel lblSeleccionaUnProveedor = new JLabel(title);
		lblSeleccionaUnProveedor.setFont(new Font("Gargi", Font.BOLD, 12));
		lblSeleccionaUnProveedor.setBounds(12, 9, 195, 15);
		contentPanel.add(lblSeleccionaUnProveedor);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton();
		try {
			Image img = ImageIO.read(getClass().getResource("img/delete.png"));
			okButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				idP = (comboBox.getSelectedItem()+"").split(" ");
				if (accion.equals("DETCOMPRO")) { // compras
					try {
						int ban;
						ban = cx.bajaIndividualCompra(Integer.parseInt(cve), Integer.parseInt(idP[0]));
						if (ban == 1) {
							JOptionPane.showMessageDialog(null, "EXISTENCIAS INSUFICIENTES");
						} else if (ban == 2) {
							JOptionPane.showMessageDialog(null, "ESE DETALLE NO EXISTE");
						} else if (ban == 0) {
							JOptionPane.showMessageDialog(null, "PRODUCTO REMOVIDO DE FORMA SATISFACTORIA");
							setVisible(false);
							dispose();
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR!");
					}
				}
				if (accion.equals("DETVTAPRO")) { // ventas
					try {
						try {
							cx.bajaIndividualVenta(Integer.parseInt(cve), Integer.parseInt(idP[0]));
							JOptionPane.showMessageDialog(null, "PRODUCTO REMOVIDO DE FORMA SATISFACTORIA");
							setVisible(false);
							dispose();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR!");
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR!");
					}
				}
			}
		});

		JButton cancelButton = new JButton("Volver");
		try {
			Image img = ImageIO.read(getClass().getResource("img/regreso.png"));
			cancelButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
	}
}

