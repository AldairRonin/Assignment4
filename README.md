## A. SOLID Documentation

### SRP — Single Responsibility Principle

Each class has one responsibility:

* `Media`, `Song`, `Podcast` — domain models
* `Repository` classes — database access
* `Service` classes — business logic
* `Main` — user interaction

---

### OCP — Open/Closed Principle

`Media` is extended by `Song` and `Podcast`.
New media types can be added without changing existing code.

---

### LSP — Liskov Substitution Principle

`Song` and `Podcast` can be used anywhere `Media` is expected.
They correctly override abstract methods.

---

### ISP — Interface Segregation Principle

Small, focused interfaces:

 `Playable` → `play()`
 `Validatable` → `validate()`

Classes implement only what they need.

---

### DIP — Dependency Inversion Principle

Services depend on interfaces, not implementations.
`MediaService` uses repository abstraction injected via constructor.

---

## B. Advanced OOP Features

### Generics

Used in collections and repositories, e.g. `List<Media>` and generic CRUD-style interfaces to ensure type safety.

---

### Lambdas

Used for concise iteration and filtering logic (e.g. processing media lists with Java lambda expressions).

---

### Reflection

Used via `ReflectionUtils` to inspect runtime class information of `Media` objects (class name, methods, fields).

---

### Interface default / static methods

Interfaces define behavior contracts (`Playable`, `Validatable`), keeping implementation clean and reusable.
---

## C. OOP Documentation

### Abstract class + subclasses

`Media` is an abstract class extended by `Song` and `Podcast`, which implement type-specific behavior.

---

### Composition relationships

`Playlist` contains a collection of `Media` objects, forming a composition relationship.

---

### Polymorphism examples

`Media` references are used to work with both `Song` and `Podcast` objects via overridden methods like `play()`.

---

### UML diagram (updated)



The UML diagram shows inheritance (`Media → Song/Podcast`), interfaces, and composition (`Playlist → Media`).

---

## D. Database & JDBC Usage

The project uses **PostgreSQL** as a relational database.

JDBC is used to:

* Connect to the database via `DatabaseConnection`
* Execute SQL queries using `PreparedStatement`
* Perform CRUD operations in repository classes (`MediaRepository`, `PlaylistRepository`)

Tables are created using `schema.sql`, including:

* `media`
* `playlists`
* `playlist_media` (many-to-many relationship)

All database logic is isolated in the **repository layer**.

---
Вот **короткий и чёткий текст** для **D. Database / JDBC Documentation**, в стиле требований README 👌

---

## D. Database & JDBC Usage

The project uses **PostgreSQL** as a relational database.

JDBC is used to:

* Connect to the database via `DatabaseConnection`
* Execute SQL queries using `PreparedStatement`
* Perform CRUD operations in repository classes (`MediaRepository`, `PlaylistRepository`)

Tables are created using `schema.sql`, including:

* `media`
* `playlists`
* `playlist_media` (many-to-many relationship)

All database logic is isolated in the **repository layer**.

---
Вот **короткий и аккуратный текст** для **E. Exception Handling**, полностью под требования README ✅

---

Вот **короткое и чёткое описание для README — пункт E** 👇

---

## E. Architecture Explanation

### Controller Layer

The `Main` class acts as a controller.
It handles user input, shows menus, and calls service methods.
No database logic is placed here.

### Service Layer

Service classes (`PlaylistService`, `MediaService`) contain business logic.
They validate input and coordinate operations between the controller and repositories.

### Repository Layer

Repository classes (`MediaRepository`, `PlaylistRepository`) handle all database operations.
They execute SQL queries and map database records to Java objects.

### Request / Response Flow

1. User selects an action in `Main`
2. `Main` calls a method in the Service layer
3. Service calls Repository methods
4. Repository interacts with the database
5. Result is returned back through Service to `Main` and printed to the user

---

## F.  How to Compile and Run

1. Make sure PostgreSQL is running.
2. Create the database and tables using `schema.sql`.
3. Update database credentials in `DatabaseConnection.java`.
4. Compile the project:

```bash
javac -d bin src/**/*.java
```

5. Run the application:

```bash
java -cp bin Main
```

---

## Requirements

* **Java:** JDK 17 or higher
* **Database:** PostgreSQL
* **JDBC Driver:** PostgreSQL JDBC Driver
* **IDE (optional):** VS Code / IntelliJ IDEA

### Database Connection

The database connection is configured in:

```java
utils/DatabaseConnection.java
```

You must set:

* database URL
* username
* password

---
## G. Screenshots
![a](https://github.com/AldairRonin/Assignment4/blob/main/docs/add_media.png?raw=true)
![a](https://github.com/AldairRonin/Assignment4/blob/main/docs/delete_media.png?raw=true)

---

## H. Reflection

### What I learned

During this assignment, I learned how to design a layered Java application using **SOLID principles**, JDBC, and a relational database. I better understood how models, services, repositories, and utilities work together in a clean architecture.

### Challenges

The main challenges were handling **user input validation**, designing a proper **exception hierarchy**, and correctly separating responsibilities between layers. Debugging JDBC errors and ensuring safe database operations also required careful attention.

### Value of SOLID architecture

Using SOLID principles made the project easier to **extend, test, and maintain**. Each class has a clear responsibility, new features can be added with minimal changes, and the overall code structure is more readable and scalable.

---

