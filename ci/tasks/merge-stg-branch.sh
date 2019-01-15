#!/bin/sh

cd out
shopt -s dotglob
mv -f ../repo/* ./
git config --global user.email "${GIT_EMAIL}"
git config --global user.name "${GIT_NAME}"
git remote add -f repo-dev ../repo-dev
git merge --no-edit repo-dev/repo-dev

