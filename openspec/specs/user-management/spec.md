# User Management

## Purpose

TBD — provides CRUD management for users, enforcing unique usernames and emails, and cascading deletes to their ratings.

## Requirements

### Requirement: User management
The system SHALL provide CRUD operations for users via a REST controller and JSF controllers, backed by service and repository layers. Only ADMIN users SHALL create, edit, or delete users. A user SHALL have a unique username, a unique email, a BCrypt-hashed password, a role (USER or ADMIN), a createdAt timestamp, and an updatedAt timestamp, and MAY have a display name. Password hashes SHALL never be returned in API responses or displayed in views.

#### Scenario: Create a user
- **WHEN** an ADMIN POSTs a user with a non-empty username, email, and password, and optionally a role, that are not already taken
- **THEN** the system hashes the password, sets `createdAt` and `updatedAt` to the current time, persists the user, assigns an id, and returns it with HTTP 201 (excluding the password field)

#### Scenario: Reject a duplicate username
- **WHEN** a client POSTs a user whose username already exists
- **THEN** the system rejects it with HTTP 409 and does not persist anything

#### Scenario: Reject a duplicate email
- **WHEN** a client POSTs a user whose email already exists
- **THEN** the system rejects it with HTTP 409 and does not persist anything

#### Scenario: Reject a user missing required fields
- **WHEN** a client POSTs a user with a blank username, blank email, or blank password
- **THEN** the system rejects it with HTTP 400

#### Scenario: Retrieve a user by id
- **WHEN** a client GETs a user by an existing id
- **THEN** the system returns the user with HTTP 200 (excluding the password field)

#### Scenario: Retrieve a missing user
- **WHEN** a client GETs a user by an id that does not exist
- **THEN** the system returns HTTP 404

#### Scenario: Update a user
- **WHEN** an ADMIN PUTs changes to an existing user (fields may include a new password and/or role)
- **THEN** the system hashes any new password (or keeps existing hash if the password field is empty), sets `updatedAt` to the current time, persists the changes, and returns the updated user with HTTP 200 (excluding the password field)

#### Scenario: Delete a user
- **WHEN** an ADMIN DELETEs an existing user
- **THEN** the system removes the user, removes that user's ratings, and returns HTTP 204

#### Scenario: Initial ADMIN is created on first startup
- **WHEN** the application starts and no user with the ADMIN role exists
- **THEN** the system creates an ADMIN user with username `admin`, BCrypt-hashed password `admin123`, displayName `Administrator`, email `admin@localhost`, and role `ADMIN`

#### Scenario: Initial ADMIN is not duplicated
- **WHEN** the application starts and an ADMIN user already exists
- **THEN** the system does not create a duplicate ADMIN account
