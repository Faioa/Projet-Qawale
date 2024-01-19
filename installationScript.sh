#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <JDK Path> <Maven Path>"
    exit 1
fi

jdk_path=$1
maven_path=$2

# Vérifier si les répertoires existent
if [ ! -d "$jdk_path" ] || [ ! -d "$maven_path" ]; then
    echo "These directories don't exist."
    exit 1
fi

export JAVA_HOME="$jdk_path"
export MAVEN_HOME="$maven_path"

export PATH="$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH"

mvn dependency:resolve

mvn compile

echo "Success !"
