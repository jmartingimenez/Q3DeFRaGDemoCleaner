/*Fuente del ejemplo original:
 * 'https://www.ntu.edu.sg/home/ehchua/programming/java/J4a_GUI_2.html'
 * */

package demos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
/** Test JTextField, JPasswordField, JFormattedTextField, JTextArea */
@SuppressWarnings("serial")
public class GuiPrueba extends JFrame {
 
   // Private variables of the GUI components
   JTextField tField;
   JButton tButton;
   JPasswordField pwField;
   JTextArea tArea;
   JFormattedTextField formattedField;
 
   /*Constructor que hace todo el laburo*/
   public GuiPrueba() {
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
            tArea.append("\nPulaste ENTER, escribiste: " + tField.getText());
            eliminarDemos(tField.getText());
         }
      }); 

      //Boton de eliminar. Si apretas ENTER va al metodo
      tButton = new JButton("Eliminar Demos");
      tfPanel.add(tButton);
      tButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
             tArea.append("\nPulsaste el botón: " + tField.getText());
             eliminarDemos(tField.getText());
          }
       });
 
      //Creacion del textArea
      tArea = new JTextArea("Mensaje default");
      tArea.setFont(new Font("Serif", Font.ITALIC, 13));
      
      /*Si los caracteres superan el ancho, salen debajo. Esto en falso crearia 
      * Una barra de desplazamiento horizontal*/
      tArea.setLineWrap(true);       
      tArea.setWrapStyleWord(true);
      
      //Color de fondo en RGB. No puede editarse el texto
      tArea.setBackground(new Color(204, 238, 241)); // light blue
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
       * barra de titulo. Seteo el tamaño de la ventana y que esta sea visible*/
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setTitle("DeFRaG Demo Cleaner - By Yony!");
      setSize(640, 320);
      setVisible(true);
   }
   //Metodo que pone en marcha el DemoCleaner
   private static void eliminarDemos(String pathDado){
	   System.out.println("El path ingresado es: " + pathDado);
	   //Pendiente
   }   
   
   /*Metodo Main que hara la creacion de la ventana*/
   public static void main(String[] args) {
      //Tengo que ver que significa esto
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            new GuiPrueba();  //El constructor hace todo
         }
      });
   } 
}
