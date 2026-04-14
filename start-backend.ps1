$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$mavenHome = Join-Path $root ".tools\apache-maven-3.9.9"
$mvn = Join-Path $mavenHome "bin\mvn.cmd"

if (-not (Test-Path $mvn)) {
    throw "Local Maven not found at $mavenHome"
}

$env:PATH = "$($mavenHome)\bin;$env:PATH"

Set-Location $root
& $mvn spring-boot:run
