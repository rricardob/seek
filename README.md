# Examen Tecnico

## Requisitos

Asegúrate de tener instalados los siguientes herramientas para poder ejecutar en local:

- **Java 17**: Esta aplicación requiere Java 17.
- **Gradle**: Usado como sistema de construcción. Puedes instalarlo manualmente o usar el wrapper incluido.
- **Base de datos Mysql**: La aplicación utiliza una base de datos Mysql
- **Spring Boot 3.2.11**: Usado como framework principal para el backend.

## Instrucciones de instalación y configuración

1. **Clonar el repositorio**

   Clona el repositorio del proyecto en tu máquina local:

   ```bash
   git clone <https://github.com/rricardob/seek>
   ```
   
2. **Compilar la aplicación**
   ejecutar el siguiente comando para compilar la aplicacion
   ```bash
   ./gradlew clean build
   ```
3. **Ejecución de la aplicación**

   Una vez iniciada, la aplicación estará disponible en `http://localhost:8080`, y para acceder al swagger con este enlace de
   manera local http://localhost:8080/swagger-ui/index.htm
   tambien se puede consultar la informacion de swagger directamente  
   online en http://18.191.25.89//swagger-ui/index.html#

4. **Tests**

   Para ejecutar los tests, usa el siguiente comando:

   ```bash
   ./gradlew test
   ```
5. **Probar endpoints**
   en la raiz del proyecto existe un archivo **seek.postman_collection.json**
   que debera importar en su postman para poder ejecutar los endpoints