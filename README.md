<h1>Pongee</h1>
======

Micro-framework web java

<h2>¿ Que es ?</h2>
Pongee es un pequeño framework web ( REST services ) para java, el cual hace uso de Jetty ( http://jetty.codehaus.org/jetty/ ) como 
servidor embebido.

<h2>¿ Como usarlo ?</h2>

1) Descargue e importe a su proyecto todas las librerias .jar contenidas en el directorio PongeeFramework.

2) Con el fin de hacer mas facil su configuracion, "Pongee" toma esta de un fichero .json, el cual debe tener la siguiente
estructura:
<pre>
{
  "port" : "3000", 
	"restServicesInfo" : 
		[
			{
				"method": "GET", 
				"url":"/book/:id",
				"action":"controllers.Book:getId"
			},
			{
				"method": "GET", 
				"url":"/user/:id",
				"action":"controllers.User:getId"
			}
		] 
}
</pre>

<h4>En donde :</h4>
<ul>
  <li>"port" : Hace referecia al puerto en el que va a escuchar la aplicacion</li>
  <li>"restServicesInfo" : Contiene array de servicios que va a publicar la aplicacion. A su vez cada servicio contiene:
    <ul>
      <li>"method" : Metodo HTTP</li>
      <li>"url" : URL del servicio. En el fichero de configuracion presentado anteriormente, se puede observar
      que "url", hace referecia a "/user/:id", lo cual significa que recibira peticiones como por ejemplo /user/123 o 
      /user/777
      </li>
      <li>"action" : Accion a ejecutar al momento de tener un llamado a tal servicio (  Clase:Metodo )</li>
    </ul>
  </li>
</ul>

Despues de haber configurado el fichero .json, se puede arrancar una aplicacion web, de la siguiente manera:
<pre>
  public class PruebaPongee {
  
      public static void main(String[] args) {
          
          ServerPG server = new ServerPG () ;
          server.setConfigurationFile("/ruta/al/fichero/carlos.json").listen();
      }
  }
</pre>

<h2>Arrancar una aplicacion</h2>
Al manejar Pongee un servidor embebido, no se hace necesario el despliegue de la aplicacion en un servidor de aplicaciones 
como tomcat. 
Simplemente contruya un .jar de su proyecto, y ejecutelo.

<h2>Fichero estaticos</h2>
Al momento de necesitar que su aplicacion sirva ficheros estaticos, es necesario tener en cuenta que Pongee tiene un estructura
de ficheros predeterminada, la cual se puede observar a continuacion:
<ul>
  <li>myApp.jar</li>
  <li>static</li>
  <li>errors</li>
</ul>
<h4>En donde</h4>
<ul>
  <li>myApp.jar, es su fichero .jar generado, el cual contiene la clase principal (main) de su aplicacion</li>
  <li>static, es el directorio que contiene todos los ficheros estaticos de la aplicacion (html,css,images)</li>
  <li>errors, directorio que contiene los ficheros .html para responder recursos personalizados a los diferentes errores,
  tales como 404, 500, 500, entre otros.
  </li>
</ul>
<h2>Ejemplo</h2>
Para lograr un entendimiento mas acelerado acerca del modo de uso y configuracion de Pongee, se ha creado un pequeño ejemplo,
en donde se puede observar claramente, los items mencionados anteriormente.



