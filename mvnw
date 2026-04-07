#!/usr/bin/env sh
DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
MAVEN="$DIR/.tools/apache-maven-3.9.9/bin/mvn.cmd"
if [ ! -f "$MAVEN" ]; then
  echo "Local Maven not found at $MAVEN"
  exit 1
fi
"$MAVEN" "-Dmaven.repo.local=$LOCALAPPDATA/Temp/quantity-m2repo" "$@"
