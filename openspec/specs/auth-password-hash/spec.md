# Password Hashing

## Purpose

TBD — provides BCrypt password hashing with work factor 12 for secure credential storage, integrated with Jakarta Security's IdentityStore SPI; password hashes are never exposed in API responses or views.

## Requirements

### Requirement: Passwords are stored as BCrypt hashes
The system SHALL hash user passwords using BCrypt with a work factor of 12 before persisting them to the database. The system SHALL NOT store plaintext passwords at any point.

#### Scenario: Password is hashed on user creation
- **WHEN** a new user is created with a plaintext password
- **THEN** the password is BCrypt-hashed before being stored in the `users` table, and the raw password is discarded

#### Scenario: Password is hashed on user update when new password is provided
- **WHEN** a user's password field is populated during an update operation
- **THEN** the new password is BCrypt-hashed before storage

#### Scenario: Existing password is preserved when update password field is empty
- **WHEN** a user is updated with an empty password field
- **THEN** the existing password hash is kept unchanged

### Requirement: Password hashes must never be exposed
The system SHALL exclude password hashes from all REST API responses and JSF view renderings. View pages SHALL NOT contain any password field (plaintext or hash).

#### Scenario: Password is never returned in API responses
- **WHEN** the system serializes a user for an API response
- **THEN** the password hash is excluded from the JSON output

#### Scenario: Password field is absent from view pages
- **WHEN** a user detail or list page is rendered
- **THEN** no password hash is displayed in the HTML

### Requirement: Password is verified using BCrypt during login
The Jakarta Security `IdentityStore` SHALL verify a login attempt's password against the stored BCrypt hash using `BCrypt.checkpw()`.

#### Scenario: Correct password verifies successfully
- **WHEN** the `IdentityStore.validate()` receives credentials with the correct password
- **THEN** BCrypt verification returns true and `CredentialValidationResult` with the caller's groups is returned

#### Scenario: Incorrect password fails verification
- **WHEN** the `IdentityStore.validate()` receives credentials with an incorrect password
- **THEN** `CredentialValidationResult.INVALID_RESULT` is returned and authentication is rejected

### Requirement: User creation and edit forms have distinct field sets
The system SHALL present different form fields for user creation vs user editing. The creation form SHALL include Username, Display Name, Email, Password, and Role. The edit form SHALL include Username, Display Name, Email, Role, and New Password (optional).

#### Scenario: User creation form includes password and role
- **WHEN** an ADMIN accesses the user creation form
- **THEN** the form displays fields for Username, Display Name, Email, Password, and Role

#### Scenario: User edit form has optional new password
- **WHEN** an ADMIN accesses the user edit form
- **THEN** the form displays fields for Username, Display Name, Email, Role, and an optional New Password field
