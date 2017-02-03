#!/bin/bash
SERIE="$1"

##
# So Legendas
##
URL_ROOT="http://legendei.com"
echo "procurando em $URL_ROOT"

url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "?ofdl=[0-9]*" | uniq)"
if [ -n "$url" ];then
  echo "encontrado! :)"
  echo "$url"
  wget -O "$SERIE".rar "$URL_ROOT/$url"
  exit
fi

url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "$URL_ROOT/download/[0-9]*/" | uniq)"
if [ -n "$url" ];then
  echo "encontrado! :)"
  echo "$url"
  wget -O "$SERIE".rar "$url"
  exit
fi

##
# So Legendas
##
URL_ROOT="http://solegendas.com.br"
echo "procurando em $URL_ROOT"

url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "$URL_ROOT/wp-content/uploads/[0-9a-zA-Z_/.]*.zip" | uniq)"
if [ -n "$url" ];then
  echo "encontrado! :)"
  echo "$url"
  wget "$url"
  exit
fi

url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "$URL_ROOT/?wpdmact=process&did=[0-9a-zA-Z=_]\+" | uniq)"
if [ -n "$url" ];then
  echo "encontrado! :)"
  echo "$url"
  wget "$url" -O $SERIE.rar
  exit
fi

echo "Nao encontrei! :("

#http://legendei.com/van-helsing-s01e01/?ofdl=67245
#http://solegendas.com.br/?wpdmact=process&did=MTY2MDUuaG90bGluaw==
#http://solegendas.com.br/wp-content/uploads/2016/10/poldark.2015.s02e04.zip
