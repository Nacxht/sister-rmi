#!/bin/bash
# Start Node 2 - SUBTRACT Server

cd "$(dirname "$0")"

# Build the project (using manual compilation)
./compile.sh

# Set RMI properties
export RMI_SERVER_HOST="0.0.0.0"
export RMI_SERVER_PORT="5001"
export MULTIPLY_SERVER_URL="192.168.161.149:5003"

# Start the server
java -cp node_2_subtract/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=$RMI_SERVER_HOST \
     -Drmi.server.host=$RMI_SERVER_HOST \
     -Drmi.server.port=$RMI_SERVER_PORT \
     -Ddivide.server.url=$DIVIDE_SERVER_URL \
     com.calculator.node2.SubtractServer
