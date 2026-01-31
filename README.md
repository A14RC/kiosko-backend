# KioskoApp Backend - Proyecto Integrador

Este repositorio contiene el núcleo lógico (Backend) de KioskoApp, una solución diseñada para la gestión automatizada de inventarios y ventas en micro-negocios. El sistema implementa una API REST utilizando Kotlin y Spring Boot, siguiendo una arquitectura de capas y garantizando la integridad transaccional de los datos.

## Requisitos Previos

Para la correcta ejecución del sistema, asegúrese de contar con las siguientes herramientas instaladas:

1. Java JDK 21 o superior.
2. Docker Desktop y Docker Compose.
3. Gradle (incluido a través del wrapper del proyecto).
4. Postman o una herramienta similar para pruebas de API.

## 1. Configuración de la Base de Datos con Docker Compose

El proyecto utiliza PostgreSQL como motor de base de datos externa para garantizar la persistencia de la información fuera del ciclo de vida de la aplicación.

Para levantar el contenedor de la base de datos, ejecute el siguiente comando en la raíz del proyecto:

```bash
docker compose up -d

```

### Detalles de Conexión (configurados en docker-compose.yml):

* **Imagen:** postgres:15-alpine
* **Base de Datos:** db_kiosko
* **Usuario:** root
* **Contraseña:** 1234
* **Puerto expuesto:** 5432

Para verificar que el servicio está activo, puede utilizar el comando `docker ps`.

## 2. Cómo ejecutar la Aplicación

Una vez que la base de datos esté activa y lista para aceptar conexiones, puede iniciar el servidor de Spring Boot.

### En Linux o macOS:

```bash
./gradlew bootRun

```

### En Windows (PowerShell/CMD):

```bash
./gradlew.bat bootRun

```

El servidor iniciará por defecto en el puerto `8080`. Puede acceder a la documentación interactiva de Swagger en la siguiente dirección: `http://localhost:8080/swagger-ui/index.html`.

## 3. Ejecución de Tests Unitarios

Siguiendo los lineamientos de calidad técnica, el proyecto incluye pruebas unitarias para la capa de servicios utilizando JUnit 5 y Mockito.

Para correr la suite completa de pruebas, ejecute:

```bash
./gradlew test

```

## 4. Visualización de Cobertura (Coverage)

El proyecto tiene configurado el plugin **JaCoCo** para medir el porcentaje de cobertura de código, cumpliendo con el requisito del 100% de cobertura en la capa de servicios.

Para generar el reporte de cobertura, ejecute el siguiente comando:

```bash
./gradlew jacocoTestReport

```

### Ruta del reporte:

Una vez finalizado el comando, el reporte detallado en formato HTML se encontrará en la siguiente ruta local:
`build/reports/jacoco/test/html/index.html`

Puede abrir el archivo `index.html` en cualquier navegador web para verificar que todas las líneas de la lógica de negocio han sido testeadas.

## 5. Instrucciones de uso de la Colección de Endpoints

En la raíz del proyecto se incluye el archivo `kiosko_postman_collection.json` con todos los endpoints configurados, incluyendo cuerpos de petición (request body) y ejemplos de respuesta.

### Cómo importar la colección:

1. Abra la aplicación **Postman**.
2. Haga clic en el botón **Import** ubicado en la esquina superior izquierda.
3. Arrastre y suelte el archivo `kiosko_postman_collection.json` o búsquelo mediante el explorador de archivos.
4. La colección "KioskoApp API - Proyecto Integrador" aparecerá en su panel lateral.

### Cómo probar los endpoints:

* **Gestión de Productos:** Utilice los endpoints de la carpeta "Productos" para crear el catálogo inicial.
* **Registro de Ventas:** Utilice el endpoint "Registrar Venta" enviando una lista de IDs de productos y cantidades. El sistema validará automáticamente el stock, calculará los subtotales y descontará las unidades del inventario en una única transacción.

## Arquitectura del Proyecto

El sistema sigue el estándar de **Arquitectura de Capas (N-Tier)**:

* **Controllers:** Exponen los endpoints REST y gestionan la entrada de datos mediante DTOs.
* **Services:** Contienen la lógica de negocio, validaciones de stock y cálculos financieros.
* **Repositories:** Gestionan el acceso a la base de datos mediante Spring Data JPA.
* **Mappers:** Realizan la conversión entre entidades de persistencia y objetos de transferencia de datos (DTOs) para proteger la integridad del modelo interno.