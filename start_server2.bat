@echo off
REM Start Node 2 - SUBTRACT Server

cd /d "%~dp0"

REM Build the project (using manual compilation)
call compile.bat

REM Set RMI properties
set RMI_SERVER_HOST=0.0.0.0
set RMI_SERVER_PORT=5002
set DIVIDE_SERVER_URL=192.168.161.238:5004

REM Start the server
java -cp node_2_subtract\build\classes\java\main;common\build\classes\java\main ^
     -Djava.rmi.server.hostname=%RMI_SERVER_HOST% ^
     -Drmi.server.host=%RMI_SERVER_HOST% ^
     -Drmi.server.port=%RMI_SERVER_PORT% ^
     -Ddivide.server.url=%DIVIDE_SERVER_URL% ^
     com.calculator.node2.SubtractServer
