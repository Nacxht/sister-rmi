@echo off
REM Start Node 1 - ADD Server

cd /d "%~dp0"

REM Build the project (using manual compilation)
call compile.bat

REM Set RMI properties
set RMI_SERVER_HOST=0.0.0.0
set RMI_SERVER_PORT=5001
set MULTIPLY_SERVER_URL=192.168.161.16:5003

REM Start the server
java -cp node_1_add\build\classes\java\main;common\build\classes\java\main ^
     -Djava.rmi.server.hostname=%RMI_SERVER_HOST% ^
     -Drmi.server.host=%RMI_SERVER_HOST% ^
     -Drmi.server.port=%RMI_SERVER_PORT% ^
     -Dmultiply.server.url=%MULTIPLY_SERVER_URL% ^
     com.calculator.node1.AddServer
