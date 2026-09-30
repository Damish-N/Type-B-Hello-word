# Hello World API

A small Spring Boot web service exposing a single endpoint, `GET /hello-world`.

| Request | Status | Body |
|---|---|---|
| `/hello-world?name=alice` | `200 OK` | `{"message":"Hello Alice"}` |
| `/hello-world?name=zoe` | `400 Bad Request` | `{"error":"Invalid Input"}` |
| `/hello-world` or `/hello-world?name=` | `400 Bad Request` | `{"error":"Invalid Input"}` |

## Tech stack

Java 17, Spring Boot 3.5 (Spring Web), Maven, JUnit 5 + AssertJ + MockMvc.

## Prerequisites

- JDK 17 or later
- Maven 3.9+

## How to run the application

```bash
mvn spring-boot:run
```

Or build a jar and run it:

```bash
mvn clean package
java -jar target/hello-world-api-0.0.1-SNAPSHOT.jar
```

The service starts on port `8080`. Try it:

```bash
curl -i "http://localhost:8080/hello-world?name=alice"   # 200 {"message":"Hello Alice"}
curl -i "http://localhost:8080/hello-world?name=zoe"     # 400 {"error":"Invalid Input"}
curl -i "http://localhost:8080/hello-world"              # 400 {"error":"Invalid Input"}
```

## How to run the tests

```bash
mvn test
```

The suite has three layers:

- `GreetingServiceTest` – plain unit tests of the business rules (no Spring context), including boundary letters (`a`, `m`, `n`, `z`), casing, whitespace, and non-English/non-letter first characters.
- `HelloWorldControllerTest` – `@WebMvcTest` slice tests of the HTTP contract: status codes, content type and JSON fields.
- `HelloWorldIntegrationTest` – starts the full application on a random port and asserts the exact response bodies from the spec.

A GitHub Actions workflow (`.github/workflows/ci.yml`) runs `mvn verify` on every push and pull request.

## Project structure

```
src/main/java/com/example/helloworld
├── HelloWorldApplication.java        # Spring Boot entry point
├── controller/HelloWorldController   # HTTP layer only
├── service/GreetingService           # Business rules
├── dto/GreetingResponse, ErrorResponse  # Response bodies (Java records)
└── exception/InvalidInputException, GlobalExceptionHandler  # Error → HTTP mapping
```

## Assumptions

- **First half of the alphabet** means the English letters `A–M`, case-insensitive. Only ASCII letters are considered; accented or non-Latin letters (`é`, `Å`, `İ`, …) are treated as invalid and return `400`.
- **Leading/trailing whitespace** is trimmed before validation, so `?name=%20alice` is treated as `alice`. A name that is only whitespace is treated as empty.
- **Names starting with a digit or symbol** (e.g. `1alice`, `_bob`) are not in the first half of the alphabet, so they return `400 Invalid Input`.
- **Capitalisation of the response:** only the first letter is upper-cased and the rest is kept as sent (`alice` → `Alice`, `McDonald` → `McDonald`), so intentional casing in names is preserved.
- **Missing parameter** returns the same `{"error":"Invalid Input"}` body as an empty one, rather than Spring's default error response.
- **Repeated parameter** (`?name=alice&name=zoe`): Spring binds repeated values into a single comma-joined string, so the rule is applied to the first value. This was left as-is to keep the solution simple.
- The internal reason for a rejection is logged at `DEBUG` level but never returned to the client; the client always receives the exact `Invalid Input` message required by the spec.
