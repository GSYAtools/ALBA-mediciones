<div style="text-align: justify; text-justify: inter-word;">
  <p>Puedes clonar este repositorio:</p>
  <pre><code>git clone https://github.com/GSYAtools/ALBA-mediciones</code></pre>
</div>

<div style="text-align: justify; text-justify: inter-word;">
  <h1>ALBA: Medición de consumo para algoritmos de seguridad</h1>
  <p>En este repositorio se contiene una colección de algoritmos de seguridad: cifrados simétricos, asimétricos, funciones de resumen... con el objetivo de conocer el consumo de recursos realizado por los mismos en persecución de conocer cual de ellos es más eficiente respecto al consumo de recursos y la seguridad que cada uno aporta</p>
</div>

<div>
  <h2>Algoritmos de Cifrado Asimétrico</h2>
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
</div>
