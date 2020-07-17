# Single Sign On anónimo en Android para n servicios designados

La adopción de esquemas de inicio de sesión único plantea múltiples preocupaciones con respecto a la privacidad de los usuarios. Teniendo en cuenta lo anterior, la Universidad de Surrey propone un protocolo que garantiza el inicio de sesión único de forma anónima y para n servicios designados. La implementación base se centra en probar las propiedades de seguridad del esquema, pero presenta limitaciones de arquitectura y de desempeño al interactuar con dispositivos móviles, lo que impide su adopción en contextos de aplicación real. Este trabajo resuelve las limitaciones de la implementación previa y evalúa la utilidad del protocolo en el campo de los tiquetes inteligentes del sector transporte, minimizando los riesgos de exposición indebida de datos sensibles de los usuarios y aprovechando las bondades de las aplicaciones móviles.

## Diseño

Con el fin de proteger la privacidad, el esquema utiliza el concepto de **verificador designado** para cada servicio. El usuario solicita la emisión de un tiquete y lo presenta ante el verificador que corresponda (a nadie más). El verificador valida la información para así permitir el acceso al servicio requerido. Ninguna de las partes conoce más información que la estrictamente necesaria para garantizar el acceso, evitando lectura de datos sensibles del usuario.

La implementación usa una aplicación Android como cliente, y NFC para  transmisión de datos.  Para el manejo de peticiones entre cliente-servidor se utiliza el patrón de diseño de comando, de forma que dichas peticiones se encapsulan en objetos para facilitar parametrización y evitar acoplamiento.

<img src="https://raw.githubusercontent.com/jd-vega11/jd-vega11.github.io/master/img/DisenioPG.png" alt="Disenio proyecto de grado" width="411px"/>

## Prototipo

El código fuente se encuentra segmentado en dos proyectos, uno con el desarrollo del servidor y otro con la aplicación Android. Ambos se encuentran en el presente repositorio

### Requisitos previos

1. Lector NFC: el presente proyecto se implementó usando un lector NFC de referencia ACR122U. Como se ha indicado en diversas ocasiones a lo largo del presente documento, este lector se utiliza para comunicar el dispositivo móvil con el servidor. Por este motivo, si bien es posible usar otras referencias, es imperativo contar con un lector que se pueda conectar a la máquina donde se ejecutará el servidor. En particular, el lector ACR122U cuenta con conexión USB. 

