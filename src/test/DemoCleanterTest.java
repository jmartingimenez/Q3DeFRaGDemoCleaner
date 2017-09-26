package test;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DemoCleanterTest {
	/*Antes del test se ejecuta esta linea. Ademas de crear una carpeta 
	 * temporal, la borra al finalizar el test. Saliese o no todo bien*/
	@Rule
	public TemporaryFolder temp = new TemporaryFolder();
	
	@Test
	public void testDemoCleaner() throws IOException{		
		/*=========================================
		 * CREANDO ARCHIVOS EN LA CARPETA TEMPORAL*
		 * =======================================*/
		
		//Demos validos que no se borran por ser el mejor tiempo en cada modo
		final File f01 = temp.newFile("mapa01[df.vq3]00.11.532(player1).dm_68");
		final File f02 = temp.newFile("mapa01[mdf.vq3]00.11.080(player1).dm_68");
		final File f03 = temp.newFile("mapa01[df.cpm]00.10.040(player2).dm_68");
		final File f04 = temp.newFile("mapa01[mdf.cpm]00.10.160(player3).dm_68");
		final File f05 = temp.newFile("mapa02[mdf.vq3]01.52.160(player1).dm_68");
		
		//Demos validos que seran borrados por haber mejores tiempos en cada modo
		final File f06 = temp.newFile("mapa01[mdf.cpm]00.10.192(player3).dm_68");
		final File f07 = temp.newFile("mapa01[mdf.cpm]00.10.224(player4).dm_68");
		final File f08 = temp.newFile("mapa01[df.vq3]00.16.224(player1).dm_68");
		
		//Demos invalidos
		final File f09 = temp.newFile("asdfaa.dm_68");
		final File f10 = temp.newFile("mapa01-00.11.532(player1).dm_68");
		final File f11 = temp.newFile("mapa01[mdf.vq3]3300.11.080(player1).dm_68");
		final File f12 = temp.newFile("mapa01[mdf.vq3]33.110.080(player1).dm_68");
		final File f13 = temp.newFile("mapa01[mdf.vq3]30.11.1080(player1).dm_68");
		final File f14 = temp.newFile("mapa01[mdf.vq3]00.02.352.dm_68");
		final File f15 = temp.newFile("m[asd]00.10.000.dm_68");
		
		//Archivos que no seran tenidos en cuenta por el DemoCleaner
		final File f16 = temp.newFile("readme.txt");
		final File f17 = temp.newFile("mapa01-00.11.720(player1)");
		
		//Asegurandome de que los archivos existan
		assertTrue(f01.exists());
		assertTrue(f02.exists());
		assertTrue(f03.exists());
		assertTrue(f04.exists());
		assertTrue(f05.exists());
		assertTrue(f06.exists());
		assertTrue(f07.exists());
		assertTrue(f08.exists());
		assertTrue(f09.exists());
		assertTrue(f10.exists());
		assertTrue(f11.exists());
		assertTrue(f12.exists());
		assertTrue(f13.exists());
		assertTrue(f14.exists());
		assertTrue(f15.exists());
		assertTrue(f16.exists());
		assertTrue(f17.exists());
	}
}