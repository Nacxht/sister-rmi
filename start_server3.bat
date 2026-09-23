@echo off
REM Start Node 3 - MULTIPLY Server

cd /d "%~dp0"

REM Build the project (using manual compilation)
call compile.bat

REM Set RMI properties
set RMI_SERVER_HOST=0.0.0.0
set RMI_SERVER_PORT=5003
set SUBTRACT_SERVER_URL=192.168.161.149:5003

REM Start the server
java -cp node_3_multiply\build\classes\java\main;common\build\classes\java\main ^
     -Djava.rmi.server.hostname=%RMI_SERVER_HOST% ^
     -Drmi.server.host=%RMI_SERVER_HOST% ^
     -Drmi.server.port=%RMI_SERVER_PORT% ^
     -Dsubtract.server.url=%SUBTRACT_SERVER_URL% ^
     com.calculator.node3.MultiplyServer
