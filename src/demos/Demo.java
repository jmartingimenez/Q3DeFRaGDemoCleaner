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
		
		//Intentando separar las partes del nombre del archivo
		try{
			nombreMapa = nombreCompleto.substring(0,nombreCompleto.lastIndexOf("["));
			modo = nombreCompleto.substring(nombreMapa.length(),nombreCompleto.lastIndexOf("]") + 1);
			tiempo = nombreCompleto.substring(nombreCompleto.lastIndexOf("]") + 1,nombreCompleto.lastIndexOf("("));	
			if(!esCorrectoElFormatoDelTiempo(tiempo))
				throw new Exception("Formato de tiempo invalido detectado.");
		}catch(Exception e){
			System.out.println(e.getMessage());
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