package Abarrotes;


import java.sql.DriverManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.io.OutputStream;
import java.security.CodeSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
	Connection cx;
	String url = "jdbc:mariadb://localhost:3306/"+bd;
	static String user = "root";
	static String pass = "password";
	static String bd = "Abarrotes";
	
	///////////////////////////////////////////////////////////
	/////                   BAJA INDIVIDUAL         ///////////
	///////////////////////////////////////////////////////////
	public int bajaIndividualVenta(int cvev, int cvep) {
		conectar();
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL BAJADETPROD(?,?)");
			cst.setInt(1, cvev);
			cst.setInt(2, cvep);
			cst.execute();
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
			return ban;
	}
	public int bajaIndividualCompra(int cvev, int cvep) {
		conectar();
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL BAJADETCOMPRO(?,?,?)");
			cst.setInt(1, cvev);
			cst.setInt(2, cvep);
			cst.registerOutParameter(3, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(3);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
			return ban;
	}
	///////////////////////////////////////////////////////////
	/////                   RESPALDO	            ///////////
	///////////////////////////////////////////////////////////
	public void backup(String url) {
		   try {
		      Process p = Runtime
		            .getRuntime()
		            .exec("mysqldump -u root -ppassword Abarrotes");
		      InputStream is = p.getInputStream();
		      FileOutputStream fos = new FileOutputStream(url);
		      byte[] buffer = new byte[1000];
		      int leido = is.read(buffer);
		      while (leido > 0) {
		         fos.write(buffer, 0, leido);
		         leido = is.read(buffer);
		      }
		      fos.close();
		   } catch (Exception e) {
		      e.printStackTrace();
		   }
		}
	public void restore(String url) {
		   try {
		      Process p = Runtime
		            .getRuntime()
		            .exec("mysql -u root -ppassword Abarrotes");
		      OutputStream os = p.getOutputStream();
		      FileInputStream fis = new FileInputStream(url);
		      byte[] buffer = new byte[1000];
		      int leido = fis.read(buffer);
		      while (leido > 0) {
		         os.write(buffer, 0, leido);
		         leido = fis.read(buffer);
		      }
		      os.flush();
		      os.close();
		      fis.close();
		   } catch (Exception e) {
		      e.printStackTrace();
		   }
		}

	///////////////////////////////////////////////////////////
	/////                  INICIO DE APP            ///////////
	///////////////////////////////////////////////////////////
	public Connection conectar() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			cx = DriverManager.getConnection(url,user,pass);
			System.out.println("Conexion satisfactoria");
		} catch (ClassNotFoundException | SQLException ex) {
			System.out.println("Fallo de conexion");
		}
		return cx; 
	}
	public void desconectar() {
		conectar();
		try {
			cx.close();
			System.out.println("Conexion satisfactoriamente cerrada");
		} catch (SQLException e) {
			System.out.println("Sesion no cerrada");
		}
	}
	
	///////////////////////////////////////////////////////////
	/////                   INICIO DE SESION        ///////////
    ///////////////////////////////////////////////////////////
	public String contra(String usuario) {
		String v = "";
		conectar();
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT CONTRASENA FROM USUARIOS WHERE NUSUARIO = \"" + usuario + " \"");
			if (res.next()) {
				v = res.getString(1);
				return v;
			} else {
				v = "";
				return null;
			}
		} catch (SQLException e) {
			System.out.println("No hay contrasena");
			v = "";
		}
		return v;
	}	
	public String getNombre() {
		String nombre = "";
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT NUSUARIO FROM USUARIOS WHERE SESION = 1");
			if (res.next()) {
				nombre = res.getString(1);
				return nombre;
			} else {
				nombre = "";
				return null;
			}
		} catch (SQLException e) {
			System.out.println("No hay contrasena");
			nombre = "";
		}
		return nombre;
	}
	public int getCvemp(String nom) {
		conectar();
		int cve = 0;
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT EMPLEADOS.CVEMP FROM EMPLEADOS INNER JOIN USUARIOS WHERE EMPLEADOS.CVEMP = USUARIOS.CVEMP AND NUSUARIO LIKE \"" + nom+"\"");
			if (res.next()) {
				cve = res.getInt(1);
				return cve;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			System.out.println("EL USUARIO NO RETORNA UNA CLAVE");
		}
		return cve;
	}
	
	public void IniciarSecion(String user) {
		String sentencia =("UPDATE  USUARIOS SET SESION = 1 WHERE NUSUARIO = ?");
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) cx.prepareStatement(sentencia);
			ps.setString(1, user);
			if (ps.executeUpdate() != 0)
				System.out.println("Sesion Iniciada");
		} catch (SQLException e) {
			System.out.println("Error al actualizar registro");
		}
	}
	public void CerrarSesion() {
		String sentencia =("UPDATE  USUARIOS SET SESION = 0");
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) cx.prepareStatement(sentencia);
			if (ps.executeUpdate() != 0)
				System.out.println("Sesion Finalizada");
		} catch (SQLException e) {
			System.out.println("Error al actualizar registro");
		}
	}

	///////////////////////////////////////////////////////////
	///// 			CONSULTAS DE TODO 				///////////
	///////////////////////////////////////////////////////////
	public ResultSet FillTable(String Query) {
		conectar();
		ResultSet rs = null;
		try {
			Statement stat = (Statement) cx.prepareStatement(Query);
			rs = stat.executeQuery(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public Vector consultaProductos(int num) {
		Vector v = new Vector();
		String mens = "";
		conectar();
		try {
			Statement s = (Statement) cx.createStatement();
			mens = "SELECT NOMP, DESCRIP, PRECIOCOMP, PRECIOVTAP, EXISTENCIASP, ESTADO "
						+ "FROM PRODUCTOS "
						+ "WHERE CVEPROD = " + num;
			ResultSet res = (ResultSet) s.executeQuery(mens);
			if (res.next()) {
				v.add(res.getString(1));
				v.add(res.getString(2));
				v.add(res.getFloat(3));
				v.add(res.getFloat(4));
				v.add(new Integer(res.getInt(5)));
				v.add(new Integer(res.getInt(6)));
				return v;

			} else {
				System.out.println("Fue nulo");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta");
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////
	/////              BAJA DE PRODUCTOS            ///////////
	///////////////////////////////////////////////////////////
	public Vector consultaProducto(int num) {
		conectar();
		Vector v = new Vector();
		String mens = "SELECT * FROM PRODUCTOS WHERE CVEPROD = " + num;
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery(mens);
			if (res.next()) {
				v.add(res.getString(2));
				v.add(res.getString(3));
				v.add(new Float(res.getFloat(4)));
				v.add(new Float(res.getFloat(5)));
				v.add(new Integer(res.getInt(6)));
				v.add(new Integer(res.getInt(7)));
				return v;
			} else {
				System.out.println("Consulta nula");
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta");
		}
		return null;
	}
	public int BajaProducto(int cve) {
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL BAJPROD(?,?)");

			cst.setInt(1, cve);
			cst.registerOutParameter(1, java.sql.Types.INTEGER);

			cst.execute();

			ban = cst.getInt(1);

		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	
	///////////////////////////////////////////////////////////
	/////              EDIT DE PRODUCTOS            ///////////
	///////////////////////////////////////////////////////////
	public int ModifProd(String datos[]) {
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL MODDESPROD(?,?,?,?)");

			cst.setInt(1, Integer.parseInt(datos[0]));
			cst.setString(2, datos[1]);
			cst.setString(3, datos[2]);
			cst.registerOutParameter(4, java.sql.Types.INTEGER);

			cst.execute();

			ban = cst.getInt(4);

		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	
	///////////////////////////////////////////////////////////
	/////              INSERTAR PRODUCTO            ///////////
	///////////////////////////////////////////////////////////
	public String getMaxProd() {
		String clave = "";
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT MAX(CVEPROD) FROM PRODUCTOS");
			if (res.next()) {
				clave = res.getString(1);
				boolean bool = res.wasNull();
				if (!bool) {
					clave = (Integer.parseInt(clave)+1)+"";
				}else {
					clave = "1000";
				}
				return clave;
			} else {
				clave = "1000";
				return clave;
			}
		} catch (SQLException e) {
			System.out.println("Error de Insercion");
		}
		return clave;
	}
	public int AltaProducto(String datos[]) {
		int ban = 0;
		try {
            CallableStatement cst = cx.prepareCall("CALL PRODAUTO(?,?,?,?)" );

            cst.setString(1, datos[0]);
            cst.setString(2, datos[1]);
            cst.setInt(3, Integer.parseInt(datos[2]));
            cst.registerOutParameter(4, java.sql.Types.INTEGER);
                
            cst.execute();
            
            ban = cst.getInt(4);

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	
	///////////////////////////////////////////////////////////
	/////              BAJA DE PROVEEDOR            ///////////
	///////////////////////////////////////////////////////////
	public ArrayList<String> getIdP(String mens) {
		ArrayList<String> a = new ArrayList<String> ();
		String lolo = "";
		int i = 0;
		conectar();
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery(mens);
			while (res.next()){  
				lolo = "";
				lolo = lolo + res.getInt(1)+" ";
				lolo = lolo + res.getString(2);
				if (a.size()>0) {
					if (!lolo.equals(a.get(i-1))) {
						a.add(lolo);
					}
				}else {
					a.add(lolo);
				}
				i++;
            }  
			return a;
		} catch (SQLException e) {
			System.out.println("Error en la consulta");
		}
		return null;
	}
	public ArrayList<Integer> getId(String mens) {
		ArrayList<Integer> a = new ArrayList<Integer> ();
		conectar();
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery(mens);
			while (res.next()){  
				a.add(new Integer(res.getInt(1)));
            }  
			return a;
		} catch (SQLException e) {
			System.out.println("Error en la consulta");
		}
		return null;
	}
	public ArrayList<String> getPName(String mens) {
		ArrayList<String> a = new ArrayList<String> ();
		conectar();
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery(mens);
			while (res.next()){  
				a.add(res.getString(1));
            }  
			return a;
		} catch (SQLException e) {
			System.out.println("Error en la consulta");
		}
		return null;
	}
	public Vector consultaProveedor(int num) {
		conectar();
		Vector v = new Vector();
		String mens = "SELECT * FROM PROVEEDORES WHERE CVEPROV = " + num;
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery(mens);
			if (res.next()){  
				v.add(res.getString(2));
				v.add(res.getString(3));
				v.add(res.getString(4));
				v.add(res.getString(5));
				v.add(res.getString(6));
				v.add(res.getString(7));
				v.add(res.getString(8));
				v.add(res.getString(9));
				v.add(res.getString(10));
				v.add(new Integer(res.getInt(11)));
				return v;
			}else {
				System.out.println("Consulta nula");
			}
		} catch (SQLException e) {
			System.out.println("Error en la consulta");
		}
		return null;
	}
	public int BajaProvedor(int cve) {
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL BAJAPROV(?,?)");

			cst.setInt(1, cve);
			cst.registerOutParameter(1, java.sql.Types.INTEGER);

			cst.execute();

			ban = cst.getInt(1);

		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	
	///////////////////////////////////////////////////////////
	/////              INSERTAR PROVEEDOR           ///////////
	///////////////////////////////////////////////////////////
	public String getMaxProv() {
		String clave = "";
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT MAX(CVEPROD) FROM PROVEEDORES");
			if (res.next()) {
				clave = res.getString(1);
				boolean bool = res.wasNull();
				if (!bool) {
					clave = (Integer.parseInt(clave) + 1) + "";
				} else {
					clave = "1";
				}
				return clave;
			} else {
				clave = "1";
				return clave;
			}
		} catch (SQLException e) {
			System.out.println("Error de Insercion");
		}
		return clave;
	}
	public int AltaProvedor(String datos[]) {
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL PROVAUTO(?,?,?,?,?,?,?,?,?,?)");
			cst.setString(1, datos[0]);
			cst.setString(2, datos[1]);
			cst.setString(3, datos[2]);
			cst.setString(4, datos[3]);
			cst.setString(5, datos[4]);
			cst.setString(6, datos[5]);
			cst.setString(7, datos[6]);
			cst.setString(8, datos[7]);
			cst.setString(9, datos[8]);
			cst.registerOutParameter(10, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(10);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	
	///////////////////////////////////////////////////////////
	/////              EDIT DE PROVEEDOR            ///////////
	///////////////////////////////////////////////////////////
	public int ModifProvedor(String datos[]) {
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL MODPROV(?,?,?,?,?,?,?,?,?,?,?)");
			cst.setInt(1, Integer.parseInt(datos[0]));
			cst.setString(2, datos[1]);
			cst.setString(3, datos[2]);
			cst.setString(4, datos[3]);
			cst.setString(5, datos[4]);
			cst.setString(6, datos[5]);
			cst.setString(7, datos[6]);
			cst.setString(8, datos[7]);
			cst.setString(9, datos[8]);
			cst.setString(10, datos[9]);
			cst.registerOutParameter(11, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(11);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}

	///////////////////////////////////////////////////////////
	///// 				Compras			 			///////////
	///////////////////////////////////////////////////////////
	public int[] Comauto(int cveprov) {
		int ban[] = new int[2];
		ban[0] = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL COMAUTO(?,?)");
			cst.setInt(1, cveprov);
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			cst.execute();
			ban[0] = cst.getInt(2);
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT MAX(CVECOMP) FROM COMPRAS");
			if (res.next()) {
				ban[1] = res.getInt(1);
			}
		}catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	public int BajaComp(int cvecomp) {
		conectar();
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL BAJACOMPRAS(?,?)");
			cst.setInt(1, cvecomp);
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(2);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}
	public int AltaDetComp(ArrayList<String> elementos, Date fecha) {
		conectar();
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL DETCPROD(?,?,?,?,?,?,?,?)");
			cst.setInt(1, Integer.parseInt(elementos.get(0)));
			cst.setInt(2, Integer.parseInt(elementos.get(1)));
			cst.setInt(3, Integer.parseInt(elementos.get(2)));
			cst.setDate(4, (java.sql.Date) fecha);
			cst.setFloat(5, Float.parseFloat(elementos.get(4)));
			cst.setFloat(6, Float.parseFloat(elementos.get(5)));
			cst.setString(7, elementos.get(6));
			cst.registerOutParameter(8, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(8);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}

	public String getTotal(int cve) {
		String clave = "";
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT TOTALCOMP FROM COMPRAS WHERE CVECOMP = " + cve);
			if (res.next()) {
				clave = res.getString(1);
			} else
				clave = "0";
		} catch (SQLException e) {
			System.out.println("Error de Query");
		}
		return clave;
	}

	///////////////////////////////////////////////////////////
	///// 				Ventas			 			///////////
	///////////////////////////////////////////////////////////
	public int[] Venauto(int cvemp) {
		int ban[] = new int[2];
		ban[0] = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL VTAUTO(?,?)");
			cst.setInt(1, cvemp);
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			cst.execute();
			ban[0] = cst.getInt(2);
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT MAX(CVEVTA) FROM VENTAS");
			if (res.next()) {
				ban[1] = res.getInt(1);
			}
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}

	public int BajaVent(int cvent) {
		conectar();
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL BAJAVTAS(?,?)");
			cst.setInt(1, cvent);
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(2);
		} catch (SQLException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		return ban;
	}

	public int AltaDetVent(ArrayList<String> elementos) {
		conectar();
		int ban = 0;
		try {
			CallableStatement cst = cx.prepareCall("CALL DETVPRO(?,?,?,?)");
			cst.setInt(1, Integer.parseInt(elementos.get(0)));
			cst.setInt(2, Integer.parseInt(elementos.get(1)));
			cst.setInt(3, Integer.parseInt(elementos.get(2)));
			cst.registerOutParameter(4, java.sql.Types.INTEGER);
			cst.execute();
			ban = cst.getInt(4);
		} catch (SQLException ex) {
			System.out.println("Error alta: " + ex.getMessage());
		}
		return ban;
	}

	public String getSubtotal(int cve) {
		String clave = "";
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT SUBTOTAL FROM VENTAS WHERE CVEVTA = " + cve);
			if (res.next()) {
				clave = res.getString(1);
			} else
				clave = "0";
		} catch (SQLException e) {
			System.out.println("Error de Query");
		}
		return clave;
	}
	
	public String getAhorro(int cve) {
		String clave = "";
		try {
			Statement s = (Statement) cx.createStatement();
			ResultSet res = (ResultSet) s.executeQuery("SELECT FORAHORRO FROM VENTAS WHERE CVEVTA = " + cve);
			if (res.next()) {
				clave = res.getString(1);
			} else
				clave = "0";
		} catch (SQLException e) {
			System.out.println("Error de Query");
		}
		return clave;
	}
	
}

