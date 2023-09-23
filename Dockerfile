# Use an official Redis as the base image
FROM redis:latest

# Expose the default Redis port
EXPOSE 6379

# Start Redis server
CMD ["redis-server"]
