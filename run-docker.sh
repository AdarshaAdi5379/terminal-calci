#!/usr/bin/env bash
IMAGE_NAME="terminal-calci"
TAG="1.0"
cmd="$1"
shift || true
case "$cmd" in
  build)
    docker build -t ${IMAGE_NAME}:${TAG} .
    ;;
  run)
    docker run --rm ${IMAGE_NAME}:${TAG} "$@"
    ;;
  shell)
    docker run --rm -it ${IMAGE_NAME}:${TAG}
    ;;
  *)
    echo "Usage: $0 {build|run|shell} [args...]"
    exit 1
    ;;
esac
