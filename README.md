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
  <hr>
</div>
<div>
  <h2>Algoritmos Implementados</h2>
</div>
<div>
  <h3>Cifrado AES</h3>
  <h4>Modos y Padding soportados por AES (Advanced Encryption Standard)</h4>
  <details>
    <summary>Expandir esta sección</summary>
    <table>
      <tr>
        <th>Modo</th>
        <th>Padding</th>
      </tr>
      <tr>
        <td rowspan="4">ECB</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>PKCS5Padding</td>
      </tr>
      <tr>
        <td>PKCS7Padding</td>
      </tr>
      <tr>
        <td>ISO10126Padding</td>
      </tr>
      <tr>
        <td rowspan="4">CBC</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>PKCS5Padding</td>
      </tr>
      <tr>
        <td>PKCS7Padding</td>
      </tr>
      <tr>
        <td>ISO10126Padding</td>
      </tr>
      <tr>
        <td>CFB</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>OFB</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>CTR</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>GCM (Galois/Counter Mode)</td>
        <td>NoPadding</td>
      </tr>
    </table>
    <p>Notas:</p>
    <ul>
      <li>ECB: Menos seguro debido a la igualdad de cifrados para bloques idénticos de texto plano.</li>
      <li>CBC: Adecuado para la mayoría de las aplicaciones que requieren seguridad mejorada respecto a ECB.</li>
      <li>CFB, OFB, CTR: Modos que permiten operar sobre flujos de datos y no requieren padding.</li>
      <li>GCM: Proporciona cifrado autenticado con eficiencia y es ampliamente utilizado en protocolos de red.</li>
    </ul>
    <p>En GCM se utilizará un vector de inicializacion (IV) de 12 bytes (96 bits), en el resto de modos (excepto ECB que no utiliza IV), se utilizara un IV de 16 bytes (128 bits)</p>
    <p>Ejecución AES</p>
    <pre><code>
      java -jar SecurityAlgorithms.jar [input_file] [output_file] AES-[modo]-[padding]-[key_size]
    </code></pre>
  </details>
</div>
<div>
  <h3>Cifrado Camellia</h3>
  <h4>Modos y Padding soportados por Camellia</h4>
  <details>
    <summary>Expandir esta sección</summary>
    <table>
      <tr>
        <th>Modo</th>
        <th>Padding</th>
      </tr>
      <tr>
        <td rowspan="3">ECB</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>PKCS5Padding</td>
      </tr>
      <tr>
        <td>ISO10126Padding</td>
      </tr>
      <tr>
        <td rowspan="3">CBC</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>PKCS5Padding</td>
      </tr>
      <tr>
        <td>ISO10126Padding</td>
      </tr>
      <tr>
        <td>CFB</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>OFB</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>CTR</td>
        <td>NoPadding</td>
      </tr>
      <tr>
        <td>GCM (Galois/Counter Mode)</td>
        <td>NoPadding</td>
      </tr>
    </table>
    <p>Notas:</p>
      <ul>
        <li>ECB: Menos seguro debido a la igualdad de cifrados para bloques idénticos de texto plano.</li>
        <li>CBC: Adecuado para la mayoría de las aplicaciones que requieren seguridad mejorada respecto a ECB.</li>
        <li>CFB, OFB, CTR: Modos que permiten operar sobre flujos de datos y no requieren padding.</li>
        <li>GCM: Proporciona cifrado autenticado con eficiencia y es ampliamente utilizado en protocolos de red.</li>
      </ul>
      <p>Ejecución Camellia</p>
      <pre><code>
        java -jar SecurityAlgorithms.jar [input_file] [output_file] Camellia-[modo]-[padding]-[key_size]
      </code></pre>
  </details>
</div>
