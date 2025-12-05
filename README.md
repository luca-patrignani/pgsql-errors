# pgsql-errors

Small utility to parse PostgreSQL textual error messages into structured Kotlin types.

## Supported parsing
- Foreign key violations of the form:
  update or delete on table "child" violates foreign key constraint "fk_constraint" on table "parent"

## SqlError

The parser returns instances of the sealed interface `SqlError`. Current implementations:

- `ForeignKeyViolation` — represents a foreign key constraint violation.

Consumers should pattern-match on `SqlError` to handle specific cases.

## Usage

```kotlin
val msg = """update or delete on table "child" violates foreign key constraint "fk_constraint" on table "parent""""
val parsed = io.github.lucapatrignani.pgsqlerrors.PgSqlErrorParser.parse(msg)
when (parsed) {
  is io.github.lucapatrignani.pgsqlerrors.PgSqlErrorParser.ForeignKeyViolation -> {
    println(parsed.referencing) // "child"
    println(parsed.referenced)  // "parent"
    println(parsed.constraint)  // "fk_constraint"
  }
}
```

If the message does not match the expected format, parse(...) throws IllegalArgumentException.
