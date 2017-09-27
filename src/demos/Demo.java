package demos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
	private String nombreCompleto;
	private String nombreMapa;
	private String modo;
	private String tiempo;
	
	public Demo(String nombreCompleto){
		this.nombreCompleto = nombreCompleto;
		
		/*Intentando separar las partes del nombre del archivo
		 * Un demo con formato correcto deberia ser como esto...
		 * 'nombreMapa[modo.fisica]tiempo(jugador)'. Se controla que el 
		 * formato del tiempo tenga como maximo '#####.##.###' 
		 * ({1,5}.{1.2}.{1.3}), siendo '#' un numero entero*/
		try{
			nombreMapa = nombreCompleto.substring(0,nombreCompleto.lastIndexOf("["));
			modo = nombreCompleto.substring(nombreMapa.length(),nombreCompleto.lastIndexOf("]") + 1);
			if(!esCorrectoElModo(modo)) throw new Exception();
			tiempo = nombreCompleto.substring(nombreCompleto.lastIndexOf("]") + 1,nombreCompleto.lastIndexOf("("));	
			if(!esCorrectoElFormatoDelTiempo(tiempo))
				throw new Exception();
		}catch(Exception e){
			this.nombreCompleto = "DEMO WITH INVALID FORMAT";
			nombreMapa = null;
			modo = null;
			tiempo = null;
		}
	}
	
	private boolean esCorrectoElFormatoDelTiempo(String tiempo){
		//Expresion regular para aceptar => '#####.##.###'
		String REGEX = "^(?:(\\d{1,5})\\.)?(?:(\\d{1,2})\\.)?(\\d{1,3})$";	
		
		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(tiempo);
		if(matcher.matches()) return true;		
		return false;
	}
	
	private boolean esCorrectoElModo(String modo){
		/*Expresion regular para los 4 modos normales
		 * [mdf.vq3]/[mdf.cpm]/[df.vq3]/[df.cpm]*/
		String dfRegex = "^(\\[)(df|mdf)\\.(vq3|cpm)(])$";	
		
		/*Expresion regular para los modos de fastcap
		 * [fc.vq3.X]/[mfc.cpm.X]/[fc.vq3.X]/[fc.cpm.X]
		 * Siendo X un valor entre 0 y 7*/
		String fcRegex = "^(\\[)(fc|mfc)\\.(vq3|cpm)\\.([0-7])(])$";	
		
		Pattern dfPattern = Pattern.compile(dfRegex);
		Pattern fcPattern = Pattern.compile(fcRegex);
		Matcher dfMatcher = dfPattern.matcher(modo);
		Matcher fcMatcher = fcPattern.matcher(modo);
		if(dfMatcher.matches() || fcMatcher.matches()) return true;		
		return false;
	}
	
	public void setNombre(String nombreCompleto){
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getNombreCompleto(){
		return nombreCompleto;
	}

	public String getNombreMapa() {
		return nombreMapa;
	}

	public void setNombreMapa(String nombreMapa) {
		this.nombreMapa = nombreMapa;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}	
}