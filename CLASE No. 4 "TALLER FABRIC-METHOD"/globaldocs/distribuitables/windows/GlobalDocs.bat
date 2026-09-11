@echo off
:: GlobalDocs Document Processor - Windows Launcher
:: Double-click this file to launch the application

title GlobalDocs Document Processor

:: Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ============================================
    echo   GlobalDocs - Java is Required
    echo ============================================
    echo.
    echo   Java is not installed or not in PATH.
    echo.
    echo   Please install Java 17 or later from:
    echo   https://adoptium.net/
    echo.
    pause
    exit /b 1
)

:: Get Java version
for /f "tokens=3" %%a in ('java -version 2^>^&1 ^| findstr /i "version"') do set JAVA_VER=%%~a
for /f "delims=. tokens=1" %%a in ("%JAVA_VER%") do set JAVA_MAJOR=%%a

if %JAVA_MAJOR% LSS 17 (
    echo.
    echo ============================================
    echo   GlobalDocs - Java Version Too Old
    echo ============================================
    echo.
    echo   Java 17 or later is required.
    echo   Current version: %JAVA_VER%
    echo.
    echo   Please install Java 17+ from:
    echo   https://adoptium.net/
    echo.
    pause
    exit /b 1
)

:: Launch the application
echo Starting GlobalDocs Document Processor...
cd /d "%~dp0"
start "" javaw -jar globaldocs-document-processor-1.0.0.jar
