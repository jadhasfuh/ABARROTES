package Abarrotes;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class Ver extends JDialog {

	private JTable table;
	ResultSet rs;
	String tit;
	Conexion cx = new Conexion();
	
	public Ver(JFrame parent, boolean modal, ResultSet r, String title) {
		super(parent, modal);
		rs = r;
		tit = title;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 720, 640);
		setLayout(new BorderLayout());
		JLabel lblNewLabel = new JLabel(tit);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/tienda.png")));
		lblNewLabel.setFont(new Font("Gargi", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		table.setFont(new Font("Gargi", Font.PLAIN, 16));
		table.setRowHeight(24);
		
		JScrollPane scrollPane = new JScrollPane(table); 
		scrollPane.setFont(new Font("Gargi", Font.PLAIN, 16));
		try {
			resultSetToTableModel(rs, table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JButton vol = new JButton();
		vol.setText("Volver");
		try {
			Image img = ImageIO.read(getClass().getResource("img/regreso.png"));
			vol.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		vol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		getContentPane().add(vol, BorderLayout.SOUTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
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
}
