echo "************ Camellia-128 **************"
java -jar SecurityAlgorithms.jar -alg Camellia -op keygen -key 128
echo "* "
echo "Camellia-128 ECB PKCS5:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode ECB -pad PKCS5Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode ECB -pad PKCS5Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 ECB PKCS7:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode ECB -pad PKCS7Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode ECB -pad PKCS7Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 ECB ISO10126:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode ECB -pad ISO10126Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode ECB -pad ISO10126Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 CBC PKCS5:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode CBC -pad PKCS5Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode CBC -pad PKCS5Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 CBC PKCS7:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode CBC -pad PKCS7Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode CBC -pad PKCS7Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 CBC ISO10126:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode CBC -pad ISO10126Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode CBC -pad ISO10126Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 CFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode CFB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode CFB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 OFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode OFB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode OFB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 CTR NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode CTR -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode CTR -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 GCM NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode GCM -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode GCM -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 EAX NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode EAX -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode EAX -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-128 OCB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_128.key -mode OCB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_128.key -mode OCB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "****************************************"

echo "************ Camellia-192 **************"
java -jar SecurityAlgorithms.jar -alg Camellia -op keygen -key 192
echo "* "
echo "Camellia-192 ECB PKCS5:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode ECB -pad PKCS5Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode ECB -pad PKCS5Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 ECB PKCS7:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode ECB -pad PKCS7Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode ECB -pad PKCS7Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 ECB ISO10126:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode ECB -pad ISO10126Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode ECB -pad ISO10126Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 CBC PKCS5:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode CBC -pad PKCS5Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode CBC -pad PKCS5Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 CBC PKCS7:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode CBC -pad PKCS7Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode CBC -pad PKCS7Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 CBC ISO10126:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode CBC -pad ISO10126Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode CBC -pad ISO10126Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 CFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode CFB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode CFB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 OFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode OFB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode OFB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 CTR NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode CTR -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode CTR -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 GCM NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode GCM -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode GCM -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 EAX NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode EAX -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode EAX -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-192 OCB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_192.key -mode OCB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_192.key -mode OCB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "****************************************"

echo "************ Camellia-256 **************"
java -jar SecurityAlgorithms.jar -alg Camellia -op keygen -key 256
echo "* "
echo "Camellia-256 ECB PKCS5:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode ECB -pad PKCS5Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode ECB -pad PKCS5Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 ECB PKCS7:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode ECB -pad PKCS7Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode ECB -pad PKCS7Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 ECB ISO10126:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode ECB -pad ISO10126Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode ECB -pad ISO10126Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 CBC PKCS5:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode CBC -pad PKCS5Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode CBC -pad PKCS5Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 CBC PKCS7:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode CBC -pad PKCS7Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode CBC -pad PKCS7Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 CBC ISO10126:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode CBC -pad ISO10126Padding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode CBC -pad ISO10126Padding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 CFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode CFB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode CFB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 OFB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode OFB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode OFB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 CTR NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode CTR -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode CTR -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 GCM NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode GCM -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode GCM -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 EAX NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode EAX -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode EAX -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "* "
echo "Camellia-256 OCB NoPadding:"
java -jar SecurityAlgorithms.jar -alg Camellia -op encrypt -key Camellia_256.key -mode OCB -pad NoPadding -in video.mp4 -out result.dat
java -jar SecurityAlgorithms.jar -alg Camellia -op decrypt -key Camellia_256.key -mode OCB -pad NoPadding -in result.dat -out video_descrypted.mp4
echo "****************************************"
