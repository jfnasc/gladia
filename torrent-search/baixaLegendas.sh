#!/bin/bash

URL_ROOT="http://solegendas.com.br"
SERIE="$1"

url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -o "$URL_ROOT/wp-content/uploads/[0-9a-zA-Z_/.]*.zip" | uniq)"
if [ -n "$url" ];then
  wget "$url"
  exit
fi

url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -o "$URL_ROOT/?wpdmact=process&did=[0-9a-zA-Z=_]\+" | uniq)"
if [ -n "$url" ];then
  wget "$url" -O $SERIE.rar
  exit
fi

#http://solegendas.com.br/?wpdmact=process&did=MTY2MDUuaG90bGluaw==
#http://solegendas.com.br/wp-content/uploads/2016/10/poldark.2015.s02e04.zip
