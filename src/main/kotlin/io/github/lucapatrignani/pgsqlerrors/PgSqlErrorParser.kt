package io.github.lucapatrignani.pgsqlerrors

/**
 * Utility to parse PostgreSQL textual error messages into structured error types.
 */
object PgSqlErrorParser {
    /**
     * Marker sealed interface for all structured error types produced by PgSqlErrorParser.
     */
    sealed interface SqlError

    /**
     * Represents a foreign key violation reported by PostgreSQL.
     *
     * @param referencing name of the table being updated/deleted
     * @param referenced name of the referenced table
     * @param constraint name of the foreign key constraint
     */
    data class ForeignKeyViolation(val referencing: String, val referenced: String, val constraint: String) : SqlError

    /**
     * Parse an error message produced by PostgreSQL and return a structured SqlError.
     *
     * @param errorMessage the raw PostgreSQL error message
     * @return a SqlError
     */
    fun parse(errorMessage: String): SqlError {
        val regex = Regex(
            """update or delete on table "([^"]+)" violates foreign key constraint "([^"]+)" on table "([^"]+)"""",
        )
        val matchResult = regex.find(errorMessage)
            ?: throw IllegalArgumentException("Error message does not match expected format")
        val (referencing, constraint, referenced) = matchResult.destructured
        return ForeignKeyViolation(referencing, referenced, constraint)
    }
}
