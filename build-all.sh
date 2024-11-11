#!/bin/bash
value=`cat version.txt`
echo "image version read $value"

for i in $(ls -d */);
do 
    echo "building repo ${i%/}"; 
    cd ${i%/} && 
    ./gradlew clean build jar
    docker build -t shamsmali/${i%/}:${value} .;
    docker push shamsmali/${i%/}:${value};    
    cd ..
done