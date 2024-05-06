![Bot](https://github.com/sanyarnd/java-course-2023-backend-template/actions/workflows/bot.yml/badge.svg)
![Scrapper](https://github.com/sanyarnd/java-course-2023-backend-template/actions/workflows/scrapper.yml/badge.svg)

# Link Tracker

## Project Overview

Link Tracker is an application designed to monitor content updates at specified URLs and send notifications to Telegram when new events are detected.

## Features

- **Telegram Bot**: Sends notifications to Telegram when tracked content updates.
- **Scraper**: Monitors specified URLs for content changes.

## Requirements

- Java 21
- Spring Boot 3
- Docker

## Installation

Clone the repository and navigate to the project directory:

```bash
git clone https://github.com/LucidMinded/java-course-2023-backend.git
cd java-course-2023-backend
```

To build the project, use the following command:

```bash
./mvnw clean package -DskipTests
```

To start the application, use the following commands:

Windows:
```cmd
./run.cmd <your-bot-telegram-token>
```

Linux/MacOS:
```bash
docker-compose up -d
java -jar scrapper/target/scrapper.jar
export TOKEN=<your-bot-telegram & java -jar ./bot/target/bot.jar
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request with your changes.
