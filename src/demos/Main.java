package demos;

public class Main {
	public static void main(String[] args) {
		String pathDado = "C:\\Documents and Settings\\Administrador\\Datos de programa\\Quake3\\defrag\\demos\\Demos movie 2016";
		DemoCleaner d = new DemoCleaner(pathDado.replace("\\", "/"));	
		d.analizar();
		d.info();		
		/*NO DESCOMENTAR HASTA TERMINAR EL TEST Y AGREGAR ALGO PARA QUE EL 
		 * USUARIO INSERTE EL PATH*/		
		//d.eliminarDemos();
	}
}