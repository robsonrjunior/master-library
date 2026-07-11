# Media Pagination

## Purpose

Provides a generic paginated query layer for the data-access tier using the JPA Criteria API. Enables server-side lazy loading of Media entities (e.g., books) through PrimeFaces' LazyDataModel without loading full entity graphs.

## Requirements

### Requirement: Generic paginated projection query
The data-access layer SHALL provide a generic `findPage(...)` operation on `MediaRepository<T>` that returns a single page of results together with the total element count, built with the JPA Criteria API. The operation SHALL accept a zero-based offset (first row), a page size, a sort specification, and a filter specification, and SHALL project results into a caller-supplied lightweight type rather than loading full entities.

#### Scenario: Return a single page with total count
- **WHEN** `findPage` is called with a first row, page size, empty sort, and empty filters
- **THEN** the system executes one query for the page content limited to the page size starting at the offset, and one query for the total count, returning both in a single `Page` result

#### Scenario: Project only requested columns
- **WHEN** `findPage` builds its query for a media type
- **THEN** the system selects only the fields declared by that type's projection (via a Criteria constructor expression) and does not load the full entity or its `description`

#### Scenario: Apply server-side sorting
- **WHEN** `findPage` receives a sort specification naming a sortable field and direction
- **THEN** the system adds a corresponding `ORDER BY` clause to both the content query and applies a stable, deterministic default order when no sort is supplied

#### Scenario: Apply server-side column and global filters
- **WHEN** `findPage` receives filters for one or more filterable fields or a global filter term
- **THEN** the system builds case-insensitive predicates for the matching fields, applies them to both the content and count queries, and returns only rows that satisfy them

### Requirement: Page result type
The system SHALL provide an immutable `Page<T>` type carrying the page content and the total number of matching elements, so callers can render paginated data without a separate count call.

#### Scenario: Expose content and total
- **WHEN** a caller receives a `Page<T>` from `findPage`
- **THEN** it can read the list of projected items for the current page and the total element count across all pages

### Requirement: Repository projection and field configuration
Each concrete repository SHALL declare, for its media type, the projection type used by `findPage`, the set of filterable and sortable field names, and the subset of fields covered by the global filter. Unknown or non-whitelisted sort/filter fields SHALL be ignored.

#### Scenario: Book repository declares its projection
- **WHEN** `findPage` runs for books
- **THEN** the system projects into `BookListItem(id, title, author, isbn, pageCount, publishedYear)` and treats `title`, `author`, and `isbn` as the global-filter fields

#### Scenario: Ignore non-whitelisted sort or filter fields
- **WHEN** a sort or filter references a field not declared filterable/sortable for the media type
- **THEN** the system ignores that field rather than failing or exposing it to the query

### Requirement: Service paginated listing
The service layer SHALL expose `findPage(...)` that delegates to the repository's generic paginated projection query, keeping `get(id)` as the separate operation that loads the complete entity by id.

#### Scenario: Service returns a page for the list
- **WHEN** a controller requests a page of books through the service
- **THEN** the service returns the projected `Page<BookListItem>` without loading full `Book` entities

#### Scenario: Service loads the complete entity by id
- **WHEN** a controller requests a single book by id through the service
- **THEN** the service returns the complete `Book` entity, including all fields needed for viewing or editing

### Requirement: Lazy data model integration
The system SHALL provide a PrimeFaces `LazyDataModel` implementation that maps the DataTable `load` and `count` callbacks to the service `findPage(...)` operation and exposes a row key based on the entity id so selection works across pages.

#### Scenario: Load a page on demand
- **WHEN** the DataTable requests a page (first, page size, sort, filters)
- **THEN** the lazy model calls `findPage(...)`, sets the total row count from the `Page` result, and returns only that page's rows

#### Scenario: Resolve selection across pages by row key
- **WHEN** rows are selected across different pages
- **THEN** the lazy model resolves each selected row by its id-based row key so the selection is preserved for bulk operations
