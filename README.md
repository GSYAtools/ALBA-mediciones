<div style="text-align: justify; text-justify: inter-word;">
  <sub>Puedes clonar este repositorio:
  <pre><code>git clone https://github.com/GSYAtools/ALBA-mediciones</code></pre></sub>
</div>

<div style="text-align: justify; text-justify: inter-word;">
  <h1>ALBA: Medición de consumo para algoritmos de seguridad</h1>
  <p>En este repositorio se contiene una colección de algoritmos de seguridad: cifrados simétricos, asimétricos, funciones de resumen... con el objetivo de conocer el consumo de recursos realizado por los mismos en persecución de conocer cual de ellos es más eficiente respecto al consumo de recursos y la seguridad que cada uno aporta</p>
</div>

<div>
  <h2>Algoritmos de Cifrado Simétrico</h2>
  <h3>AES (Advanced Encryption Standard)</h3>
  <table>
    <tr>
    <th>Modo</th>
    <th>Padding</th>
    </tr>
    <tr>
    <td rowspan="2">ECB</td>
      <td>PKCS5Padding</td>
    </tr>
    <tr>
      <td>ISO10126Padding</td>
    </tr>
    <tr>
    <td rowspan="2">CBC</td>
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
  <pre><code>java -jar SecurityAlgorithms -alg aes -op keygen -key 128|192|256</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg aes -op encrypt|decrypt -mode MODO -pad PADDING -key key_file_path -in input_file_path -out output_file_path</code></pre>
  
  <h3>Camellia Cipher</h3>
  <table>
    <tr>
    <th>Modo</th>
    <th>Padding</th>
    </tr>
    <tr>
      <td rowspan="2">ECB</td>
      <td>PKCS5Padding</td>
    </tr>
    <tr>
      <td>ISO10126Padding</td>
    </tr>
    <tr>
      <td rowspan="2">CBC</td>
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
  <pre><code>java -jar SecurityAlgorithms -alg camellia -op keygen -key 128|192|256</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg camellia -op encrypt|decrypt -mode MODO -pad PADDING -key key_file_path -in input_file_path -out output_file_path</code></pre>
</div>

<div>
  <h2>Algoritmos de Cifrado Simétrico</h2>
  <h3>RSA</h3>
</div>

<div>
  <h2>Funciones de Resumen</h2>
  <h3>MD5</h3>
  <pre><code>java -jar SecurityAlgorithms -alg md5 -op resume -in input_file_path</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg md5 -op verify -hash hash_file_path -in file_path</code></pre>

  <h3>SHA-1, SHA-2, SHA-3</h3>
  <pre><code>java -jar SecurityAlgorithms -alg sha-1 -op resume -in input_file_path</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg sha-1 -op verify -hash hash_file_path -in file_path</code></pre>
  <br>
  <pre><code>java -jar SecurityAlgorithms -alg sha-2 -op resume -mode 256|512 -in input_file_path</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg sha-2 -op verify -mode 256|512 -hash hash_file_path -in file_path</code></pre>
  <br>
  <pre><code>java -jar SecurityAlgorithms -alg sha-3 -op resume -mode 256|512 -in input_file_path</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg sha-3 -op verify -mode 256|512 -hash hash_file_path -in file_path</code></pre>

  <h3>RIPEMD-160</h3>
  <pre><code>java -jar SecurityAlgorithms -alg ripemd-160 -op resume -in input_file_path</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg ripemd-160 -op verify -hash hash_file_path -in file_path</code></pre>

  <h3>WHIRPOOL</h3>
  <pre><code>java -jar SecurityAlgorithms -alg whirpool -op resume -in input_file_path</code></pre>
  <pre><code>java -jar SecurityAlgorithms -alg whirpool -op verify -hash hash_file_path -in file_path</code></pre>
</div>
