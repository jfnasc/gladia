#!/bin/bash
arg0="$1"

if [ -n "$arg0" ];then
  buscar-legendas "$arg0"
  exit;
fi

for serie in $(ls | grep -o '[0-9A-Za-z.-]\+S[0-9]\{2\}E[0-9]\{2\}');do
    serie="$(echo $serie | awk '{print tolower($0)}' | sed -e 's|\.|-|gi')"
    
    serie="$(echo $serie | sed -e 's|poldark-2015|poldark|gi')"
    
    buscar-legendas $serie
done    