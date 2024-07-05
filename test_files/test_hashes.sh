echo "************* Hash Functions***************"

echo "* "
echo "MD5"
java -jar SecurityAlgorithms.jar -alg MD5 -op resume -in video.mp4
java -jar SecurityAlgorithms.jar -alg MD5 -op verify -in video.mp4 -hash md5.txt

echo "* "
echo "SHA-1"
java -jar SecurityAlgorithms.jar -alg SHA-1 -op resume -in video.mp4
java -jar SecurityAlgorithms.jar -alg SHA-1 -op verify -in video.mp4 -hash sha_1.txt

echo "* "
echo "SHA-2 256"
java -jar SecurityAlgorithms.jar -alg SHA-2 -op resume -in video.mp4 -mode 256
java -jar SecurityAlgorithms.jar -alg SHA-2 -op verify -in video.mp4 -mode 256 -hash sha_2.txt

echo "* "
echo "SHA-2 512"
java -jar SecurityAlgorithms.jar -alg SHA-2 -op resume -in video.mp4 -mode 512
java -jar SecurityAlgorithms.jar -alg SHA-2 -op verify -in video.mp4 -mode 512 -hash sha_2.txt

echo "* "
echo "SHA-3 256"
java -jar SecurityAlgorithms.jar -alg SHA-3 -op resume -in video.mp4 -mode 256
java -jar SecurityAlgorithms.jar -alg SHA-3 -op verify -in video.mp4 -mode 256 -hash sha_3.txt

echo "* "
echo "SHA-3 512"
java -jar SecurityAlgorithms.jar -alg SHA-3 -op resume -in video.mp4 -mode 512
java -jar SecurityAlgorithms.jar -alg SHA-3 -op verify -in video.mp4 -mode 512 -hash sha_3.txt

echo "* "
echo "RIPEMD-160"
java -jar SecurityAlgorithms.jar -alg RIPEMD-160 -op resume -input video.mp4
java -jar SecurityAlgorithms.jar -alg RIPEMD-160 -op verify -in video.mp4 -hash ripemd160.txt

echo "* "
echo "WHIRPOOL"
java -jar SecurityAlgorithms.jar -alg WHIRPOOL -in video.mp4 -op resume
java -jar SecurityAlgorithms.jar -alg WHIRPOOL -in video.mp4 -op verify -hash whirpool.txt
