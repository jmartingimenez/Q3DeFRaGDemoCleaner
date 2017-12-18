/*Fuente del ejemplo original:
 * 'https://www.ntu.edu.sg/home/ehchua/programming/java/J4a_GUI_2.html'
 * */
package demos;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

 
@SuppressWarnings("serial")
public class GuiMain extends JFrame {
	private JTextField tField;
	private JButton tButton;
	private JTextArea tArea;
 
	/*Constructor que hace todo el laburo*/
	public GuiMain() {
    /*JPanel para las cosas que estan por encima del textArea. 
    * (filas, columnas, limite horizontal y limite vertical <= GridLayout)*/
    JPanel tfPanel = new JPanel(new GridLayout(2, 2, 10, 1));
    tfPanel.setBorder(BorderFactory.createTitledBorder("Path: "));
 
    //textField donde se pega el Path. Si apretas ENTER va al metodo
    tField = new JTextField(10);
    tfPanel.add(tField);
    tField.addActionListener(new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    	    //Para que el scroll de la consola baje automaticamente
    	    tArea.setCaretPosition(tArea.getDocument().getLength());
            eliminarDemos(tField.getText(), tArea);
        }
    });    
    
    //Opcion contextual para pegar el path con click derecho
    JPopupMenu popup = new JPopupMenu();
    JMenuItem item = new JMenuItem(new DefaultEditorKit.PasteAction());
    item.setText("Pegar");
    popup.add(item);
    tField.setComponentPopupMenu(popup);    

    //Boton de eliminar. Si apretas ENTER va al metodo
    tButton = new JButton("Eliminar Demos de sobra");
    tfPanel.add(tButton);
    tButton.addActionListener(new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    	    tArea.setCaretPosition(tArea.getDocument().getLength());
            eliminarDemos(tField.getText().replace("\\", "/"), tArea);
        }
    });
 
    //Creacion del textArea
    tArea = new JTextArea(""
    		+ "Copia el path de la carpeta demos y pulsa el botón "
    		+ "correspondiente (o presiona ENTER).\n"
    		+ "Código fuente: https://github.com/jmartingimenez/Q3DeFRaGDemoCleaner\n"
    		+ "================================================================\n");
    tArea.setFont(new Font("Arial", Font.PLAIN, 13));
    tArea.setForeground(Color.green);
      
    /*Si los caracteres superan el ancho, salen debajo. Esto en falso crearia 
    * Una barra de desplazamiento horizontal*/
    tArea.setLineWrap(true);       
    tArea.setWrapStyleWord(true);
      
    //Color de fondo en RGB. No puede editarse el texto
    tArea.setBackground(new Color(0, 0, 0));
    tArea.setEditable(false);
      
    //Encerrando el TextArea dentro de un panel que tiene desplazamiento
    JScrollPane tAreaScrollPane = new JScrollPane(tArea);
    tAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    tAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
    //Seteando el contenedor
    Container cp = this.getContentPane();
    cp.setLayout(new BorderLayout(5, 5));
    cp.add(tfPanel, BorderLayout.NORTH);
    cp.add(tAreaScrollPane, BorderLayout.CENTER);
 
    /*El programa finaliza al cerrar la ventana. Defino lo que sale en la 
     * barra de titulo. Seteo el tamaño de la ventana (no se puede cambiar su 
     * tamaño)y que esta sea visible*/
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("DeFRaG Demo Cleaner - By Yony!");
    setSize(560, 320);
    setResizable(false);
    setVisible(true);   
}
   
	//Metodo que pone en marcha el DemoCleaner
	private static void eliminarDemos(String pathDado, JTextArea tArea){		
		try{
			//Comprobando que el path exista
			if(!new File(pathDado).exists())
				throw new FileNotFoundException();
			
			//Poniendo en marcha el Demo Cleaner
			DemoCleaner d = new DemoCleaner(pathDado);
			
			//Analiza demos a mantener, eliminar y archivos a ignorar
			d.analizar();
			
			//Se pone un mini resumen de los archivos encontrados
			tArea.append(d.info());
			
			//Se eliminan los demos
			d.eliminarDemos();
		}catch(FileNotFoundException e){
			tArea.append("\n>ERROR!!: No se encuentra el path.");
		}catch(SecurityException e){
			tArea.append("\n>ERROR!!: No hay permisos suficientes.");
		}catch(Exception e){
			tArea.append("\nERROR!!: Fallo no esperado. Contacte al creador de la "
					+ "aplicación e informele sobre el siguiente error: " 
					+ e.getClass().getSimpleName());
			e.printStackTrace();
		}
	}   
   
	/*Metodo Main que hara la creacion de la ventana*/
	public static void main(String[] args) {
		//Tengo que ver que significa esto
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GuiMain();  //El constructor hace todo
			}
		});
	} 
}