2. Instalar los controladores según el sistema operativo en que se ejecutará el servidor. Para el caso del ACR122U, estos controladores se pueden descargar en la siguiente página web (sección Downloads): [https://www.acs.com.hk/en/products/3/acr122u-usb-nfc-reader/](https://www.acs.com.hk/en/products/3/acr122u-usb-nfc-reader/)

3. JDK versión 1.8 o superior en la máquina del servidor. 

4.	Ambientes de desarrollo. Para el servidor se trabaja con Eclipse (versión 4.9 o superior). Para el cliente se trabaja con Android Studio (versión 3.2 o superior).

5.	Dispositivo móvil con sistema operativo Android con soporte para NFC. 

6.	Clonar el repositorio con el código fuente

### Preparación

#### Servidor

1.	Importar el código del servidor (carpeta BETS-Server) en Eclipse como un proyecto Maven (ir al menú Archivo -> Importar -> Maven -> Proyectos Maven Existentes). 

2.	Conectar el lector NFC a la máquina donde se está haciendo el proceso de ejecución del servidor.

#### Cliente 

3.	Importar el código del cliente (carpeta BETS-Android) en Android Studio.

4.	Conectar el dispositivo móvil a la máquina donde se importó el código del cliente.

### Ejecución

#### Servidor 

1.	En Eclipse, ejecutar la aplicación servidor desde la clase Main.java que se encuentra en el paquete uk.ac.surrey.bets_framework.

2.	En la consola de la aplicación, verificar que el servidor pasó por el proceso SET UP sin ningún problema (i.e. no hay ninguna excepción impresa) y que finalmente se quedó esperando en estado OPEN. Esto indica que está esperando que un dispositivo se vincule a través del lector NFC. 

#### Cliente

3.	En Android Studio, ejecutar la aplicación cliente. Hacer este proceso con el dispositivo móvil conectado instalará el .apk correspondiente y abrirá la vista de inicio de sesión.

4.	Hacer click en REGISTRARSE para crear su cuenta de usuario. Esto abrirá una nueva vista donde deberá ingresar la información básica que se solicita. 

5.	Cuando haya terminado de llenar el formulario, haga click en REGISTRARSE. 

6.	Ponga su teléfono en el lector NFC (la aplicación le muestra un mensaje dándole esta indicación).

7.	Espere mientras se efectúa la operación.

#### Servidor

8.	Puesto que el cliente acaba de enviar una solicitud de registro, en la consola debe aparecer un mensaje indicando el inicio de dicha operación, al igual que una serie de mensajes subsiguientes que indican el estado actual. Al finalizar se mostrará que el proceso se efectuó exitosamente.

#### Cliente 

9.	Una vez el servidor retorna las credenciales del usuario de forma correcta, se muestra un mensaje que indica el éxito del proceso de registro. Posteriormente, se ingresa al menú principal de la aplicación.

10.	Puesto que ya tiene acceso, puede crear un nuevo viaje en el medio de transporte de su preferencia (emisión de tiquete) y simular el acceso a dicho medio de transporte (validación). Estas operaciones se efectúan y se verifican en la consola del servidor de una forma semejante a los pasos de registro previamente enunciados en los numerales 5 a 9. 

### Manejo de errores

1.	Si se generan errores al importar el código en Eclipse, se recomienda eliminar los archivos .classpath y .project, al igual que el directorio .settings. 

2.	Si el lector NFC no está conectado a la máquina del servidor o si los controladores no fueron instalados correctamente se genera una excepción de estado ilegal. Los mensajes que aparecerán en la consola serán semejantes a los que se muestran a continuación:

```
processing FAILURE (null, 0) in state ControlState1
ending on error
could not setup or tear down client
java.lang.IllegalStateException: could not setup client
```

3.	Si el dispositivo móvil no cuenta con soporte para NFC, al instalar la aplicación y tratar de hacer el registro, Android Studio mostrará una excepción asociada al servicio APDU y se interrumpirá el proceso de forma abrupta. 

### Verificar correcto funcionamiento

Con el fin de probar que el prototipo está funcionando de forma apropiada, estás son algunas acciones que debe tener en cuenta:

1.	En la consola del servidor se debe observar que el servidor se queda esperando en estado OPEN cuando el dispositivo móvil no está en contacto con el lector NFC.

2.	En todas las operaciones del protocolo AnonSSO (registro, emisión y validación), la aplicación móvil indica que debe poner el dispositivo móvil en el lector NFC. Una vez hecho esto, luego indica que se está efectuando la operación con un mensaje de espera. Finalmente, si todo está funcionando bien, se muestra claramente un mensaje que indica que la operación finalizó con éxito. En caso contrario, se muestra un mensaje de error y en la consola de Android Studio se puede consultar el log de las excepciones generadas. 

3.	Cuando el usuario ubica su dispositivo en el lector NFC luego de hacer una solicitud de registro, emisión o validación, la consola del servidor debe mostrar casi de forma inmediata que se estableció un vinculo con un cliente y que se inició alguno de estos procesos. Asimismo, se muestra de forma detallada el estado actual de la operación (por ejemplo, en el proceso de registro se muestran mensajes que indican que la autoridad central generó las credenciales). Al finalizar se muestra un mensaje de éxito. En caso contrario, se indica el error y se imprime el mensaje de la excepción correspondiente en caso de haberse generado.

### Video

Haga click en la imagen para abrir el video que muestra el funcionamiento de la aplicación móvil

 <a href="https://youtu.be/WO3yCEQdFJA" target="_blank"><img src="https://github.com/jd-vega11/jd-vega11.github.io/blob/master/img/RuedaS.jpg" alt="Video proyecto de grado" width="711px"/></a> 





