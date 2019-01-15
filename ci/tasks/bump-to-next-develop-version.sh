#!/bin/sh

VERSION=`cat version/number`-SNAPSHOT
MESSAGE="[Concourse CI] Bump to Next Development Version ($VERSION)"

cd out
shopt -s dotglob
mv -f ../repo/* ./
git remote add -f master ../repo-master
git merge --no-edit master/master
echo "Bump to $VERSION"
./mvnw versions:set -DnewVersion=${VERSION} -DallowSnapshots -Dmaven.repo.local=../m2/rootfs/opt/m2
git config --global user.email "${GIT_EMAIL}"
git config --global user.name "${GIT_NAME}"
git add pom.xml
git commit -m "${MESSAGE}"