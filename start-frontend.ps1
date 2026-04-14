$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$nodeHome = Join-Path $root ".tools\node-v24.14.1-win-x64"
$npm = Join-Path $nodeHome "npm.cmd"

if (-not (Test-Path $npm)) {
    throw "Local Node/npm not found at $nodeHome"
}

$env:PATH = "$nodeHome;$env:PATH"

Set-Location (Join-Path $root "frontend")
& $npm run dev
