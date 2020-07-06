package info.galudisu.repository

import info.galudisu.model.Tables._
import info.galudisu.model.tables.records.TeachersRecord
import org.jooq.{Condition, DSLContext}

import scala.collection.JavaConverters._
import scala.compat.java8.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}

/**
  * @author Galudisu
  *
  * @param dsl [[DSLContext]] dslContext
  */
class TeacherRepository(val dsl: DSLContext)(implicit ex: ExecutionContext)
    extends JooqRepository[TeachersRecord, String] {

  override def insertOne(t: TeachersRecord): Future[TeachersRecord] = {
    dsl
      .insertInto(TEACHERS)
      .columns(TEACHERS.ID, TEACHERS.FIRST_NAME, TEACHERS.LAST_NAME)
      .values(t.getId, t.getFirstName, t.getLastName)
      .executeAsync()
      .toScala
      .map(_ => t)
  }
  override def deleteOne(t: TeachersRecord): Future[Int]                  = ???
  override def deleteById(id: String): Future[Int]                        = ???
  override def update(t: TeachersRecord): Future[TeachersRecord]          = ???
  override def selectOne(id: String): Future[TeachersRecord]              = ???
  override def selectAll(): Future[List[TeachersRecord]]                  = ???
  override def select(condition: Condition): Future[List[TeachersRecord]] = ???
}
