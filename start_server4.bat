@echo off
REM Start Node 4 - DIVIDE Server

cd /d "%~dp0"

REM Build the project (using manual compilation)
call compile.bat

REM Set RMI properties
set RMI_SERVER_HOST=0.0.0.0
set RMI_SERVER_PORT=5004

REM Start the server
java -cp node_4_divide\build\classes\java\main;common\build\classes\java\main ^
     -Djava.rmi.server.hostname=%RMI_SERVER_HOST% ^
     -Drmi.server.host=%RMI_SERVER_HOST% ^
     -Drmi.server.port=%RMI_SERVER_PORT% ^
     com.calculator.node4.DivideServer