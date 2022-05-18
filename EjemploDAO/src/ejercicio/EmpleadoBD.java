package ejercicio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoBD {

	public Connection conexion;

	public void conectarBD() {
		String host = "localhost";
		int puerto = 3306;
		String db = "empleado";
		String url = "jdbc:mysql://" + host + ":" + puerto + "/" + db;
		String login = "root";
		String password = "";

		try {
			// Conectamos con la base de datos
			conexion = DriverManager.getConnection(url, login, password);
			System.out.println((conexion != null) ? "CONEXION OK" : "CONEXION FALLIDA");

		} catch (java.sql.SQLException sqle) {
			System.out.println("Error: " + sqle);
		}
	}

	public ResultSet consultarEmpleados(String consulta) {
		ResultSet resultado;
		try {
			PreparedStatement sentencia = conexion.prepareStatement(consulta);
			resultado = sentencia.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return resultado;
	}

	public List<Empleado> getEmpleados() {
		List<Empleado> lista = null;
		try {
			PreparedStatement st = conexion.prepareStatement("SELECT * from empleado");
			lista = new ArrayList();
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Empleado emp = new Empleado();
				emp.setDni(rs.getString("dni"));
				emp.setNombre(rs.getString("nombre"));
				emp.setApellidos(rs.getString("apellidos"));
				emp.setSueldo(rs.getInt("sueldo"));
				lista.add(emp);
			}
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public boolean actualizar(int dniP, int sueldoP) {
		try {
			PreparedStatement sentencia = conexion
					.prepareStatement("update empleado set sueldo = " + sueldoP + " where dni=" + dniP);
			int totalRows = sentencia.executeUpdate(); // devuelve el número de tuplas modificadas
			System.out.println("Se han modificado: " + totalRows + " tuplas");
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean borrar(int dniP) {
		try {
			PreparedStatement sentencia = conexion.prepareStatement("DELETE FROM empleado  WHERE dni =" + dniP);
			int totalRows = sentencia.executeUpdate(); // devuelve el número de tuplas modificadas
			System.out.println("Se han eliminado: " + totalRows + " tuplas");
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Empleado buscar(int dniP) {
		Empleado empBuscado = null;
		try {
			PreparedStatement sentencia = conexion.prepareStatement("select* FROM empleado  WHERE dni =" + dniP);
			empBuscado = (Empleado) sentencia;
			int totalRows = sentencia.executeUpdate(); // devuelve el número de tuplas modificadas
			System.out.println("Se han encontrado: " + totalRows + " tuplas");
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empBuscado;
	}

	public boolean insertar(String valores) {
		try {
			PreparedStatement sentencia = conexion.prepareStatement(valores);
			int totalRows = sentencia.executeUpdate(); // devuelve el número de tuplas modificadas
			System.out.println("Se han insertado: " + totalRows + " tuplas");
			sentencia.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void imprimirResultadoEmp(ResultSet resultado) {
		try {
			System.out.println("DNI\tNOMBRE\tAPELLIDOS\tSALARIO");
			System.out.println("-------------------------------------------------------");
			while (resultado.next()) {
				System.out.println(resultado.getString("dni") + "\t" + resultado.getString("nombre") + "\t"
						+ resultado.getString("apellidos") + "\t" + resultado.getInt("sueldo") + "\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
