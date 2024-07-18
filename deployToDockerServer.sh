#!/bin/bash
export DOCKER_HOST="tcp://192.168.0.6:2376"
# Define the Docker context name and host
CONTEXT_NAME="remote"
DOCKER_HOST="tcp://192.168.0.6:2376"  # Replace with your Docker host details

# Function to check if the context already exists
context_exists() {
    docker context ls | grep -q "^$CONTEXT_NAME "
}

# Function to create a new Docker context
create_context() {
    echo "Creating Docker context '$CONTEXT_NAME'..."
    docker context create $CONTEXT_NAME --docker "host=$DOCKER_HOST"
    if [ $? -eq 0 ]; then
        echo "Context '$CONTEXT_NAME' created successfully."
    else
        echo "Failed to create context '$CONTEXT_NAME'."
    fi
}

# Main script logic
if context_exists; then
    echo "Context '$CONTEXT_NAME' already exists."
else
    create_context
fi
docker-compose up --build -d