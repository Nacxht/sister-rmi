#!/bin/bash
# Run Calculator Client

cd "$(dirname "$0")"

# Build the project (using manual compilation)
./compile.sh

# Set client properties
export SERVER_URL="192.168.161.137:5001"

# Run the client
java -cp client/build/classes/java/main:common/build/classes/java/main \
     -Dserver.url=$SERVER_URL \
     com.calculator.client.CalculatorClient