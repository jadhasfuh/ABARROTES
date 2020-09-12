package Abarrotes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Ventas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	public int cVen;
	ArrayList<String> Detalles;
	JLabel lblTotal;
	Conexion cx = new Conexion();
	boolean open;
	
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
	
	public Ventas(int cve) {
		cVen = cve;
		open = true;
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
			ResultSet rs = cx.FillTable("SELECT PRODUCTOS.NOMP, DETVTAPRO.CANTVP, PRODUCTOS.PRECIOVTAP, (DETVTAPRO.CANTVP*PRODUCTOS.PRECIOVTAP) FROM VENTAS INNER JOIN (DETVTAPRO INNER JOIN PRODUCTOS"
					+ " ON DETVTAPRO.CVEPROD = PRODUCTOS.CVEPROD) ON VENTAS.CVEVTA = DETVTAPRO.CVEVTA WHERE VENTAS.CVEVTA = "+cVen);
			resultSetToTableModel(rs, table);
			table.getColumnModel().getColumn(3).setHeaderValue("SUBTOTAL");
		} catch (SQLException e) {}
	
		Timer t = new Timer (1000, new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		    	if (open) {
			    	try {
			    		ResultSet rs = cx.FillTable("SELECT PRODUCTOS.NOMP, DETVTAPRO.CANTVP, PRODUCTOS.PRECIOVTAP, (DETVTAPRO.CANTVP*PRODUCTOS.PRECIOVTAP) FROM VENTAS INNER JOIN (DETVTAPRO INNER JOIN PRODUCTOS"
								+ " ON DETVTAPRO.CVEPROD = PRODUCTOS.CVEPROD) ON VENTAS.CVEVTA = DETVTAPRO.CVEVTA WHERE VENTAS.CVEVTA = "+cVen);
						resultSetToTableModel(rs, table);
						table.getColumnModel().getColumn(3).setHeaderValue("SUBTOTAL");
						String b = cx.getSubtotal(cVen);
						lblTotal.setText(" TOTAL: " + b);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		    	}
		     }
		}); 
        t.start();
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setFont(new Font("Gargi", Font.PLAIN, 16));
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel botones = new JPanel(new BorderLayout());
		
		JButton add = new JButton();
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DectVenpro v = new DectVenpro(cVen);
				v.setVisible(true);
			}
		});
		try {
			Image img = ImageIO.read(getClass().getResource("img/badd.png"));
			add.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		add.setOpaque(false);
		add.setContentAreaFilled(false);
		add.setBorderPainted(false);
		
		JButton del = new JButton();
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getRowCount()>0) {
					SelectProd v = new SelectProd("Selecciona un producto", "DETVTAPRO", cVen+"");
					v.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "NO HAY PRODUCTOS CARGADOS");
				}
			}
		});
		try {
			Image img = ImageIO.read(getClass().getResource("img/bdelete.png"));
			del.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		del.setOpaque(false);
		del.setContentAreaFilled(false);
		del.setBorderPainted(false);
		
		botones.add(add,BorderLayout.NORTH);
		botones.add(del,BorderLayout.SOUTH);

		contentPanel.add(botones, BorderLayout.EAST);

		JLabel lblNewLabel = new JLabel("VENTA "+cVen);
		lblNewLabel.setFont(new Font("Gargi", Font.BOLD, 18));
		contentPanel.add(lblNewLabel, BorderLayout.NORTH);

		JPanel zona = new JPanel(new GridLayout(2, 1));
		getContentPane().add(zona, BorderLayout.SOUTH);

		String b = cx.getTotal(cVen);
		lblTotal = new JLabel(" TOTAL: " + b);
		lblTotal.setFont(new Font("Gargi", Font.BOLD, 18));
		zona.add(lblTotal);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		zona.add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getRowCount()>0) {
					JOptionPane.showMessageDialog(null, "VENTA REALIZADA CON EXITO");
					setVisible(false);
					open = false;
					cx.backup("backup.sql");
					cx.desconectar();
					dispose();
				}else {
					JOptionPane.showMessageDialog(null, "NO HA VENDIDO NINGUN ARTICULO");
				}
			}
		});
		try {
			Image img = ImageIO.read(getClass().getResource("img/dinero.png"));
			okButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancelar");
		try {
			Image img = ImageIO.read(getClass().getResource("img/delete.png"));
			cancelButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int ban = cx.BajaVent(cVen);
					if (ban == 1) {
						JOptionPane.showMessageDialog(null, "VENTA INEXISTENTE");
					} else if (ban == 0) {
						JOptionPane.showMessageDialog(null, "VENTA CANCELADA");
						setVisible(false);
						dispose();
					}
					setVisible(false);
					open = false;
					cx.desconectar();
					dispose();
				} catch (Exception e1) {
				}
			}
		});
	}

}

