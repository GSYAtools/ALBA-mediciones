<div>
  <h1>ALBA mediciones</h1>
  <p>Algoritmos de Seguridad para medición de consumos</p>
  <hr>
</div>
<div>
  <h2>Tutorial Docker</h2>
  <p>Trabajamos con Ubuntu en este caso</p>
  <h3>Permisos</h3>
  <p>Damos permisos al usuario actual para utilizar Docker</p>
  <pre><code>sudo usermod -aG docker $USER</code></pre>
  <p>Tras esto deberemos reiniciar la sesión de nuestro usuario</p>
  <h3>Creación de Dockerfile</h3>
  <p>Generamos un documentos con nombre "Dockerfile" con el siguiente contenido con el objetivo de ejecutar nuestra aplicación:</p>
  <pre><code>
    FROM openjdk:20
    COPY . /usr/app
    WORKDIR /usr/app
  </code></pre>
  <h3>Contrucción de la imagen y el contenedor</h3>
  <pre><code>
    docker build -t [image_name] [app_path] # Para la creación de la imagen
    docker image ls # Para ver el listado de imagenes
    docker run -it {--rm} --name [container_name] [image_name] {command_line} # Para crear el contenedor a partir de la imagen {--rm} eliminara el contenedor al finalizar la ejecución del mismo
    docker container ls -al # Para ver el listado de contenedores
    docker rm [container_id] # Para eliminar contenedores
    docker rmi [image_id] # Para eliminar imagenes
  </code></pre>
  <h3>Ejecutando nuestra aplicación</h3>
  <p>Ejecutamos nuestra app al tiempo de crear el container:</p>
  <pre><code>
    docker run -it --name [container_name] [image_name] java -jar [jar_file_name] [input_file] [output_file] [algoritmo]
  </code></pre>
</div>
