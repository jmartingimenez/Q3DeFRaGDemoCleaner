Esta aplicación, es una utilidad para:
- [Quake III](https://en.wikipedia.org/wiki/Quake_III_Arena)
- [DeFRaG](https://en.wikipedia.org/wiki/DeFRaG)

Q3DefragDemoCleaner es mi propia versión de una utilidad existente para 
eliminar los demos innecesarios de la carpeta DeFRaG.

El uso es muy sencillo. En el cuadro de texto se copia el path de la carpeta 
'demos' que se desea limpiar y se pulsa el botón 'Eliminar Demos de sobra' (o 
se pulsa ENTER). El programa solo tiene en cuenta los archivos con extensión 
'.dm_68' que tengan el nombre con el formato típico de DeFRaG
(nombreMapa[modo.física]tiempo(jugador).dm_68).

To do (Sin orden):

- Completar las clases y reorganizar las carpetas para ordenar mejor las cosas.
- Pruebas exhautivas para confirmar que todo funciona perfecto.
- Aprender cosas sobre Swing para mejorar la interfaz gráfica actual.

Aclaración sobre el Release

- Require Java 8. Para usar una versión anterior descargar el código fuente y crear el JAR

Generando un ejecutable en Eclipse:

- File -> Export -> Java -> Runnable JAR File -> Elegir 'GUIMain' en 'Launch Configuration' 
