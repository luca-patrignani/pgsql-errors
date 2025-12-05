package io.github.lucapatrignani.pgsqlerrors

import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.Assertions.assertEquals

class ParseTest : FunSpec() {
    init {
        test("Foreign key violation parsing") {
            val error = """update or delete on table "child" violates foreign key constraint "fk_constraint" on table "parent""""
            val parsedError = PgSqlErrorParser.parse(error)
            when (parsedError) {
                is PgSqlErrorParser.ForeignKeyViolation -> Unit
            }
            assertEquals(parsedError.referencing, "child")
            assertEquals(parsedError.referenced, "parent")
            assertEquals(parsedError.constraint, "fk_constraint")
        }
    }
}
