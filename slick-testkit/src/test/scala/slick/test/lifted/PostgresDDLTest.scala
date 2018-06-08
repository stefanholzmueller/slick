package slick.test.lifted

import org.junit.Assert._
import org.junit.Test

/** Test case for the PostgresSQL DDL */
class PostgresDDLTest {
  import slick.jdbc.PostgresProfile.api._

  @Test def testDoublePrecisionType: Unit = {
    class T(tag: Tag) extends Table[Double](tag, "mytable") {
      def num = column[Double]("num", O.PrimaryKey)

      def * = num
    }
    val ts = TableQuery[T]

    val statement = ts.schema.createStatements.mkString
    assertTrue("DDL (create) must contain DOUBLE PRECISION column: " + statement,
      statement.contains("DOUBLE PRECISION"))
  }
}
