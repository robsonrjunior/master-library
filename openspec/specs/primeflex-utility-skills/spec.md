# PrimeFlex Utility Skills

## Purpose

Provides AI-readable skill files for each PrimeFlex utility family, derived from the vendored PrimeFlex 3.3.1 documentation, documenting class references, responsive/state prefixes, canonical usage patterns in the project's JSF/PrimeFaces context, and source cross-references to enable informed code generation.

## Requirements

### Requirement: Skill authoring convention
The system SHALL define a single, consistent authoring convention for all PrimeFlex utility skills so they are discoverable and loadable by the opencode skill tool.

Each skill MUST live under `.opencode/skills/primeflex/<family>/SKILL.md` and MUST begin with YAML frontmatter containing at least `name` (`primeflex-<family>`) and `description` fields, where `description` states when to use the skill and which utility classes/topics it covers using a "USE FOR:" keyword list. A `CONVENTIONS.md` at `.opencode/skills/primeflex/` MUST document the source-of-truth path, the doc-topic → family map, and authoring rules. Skill bodies MUST be written for an AI reader and MUST NOT copy proprietary license text.

#### Scenario: New skill follows convention
- **WHEN** a PrimeFlex utility skill is created
- **THEN** it is located at `.opencode/skills/primeflex/<family>/SKILL.md`
- **AND** it contains YAML frontmatter with `name: primeflex-<family>` and a `description` with a "USE FOR:" list

#### Scenario: Skill is loadable
- **WHEN** the skill tool lists available skills
- **THEN** each created PrimeFlex skill appears with its name and description

### Requirement: Utility family coverage
The system SHALL provide one skill per PrimeFlex utility family such that every topic directory under `primeflex-3.3.1/primeflex-3.3.1/components/doc/` (excluding the `common` doc-infrastructure components, which are covered as prefix/scale conventions in the setup skill) is covered by exactly one family skill.

The families MUST cover at minimum: setup (installation, theming, prefixes), grid (grid system, form layout), flexbox, spacing, sizing, typography, colors, border, position/display, background, effects, and motion (animations, transitions, transforms).

#### Scenario: All doc topics are mapped
- **WHEN** the doc-topic → family map in `CONVENTIONS.md` is reviewed
- **THEN** every topic directory under the vendored PrimeFlex doc tree is assigned to exactly one family skill

#### Scenario: Required families exist
- **WHEN** the set of PrimeFlex skills is reviewed
- **THEN** a skill exists for each of the setup, grid, flexbox, spacing, sizing, typography, colors, border, position, background, effects, and motion families

### Requirement: Class reference tables
Each family skill SHALL present the utility class reference for its topics as tables mapping class name to CSS property, derived from the vendored `classesdoc.js` sources.

Regular value scales MAY be condensed using pattern notation (e.g., `p-{0..8}`) provided the scale values are enumerated once; irregular sets MUST be listed explicitly. Class names MUST match the vendored 3.3.1 source exactly.

#### Scenario: Class table is provided
- **WHEN** a utility topic is documented in a family skill
- **THEN** a table (or condensed pattern with its value scale) maps each class to its CSS effect

#### Scenario: Class names match source
- **WHEN** a class name in a skill is checked against the corresponding `classesdoc.js`
- **THEN** the name matches the vendored source exactly

### Requirement: Responsive and state prefix documentation
The setup skill SHALL document the PrimeFlex prefix system — responsive breakpoints (`sm:`, `md:`, `lg:`, `xl:` with their min-widths) and state variants (`hover:`, `focus:`, `active:`) — and each family skill SHALL state whether its classes support these prefixes.

#### Scenario: Prefixes documented centrally
- **WHEN** the setup skill is opened
- **THEN** it lists the breakpoint prefixes with their min-width values and the state variant prefixes

#### Scenario: Family skill states prefix support
- **WHEN** a family skill documents responsive-capable classes
- **THEN** it shows at least one prefixed usage example (e.g., `md:flex-row`)

### Requirement: Canonical usage in XHTML context
Each family skill SHALL include at least one canonical usage snippet showing the utility classes applied in the project's JSF/PrimeFaces context (e.g., via `styleClass` on `p:` components or `class` on plain elements in Facelets views).

#### Scenario: XHTML snippet is provided
- **WHEN** a family skill is opened
- **THEN** it contains at least one snippet using PrimeFlex classes on a PrimeFaces component or Facelets markup

### Requirement: Source cross-referencing
Each family skill SHALL reference the vendored doc topic directories it was derived from using repository-relative paths under `primeflex-3.3.1/primeflex-3.3.1/components/doc/`.

#### Scenario: Skill links to source
- **WHEN** a family skill documents a topic
- **THEN** it cites the repo-relative path to the corresponding doc topic directory

### Requirement: No modification of application or vendored code
The system SHALL treat the vendored PrimeFlex distribution and the Master Library application code as read-only; creating the skills MUST NOT modify application Java, XHTML, `pom.xml`, or any file under `primeflex-3.3.1/`.

#### Scenario: Sources remain unchanged
- **WHEN** the skills are created
- **THEN** no files under `primeflex-3.3.1/` or the app's `src/` are added, edited, or removed
- **AND** only files under `.opencode/skills/primeflex/` are created
