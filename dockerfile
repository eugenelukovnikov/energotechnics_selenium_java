FROM maven:3.9.10-eclipse-temurin-21 AS build

# Install dependencies for Chrome
RUN apt-get update && apt-get install -y wget \
    && wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && apt-get install -y ./google-chrome-stable_current_amd64.deb \
    && rm google-chrome-stable_current_amd64.deb

# Node.js 20 + npm
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs

# Allure 3  
RUN npm install -g allure-commandline && \
    npm install -g allure

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Set environment variables for Headless Chrome
ENV CHROME_BIN=/usr/bin/google-chrome-stable

# Command to run tests and generate allure report
CMD ["/bin/bash", "-c", "mvn test -Dheadless=true -Dmaven.test.failure.ignore=true && allure generate allure-results -o allure-report && \
    echo 'Allure report generated in allure-report/'"]
