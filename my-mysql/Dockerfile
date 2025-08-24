# Use the official MySQL 8.0 image from Docker Hub
FROM mysql:8.0.42

# Environment variables
ENV MYSQL_ALLOW_EMPTY_PASSWORD="yes"
ENV MYSQL_DATABASE="db_account"
ENV MYSQL_ROOT_PASSWORD=""

# Copy custom MySQL configuration if needed
# COPY ./mysql-custom.cnf /etc/mysql/conf.d/mysql-custom.cnf

# Expose port (optional)
EXPOSE 3306

# Define a volume for persisting data (optional)
VOLUME /var/lib/mysql

# Optionally, specify a health check command if needed
# HEALTHCHECK --interval=30s --timeout=3s \
#   CMD mysqladmin ping -h 127.0.0.1 || exit 1

# Start MySQL server
CMD ["mysqld"]