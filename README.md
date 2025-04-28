# Card Matcher Backend

Este es el backend de una aplicación llamada **Card Matcher**. El objetivo de esta aplicación es permitir a los usuarios gestionar, intercambiar y coleccionar cartas. Los usuarios pueden registrar su cuenta, iniciar sesión, y organizar sus cartas en listas de tipo "deseadas", "ofrecidas" o "colección".

## Tecnologías Utilizadas ⚙️

- **Java 23** 
- **Spring Boot** (framework principal)
- **JPA** (Java Persistence API) para la gestión de bases de datos
- **MariaDB** como sistema de base de datos
- **JWT** (JSON Web Tokens) para autenticación 
- **Lombok** para reducción de código boilerplate

## Estructura del Proyecto 📁

Este proyecto sigue una estructura MVC utilizando Spring Boot. A continuación se describen las principales clases y funciones del proyecto:

### Modelos de Datos

1. **User**: Representa a un usuario de la aplicación. Tiene atributos como nombre de usuario, correo electrónico, contraseña y una lista de tarjetas asociadas.
   
2. **Card**: Representa una carta coleccionable. Tiene atributos como nombre, imagen, categoría, rareza y un identificador de expansión.

3. **CardList**: Representa una lista de tarjetas asociadas a un usuario. Las listas pueden ser de tres tipos: "deseadas", "ofrecidas" o "colección".

4. **Set**: Representa las distintas expansiónes de las cartas, con atributos como el nombre, número total de cartas y fecha de lanzamiento.

### Funciones principales

- **Registro de usuario**: Los usuarios pueden registrarse proporcionando un nombre de usuario, correo electrónico y contraseña.
- **Inicio de sesión**: Los usuarios pueden iniciar sesión utilizando su nombre de usuario y contraseña para obtener un token JWT.
- **Gestión de cartas**: Los usuarios pueden agregar tarjetas a sus listas de "deseadas", "ofrecidas" o "colección".

## Configuración ⚡

### Requisitos previos

- **Java 23**
- **MariaDB** (o cualquier base de datos compatible con JDBC)
- **Maven** para gestión de dependencias

### Variables de Entorno

Para configurar el acceso a la base de datos y el JWT, necesitas establecer algunas variables de entorno en el archivo `.env` o directamente en tu entorno de desarrollo:

- `DB_URL`: La URL de la base de datos (por ejemplo: `jdbc:mariadb://localhost:3306/cardmatcher`)
- `DB_USERNAME`: El nombre de usuario para la base de datos.
- `DB_PASSWORD`: La contraseña para la base de datos.
- `JWT_SECRET`: Una clave secreta para generar el token JWT.

### Paso a paso para ejecutar el proyecto 

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/tu-usuario/card-matcher-backend.git
   cd card-matcher-backend
   Configurar el archivo .env:

2. Asegúrate de tener un archivo .env con las siguientes variables de entorno configuradas adecuadamente:


DB_URL=jdbc:mariadb://localhost:3306/cardmatcher
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
JWT_SECRET=tu_clave_secreta

3. Compilar el proyecto:

Utiliza Maven para compilar el proyecto y sus dependencias:

mvn clean install

4. Ejecutar el proyecto:

Para ejecutar la aplicación, usa el siguiente comando:

mvn spring-boot:run

El backend estará disponible en http://localhost:8080.


:)
