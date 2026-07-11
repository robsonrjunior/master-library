# Media Ratings

## Purpose

TBD — allows users to rate books, movies, and series with a numeric score and optional comment, with one rating per user per media item.

## Requirements

### Requirement: Rate a media item
The system SHALL allow a user to rate exactly one media item — a book, movie, or series — via a REST controller backed by service and repository layers. A rating SHALL reference an existing user and an existing media item, SHALL carry a numeric score, and MAY carry a comment.

#### Scenario: Create a rating for a book
- **WHEN** a client POSTs a rating referencing an existing user and an existing book with a valid score
- **THEN** the system persists the rating, assigns an id, and returns it with HTTP 201

#### Scenario: Create a rating for a movie or series
- **WHEN** a client POSTs a rating referencing an existing user and an existing movie or series with a valid score
- **THEN** the system persists the rating and returns it with HTTP 201

#### Scenario: Reject a rating for a non-existent user
- **WHEN** a client POSTs a rating whose user id does not exist
- **THEN** the system rejects it with HTTP 404 and does not persist anything

#### Scenario: Reject a rating for a non-existent media item
- **WHEN** a client POSTs a rating whose media id does not exist
- **THEN** the system rejects it with HTTP 404 and does not persist anything

### Requirement: Score range and increment
The system SHALL accept only scores from 0 to 10 inclusive, in increments of 0.5.

#### Scenario: Accept a valid half-step score
- **WHEN** a client submits a rating with a score of 0, 7.5, or 10
- **THEN** the system accepts the score

#### Scenario: Reject an out-of-range score
- **WHEN** a client submits a rating with a score below 0 or above 10
- **THEN** the system rejects it with HTTP 400

#### Scenario: Reject a non-half-step score
- **WHEN** a client submits a rating with a score that is not a multiple of 0.5 (e.g., 7.3)
- **THEN** the system rejects it with HTTP 400

### Requirement: Optional comment
The system SHALL allow a rating to include an optional free-text comment and SHALL accept ratings with no comment.

#### Scenario: Rating with a comment
- **WHEN** a client submits a rating including a comment
- **THEN** the system persists the comment alongside the score

#### Scenario: Rating without a comment
- **WHEN** a client submits a rating with no comment
- **THEN** the system persists the rating with an empty/absent comment

### Requirement: One rating per user per media item
The system SHALL allow at most one rating from a given user for a given media item.

#### Scenario: Duplicate rating updates the existing one
- **WHEN** a user submits a rating for a media item they have already rated
- **THEN** the system updates the existing rating's score and comment rather than creating a second rating

### Requirement: Manage existing ratings
The system SHALL allow listing, updating, and deleting ratings.

#### Scenario: List ratings by user
- **WHEN** a client GETs ratings filtered by an existing user id
- **THEN** the system returns all ratings created by that user with HTTP 200

#### Scenario: List ratings by media item
- **WHEN** a client GETs ratings filtered by an existing media id
- **THEN** the system returns all ratings for that media item with HTTP 200

#### Scenario: Update a rating
- **WHEN** a client PUTs a new valid score and/or comment to an existing rating
- **THEN** the system persists the changes and returns the updated rating with HTTP 200

#### Scenario: Delete a rating
- **WHEN** a client DELETEs an existing rating
- **THEN** the system removes the rating and returns HTTP 204
