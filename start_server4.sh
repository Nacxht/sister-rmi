#!/bin/bash
# Start Node 4 - DIVIDE Server

cd "$(dirname "$0")"

# Build the project (using manual compilation)
./compile.sh

# Set RMI properties
export RMI_SERVER_HOST="192.168.161.238"
export RMI_SERVER_PORT="5004"

# Start the server
java -cp node_4_divide/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=$RMI_SERVER_HOST \
     -Drmi.server.host=$RMI_SERVER_HOST \
     -Drmi.server.port=$RMI_SERVER_PORT \
     com.calculator.node4.DivideServer