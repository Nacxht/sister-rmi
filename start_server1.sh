#!/bin/bash
# Start Node 1 - ADD Server

cd "$(dirname "$0")"

# Build the project (using manual compilation)
./compile.sh

# Set RMI properties
export RMI_SERVER_HOST="192.168.161.137"
export RMI_SERVER_PORT="5001"
export MULTIPLY_SERVER_URL="192.168.161.16:5003"

# Start the server
java -cp node_1_add/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=$RMI_SERVER_HOST \
     -Drmi.server.host=$RMI_SERVER_HOST \
     -Drmi.server.port=$RMI_SERVER_PORT \
     -Dmultiply.server.url=$MULTIPLY_SERVER_URL \
     com.calculator.node1.AddServer
