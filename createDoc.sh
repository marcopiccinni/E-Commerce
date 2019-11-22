#/bin/bash !

rm -rf ./doc/*.html
cd ./src ;
javadoc -private -d ../doc *.java 