#!/bin/bash
# Start Node 3 - MULTIPLY Server

cd "$(dirname "$0")"

# Build the project (using manual compilation)
./compile.sh

# Set RMI properties
export RMI_SERVER_HOST="192.168.161.16"
export RMI_SERVER_PORT="5003"
export SUBTRACT_SERVER_URL="192.168.161.149:5002"

# Start the server
java -cp node_3_multiply/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=$RMI_SERVER_HOST \
     -Drmi.server.host=$RMI_SERVER_HOST \
     -Drmi.server.port=$RMI_SERVER_PORT \
     -Dsubtract.server.url=$SUBTRACT_SERVER_URL \
     com.calculator.node3.MultiplyServer
