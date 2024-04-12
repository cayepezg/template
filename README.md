# Generador de PAYLOAD.

Recibiendo dos JSON string como datos de entrada, el primero una platilla y el segundo un Json con valores, 
haciendo uso de JsonPath, combina ambos archivos, realizando las validaciones respectivas para generar una
salida.

## Compilación del archivo

En la raíz del proyecto ejecutar el comando:
```
mvn clean package
```

## Ejecución 

Una vez ejecutada la tarea de compilación se generarán dos archivos con extensión jar: templates-0.0.1.jar y templates-0.0.1-jar-with-dependencies.jar.
El primero no tiene embebidas las dependecias de la aplicación por lo cual debe ejecutarse en un ambiente que tenga disponible las librerías necesarias.
El Segundo incluye en el empaquetado todas las dependencias, por lo cual es el que utilizaremos mediante la ejecución del siguiente comando, estando en
el directorio ../templates/target:

```
java -jar templates-0.0.1-jar-with-dependencies.jar {{ruta_al_template.json}} {{ruta_al_payload.json}}
```

Por ejemplo: 

```
java -jar templates-0.0.1-jar-with-dependencies.jar /tmp/template.json /tmp/cotiz-prod-individual.json
```

El resultado obtenido el la consola, será un JSON string con los valores del paylod sustituidos en la plantilla para los casos en que coresponda.


---

**By Carlos Yépez <cayepezg@mail.com>**