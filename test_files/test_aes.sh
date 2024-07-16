echo "************ AES-128 **************"
java -jar SecurityAlgorithms.jar -alg AES -op keygen -key 128
echo "AES-128 ECB PKCS5:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode ECB -pad PKCS5Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode ECB -pad PKCS5Padding -in result.dat -out output.pdf
echo "AES-128 ECB ISO10126:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode ECB -pad ISO10126Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode ECB -pad ISO10126Padding -in result.dat -out output.pdf
echo "AES-128 CBC PKCS5:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode CBC -pad PKCS5Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode CBC -pad PKCS5Padding -in result.dat -out output.pdf
echo "AES-128 CBC ISO10126:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode CBC -pad ISO10126Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode CBC -pad ISO10126Padding -in result.dat -out output.pdf
echo "AES-128 CFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode CFB -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode CFB -pad NoPadding -in result.dat -out output.pdf
echo "AES-128 OFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode OFB -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode OFB -pad NoPadding -in result.dat -out output.pdf
echo "AES-128 CTR NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode CTR -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode CTR -pad NoPadding -in result.dat -out output.pdf
echo "AES-128 GCM NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_128.key -mode GCM -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_128.key -mode GCM -pad NoPadding -in result.dat -out output.pdf
echo "****************************************"

echo "************ AES-192 **************"
java -jar SecurityAlgorithms.jar -alg AES -op keygen -key 192
echo "AES-192 ECB PKCS5:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode ECB -pad PKCS5Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode ECB -pad PKCS5Padding -in result.dat -out output.pdf
echo "AES-192 ECB ISO10126:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode ECB -pad ISO10126Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode ECB -pad ISO10126Padding -in result.dat -out output.pdf
echo "AES-192 CBC PKCS5:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode CBC -pad PKCS5Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode CBC -pad PKCS5Padding -in result.dat -out output.pdf
echo "AES-192 CBC ISO10126:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode CBC -pad ISO10126Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode CBC -pad ISO10126Padding -in result.dat -out output.pdf
echo "AES-192 CFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode CFB -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode CFB -pad NoPadding -in result.dat -out output.pdf
echo "AES-192 OFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode OFB -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode OFB -pad NoPadding -in result.dat -out output.pdf
echo "AES-192 CTR NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode CTR -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode CTR -pad NoPadding -in result.dat -out output.pdf
echo "AES-192 GCM NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_192.key -mode GCM -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_192.key -mode GCM -pad NoPadding -in result.dat -out output.pdf
echo "****************************************"

echo "************ AES-256 **************"
java -jar SecurityAlgorithms.jar -alg AES -op keygen -key 256
echo "AES-256 ECB PKCS5:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode ECB -pad PKCS5Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode ECB -pad PKCS5Padding -in result.dat -out output.pdf
echo "AES-256 ECB ISO10126:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode ECB -pad ISO10126Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode ECB -pad ISO10126Padding -in result.dat -out output.pdf
echo "AES-256 CBC PKCS5:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode CBC -pad PKCS5Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode CBC -pad PKCS5Padding -in result.dat -out output.pdf
echo "AES-256 CBC ISO10126:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode CBC -pad ISO10126Padding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode CBC -pad ISO10126Padding -in result.dat -out output.pdf
echo "AES-256 CFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode CFB -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode CFB -pad NoPadding -in result.dat -out output.pdf
echo "AES-256 OFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode OFB -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode OFB -pad NoPadding -in result.dat -out output.pdf
echo "AES-256 CTR NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode CTR -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode CTR -pad NoPadding -in result.dat -out output.pdf
echo "AES-256 GCM NoPadding:"
java -jar SecurityAlgorithms.jar -alg AES -op encrypt -key AES_256.key -mode GCM -pad NoPadding -in input.pdf -out result.dat
java -jar SecurityAlgorithms.jar -alg AES -op decrypt -key AES_256.key -mode GCM -pad NoPadding -in result.dat -out output.pdf
echo "****************************************"
