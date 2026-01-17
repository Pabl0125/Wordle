# 🟩 Desktop Wordle - JavaFX

Una recreación de escritorio del popular juego **Wordle**, desarrollada en **Java** utilizando la biblioteca gráfica **JavaFX** y gestionada con **Gradle**. Este proyecto demuestra una arquitectura limpia, modular y el uso de recursos modernos de Java.

---

## 📸 Capturas de Pantalla

Aquí puedes ver cómo luce el juego en acción:

|                                                     Pantalla Principal                                                     |                            Ventana de Resultado                            |
|:--------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------:|
| ![Pantalla Principal](/src/main/resources/app/wordle/desktopwordle/Screenshot1.png) | ![Resultado](/src/main/resources/app/wordle/desktopwordle/Screenshot2.png) |

---

## 🚀 Características

* **Mecánica Clásica:** Adivina la palabra oculta de 5 letras en 6 intentos.
* **Feedback Visual:** Animaciones y colores que dan al usuario una idea clara de su progreso:
    * 🟩 **Verde:** Letra correcta en la posición correcta.
    * 🟨 **Amarillo:** Letra correcta en la posición incorrecta.
    * ⬜ **Gris:** La letra no está en la palabra.
* **Interfaz Gráfica (GUI):** Construida con **JavaFX** y estilizada mediante **CSS**.
* **Gestión de Dependencias:** Proyecto migrado a **Gradle** para una fácil compilación y ejecución en cualquier sistema operativo.
* **Diccionario Local:** Carga palabras desde un archivo de texto interno (`wordleWords.txt`).
* **Nota:** El diccionario no contempla plurales ni nombres propios como palabras válidas a la hora de jugar 
---

## 🛠️ Requisitos Previos

Para ejecutar este proyecto, necesitarás tener instalado:

* **Java JDK 21** (Recomendado). El proyecto utiliza Toolchains para garantizar compatibilidad.
* **Git** (para clonar el repositorio).

> **Nota:** No es necesario tener Gradle instalado globalmente, ya que el proyecto incluye el **Gradle Wrapper**.

---

## ☕ Nota para usuarios con versiones muy recientes de Java (JDK 22/25)

Si tu sistema operativo utiliza por defecto una versión de Java "Bleeding Edge" (como Java 25) o muy reciente, es posible que encuentres errores al iniciar Gradle debido a restricciones de seguridad en el acceso nativo, o errores del tipo:
> `java.lang.IllegalArgumentException: Unsupported major.minor version`

Aunque este proyecto configura automáticamente un entorno aislado (**Sandbox**) con Java 21 para compilar el código, **Gradle necesita una versión de Java compatible para poder arrancarse a sí mismo** antes de leer esa configuración.

### ✅ Solución

No necesitas desinstalar tu versión actual de Java. Simplemente debes indicar a Gradle que utilice una versión estable (Java 17 o 21) **solo para el proceso de arranque**.

#### Opción A: Solución Permanente (Recomendada)
Crea o edita el archivo `gradle.properties` en la raíz del proyecto para fijar el Java de arranque de Gradle globalmente. Añade la siguiente línea apuntando a tu instalación de Java estable:

```properties
org.gradle.java.home=/ruta/absoluta/a/tu/java-21-openjdk
```
#### Opción B: Solución Temporal (Línea de comandos)

Puedes forzar la variable de entorno solo para la ejecución actual:

En Linux / macOS:
```bash
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 ./gradlew run
```
En Windows (PowerShell):
```powershell
$env:JAVA_HOME="C:\Archivos de Programa\Java\jdk-21"
.\gradlew run
```
------

## 💻 Instalación y Ejecución

Sigue estos pasos para probar el juego en tu máquina local:

### 1. Clonar el Repositorio

Abre tu terminal y ejecuta:

```bash
git clone https://github.com/Pabl0125/Wordle.git
cd Wordle
```

### 2. Ejecutar con Gradle

El proyecto utiliza el **Gradle Wrapper** (`gradlew`), que descargará automáticamente la versión correcta de Gradle y las dependencias de JavaFX necesarias.

* **En Linux / macOS:**
```bash
./gradlew clean run
```


* **En Windows (CMD / PowerShell):**
```bash
gradlew.bat clean run
```

---

## 📂 Estructura del Proyecto

El código ha sido refactorizado para seguir el estándar de arquitectura de **Gradle**:

```text
Wordle/
├── src/
│   ├── main/
│   │   ├── java/app/wordle/desktopwordle/  <-- Código Fuente (.java)
│   │   │   ├── Launcher.java               <-- Punto de entrada (Main)
│   │   │   ├── HelloAplication.java        <-- Clase Application JavaFX
│   │   │   └── Controller.java             <-- Lógica del juego
│   │   └── resources/app/wordle/desktopwordle/ <-- Recursos (.fxml, .css, .txt)
│       └── module-info.java                <-- Configuración de Módulos
├── build.gradle.kts                        <-- Configuración de Dependencias
└── README.md

```

---

## 🛠️ Tecnologías Utilizadas

Este proyecto ha sido construido aprovechando las características modernas del ecosistema Java:

* **Lenguaje Principal:** [Java 21](https://openjdk.org/projects/jdk/21/) - Utilizando las últimas mejoras de sintaxis y rendimiento (LTS).
* **Interfaz de Usuario:** **JavaFX** - Framework gráfico moderno para aplicaciones de escritorio ricas.
* **Gestión de Construcción:** **Gradle** - Automatización de dependencias y compilación (DSL de Kotlin).
* **Diseño:** **CSS3** - Estilización personalizada para lograr el aspecto visual idéntico al juego web.
* **Estructura de Datos:** **FXML** - Separación clara entre la lógica del código y el diseño de la interfaz.
