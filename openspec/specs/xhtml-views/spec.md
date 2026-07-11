# XHTML Views

## Purpose

Provide the shared Facelets layout and the primary XHTML entry points for the master library UI, ensuring the application renders through a consistent shell.

## Requirements

### Requirement: Shared XHTML layout renders successfully
The system SHALL provide a shared Facelets layout that compiles without parsing errors and renders a consistent shell for all XHTML pages.

#### Scenario: Layout template compiles
- **WHEN** JSF loads `/WEB-INF/templates/layout.xhtml`
- **THEN** the template compiles successfully and does not raise a Facelet parsing exception

### Requirement: Main views use the shared layout
The system SHALL render the primary XHTML pages through the shared layout so they inherit the same top bar, navigation, content area, and footer.

#### Scenario: Index page renders in the shared shell
- **WHEN** a user opens `index.xhtml`
- **THEN** the page renders inside the shared layout with the page content inserted into the content area

#### Scenario: CRUD pages render in the shared shell
- **WHEN** a user opens `book-crud.xhtml`, `movie-crud.xhtml`, `series-crud.xhtml`, `rating-crud.xhtml`, or `user-crud.xhtml`
- **THEN** each page renders inside the shared layout with its page-specific content visible in the content area

### Requirement: Navigation exposes the main views
The system SHALL expose navigation entries for the primary media and user pages from the shared shell.

#### Scenario: User selects a navigation target
- **WHEN** a user selects Books, Movies, Series, Users, or Ratings from the navigation menu
- **THEN** the system navigates to the corresponding XHTML view
