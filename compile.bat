@echo off
REM Manual compilation script for Java RMI project (alternative to Gradle)

echo Compiling Java RMI project...

REM Create build directories
if not exist common\build\classes\java\main mkdir common\build\classes\java\main
if not exist node_1_add\build\classes\java\main mkdir node_1_add\build\classes\java\main
if not exist node_2_subtract\build\classes\java\main mkdir node_2_subtract\build\classes\java\main
if not exist node_3_multiply\build\classes\java\main mkdir node_3_multiply\build\classes\java\main
if not exist node_4_divide\build\classes\java\main mkdir node_4_divide\build\classes\java\main
if not exist client\build\classes\java\main mkdir client\build\classes\java\main

REM Compile common module
echo Compiling common module...
javac -d common\build\classes\java\main ^
    common\src\main\java\com\calculator\common\*.java

REM Compile node modules
echo Compiling Node 1 (ADD)...
javac -cp common\build\classes\java\main ^
    -d node_1_add\build\classes\java\main ^
    node_1_add\src\main\java\com\calculator\node1\*.java

echo Compiling Node 2 (SUBTRACT)...
javac -cp common\build\classes\java\main ^
    -d node_2_subtract\build\classes\java\main ^
    node_2_subtract\src\main\java\com\calculator\node2\*.java

echo Compiling Node 3 (MULTIPLY)...
javac -cp common\build\classes\java\main ^
    -d node_3_multiply\build\classes\java\main ^
    node_3_multiply\src\main\java\com\calculator\node3\*.java

echo Compiling Node 4 (DIVIDE)...
javac -cp common\build\classes\java\main ^
    -d node_4_divide\build\classes\java\main ^
    node_4_divide\src\main\java\com\calculator\node4\*.java

echo Compiling Client...
javac -cp common\build\classes\java\main ^
    -d client\build\classes\java\main ^
    client\src\main\java\com\calculator\client\*.java

echo Compilation complete!