# Predicción del Clima en una Galaxia Lejana

Este proyecto es una aplicación que predice el clima en una galaxia lejana habitada por tres civilizaciones: Vulcanos, Ferengis y Betasoides. Estas civilizaciones dominan la predicción del clima mediante un complejo sistema informático. La aplicación calcula y provee información sobre los periodos de sequía, lluvia y condiciones óptimas de presión y temperatura para los próximos 10 años.

# Premisas
- Planeta Ferengi: Se desplaza con una velocidad angular de 1 grado por día en sentido horario. Su distancia con respecto al sol es de 500km.
- Planeta Betasoide: Se desplaza con una velocidad angular de 3 grados por día en sentido horario. Su distancia con respecto al sol es de 2000km.
- Planeta Vulcano: Se desplaza con una velocidad angular de 5 grados por día en sentido anti-horario. Su distancia con respecto al sol es de 1000km.
- Órbitas: Todas las órbitas son circulares.

# Reglas Climáticas
- Sequía: Cuando los tres planetas están alineados entre sí y con respecto al sol.
- Lluvia: Cuando los tres planetas no están alineados y forman un triángulo en el cual el sol está dentro.
- Condiciones Óptimas: Cuando los tres planetas están alineados entre sí, pero no con respecto al sol.
- Pico de Lluvia: El día en el que el perímetro del triángulo formado por los tres planetas es máximo.

# Endpoints

Base URL: http://localhost:8080/api/clima

Se obtiene la información respectiva a un dia especifico.
- GET /dia/{dia}

Se obtiene la información de las cantidades de distintos climas a traves de 10 años y el dia mas lluvioso de estos.
- GET /resultados

Se obtienen todos los dias que tengan el clima especificado.
- GET /all/{clima}

# Tecnologías Utilizadas
- Java 17: Lenguaje de programación principal.
- Spring Boot 3.3.1: Framework para construir la aplicación.
- Maven: Herramienta de gestión de proyectos y dependencias.
- REST API: Para la interacción con el sistema de predicción climática.
