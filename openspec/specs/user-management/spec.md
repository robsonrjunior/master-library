# User Management

## Purpose

TBD — provides CRUD management for users, enforcing unique usernames and emails, and cascading deletes to their ratings.

## Requirements

### Requirement: User management
The system SHALL provide CRUD operations for users via a REST controller, backed by service and repository layers. A user SHALL have a unique username and a unique email, and MAY have a display name.

#### Scenario: Create a user
- **WHEN** a client POSTs a user with a non-empty username and email that are not already taken
- **THEN** the system persists the user, assigns an id, and returns it with HTTP 201

#### Scenario: Reject a duplicate username
- **WHEN** a client POSTs a user whose username already exists
- **THEN** the system rejects it with HTTP 409 and does not persist anything

#### Scenario: Reject a duplicate email
- **WHEN** a client POSTs a user whose email already exists
- **THEN** the system rejects it with HTTP 409 and does not persist anything

#### Scenario: Reject a user missing required fields
- **WHEN** a client POSTs a user with a blank username or blank email
- **THEN** the system rejects it with HTTP 400

#### Scenario: Retrieve a user by id
- **WHEN** a client GETs a user by an existing id
- **THEN** the system returns the user with HTTP 200

#### Scenario: Retrieve a missing user
- **WHEN** a client GETs a user by an id that does not exist
- **THEN** the system returns HTTP 404

#### Scenario: Update a user
- **WHEN** a client PUTs changes to an existing user
- **THEN** the system persists the changes and returns the updated user with HTTP 200

#### Scenario: Delete a user
- **WHEN** a client DELETEs an existing user
- **THEN** the system removes the user, removes that user's ratings, and returns HTTP 204
