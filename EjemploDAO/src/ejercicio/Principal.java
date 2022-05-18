package ejercicio;

import java.sql.ResultSet;

public class Principal {
	

	public static void main(String[] args) {
		EmpleadoBD emp = new EmpleadoBD();
		emp.conectarBD();
		String consulta = "SELECT * from empleado";
		String valores=("INSERT INTO empleado values ('5435311A','Juanito','Valderrama',3000),('465744F','	Paco','Perez',2000),('54657843F','Manolo','DelBombo',1000),('46543L','Javier','Cabrerinha',3000)");
		emp.insertar(valores);
		emp.getEmpleados();
		ResultSet resultado = emp.consultarEmpleados(consulta);

		emp.imprimirResultadoEmp(resultado);
	}
}
	
