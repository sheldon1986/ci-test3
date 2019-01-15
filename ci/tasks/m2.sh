#!/bin/sh

if [ "$1" == "init" ]; then
	mkdir -p ../m2/rootfs/opt/m2
fi

cd repo
./mvnw clean package -Dmaven.repo.local=../m2/rootfs/opt/m2
# download install versions plugin
./mvnw versions:set -DnewVersion=5.0.0 -Dmaven.repo.local=../m2/rootfs/opt/m2
cd ../m2
tar -C rootfs -cf rootfs.tar .
mv rootfs.tar ../to-push