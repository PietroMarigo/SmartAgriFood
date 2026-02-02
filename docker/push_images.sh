#!/bin/bash
USERNAME="pietromarigo"
TAG="1.0"

SERVICES=("auth-service" "warehouse-service" "order-service" "office-service" "api-gateway" "frontend")

echo "Rebuilding and Pushing for $USERNAME..."

for SERVICE in "${SERVICES[@]}"; do
  echo "------------------------------------------"
  echo "Building $SERVICE..."

  docker build -t "$SERVICE:latest" "../$SERVICE"

  docker tag "$SERVICE:latest" "$USERNAME/$SERVICE:$TAG"
  docker tag "$SERVICE:latest" "$USERNAME/$SERVICE:latest"

  echo "Pushing $SERVICE to Docker Hub..."
  docker push "$USERNAME/$SERVICE:$TAG"
  docker push "$USERNAME/$SERVICE:latest"
done

echo "All done congratulations"
