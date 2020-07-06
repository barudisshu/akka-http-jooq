/*
 * This file is generated by jOOQ.
 */
package info.galudisu.model.tables


import info.galudisu.model.Indexes
import info.galudisu.model.Keys
import info.galudisu.model.Timetable
import info.galudisu.model.tables.records.ClassroomsRecord

import java.lang.Class
import java.lang.Integer
import java.lang.String
import java.sql.Timestamp
import java.util.Arrays
import java.util.List

import javax.annotation.Generated

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Index
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row5
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.TableImpl

import scala.Array


object Classrooms {

  /**
   * The reference instance of <code>timetable.classrooms</code>
   */
  val CLASSROOMS = new Classrooms
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
class Classrooms(
  alias : Name,
  child : Table[_ <: Record],
  path : ForeignKey[_ <: Record, ClassroomsRecord],
  aliased : Table[ClassroomsRecord],
  parameters : Array[ Field[_] ]
)
extends TableImpl[ClassroomsRecord](
  alias,
  Timetable.TIMETABLE,
  child,
  path,
  aliased,
  parameters,
  DSL.comment("")
)
{

  /**
   * The class holding records for this type
   */
  override def getRecordType : Class[ClassroomsRecord] = {
    classOf[ClassroomsRecord]
  }

  /**
   * The column <code>timetable.classrooms.id</code>.
   */
  val ID : TableField[ClassroomsRecord, String] = createField(DSL.name("id"), org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), "")

  /**
   * The column <code>timetable.classrooms.lib</code>.
   */
  val LIB : TableField[ClassroomsRecord, String] = createField(DSL.name("lib"), org.jooq.impl.SQLDataType.VARCHAR(64), "")

  /**
   * The column <code>timetable.classrooms.max</code>.
   */
  val MAX : TableField[ClassroomsRecord, Integer] = createField(DSL.name("max"), org.jooq.impl.SQLDataType.INTEGER, "")

  /**
   * The column <code>timetable.classrooms.create_time</code>.
   */
  val CREATE_TIME : TableField[ClassroomsRecord, Timestamp] = createField(DSL.name("create_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), "")

  /**
   * The column <code>timetable.classrooms.modify_time</code>.
   */
  val MODIFY_TIME : TableField[ClassroomsRecord, Timestamp] = createField(DSL.name("modify_time"), org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), "")

  /**
   * Create a <code>timetable.classrooms</code> table reference
   */
  def this() = {
    this(DSL.name("classrooms"), null, null, null, null)
  }

  /**
   * Create an aliased <code>timetable.classrooms</code> table reference
   */
  def this(alias : String) = {
    this(DSL.name(alias), null, null, info.galudisu.model.tables.Classrooms.CLASSROOMS, null)
  }

  /**
   * Create an aliased <code>timetable.classrooms</code> table reference
   */
  def this(alias : Name) = {
    this(alias, null, null, info.galudisu.model.tables.Classrooms.CLASSROOMS, null)
  }

  private def this(alias : Name, aliased : Table[ClassroomsRecord]) = {
    this(alias, null, null, aliased, null)
  }

  def this(child : Table[_ <: Record], key : ForeignKey[_ <: Record, ClassroomsRecord]) = {
    this(Internal.createPathAlias(child, key), child, key, info.galudisu.model.tables.Classrooms.CLASSROOMS, null)
  }

  override def getSchema : Schema = Timetable.TIMETABLE

  override def getIndexes : List[ Index ] = {
    return Arrays.asList[ Index ](Indexes.CLASSROOMS_PRIMARY)
  }

  override def getPrimaryKey : UniqueKey[ClassroomsRecord] = {
    Keys.KEY_CLASSROOMS_PRIMARY
  }

  override def getKeys : List[ UniqueKey[ClassroomsRecord] ] = {
    return Arrays.asList[ UniqueKey[ClassroomsRecord] ](Keys.KEY_CLASSROOMS_PRIMARY)
  }

  override def as(alias : String) : Classrooms = {
    new Classrooms(DSL.name(alias), this)
  }

  override def as(alias : Name) : Classrooms = {
    new Classrooms(alias, this)
  }

  /**
   * Rename this table
   */
  override def rename(name : String) : Classrooms = {
    new Classrooms(DSL.name(name), null)
  }

  /**
   * Rename this table
   */
  override def rename(name : Name) : Classrooms = {
    new Classrooms(name, null)
  }

  // -------------------------------------------------------------------------
  // Row5 type methods
  // -------------------------------------------------------------------------

  override def fieldsRow : Row5[String, String, Integer, Timestamp, Timestamp] = {
    super.fieldsRow.asInstanceOf[ Row5[String, String, Integer, Timestamp, Timestamp] ]
  }
}