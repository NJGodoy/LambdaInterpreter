# Interprete lambda
## Descripción
Este proyecto implementa un intérprete para el cálculo lambda, con soporte para estrategias de reducción call-by-name y call-by-value, así como la capacidad de listar variables libres en una expresión lambda.

## Información del Sistema
Este proyecto fue desarrollado y probado en el siguiente entorno:

- Sistema Operativo: Windows 11
- Java SDK: OpenJDK 17
- Scala SDK: 3.1.3
- SBT: 1.9.7

## Requisitos Previos
Asegúrate de tener instalados los siguientes componentes en tu sistema antes de intentar compilar y ejecutar el proyecto:

+ Java SDK 17
+ Scala SDK 3.1.3
+ SBT 1.9.7: Información de instalación https://www.scala-sbt.org/download/

>[NOTE]
cualquier entorno en el que se ejecute el programa debe estar configurado
en codificación UTF-8

Clona el Repositorio a tu máquina local usando git:
```
git clone https://https://github.com/NJGodoy/LambdaInterpreter.git
```
Luego compila desde la misma carpeta donde tengas instalada la carpeta build.sbt:
```
sbt compile
```
Ejecutar el Intérprete:
```
sbt run
```
El intérprete se iniciará y podrás interactuar con él a través de la línea de comandos.

## Uso
Una vez que el intérprete esté en funcionamiento, puedes ingresar expresiones lambda y recibir las expresiones reducidas.
El intérprete soporta los siguientes comandos:

### Reducción Call-by-Name (por defecto):
Ingresa una expresión lambda y obtendrás la reducción usando call-by-name.

```
> (λx.λy.y (λx.(x x) λx.(x x)))
λy.y
```

### Reducción Call-by-Value:
Cambia a la estrategia de reducción call-by-value.

```
> set call-by-value
Modo de reducción cambiado a call-by-value.
```

### Variables Libres: 
Cambia al modo de cálculo de variables libres.

```
> set free-variables
Modo cambiado a cálculo de variables libres.
```

### Salir del Intérprete:
Para salir del intérprete, simplemente ingresa:

```
> exit
```

## Ejemplo de Uso
```
$ sbt run
Iniciando interfaz del interprete Lambda
> (λx.λy.y (λx.(x x) λx.(x x)))
├──Aplicacion
│  ├──Abstraccion
│  │  ├──x
│  │  └──Abstraccion
│  │     ├──y
│  │     └──y
│  └──Aplicacion
│     ├──Abstraccion
│     │  ├──x
│     │  └──Aplicacion
│     │     ├──x
│     │     └──x
│     └──Abstraccion
│        ├──x
│        └──Aplicacion
│           ├──x
│           └──x
Reducido por call-by-name: λy.y
> set call-by-value
Modo de reducción cambiado a call-by-value.
> (λx.x λy.y)
├──Aplicacion
│  ├──Abstraccion
│  │  ├──x
│  │  └──x
│  └──Abstraccion
│     ├──y
│     └──y
Reducido por call-by-value: λy.y
λy.y
> set free-variables
Modo cambiado a cálculo de variables libres.
> (λf.λx.(y x) z)
Variables libres: Set(y, z)
> exit
```