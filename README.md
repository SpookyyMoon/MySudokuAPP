# My Sudoku APP

MySudokuAPP es una aplicación desarrollada en Android Studio que adapta el juego clasíco a una experiencia moderna a traves de sistemas de pistas, crono, dificultades y una interfaz moderna y cómoda.

## Características principales:

- Generación Dinámica de Tableros – Implementación de un algoritmo de Backtracking que garantiza la creación de tableros únicos y con solución única en cada partida, asegurando una experiencia siempre nueva.

- Sistema de Niveles de Dificultad – Algoritmos de filtrado que ajustan la dispinibilidad de pistas, detecciónn y resltado de errores permitiendo modos "Facil", "Medio" y "Dificil".

- Asistencia Inteligente al Jugador – Sistema de validación en tiempo real con detección de errores, resaltado de celdas relacionadas y una función de pistas (hints) para ayudar en momentos de bloqueo.

- Cronómetro y Seguimiento de Tiempo – Medición precisa del tiempo de resolución para fomentar la competitividad, con pausa automática para garantizar la integridad de las marcas.
  
- Sistema de Puntuación y Ranking – Ranking basado en la dificultad seleccionada y el tiempo empleado.

- Persistencia de Datos en la Nube – Integración completa con MongoDB Atlas para el guardado de récords.

- Arquitectura de Red Robusta – Comunicación fluida con el backend mediante Retrofit, gestionando de forma eficiente las peticiones asíncronas para el registro de puntuaciones y obtención de datos.

- Interfaz de Usuario Reactiva – Diseño optimizado para Android con el uso de Alerts personalizados para notificaciones importantes y una experiencia táctil intuitiva para la inserción de números.

---

## Diseño en figma (Prototipo):

https://www.figma.com/proto/YpTwyeiypEkDYbFNRAAD3L/MySudoku-APP?t=OQ0V96luWr4vpIPl-1&scaling=scale-down&content-scaling=fixed&page-id=0%3A1&node-id=4-76&starting-point-node-id=4%3A76

---

## Tecnologías utilizadas:

- **Lenguaje principal:** Java (Android SDK)
- **Interfaz gráfica:** XML
- **Comunicación con backend:** Retrofit 2 (Consumo de API REST)
- **Backend:** Node.js + Express + MongoDB Atlas (API REST)  
- **Gestión de datos:**  MongoDB Atlas
  
---

## Imágenes en ejecución:

### Vista de inicio
<img width="633" height="1250" alt="{7AD9BCF6-FC08-448C-B77D-F6C570150EB3}" src="https://github.com/user-attachments/assets/321ad26e-13b1-4824-9ebc-4db36afaeb1a" />

### Selección de nombre
<img width="635" height="1248" alt="{A7842626-64CC-4D7D-A222-B58769952FDE}" src="https://github.com/user-attachments/assets/e5c781b3-2845-4c7e-a23e-68b083c6a30a" />

### Selección de dificultad
<img width="621" height="1247" alt="{29707A02-0B3D-4556-BBC2-D245AA53927D}" src="https://github.com/user-attachments/assets/234e8383-8e3b-4aac-abe9-e381364ee2b0" />

### Partida
<img width="635" height="1251" alt="{9E6A4037-DD3D-424C-843C-8FEE57025CB9}" src="https://github.com/user-attachments/assets/1202c3e9-c3fb-4eb0-b171-930d58b6c3ad" />

