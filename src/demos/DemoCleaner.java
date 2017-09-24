package demos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DemoCleaner {
	private String path;
	private Long cantidadDemosTotal;
	private List<Demo> listaDeDemosAMantener;
	private List<Demo> listaDeDemosParaEliminar;
	
	//Constructor
	public DemoCleaner(String path){
		this.path = path;
		this.cantidadDemosTotal = 0L;
		this.listaDeDemosAMantener = new LinkedList<Demo>();
		this.listaDeDemosParaEliminar = new LinkedList<Demo>();
	}
	
	//Setters & Getters
	public String getPath(){
		return path;
	}
	
	public Long getCantidadDemosTotal(){
		return cantidadDemosTotal;
	}

	public List<Demo> getListaDemosAMantener(){
		return listaDeDemosAMantener;
	}
	
	public List<Demo> getListaDeDemosParaEliminar(){
		return listaDeDemosParaEliminar;
	}		
	
	//Desde este método se llama a los privados que clasifican los demos
	public void analizar(){
		this.setListaDeDemosAMantener();
		this.excluirDemosConFormatoInvalido();
		this.mantenerDemosConElMejorTiempo();		
	}
	
	//Métodos privados para el manejo de demos
	private void setListaDeDemosAMantener(){			
		try {
			File[] demoList;
			demoList = this.getDemos();
			for(int i=0;i<demoList.length;i++){
				if(demoList[i].isFile())
					listaDeDemosAMantener.add(new Demo(demoList[i].getName()));	
			}
			this.cantidadDemosTotal = (long)listaDeDemosAMantener.size();	
			System.out.println("Demos encontrados: " + cantidadDemosTotal);
		} catch (FileNotFoundException e) {			
			e.getMessage();
			e.printStackTrace();
		}
	}
	
	//Método auxiliar para 'setListaDeDemosCompleta()'
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
		Long demoValido = (long)0;
		Long demoInvalido = (long)0;
		for (Demo demo : listaDeDemosAMantener) {
			if(demo.getNombreCompleto().equals("DEMO WITH INVALID FORMAT")){
				listaDeDemosParaEliminar.add(demo);
				demoInvalido++;				
			}
			else demoValido++;			
		}
		
		listaDeDemosAMantener.removeAll(listaDeDemosParaEliminar);
		System.out.println("Demos con formato valido: " 	+ 	demoValido);
		System.out.println("Demos con formato invalido: " 	+ 	demoInvalido);
	}
	
	/*Ahora voy a verificar los mapas repetidos para quitar los tiempos que no 
	 * sean los mejores. Uso listaDeDemosCompleta al ser la que ya tiene 
	 * solamente los demos con formato valido*/
	private void mantenerDemosConElMejorTiempo(){
		for (Demo demoA : listaDeDemosAMantener) {
			for (Demo demoB : listaDeDemosAMantener) {
				if((!(demoA.getNombreCompleto().equals(demoB.getNombreCompleto()))) &&
						demoA.getNombreMapa().equals(demoB.getNombreMapa()) &&
						demoA.getModo().equals(demoB.getModo()) &&
						this.esMejorTiempo(demoA.getTiempo(), demoB.getTiempo()))
					listaDeDemosParaEliminar.add(demoB);													
			}
		}
		listaDeDemosAMantener.removeAll(listaDeDemosParaEliminar);
		System.out.println("Demos a mantener: " + listaDeDemosAMantener.size());
		System.out.println("Demos a eliminar: " + listaDeDemosParaEliminar.size());
	}
	
	/*Método privado que compara 2 tiempos para ver cual es el menor
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
		 * ser menor 's1' que 's2' (ambos 0 en esta iteración), se comprueba si 
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
		
		/*Aca no se debería llegar normalmente, pero si el primer String es mas 
		 * largo que el segundo se retorna falso. Verdadero en caso contrario*/		
		if(s1.hasNext()){
			s1.close();s2.close();
			return false;
		}
		s1.close();s2.close();
		return true;
	}
	
	//Método que confirma la eliminación de los demos seleccionados
	public void eliminarDemos(){
		if(listaDeDemosParaEliminar.isEmpty())
			System.out.println("No hay demos para eliminar.");
		//Pendiente
	}	
}