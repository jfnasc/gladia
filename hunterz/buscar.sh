#!/bin/bash

CLASSPATH="."

for jar in $(ls lib | grep '.jar');do
  CLASSPATH="$CLASSPATH:./lib/$jar"
done

java -cp "$CLASSPATH" org.avalon.app.Main
