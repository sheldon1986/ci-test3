#!/bin/sh

cd repo
./mvnw test -Dmaven.test.skip=true -Dmaven.repo.local=../m2/rootfs/opt/m2
