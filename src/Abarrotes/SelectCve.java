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

public class SelectCve extends JDialog {

	private final JPanel contentPanel = new JPanel();
	Conexion cx = new Conexion();

	public SelectCve(String title) {
		setBounds(100, 100, 219, 165);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Gargi", Font.BOLD, 12));
		comboBox.setBounds(12, 36, 183, 34);
		contentPanel.add(comboBox);

		try {
			ArrayList<Integer> getId = cx.getId("SELECT CVEPROV FROM PROVEEDORES");
			ArrayList<String> getPName = cx.getPName("SELECT NOMPROV FROM PROVEEDORES");
			for (int i = 0; i < getId.size(); i++) {
				comboBox.addItem(getId.get(i) +  " "  + getPName.get(i) );
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "NO HAY PROVEEDORES CARGADOS");
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
			Image img = ImageIO.read(getClass().getResource("img/bolsa.png"));
			okButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int ban[];
					ban = cx.Comauto(((Integer)comboBox.getSelectedIndex())+1);
					if (ban[0] == 1) {
						JOptionPane.showMessageDialog(null, "LA CLAVE NO DEBE ESTAR VACÍA O SER 0");
					}else if(ban[0] == 2) {
						JOptionPane.showMessageDialog(null, "LA CLAVE DEL PROVEEDOR NO EXISTE");
					}else if(ban[0] == 3) {
						JOptionPane.showMessageDialog(null, "ESTE PROVEEDOR ESTA INACTIVO O DADO DE BAJA");
					}else if (ban[0] == 0) {
						JOptionPane.showMessageDialog(null, "COMPRA INSERTADA DE FORMA SATISFACTORIA");
						Compras v = new Compras(ban[1]);
						v.setVisible(true);
						setVisible(false);
						dispose();
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR!");
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

