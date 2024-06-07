# Contributing to Fellow-chat

Thank you for considering contributing to Fellow-chat! We're excited to have your help.

## Code of Conduct

Please read and follow our [Code of Conduct](CODE_OF_CONDUCT.md) to ensure a welcoming environment for all contributors.

## Setting Up the Development Environment

1. Clone the repository:
    ```sh
    git clone https://github.com/username/Fellow-chat.git
    ```
2. Navigate to the project directory:
    ```sh
    cd Fellow-chat
    ```
3. Build the project with Gradle:
    ```sh
    gradle build
    ```

## Running the Application

To run the application with Docker:

1. Build the Docker image:
    ```sh
    docker build -t svalka.cr.cloud.ru/giga-fellow-chat:latest .
    ```
2. Start the application using Docker Compose:
    ```sh
    docker-compose up
    ```
   You can add the `-d` flag to run the containers in the background.

## Creating a New Branch

Before you start working, create a new branch for your changes:

```shell
git checkout -b feature/your-feature-name
```

## Making Commits

Write clear, concise commit messages following this format:

Short (50 chars or fewer) summary of changes

More detailed explanation of the changes, if necessary. Wrap it to about 72 characters or so. In some cases, it's fine
to leave this section blank.

## Running Tests

Make sure your changes pass all tests. Add new tests if necessary to cover your changes:

```shell
gradle test #or ./gradlew test
```

## Creating a Pull Request

When your changes are ready, create a pull request with a clear description of the changes and any additional context.
Follow our PR template if provided.

## Review Process

Your pull request will be reviewed by project maintainers. They may suggest improvements or request changes. Be
responsive to feedback and be prepared to iterate on your PR.

## License

By contributing to Fellow-chat, you agree that your contributions will be licensed under the project's MIT License.