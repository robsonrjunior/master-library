# Book CRUD (JSF)

## Purpose

Provides dedicated list and form pages for Book CRUD with server-side lazy pagination, replacing the single-page modal-dialog design. Uses a lazy `DataTable` on `book-list.xhtml` and a separate `book.xhtml` form page supporting create, edit, and view modes, with separate `ViewScoped` CDI controllers for each concern.

## Requirements

### Requirement: Book list page
The system SHALL provide a JSF page (`book-list.xhtml`) that displays books in a PrimeFaces DataTable configured for server-side lazy loading, with pagination, sorting, filtering, and delete operations. The table SHALL NOT open create/edit dialogs; the "New", "Edit", and "View" actions SHALL navigate to `book.xhtml`.

#### Scenario: List books with server-side pagination
- **WHEN** a user navigates to `book-list.xhtml`
- **THEN** the system displays a lazy DataTable, paginated at 10 rows per page, with columns for id, title, author, isbn, page count, and published year, fetching only the current page from the server

#### Scenario: Search books server-side
- **WHEN** a user types in the global search filter
- **THEN** the system requests a filtered page from the server matching the term case-insensitively against title, author, and isbn

#### Scenario: Sort books server-side
- **WHEN** a user clicks a sortable column header
- **THEN** the system requests a page sorted by that column, toggling ascending/descending on repeated clicks

#### Scenario: Navigate to create
- **WHEN** a user clicks the "New" button in the toolbar
- **THEN** the system navigates to `book.xhtml?mode=create` with an empty form

#### Scenario: Navigate to edit
- **WHEN** a user clicks the edit icon on a book row
- **THEN** the system navigates to `book.xhtml?id=<id>&mode=edit`

#### Scenario: Navigate to view
- **WHEN** a user clicks the view icon on a book row
- **THEN** the system navigates to `book.xhtml?id=<id>&mode=view`

#### Scenario: Delete selected books
- **WHEN** a user selects one or more books via checkboxes and clicks "Delete"
- **THEN** the system shows a confirmation dialog; upon confirmation, deletes the selected books via `BookService`, clears the selection, shows a success message, and refreshes the lazy table

#### Scenario: Delete a single book from row action
- **WHEN** a user clicks the delete icon on a specific book row
- **THEN** the system shows a confirmation dialog; upon confirmation, deletes that book via `BookService`, shows a success message, and refreshes the lazy table

### Requirement: Book form page
The system SHALL provide a JSF page (`book.xhtml`) that serves as a single form for creating, editing, and viewing a book, selecting its behavior from a `mode` view parameter (`create`, `edit`, `view`). The page SHALL declare its `id` and `mode` view parameters and a view action via a `<f:metadata>` section supplied through the shared template's metadata insertion point. The form fields SHALL be laid out using the shared Bootstrap 5 grid (responsive `row`/`col-*` classes) rather than the PrimeFaces grid classes.

#### Scenario: Create mode shows an empty editable form
- **WHEN** a user opens `book.xhtml?mode=create`
- **THEN** the system shows an empty, editable form with fields for title, author, isbn, page count, published year, and description, and "Save"/"Cancel" actions

#### Scenario: Edit mode shows a populated editable form
- **WHEN** a user opens `book.xhtml?id=<id>&mode=edit` for an existing book
- **THEN** the system loads the complete book by id and shows an editable form pre-populated with its values

#### Scenario: View mode shows a read-only form
- **WHEN** a user opens `book.xhtml?id=<id>&mode=view` for an existing book
- **THEN** the system loads the complete book by id and displays all fields read-only, offering "Edit" and "Back" actions instead of "Save"

#### Scenario: Switch from view to edit
- **WHEN** a user clicks "Edit" while in view mode
- **THEN** the system navigates to `book.xhtml?id=<id>&mode=edit`

#### Scenario: Save creates or updates and returns to the list
- **WHEN** a user submits a valid form
- **THEN** the system persists the book via `BookService` (create when no id, update otherwise), then redirects to `book-list.xhtml` (Post/Redirect/Get) with a success message

#### Scenario: Reject save without title
- **WHEN** a user submits the form with an empty or blank title
- **THEN** the system shows a validation error, stays on the form, and does not persist anything

#### Scenario: Cancel returns to the list without saving
- **WHEN** a user clicks "Cancel" or "Back"
- **THEN** the system navigates to `book-list.xhtml` without persisting changes

#### Scenario: Invalid id redirects with an error
- **WHEN** a user opens `book.xhtml` with an id that does not exist (e.g. `?id=999&mode=edit`)
- **THEN** the system catches the `ResourceNotFoundException`, stores an error message in Flash Scope, and redirects to `book-list.xhtml` where the message is shown

#### Scenario: Form fields laid out on the Bootstrap grid
- **WHEN** a user opens `book.xhtml` in any mode
- **THEN** the form fields are arranged using Bootstrap grid rows and columns, with page count and published year sharing a row on wide viewports and stacking on narrow viewports

