# Authentication & Login

## Purpose

TBD — provides form-based authentication via Jakarta EE Security, container-managed sessions, login/logout, session timeout of 30 minutes, and public page exclusions.

## Requirements

### Requirement: User can log in with username and password
The system SHALL authenticate users via Jakarta EE Security form authentication. The `LoginController` SHALL call `SecurityContext.authenticate()` with `UsernamePasswordCredential`. On success, the container SHALL establish an HTTP session with the authenticated principal and redirect to the home page. On failure, the system SHALL display an "Invalid username or password" message on the login form.

#### Scenario: Successful login
- **WHEN** a user submits a valid username and correct password via the login form
- **THEN** `SecurityContext.authenticate()` returns a valid `CredentialValidationResult`, the container establishes a session, and the user is redirected to `/index.xhtml`

#### Scenario: Failed login with wrong password
- **WHEN** a user submits a valid username but an incorrect password
- **THEN** `SecurityContext.authenticate()` returns `INVALID_RESULT`, an "Invalid username or password" message is displayed, and the user remains on the login page

#### Scenario: Failed login with unknown username
- **WHEN** a user submits a username that does not exist
- **THEN** `SecurityContext.authenticate()` returns `INVALID_RESULT`, an "Invalid username or password" message is displayed, and the user remains on the login page

#### Scenario: Login with blank credentials
- **WHEN** a user submits the login form with empty username or empty password
- **THEN** the system redisplays the login page with a validation message requiring both fields

### Requirement: User can log out
The system SHALL provide a logout action that calls `HttpServletRequest.logout()`, invalidates the current `HttpSession`, and redirects to the login page.

#### Scenario: Successful logout
- **WHEN** an authenticated user clicks the logout action
- **THEN** the container terminates the authentication session, the HTTP session is invalidated, and the user is redirected to `/login.xhtml`

### Requirement: Unauthenticated users are redirected to login
The Jakarta EE Security container SHALL redirect any unauthenticated request for a protected resource to the login page, preserving the originally requested URL for post-login redirection.

#### Scenario: Access protected page without session
- **WHEN** an unauthenticated user navigates directly to a protected page URL (e.g., `/user-list.xhtml`)
- **THEN** the container redirects to `/login.xhtml` and after successful login the container redirects to the originally requested page

### Requirement: Public pages are accessible without authentication
The system SHALL allow unauthenticated access to `/login.xhtml`, `/jakarta.faces.resource/*`, and `/resources/*` by excluding these URL patterns from `web.xml` security constraints.

#### Scenario: Access login page without session
- **WHEN** an unauthenticated user navigates to `/login.xhtml`
- **THEN** the system renders the login form without redirecting

#### Scenario: Access JSF resource without session
- **WHEN** the browser requests a JSF resource (e.g., `/jakarta.faces.resource/css/theme.css`)
- **THEN** the system serves the resource without requiring authentication

### Requirement: Session timeout after 30 minutes
The system SHALL configure an HTTP session timeout of 30 minutes via `web.xml` `<session-config>`. When the session expires, the container SHALL invalidate the authenticated session and the next request to a protected resource SHALL redirect to the login page.

#### Scenario: Session expires due to inactivity
- **WHEN** an authenticated user's HTTP session remains idle for 30 minutes
- **THEN** the container invalidates the session and the next request to a protected page redirects to the login page
