# PrimeFlex Styling

## Purpose

Defines PrimeFlex 3.3.1 as the sole, locally-served utility CSS framework for the application, replacing Bootstrap entirely in all XHTML views and composition components.

## Requirements

### Requirement: PrimeFlex is the sole utility CSS framework
The application SHALL use PrimeFlex 3.3.1, served locally from `resources/css/primeflex.min.css` via `<h:outputStylesheet>`, as the only utility CSS framework. The application MUST NOT load Bootstrap CSS or JavaScript from any source (CDN or local).

#### Scenario: No Bootstrap assets in the layout template
- **WHEN** `WEB-INF/templates/layout.xhtml` is inspected
- **THEN** it contains no `<link>` or `<script>` tags referencing Bootstrap, and it loads `css/primeflex.min.css` via `<h:outputStylesheet>`

#### Scenario: Pages render without external CDN requests
- **WHEN** any page is loaded in a browser
- **THEN** no network requests are made to `cdn.jsdelivr.net` or any other Bootstrap asset host

### Requirement: Views use only PrimeFlex utility classes
All XHTML views and includes SHALL express layout, spacing, sizing, typography, color, and border utilities exclusively through PrimeFlex 3.3.1 class names (plus PrimeFaces `ui-*`/`pi-*` component classes and app-specific classes). Bootstrap-only class names MUST NOT appear in any `class` or `styleClass` attribute.

#### Scenario: No Bootstrap-only classes remain
- **WHEN** all files under `src/main/webapp` matching `*.xhtml` are searched for Bootstrap-only class names (`d-flex`, `d-block`, `w-100`, `h-100`, `vw-100`, `vh-100`, `row`, `g-[0-9]`, `col-md-*`, `me-*`, `ms-*`, `text-muted`, `bg-light`, `border-0`, `rounded-0`, `form-control-plaintext`, `display-4`, `lead`, `small`, standalone `card`)
- **THEN** no matches are found

#### Scenario: Responsive form grids use PrimeFlex grid
- **WHEN** a form page (book, movie, series, user, rating) renders its field grid
- **THEN** fields are laid out with PrimeFlex `grid` and `col-12`/`md:col-*` classes, stacking to full width on small screens and splitting into columns at the `md` breakpoint, visually equivalent to the previous Bootstrap `row`/`col-md-*` layout

#### Scenario: Full-height shell layout preserved
- **WHEN** any page renders inside the shared layout
- **THEN** the body fills the viewport (`w-screen h-screen`-based flex column), the shell area grows to fill remaining space, list tables stretch to full width/height, and the footer stays at the bottom — matching the pre-migration layout behavior
