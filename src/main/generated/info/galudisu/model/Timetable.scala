/*
 * This file is generated by jOOQ.
 */
package info.galudisu.model


import info.galudisu.model.tables.Classrooms
import info.galudisu.model.tables.Lessons
import info.galudisu.model.tables.Plans
import info.galudisu.model.tables.Teachers

import java.util.ArrayList
import java.util.Arrays
import java.util.List

import javax.annotation.Generated

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl

import scala.Array


object Timetable {

  /**
   * The reference instance of <code>timetable</code>
   */
  val TIMETABLE = new Timetable
}

/**
 * This class is generated by jOOQ.
 */
@Generated(
  value = Array(
    "http://www.jooq.org",
    "jOOQ version:3.12.4"
  ),
  comments = "This class is generated by jOOQ"
)
class Timetable extends SchemaImpl("timetable", DefaultCatalog.DEFAULT_CATALOG) {

  override def getCatalog : Catalog = DefaultCatalog.DEFAULT_CATALOG

  override def getTables : List[Table[_]] = {
    val result = new ArrayList[Table[_]]
    result.addAll(getTables0)
    result
  }

  private def getTables0(): List[Table[_]] = {
    return Arrays.asList[Table[_]](
      Classrooms.CLASSROOMS,
      Lessons.LESSONS,
      Plans.PLANS,
      Teachers.TEACHERS)
  }
}
