package demos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DemoCleaner {
	private String path;
	private int cantidadDemosTotal;
	private Set<Demo> listaDeDemosAMantener;
	private Set<Demo> listaDeDemosParaEliminar;
	private boolean analisisCompleto;
	
	//Constructor
	public DemoCleaner(String path){
		this.path = path;
		this.cantidadDemosTotal = 0;
		this.listaDeDemosAMantener = new HashSet<Demo>();
		this.listaDeDemosParaEliminar = new HashSet<Demo>();
		this.analisisCompleto = false;
	}
	
	//Setters & Getters
	public String getPath(){
		return path;
	}
	
	public int getCantidadDemosTotal(){
		return cantidadDemosTotal;
	}

	public Set<Demo> getListaDemosAMantener(){
		return listaDeDemosAMantener;
	}
	
	public Set<Demo> getListaDeDemosParaEliminar(){
		return listaDeDemosParaEliminar;
	}	
	
	public String info(){
		if(!analisisCompleto)
			return ">ERROR!!: Debe realizarse el análisis primero.";
		else{
			return  "\n>Total de demos encontrados: " + cantidadDemosTotal +
					"\n>Total de demos a mantener: " + listaDeDemosAMantener.size() + 
					"\n>Total de demos a eliminar: " + listaDeDemosParaEliminar.size();
		}
	}
	
	//Desde este metodo se llama a los privados que clasifican los demos
	public void analizar(){
		this.setListaDeDemosAMantener();
		this.excluirDemosConFormatoInvalido();
		this.mantenerDemosConElMejorTiempo();
		analisisCompleto = true;
	}
	
	//Metodos privados para el manejo de demos
	private void setListaDeDemosAMantener(){			
		try {
			File[] demoList;
			demoList = this.getDemos();
			for(int i=0;i<demoList.length;i++){
				if(demoList[i].isFile())
					listaDeDemosAMantener.add(new Demo(demoList[i].getName()));	
			}
			this.cantidadDemosTotal = listaDeDemosAMantener.size();	
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
	}
	
	//Metodo auxiliar para 'setListaDeDemosCompleta()'
	private File[] getDemos() throws FileNotFoundException{
		if(!new File(path).exists())
			throw new FileNotFoundException("No se encontro la ruta");
		return new File(path).listFiles(
					new FilenameFilter(){
						@Override
						public boolean accept(File dir, String filename){
							return filename.endsWith(".dm_68");
						}
					}
			);
	}
	
	private void excluirDemosConFormatoInvalido(){
		for (Demo demo : listaDeDemosAMantener)
			if(demo.getNombreCompleto().equals("DEMO WITH INVALID FORMAT"))
				listaDeDemosParaEliminar.add(demo);	
		
		listaDeDemosAMantener.removeAll(listaDeDemosParaEliminar);
	}
	
	/*Ahora voy a verificar los mapas repetidos para quitar los tiempos que no 
	 * sean los mejores. Uso esta lista al ser la que ya tiene solamente los demos 
	 * con formato valido*/
	private void mantenerDemosConElMejorTiempo(){
		for (Demo demoA : listaDeDemosAMantener)
			for (Demo demoB : listaDeDemosAMantener)
				if((!(demoA.getNombreCompleto().equals(demoB.getNombreCompleto()))) &&
						demoA.getNombreMapa().equals(demoB.getNombreMapa()) &&
						demoA.getModo().equals(demoB.getModo()) &&
						this.esMejorTiempo(demoA.getTiempo(), demoB.getTiempo()))
					listaDeDemosParaEliminar.add(demoB);

		listaDeDemosAMantener.removeAll(listaDeDemosParaEliminar);
	}
	
	/*Metodo privado que compara 2 tiempos para ver cual es el menor
	 * (Revisar luego como evitar no repetir mucho el cierre de los Scanners)*/
	private boolean esMejorTiempo(String tiempoA, String tiempoB){
		int t1;
		int t2;
		Scanner s1 = new Scanner(tiempoA);
		Scanner s2 = new Scanner(tiempoB);
		s1.useDelimiter("\\.");
		s2.useDelimiter("\\.");
		/*El delimiter dividio el String en partes. Suponiendo que...
		 * 'tiempoA' = "00.09.600", 'tiempoB' = "00.09.760"...
		 * 's1' tendra 0, 9 y 600. 's2' tendra 0, 9 y 760.
		 * Se va iterando y se compara. Primero se comparan los dos ceros. Al no 
		 * ser menor 's1' que 's2' (ambos 0 en esta iteracion), se comprueba si 
		 * 's2' es mayor (Esto para asegurarse de que s1 y s2 sean iguales)*/
		while(s1.hasNext() && s2.hasNext()){
			t1 = s1.nextInt();
			t2 = s2.nextInt();
			if(t1 < t2){
				s1.close();s2.close();
				return true;			
			}
			else if(t1 > t2){
				s1.close();s2.close();
				return false;				
			}
		}
		
		/*Aca no se deberia llegar normalmente, pero si el primer String es mas 
		 * largo que el segundo se retorna falso. Verdadero en caso contrario*/		
		if(s1.hasNext()){
			s1.close();s2.close();
			return false;
		}
		s1.close();s2.close();
		return true;
	}
	
	//Metodo que confirma la eliminacion de los demos seleccionados
	public void eliminarDemos(){
		if(listaDeDemosParaEliminar.isEmpty() || !analisisCompleto)
			System.out.println("No hay demos para eliminar o no se realizo analisis.");
		else{
			//Este metodo corre si o si despues del analisis, no necesito try-catch
			File listaDeArchivos = new File(path);
			for (File file : listaDeArchivos.listFiles()) {
				Demo d = new Demo(file.getName());				
				if(listaDeDemosParaEliminar.contains(d) &&
						(!d.getNombreCompleto().equals("DEMO WITH INVALID FORMAT")))
					file.delete();
			}
		}		
	}	
}