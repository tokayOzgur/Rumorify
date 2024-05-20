# docker-refresh.ps1

# Stop and remove existing containers
docker-compose down

# Remove unused images, networks, and volumes
docker system prune -f

# Build and start containers
docker-compose up --build -d

# Display the status of the running containers
docker ps

# To run the services with the override file, use the following command:
# open a GitBash terminal and run bash docker-refresh.sh