#!/bin/sh

cd out
shopt -s dotglob
mv -f ../repo/* ./
git config --global user.email "${GIT_EMAIL}"
git config --global user.name "${GIT_NAME}"
#git remote add -f repo-stg ../repo-stg
#git merge --no-edit repo-stg/repo-stg
git checkout master
git merge staging