### Requirement: Book list controller
The system SHALL provide a CDI-managed backing bean (`BookListController`) annotated with `@Named` and `@ViewScoped` in the `controllers` package, responsible only for listing, pagination, filtering, and delete. It SHALL expose a `LazyDataModel` for the DataTable and hold the current selection for bulk delete.

#### Scenario: Expose the lazy data model
- **WHEN** `book-list.xhtml` renders its DataTable
- **THEN** `BookListController` provides a `LazyDataModel` whose `load`/`count` delegate to `BookService.findPage(...)`

#### Scenario: Delete selected books
- **WHEN** the delete action is confirmed for the current selection
- **THEN** `BookListController` calls `BookService.delete(id)` for each selected book, clears the selection, and shows a growl success message

### Requirement: Book form controller
The system SHALL provide a CDI-managed backing bean (`BookController`) annotated with `@Named` and `@ViewScoped` in the `controllers` package, responsible only for creating, editing, and viewing a single book. It SHALL determine the `id` and `mode` from the request parameters, load state before the page renders, expose mode helpers, and return a redirect outcome on save. The controller SHALL always expose a non-null `book` instance so that value expressions such as `#{bookController.book.title}` never resolve against a null base, even if state loading has not yet run.

#### Scenario: Load state before render
- **WHEN** `book.xhtml` is requested with `id` and `mode` parameters on an initial (non-postback) GET
- **THEN** a `preRenderView` listener reads `id` and `mode` from the request parameters and loads a new `Book` for create mode or the complete book via `BookService.get(id)` for edit/view mode, before the page renders

#### Scenario: Skip reload on postback
- **WHEN** the form is submitted (a JSF postback)
- **THEN** the `preRenderView` listener does not re-read parameters or reload the book, preserving the submitted form state

#### Scenario: Book is never null during render
- **WHEN** `book.xhtml` renders any input bound to `#{bookController.book.<field>}`
- **THEN** `bookController.book` is a non-null `Book` instance and no `Target Unreachable, 'null' returned null` exception is thrown

#### Scenario: Create mode initializes an empty book
- **WHEN** `book.xhtml?mode=create` is requested with no `id`
- **THEN** `BookController` initializes `book` to a new empty `Book` and the form renders empty and editable without error

#### Scenario: Edit mode populates the book fields
- **WHEN** `book.xhtml?id=<id>&mode=edit` is requested for an existing book
- **THEN** `BookController` loads the complete book via `BookService.get(id)` and every form field (title, author, isbn, page count, published year, description) is pre-populated with the stored values

#### Scenario: View mode populates the book fields read-only
- **WHEN** `book.xhtml?id=<id>&mode=view` is requested for an existing book
- **THEN** `BookController` loads the complete book via `BookService.get(id)` and every field displays the stored value read-only

#### Scenario: Expose mode helpers to the view
- **WHEN** the form renders
- **THEN** `BookController` exposes whether the current mode is create, edit, or view so inputs can be rendered editable or read-only and the correct action buttons shown

#### Scenario: Save determines create versus update
- **WHEN** the save action runs
- **THEN** `BookController` calls `BookService.create()` when the book has no id and `BookService.update()` otherwise, then returns a Post/Redirect/Get outcome to `book-list.xhtml` with a flash success message

#### Scenario: Handle missing book by redirecting
- **WHEN** the state loading cannot find the requested book
- **THEN** `BookController` catches `ResourceNotFoundException`, stores an error message in Flash Scope, and redirects to `book-list.xhtml`

### Requirement: Template metadata insertion point
The shared layout template (`layout.xhtml`) SHALL provide a `<f:metadata>` region, declared as a direct child of the view root, with a `<ui:insert name="metadata"/>` placeholder so that templated pages MAY declare metadata content while still using the shared template. Because Mojarra does not process `<f:viewParam>`/`<f:viewAction>` supplied through Facelets templating (`ui:insert`/`ui:define`) as build-time view metadata, templated pages that require pre-render initialization SHALL instead trigger it with a `preRenderView` event placed within their content, reading request parameters directly.

#### Scenario: Templated page initializes via preRenderView
- **WHEN** `book.xhtml` is requested via a GET (e.g. from a "New", "Edit", or "View" link)
- **THEN** an `<f:event type="preRenderView" listener="#{bookController.loadForm}"/>` in the page content executes before the form renders, so bound values are populated

#### Scenario: Metadata region is at the view root
- **WHEN** the shared template renders
- **THEN** the `<f:metadata>` region is a direct child of the view root so any page-supplied view metadata is eligible to be recognized by JSF

#### Scenario: Pages without metadata are unaffected
- **WHEN** a templated page does not define a `metadata` block
- **THEN** the template renders normally with an empty metadata region

### Requirement: Navbar navigation to book list
The navigation menu SHALL link the "Livros" item to the `book-list` outcome instead of `book-crud`.

#### Scenario: Menu opens the book list
- **WHEN** a user clicks the "Livros" menu item
- **THEN** the system navigates to `book-list.xhtml`
