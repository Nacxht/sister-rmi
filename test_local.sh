#!/bin/bash
# Test script for running all servers locally

cd "$(dirname "$0")"

echo "Building project..."
./compile.sh

echo "Starting servers in background..."

# Start Node 4 (DIVIDE) - no dependencies
java -cp node_4_divide/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=localhost \
     -Drmi.server.host=localhost \
     -Drmi.server.port=5004 \
     com.calculator.node4.DivideServer &
NODE4_PID=$!
echo "Node 4 (DIVIDE) started with PID: $NODE4_PID"

sleep 2

# Start Node 2 (SUBTRACT) - depends on Node 4
java -cp node_2_subtract/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=localhost \
     -Drmi.server.host=localhost \
     -Drmi.server.port=5002 \
     -Ddivide.server.url=localhost:5004 \
     com.calculator.node2.SubtractServer &
NODE2_PID=$!
echo "Node 2 (SUBTRACT) started with PID: $NODE2_PID"

sleep 2

# Start Node 3 (MULTIPLY) - depends on Node 2
java -cp node_3_multiply/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=localhost \
     -Drmi.server.host=localhost \
     -Drmi.server.port=5003 \
     -Dsubtract.server.url=localhost:5002 \
     com.calculator.node3.MultiplyServer &
NODE3_PID=$!
echo "Node 3 (MULTIPLY) started with PID: $NODE3_PID"

sleep 2

# Start Node 1 (ADD) - depends on Node 3
java -cp node_1_add/build/classes/java/main:common/build/classes/java/main \
     -Djava.rmi.server.hostname=localhost \
     -Drmi.server.host=localhost \
     -Drmi.server.port=5001 \
     -Dmultiply.server.url=localhost:5003 \
     com.calculator.node1.AddServer &
NODE1_PID=$!
echo "Node 1 (ADD) started with PID: $NODE1_PID"

sleep 3

echo "Running client..."
java -cp client/build/classes/java/main:common/build/classes/java/main \
     -Dserver.url=localhost:5001 \
     com.calculator.client.CalculatorClient

echo ""
echo "Cleaning up..."
kill $NODE1_PID $NODE2_PID $NODE3_PID $NODE4_PID 2>/dev/null
echo "Test complete"