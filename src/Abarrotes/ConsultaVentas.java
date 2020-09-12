package Abarrotes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTable.PrintMode;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ConsultaVentas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JLabel lblTotal;
	private int cveC;
	private JComboBox<Integer> cves;
	private JLabel lblNewLabel, lblTotal_1 ;
	Conexion cx = new Conexion();
	
	public void resultSetToTableModel(ResultSet rs, JTable table) throws SQLException{
        DefaultTableModel tableModel = new DefaultTableModel();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++){
            tableModel.addColumn(metaData.getColumnLabel(columnIndex));
        }
        Object[] row = new Object[columnCount];
        while (rs.next()){
            for (int i = 0; i < columnCount; i++){
                row[i] = rs.getObject(i+1);
            }
            tableModel.addRow(row);
        }
        table.setModel(tableModel);
    }
	
	public ConsultaVentas() {
		setBounds(100, 100, 720, 480);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		table.setFont(new Font("Gargi", Font.PLAIN, 16));
		table.setRowHeight(24);

		try {
			ResultSet rs = cx.FillTable("SELECT PRODUCTOS.NOMP, DETVTAPRO.CANTVP, PRODUCTOS.PRECIOVTAP, (DETVTAPRO.CANTVP*PRODUCTOS.PRECIOVTAP) FROM VENTAS INNER JOIN (DETVTAPRO INNER JOIN PRODUCTOS "
					+ "ON DETVTAPRO.CVEPROD = PRODUCTOS.CVEPROD) ON VENTAS.CVEVTA = DETVTAPRO.CVEVTA WHERE VENTAS.CVEVTA = "+cveC);
			resultSetToTableModel(rs, table);
			table.getColumnModel().getColumn(3).setHeaderValue("SUBTOTAL");
		} catch (SQLException e) {}
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setFont(new Font("Gargi", Font.PLAIN, 16));
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		JButton add = new JButton();
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cveC = (int) cves.getSelectedItem();
				lblNewLabel.setText("VENTA #" + cveC);
				try {
					ResultSet rs = cx.FillTable("SELECT PRODUCTOS.NOMP, DETVTAPRO.CANTVP, PRODUCTOS.PRECIOVTAP, (DETVTAPRO.CANTVP*PRODUCTOS.PRECIOVTAP) FROM VENTAS INNER JOIN (DETVTAPRO INNER JOIN PRODUCTOS "
							+ "ON DETVTAPRO.CVEPROD = PRODUCTOS.CVEPROD) ON VENTAS.CVEVTA = DETVTAPRO.CVEVTA WHERE VENTAS.CVEVTA = "+cveC);
					resultSetToTableModel(rs, table);
					table.getColumnModel().getColumn(3).setHeaderValue("SUBTOTAL");
					String b = cx.getSubtotal(cveC);
					String c = cx.getAhorro(cveC);
					lblTotal.setText(" TOTAL: " + b);
					lblTotal_1.setText(" PARA AHORRO: "+ c);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		try {
			Image img = ImageIO.read(getClass().getResource("img/bigbus.png"));
			add.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add.setOpaque(false);
		add.setContentAreaFilled(false);
		add.setBorderPainted(false);
		contentPanel.add(add, BorderLayout.EAST);

		lblNewLabel = new JLabel("VENTA #");
		lblNewLabel.setFont(new Font("Gargi", Font.BOLD, 18));
		contentPanel.add(lblNewLabel, BorderLayout.NORTH);

		JPanel zona = new JPanel(new GridLayout(2, 1));
		getContentPane().add(zona, BorderLayout.SOUTH);

		String b = cx.getTotal(cveC);
		lblTotal = new JLabel(" TOTAL: " + b);
		lblTotal.setFont(new Font("Gargi", Font.BOLD, 18));
		zona.add(lblTotal);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		zona.add(buttonPane, BorderLayout.SOUTH);

		JLabel lbconsulta = new JLabel();
		lbconsulta.setFont(new Font("Gargi", Font.BOLD, 12));
		lbconsulta.setText("Clave de compra: ");

		cves = new JComboBox<Integer>();
		cves.setFont(new Font("Gargi", Font.BOLD, 12));
		buttonPane.add(lbconsulta);
		buttonPane.add(cves);

		JButton cancelButton = new JButton("Volver");
		cancelButton.setFont(new Font("Gargi", Font.BOLD, 12));
		try {
			Image img = ImageIO.read(getClass().getResource("img/regreso.png"));
			cancelButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		lblTotal_1 = new JLabel(" PARA AHORRO: 0");
		lblTotal_1.setFont(new Font("Gargi", Font.BOLD, 18));
		zona.add(lblTotal_1);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {     
					MessageFormat headerFormat = new MessageFormat("Listado de Compras");
			        MessageFormat footerFormat = new MessageFormat("- Página {0} -");
			        table.print(PrintMode.FIT_WIDTH, headerFormat, footerFormat);
				  } catch (PrinterException ex) {
					  JOptionPane.showMessageDialog(null, "No se ha podido imprimir correctamente, intentalo más tarde.");
				  }  
			}
		});
		btnImprimir.setFont(new Font("Gargi", Font.BOLD, 12));
		try {
			Image img = ImageIO.read(getClass().getResource("img/print.png"));
			btnImprimir.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		zona.add(btnImprimir);
		ArrayList<Integer> getId = cx.getId("SELECT CVEVTA FROM VENTAS");
		for (int i = 0; i < getId.size(); i++) {
			cves.addItem(getId.get(i));
		}
		
	}
}