#!/bin/sh

cd repo
./mvnw package -Dmaven.test.skip=true -Dmaven.repo.local=../m2/rootfs/opt/m2
mv target/*.jar ../output/demo.jar