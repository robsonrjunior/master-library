# Master Library

A personal catalog for tracking books, movies, and series you have already watched or read. Users can rate items and leave comments.

Built with **Jakarta EE 11** on **Payara Micro**, using Java 21.

---

## Requirements

- [Java SE 21+](https://adoptium.net)
- [Maven Wrapper](https://maven.apache.org/wrapper/) (already included — no Maven install needed)
- [Docker](https://docs.docker.com/get-docker/) _(optional, for container-based run)_

## Development Notes

- Model classes in `src/main/java/com/github/robsonrjunior/master/library/model` should use Lombok annotations for boilerplate (`@Getter`, `@Setter`, etc.) instead of manual accessors.
- Enable annotation processing in your IDE so Lombok-generated methods are recognized during development.
- Verify local changes with `./mvnw -q -DskipTests compile`.

> First time only: make the Maven Wrapper executable.
>
> ```bash
> chmod +x mvnw
> ```

---

## Running the Project

### Standard

Load environment variables and start the application:

```bash
export $(grep -v '^#' .env | xargs)
./mvnw clean package payara-micro:start
```

Access the application at [http://localhost:8080/master-library](http://localhost:8080/master-library).

---

### Docker

Build the image:

```bash
./mvnw clean package
docker build -t master-library:v1 .
```

Run the container:

```bash
docker run -it --rm --env-file .env -p 8080:8080 master-library:v1
```

Access the application at [http://localhost:8080/master-library](http://localhost:8080/master-library).

---

### Hot Reload

Start the application in development mode with automatic reload on code changes:

```bash
export $(grep -v '^#' .env | xargs)
./mvnw package payara-micro:dev
```

### Remove current process

```bash
sudo fuser -k 8080/tcp
```
