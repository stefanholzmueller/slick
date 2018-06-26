package slick.test.lifted

import org.junit.Assert._
import org.junit.Test

/** Test case for the PostgresSQL DDL */
class PostgresDDLTest {

  @Test def testPostgresTableWithPostgresStatement: Unit = {
    import slick.jdbc.PostgresProfile.api._
    val ts = TableQuery[TablesDefinedWithPostgresImport.TableWithDoubleColumn]

    val statement = ts.schema.createStatements.mkString
    expectColumnType("DOUBLE PRECISION", statement)
  }

  @Test def testPostgresTableWithH2Statement: Unit = {
    import slick.jdbc.H2Profile.api._
    val ts = TableQuery[TablesDefinedWithPostgresImport.TableWithDoubleColumn]

    val statement = ts.schema.createStatements.mkString
    expectColumnType("DOUBLE PRECISION", statement)
  }

  @Test def testH2TableWithPostgresStatement: Unit = {
    import slick.jdbc.PostgresProfile.api._
    val ts = TableQuery[TablesDefinedWithH2Import.TableWithDoubleColumn]

    val statement = ts.schema.createStatements.mkString
    expectColumnType("DOUBLE", statement)
  }

  @Test def testH2TableWithH2Statement: Unit = {
    import slick.jdbc.H2Profile.api._
    val ts = TableQuery[TablesDefinedWithH2Import.TableWithDoubleColumn]

    val statement = ts.schema.createStatements.mkString
    expectColumnType("DOUBLE", statement)
  }

  def expectColumnType(columnType: String, statement: String) = {
    assertTrue(s"DDL (create) must contain $columnType column: " + statement,
      statement.contains(s"$columnType NOT NULL"))
  }

}

object TablesDefinedWithPostgresImport {
  import slick.jdbc.PostgresProfile.api._
  class TableWithDoubleColumn(tag: Tag) extends Table[Double](tag, "postgrestable") {
    def num = column[Double]("num", O.PrimaryKey)

    def * = num
  }
}

object TablesDefinedWithH2Import {
  import slick.jdbc.H2Profile.api._
  class TableWithDoubleColumn(tag: Tag) extends Table[Double](tag, "h2table") {
    def num = column[Double]("num", O.PrimaryKey)

    def * = num
  }
}
