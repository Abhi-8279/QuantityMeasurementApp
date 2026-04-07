@echo off
setlocal
set MAVEN_DIR=%~dp0.tools\apache-maven-3.9.9\bin\mvn.cmd
if not exist "%MAVEN_DIR%" (
  echo Local Maven not found at "%MAVEN_DIR%"
  exit /b 1
)
call "%MAVEN_DIR%" -Dmaven.repo.local=%LOCALAPPDATA%\Temp\quantity-m2repo %*
