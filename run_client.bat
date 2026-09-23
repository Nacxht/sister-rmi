@echo off
REM Run Calculator Client

cd /d "%~dp0"

REM Build the project (using manual compilation)
call compile.bat

REM Set client properties
set SERVER_URL=localhost:5001

REM Run the client
java -cp client\build\classes\java\main;common\build\classes\java\main ^
     -Dserver.url=%SERVER_URL% ^
     com.calculator.client.CalculatorClient