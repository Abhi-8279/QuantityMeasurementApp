@echo off
setlocal
set "MAVEN_HOME=%~dp0.tools\apache-maven-3.9.9"

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
  echo Maven not found at "%MAVEN_HOME%".
  exit /b 1
)

set "PATH=%MAVEN_HOME%\bin;%PATH%"
"%MAVEN_HOME%\bin\mvn.cmd" %*