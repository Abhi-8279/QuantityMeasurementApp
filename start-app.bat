@echo off
start "Quantity Backend" powershell -ExecutionPolicy Bypass -File "%~dp0start-backend.ps1"
start "Quantity Frontend" powershell -ExecutionPolicy Bypass -File "%~dp0start-frontend.ps1"
