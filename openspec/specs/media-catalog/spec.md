# Media Catalog

## Purpose

TBD — provides CRUD management for books, movies, and series, and supports listing/searching media by title.

## Requirements

### Requirement: Book management
The system SHALL provide CRUD operations for books via a REST controller, backed by service and repository layers. A book SHALL have a title (required) and MAY have a description, author, ISBN, page count, and published year.

#### Scenario: Create a book
- **WHEN** a client POSTs a book with a non-empty title
- **THEN** the system persists the book, assigns an id, and returns it with HTTP 201

#### Scenario: Reject a book without a title
- **WHEN** a client POSTs a book with a missing or blank title
- **THEN** the system rejects it with HTTP 400 and does not persist anything

#### Scenario: Retrieve a book by id
- **WHEN** a client GETs a book by an existing id
- **THEN** the system returns the book with HTTP 200

#### Scenario: Retrieve a missing book
- **WHEN** a client GETs a book by an id that does not exist
- **THEN** the system returns HTTP 404

#### Scenario: Update a book
- **WHEN** a client PUTs changes to an existing book
- **THEN** the system persists the changes and returns the updated book with HTTP 200

#### Scenario: Delete a book
- **WHEN** a client DELETEs an existing book
- **THEN** the system removes the book and returns HTTP 204

### Requirement: Movie management
The system SHALL provide CRUD operations for movies via a REST controller, backed by service and repository layers. A movie SHALL have a title (required) and MAY have a description, director, runtime in minutes, and release year.

#### Scenario: Create a movie
- **WHEN** a client POSTs a movie with a non-empty title
- **THEN** the system persists the movie, assigns an id, and returns it with HTTP 201

#### Scenario: Reject a movie without a title
- **WHEN** a client POSTs a movie with a missing or blank title
- **THEN** the system rejects it with HTTP 400 and does not persist anything

#### Scenario: Delete a movie
- **WHEN** a client DELETEs an existing movie
- **THEN** the system removes the movie and returns HTTP 204

### Requirement: Series management
The system SHALL provide CRUD operations for series via a REST controller, backed by service and repository layers. A series SHALL have a title (required) and MAY have a description, number of seasons, number of episodes, start year, end year, and a status of ONGOING or ENDED.

#### Scenario: Create a series
- **WHEN** a client POSTs a series with a non-empty title
- **THEN** the system persists the series, assigns an id, and returns it with HTTP 201

#### Scenario: Reject an invalid status
- **WHEN** a client POSTs a series with a status outside {ONGOING, ENDED}
- **THEN** the system rejects it with HTTP 400

#### Scenario: Delete a series
- **WHEN** a client DELETEs an existing series
- **THEN** the system removes the series and returns HTTP 204

### Requirement: Media search by title
The system SHALL allow listing media items and searching them by a partial, case-insensitive title match, per media type.

#### Scenario: List all books
- **WHEN** a client GETs the books collection with no filter
- **THEN** the system returns all books with HTTP 200

#### Scenario: Search movies by partial title
- **WHEN** a client GETs the movies collection filtered by a title substring
- **THEN** the system returns only movies whose title contains that substring, case-insensitively
