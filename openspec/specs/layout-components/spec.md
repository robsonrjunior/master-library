# Layout Components

## Purpose

Provides reusable Facelets composition components (topbar, navbar, footer, shell) and a master layout template, replacing the monolithic index.xhtml with a maintainable component architecture.

## Requirements

### Requirement: Layout components
The system SHALL provide reusable Facelets composition components in `WEB-INF/includes/`: `topbar.xhtml`, `navbar.xhtml`, `footer.xhtml`, and `shell.xhtml`. The shell component SHALL compose the topbar, navbar, and a content insertion point into a single structural unit.

#### Scenario: Topbar renders a horizontal mega menu
- **WHEN** the `topbar.xhtml` component is included
- **THEN** it renders a horizontal `p:megaMenu` with the PrimeFaces logo on the left and a search input on the right, matching the current top bar appearance

#### Scenario: Navbar renders a vertical mega menu with entity links
- **WHEN** the `navbar.xhtml` component is included
- **THEN** it renders a vertical `p:megaMenu` with functional submenus linking to Book, Movie, Series, User, and Ratings CRUD pages via `outcome` navigation

#### Scenario: Footer renders at the bottom
- **WHEN** the `footer.xhtml` component is included
- **THEN** it renders a footer element with copyright or project attribution text

#### Scenario: Shell composes topbar, navbar, and content
- **WHEN** the `shell.xhtml` component is rendered
- **THEN** it includes `topbar.xhtml` at the top, `navbar.xhtml` on the side, and a `<ui:insert name="content">` slot for page-specific content in the main area, all within a full-height card container

### Requirement: Master layout template
The system SHALL provide a master Facelets template (`layout.xhtml`) in `WEB-INF/templates/` that uses `shell.xhtml` for the top portion and `footer.xhtml` for the bottom.

#### Scenario: Layout wraps content with shell and footer
- **WHEN** a page uses `layout.xhtml` as its template
- **THEN** the page's content is rendered inside the shell (with topbar and navbar around it), and the footer appears at the bottom of the viewport

#### Scenario: Page defines content via ui:define
- **WHEN** a page declares `<ui:define name="content">` with custom content
- **THEN** that content is inserted into the shell's content slot, maintaining the surrounding topbar and navbar

### Requirement: Functional navigation menu
The system SHALL replace all placeholder menu items in the vertical navbar with links to real entity CRUD pages. The horizontal top bar SHALL remain visually unchanged (no links added to it).

#### Scenario: Book menu item navigates to book-crud page
- **WHEN** a user clicks the "Books" menu item in the vertical navbar
- **THEN** the browser navigates to `book-crud.xhtml` (resolved via JSF implicit navigation)

#### Scenario: Other entity menu items navigate to stub pages
- **WHEN** a user clicks the "Movies", "Series", "Users", or "Ratings" menu items
- **THEN** the browser navigates to the corresponding stub XHTML page (content to be implemented in a future change)

#### Scenario: Top bar menu remains unchanged
- **WHEN** the page renders
- **THEN** the horizontal top bar retains its current visual structure (logo, placeholder submenus, search input) without additional entity links
