# spark-jpa
Ejemplo de spark y jpa

## Pasos Básicos
- levantar base de datos
- configurar src/main/resources/META-INF/persistance.xml
    --> Base de datos, usuario, password, etc
    --> Ahora esta armado para Mysql, pero si se cambia el driver y el dialecto se puede configurar cualquiera. Con un poco de google se resuelve
- Ejecutar clase Server.java
  


## Ejemplo para levantar mysql usando docker
```
# creo el directorio data
docker run --rm -v "$PWD/data":/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123  --user 1000:1000 --name some-mysql mysql:8 --default-authentication-plugin=mysql_native_password
```