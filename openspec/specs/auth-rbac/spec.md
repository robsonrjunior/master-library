# Role-Based Access Control

## Purpose

TBD — provides role-based access control with USER and ADMIN roles enforced via Jakarta EE Security at the view layer (web.xml), REST layer (@RolesAllowed), and JSF controller layer; sidebar navigation is role-aware.

## Requirements

### Requirement: User entity has a role
The system SHALL assign every user exactly one role from the set {USER, ADMIN}. The role SHALL be stored as a string column in the `users` table and mapped to a Java enum. The custom `IdentityStore` SHALL return the role as a group name so that the Jakarta EE Security container recognizes it for `isUserInRole()` and `@RolesAllowed` checks.

#### Scenario: Create user with role
- **WHEN** a client creates a user with a valid role value
- **THEN** the system persists the user with that role

#### Scenario: User defaults to USER role
- **WHEN** a user is created without an explicit role
- **THEN** the system assigns the `USER` role by default

### Requirement: ADMIN users can access administration pages
The system SHALL grant access to administration pages (`/user-list`, `/user`, `/rating-list`, `/rating`) exclusively to users with the `ADMIN` role, enforced by `web.xml` `<security-constraint>` and `<auth-constraint>`.

#### Scenario: ADMIN accesses administration page
- **WHEN** an authenticated ADMIN user navigates to `/user-list.xhtml`
- **THEN** the container permits access and the page renders

#### Scenario: USER accesses administration page
- **WHEN** an authenticated USER (non-ADMIN) navigates to `/user-list.xhtml`
- **THEN** the container returns HTTP 403 (Forbidden)

#### Scenario: Unauthenticated user accesses administration page
- **WHEN** an unauthenticated user navigates to `/user-list.xhtml`
- **THEN** the container redirects to the login page

### Requirement: Administrative controllers are protected with @RolesAllowed
The system SHALL annotate administrative JSF controllers (`UserListController`, `UserController`, `RatingListController`, `RatingController`) with `@RolesAllowed("ADMIN")` to enforce access control at the bean level, beyond page-level protection.

#### Scenario: ADMIN invokes protected controller method
- **WHEN** an ADMIN user triggers an action on an administrative controller
- **THEN** the container permits execution

#### Scenario: USER invokes protected controller method
- **WHEN** a USER triggers an action on an administrative controller
- **THEN** the container denies access with a security exception

### Requirement: Sidebar navigation is role-aware
The system SHALL render sidebar navigation as follows: **Products** (Books, Movies, Series) and **Reviews** visible to every authenticated user; **Administração** (Users, Ratings) visible only to users with the `ADMIN` role.

#### Scenario: ADMIN sees full sidebar
- **WHEN** an ADMIN user views any page with the sidebar
- **THEN** the **Products**, **Reviews**, and **Administração** submenus are all visible

#### Scenario: USER sees restricted sidebar
- **WHEN** a USER (non-ADMIN) views any page with the sidebar
- **THEN** the **Products** and **Reviews** submenus are visible; the **Administração** submenu is not rendered

### Requirement: Topbar shows authenticated user identity
The system SHALL display the authenticated user's display name (or username if display name is empty) in the topbar via the `CurrentUser` bean, along with a logout action.

#### Scenario: Authenticated user sees identity in topbar
- **WHEN** an authenticated user views any page
- **THEN** the topbar shows the user's display name and a logout action

#### Scenario: Unauthenticated user sees no identity
- **WHEN** an unauthenticated user views the login page
- **THEN** the topbar does not show user identity or logout (the login page uses a minimal layout without the shell)

### Requirement: Product catalog pages are accessible to all authenticated roles
The system SHALL allow both `USER` and `ADMIN` roles to access product catalog pages (`/book-list`, `/book`, `/movie-list`, `/movie`, `/series-list`, `/series`, `/rating`) and the home page.

#### Scenario: USER accesses catalog page
- **WHEN** an authenticated USER navigates to `/book-list.xhtml`
- **THEN** the container permits access and the page renders

#### Scenario: ADMIN accesses catalog page
- **WHEN** an authenticated ADMIN navigates to `/book-list.xhtml`
- **THEN** the container permits access and the page renders

### Requirement: REST endpoints enforce role-based authorization
The system SHALL protect REST endpoints as follows: catalog browsing and read-only APIs SHALL be accessible to both `USER` and `ADMIN` roles; user management and rating administration endpoints SHALL require `@RolesAllowed("ADMIN")`.

#### Scenario: ADMIN calls user management endpoint
- **WHEN** an authenticated ADMIN sends a request to `/api/users/*`
- **THEN** the container permits the request and the resource method executes

#### Scenario: USER calls user management endpoint
- **WHEN** an authenticated USER sends a request to `/api/users/*`
- **THEN** the container returns HTTP 403 (Forbidden)

#### Scenario: Unauthenticated call to protected REST endpoint
- **WHEN** an unauthenticated client sends a request to a protected REST endpoint
- **THEN** the container returns HTTP 401 (Unauthorized)

### Requirement: Only ADMIN may perform user CRUD
The system SHALL restrict user creation, editing, and deletion exclusively to the ADMIN role. Regular USER accounts SHALL NOT be able to access user management features. Self-service profile editing and self-service password changes are out of scope.

#### Scenario: ADMIN creates a user
- **WHEN** an ADMIN submits a user creation form
- **THEN** the system persists the new user

#### Scenario: USER attempts to create a user
- **WHEN** a USER attempts to access user creation
- **THEN** the system denies the request

#### Scenario: ADMIN edits a user
- **WHEN** an ADMIN submits changes to an existing user
- **THEN** the system persists the changes

#### Scenario: ADMIN deletes a user
- **WHEN** an ADMIN deletes an existing user
- **THEN** the system removes the user and their ratings

### Requirement: Authentication and identity are separated into distinct beans
The system SHALL use `LoginController` (`@RequestScoped`) exclusively for authentication logic and `CurrentUser` (`@Named`, `@RequestScoped`) exclusively for exposing the authenticated user identity to views. The authenticated identity SHALL remain managed by the Jakarta EE Security container.

#### Scenario: LoginController handles authentication
- **WHEN** a login attempt is made
- **THEN** `LoginController.login()` processes the credentials and delegates to `SecurityContext.authenticate()` without storing session state

#### Scenario: CurrentUser exposes identity in views
- **WHEN** a view references `#{currentUser.isAdmin()}` or `#{currentUser.username}`
- **THEN** the `CurrentUser` bean returns the value from the container's security context without storing independent session state
