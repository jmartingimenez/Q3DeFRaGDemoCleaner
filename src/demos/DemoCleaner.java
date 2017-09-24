package demos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;

public class DemoCleaner {
	private String path;
	private Long cantidadDemos;
	private List<Demo> listaDeDemosCompleta;
	private List<Demo> listaDeDemosParaEliminar;
	
	//Constructor
	public DemoCleaner(String path){
		this.path = path;
		this.cantidadDemos = 0L;
		this.listaDeDemosCompleta = new LinkedList<Demo>();
		this.listaDeDemosParaEliminar = new LinkedList<Demo>();
		
		//Métodos privados
		this.setListaDeDemosCompleta();
		this.excluirDemosConFormatoInvalido();
		//this.mantenerDemosConElMejorTiempo();
	}
	
	//Setters & Getters
	public String getPath(){
		return path;
	}
	
	public Long getCantidadDemos(){
		return cantidadDemos;
	}

	public List<Demo> getListaDemosCompleta(){
		return listaDeDemosCompleta;
	}
	
	public List<Demo> getListaDeDemosParaEliminar(){
		return listaDeDemosParaEliminar;
	}		
	
	//Método que confirma la eliminación de los demos seleccionados
	public void deleteDemos(){
		if(listaDeDemosParaEliminar.isEmpty())
			System.out.println("No hay demos para eliminar.");
		else{
			System.out.println("Se eliminaran: " + listaDeDemosParaEliminar.size());
		}
		
	}
	
	//Métodos privados para el manejo de demos
	private void setListaDeDemosCompleta(){			
		try {
			File[] demoList;
			demoList = this.getDemos();
			for(int i=0;i<demoList.length;i++){
				if(demoList[i].isFile())
					listaDeDemosCompleta.add(new Demo(demoList[i].getName()));	
			}
			this.cantidadDemos = (long)listaDeDemosCompleta.size();	
			System.out.println("Cantidad de demos encontrados: " + cantidadDemos);
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
		for (Demo demo : listaDeDemosCompleta) {
			if(demo.getNombreCompleto().equals("DEMO WITH INVALID FORMAT")){
				listaDeDemosParaEliminar.add(demo);
				demoInvalido++;				
			}
			else{
				demoValido++;
				
				//Descomentar para ver los demos validos por consola
				/*System.out.println(listaDeDemosCompleta.get(i).getNombreCompleto());
				System.out.println(listaDeDemosCompleta.get(i).getNombreMapa() + 
						listaDeDemosCompleta.get(i).getModo() + 
						listaDeDemosCompleta.get(i).getTiempo());*/				
			}
		}
		
		listaDeDemosCompleta.removeAll(listaDeDemosParaEliminar);
		System.out.println("Demos validos: " 	+ 	demoValido);
		System.out.println("Demos invalidos: " 	+ 	demoInvalido);
	}
	
	/*En base SOLO a los demos validos, ahora voy a verificar los mapas repetidos 
	 * para quitar los tiempos que no sean los mejores. Uso listaDeDemosCompleta 
	 * al ser la que ya tiene solamente los demos con formato valido*/
	private void mantenerDemosConElMejorTiempo(){
		for(int i = 0; i < listaDeDemosCompleta.size(); i++)
			for(int j = 0; i < listaDeDemosCompleta.size(); j++)
				System.out.println("x");
				//Agregar a listaDeDemosParaEliminar
	}
}