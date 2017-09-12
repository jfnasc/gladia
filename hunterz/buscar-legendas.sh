#!/bin/bash
FOUND="0"
SERIE="$1"

SERIE="$(echo $SERIE | sed -e 's|ballers-2015|ballers|gi')"

echo
echo -----------------------------------------------------
echo $SERIE
echo -----------------------------------------------------
echo

##
# So Legendas
##
URL_ROOT="http://solegendas.com.br"
echo "procurando em $URL_ROOT"

if [ "$FOUND" -eq "0" ];then
  url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "$URL_ROOT/wp-content/uploads/[0-9a-zA-Z_/.]*.zip" | uniq)"
  if [ -n "$url" ];then
    echo "encontrado! :)"
    echo "$url"
    wget "$url"
    echo -----------------------------------------------------
    echo
    FOUND="1"
  fi
fi

if [ "$FOUND" -eq "0" ];then
  url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "$URL_ROOT/?wpdmact=process&did=[0-9a-zA-Z=_]\+" | uniq)"
  if [ -n "$url" ];then
    echo "encontrado! :)"
    echo "$url"
    wget "$url" -O $SERIE.rar
    echo -----------------------------------------------------
    echo
    FOUND="1"
  fi
fi

##
# Legendei
##
URL_ROOT="http://legendei.com"

echo "procurando em $URL_ROOT"

if [ "$FOUND" -eq "0" ];then
  url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "?ofdl=[0-9]*" | uniq)"
  if [ -n "$url" ];then
    echo "encontrado! :)"
    echo "$url"
    wget -O "/tmp/$SERIE" "$URL_ROOT/$url"
    echo -----------------------------------------------------
    echo
    FOUND="1"
  fi
fi

if [ "$FOUND" -eq "0" ];then
  url="$(wget -qO- "$URL_ROOT/$SERIE" | grep -io "$URL_ROOT/download/[0-9]*/" | uniq)"
  if [ -n "$url" ];then
    echo "encontrado! :)"
    echo "$url"
    wget -O "/tmp/$SERIE" "$url"
    echo -----------------------------------------------------
    echo
    FOUND="1"
  fi
fi

if [ "$FOUND" -eq "1" ];then
  echo "Encontrado! :)"
  echo "Verificando o tipo de arquivo [$(file -bi "/tmp/$SERIE")]"
  if [ -n "$(file -bi "/tmp/$SERIE" | grep zip)" ];then
    echo "Movendo o arquivo..."
    mv "/tmp/$SERIE" "./$SERIE.zip"  
  fi
  if [ -n "$(file -bi "/tmp/$SERIE" | grep rar)" ];then
    echo "Movendo o arquivo..."
    mv "/tmp/$SERIE" "./$SERIE.rar"  
  fi

  exit;
fi

echo "Nao encontrei! :("

#http://legendei.com/van-helsing-s01e01/?ofdl=67245
#http://solegendas.com.br/?wpdmact=process&did=MTY2MDUuaG90bGluaw==
#http://solegendas.com.br/wp-content/uploads/2016/10/poldark.2015.s02e04.zip
