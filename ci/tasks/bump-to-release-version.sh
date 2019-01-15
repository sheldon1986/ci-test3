#!/bin/sh

VERSION=`cat version/number`
MESSAGE="[Concourse CI] Release $VERSION"

cd out
shopt -s dotglob
mv -f ../repo/* ./
echo "Bump to ($VERSION)"
./mvnw versions:set -DnewVersion=${VERSION} -Dmaven.repo.local=../m2/rootfs/opt/m2
git config --global user.email "${GIT_EMAIL}"
git config --global user.name "${GIT_NAME}"
git add pom.xml
git commit -m "${MESSAGE}"