# PrimeFaces Showcase Skills

## Purpose

Provides AI-readable skill files for each PrimeFaces component family in the vendored showcase, documenting component purposes, canonical usage patterns, and source cross-references to enable informed code generation.

## Requirements

### Requirement: Skill authoring convention
The system SHALL define a single, consistent authoring convention for all PrimeFaces showcase skills so they are discoverable and loadable by the opencode skill tool.

Each skill MUST live under `.opencode/skills/primefaces/<family>/SKILL.md` and MUST begin with YAML frontmatter containing at least `name` and `description` fields, where `description` states when to use the skill and which components it covers. The skill body MUST be written for an AI reader and MUST NOT copy proprietary license text.

#### Scenario: New skill follows convention
- **WHEN** a PrimeFaces showcase skill is created
- **THEN** it is located at `.opencode/skills/primefaces/<family>/SKILL.md`
- **AND** it contains YAML frontmatter with `name` and a `description` describing when to use it and which components it covers

#### Scenario: Skill is loadable
- **WHEN** the skill tool lists available skills
- **THEN** each created PrimeFaces skill appears with its name and description

### Requirement: Component family coverage
The system SHALL provide one skill per PrimeFaces showcase component family present under `primefaces/primefaces-showcase/src/main/webapp/ui/`, covering at minimum the `data`, `input`, `panel`, `menu`, `overlay`, `file`, `button`, `message`, `chart`, `multimedia`, and `misc` families.

Each family skill MUST enumerate the key components in that family and, for each, describe the component's purpose and the primary showcase example(s) that demonstrate it.

#### Scenario: Family skill enumerates components
- **WHEN** a family skill (e.g. `data`) is opened
- **THEN** it lists the main components of that family (e.g. dataTable, dataView, dataList, treeTable) with a one-line purpose for each

#### Scenario: Required families are covered
- **WHEN** the set of PrimeFaces skills is reviewed
- **THEN** a skill exists for each of the data, input, panel, menu, overlay, file, button, message, chart, multimedia, and misc families

### Requirement: Canonical usage patterns
Each family skill SHALL present the canonical XHTML markup pattern and the matching backing-bean pattern for its representative components, derived from the showcase source.

The XHTML pattern MUST use the correct namespaces (`primefaces`, `jakarta.faces.html`, `jakarta.faces.core`, `jakarta.faces.facelets`) as used in the showcase. The backing-bean pattern MUST show the correct CDI annotation (`@Named`) and an appropriate scope, matching the showcase view bean for that example. Patterns MUST reflect the Jakarta EE 11 / Faces 4 conventions used in the vendored showcase.

#### Scenario: XHTML pattern is provided
- **WHEN** a component is documented in a family skill
- **THEN** a minimal, correct XHTML snippet using PrimeFaces namespaces is shown for it

#### Scenario: Backing-bean pattern is provided
- **WHEN** a component requires a backing bean in its showcase example
- **THEN** the skill shows the matching bean pattern with `@Named` and the correct scope

### Requirement: Source example cross-referencing
Each documented component SHALL reference the concrete showcase source file(s) it was derived from so a reader can inspect the full working example.

References MUST use repository-relative paths under `primefaces/primefaces-showcase/` and SHALL identify both the XHTML view and, where applicable, the backing bean.

#### Scenario: Component links to source
- **WHEN** a component pattern is presented in a skill
- **THEN** it cites the repo-relative path to the showcase XHTML example
- **AND** it cites the repo-relative path to the backing bean when one exists

### Requirement: No modification of application or showcase code
The system SHALL treat the vendored showcase and the Master Library application code as read-only sources; creating the skills MUST NOT modify application Java, XHTML, `pom.xml`, or any file under `primefaces/`.

#### Scenario: Sources remain unchanged
- **WHEN** the skills are created
- **THEN** no files under `primefaces/` or the app's `src/` are added, edited, or removed
- **AND** only files under `.opencode/skills/primefaces/` are created
