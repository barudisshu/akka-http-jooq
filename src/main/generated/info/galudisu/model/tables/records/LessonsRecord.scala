/*
 * This file is generated by jOOQ.
 */
package info.galudisu.model.tables.records


import info.galudisu.model.tables.Lessons

import java.lang.String
import java.sql.Timestamp

import javax.annotation.Generated

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl

import scala.Array


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
class LessonsRecord extends UpdatableRecordImpl[LessonsRecord](Lessons.LESSONS) with Record4[String, String, Timestamp, Timestamp] {

  /**
   * Setter for <code>timetable.lessons.id</code>.
   */
  def setId(value : String) : Unit = {
    set(0, value)
  }

  /**
   * Getter for <code>timetable.lessons.id</code>.
   */
  def getId : String = {
    val r = get(0)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>timetable.lessons.lib</code>.
   */
  def setLib(value : String) : Unit = {
    set(1, value)
  }

  /**
   * Getter for <code>timetable.lessons.lib</code>.
   */
  def getLib : String = {
    val r = get(1)
    if (r == null) null else r.asInstanceOf[String]
  }

  /**
   * Setter for <code>timetable.lessons.create_time</code>.
   */
  def setCreateTime(value : Timestamp) : Unit = {
    set(2, value)
  }

  /**
   * Getter for <code>timetable.lessons.create_time</code>.
   */
  def getCreateTime : Timestamp = {
    val r = get(2)
    if (r == null) null else r.asInstanceOf[Timestamp]
  }

  /**
   * Setter for <code>timetable.lessons.modify_time</code>.
   */
  def setModifyTime(value : Timestamp) : Unit = {
    set(3, value)
  }

  /**
   * Getter for <code>timetable.lessons.modify_time</code>.
   */
  def getModifyTime : Timestamp = {
    val r = get(3)
    if (r == null) null else r.asInstanceOf[Timestamp]
  }

  // -------------------------------------------------------------------------
  // Primary key information
  // -------------------------------------------------------------------------
  override def key : Record1[String] = {
    return super.key.asInstanceOf[ Record1[String] ]
  }

  // -------------------------------------------------------------------------
  // Record4 type implementation
  // -------------------------------------------------------------------------

  override def fieldsRow : Row4[String, String, Timestamp, Timestamp] = {
    super.fieldsRow.asInstanceOf[ Row4[String, String, Timestamp, Timestamp] ]
  }

  override def valuesRow : Row4[String, String, Timestamp, Timestamp] = {
    super.valuesRow.asInstanceOf[ Row4[String, String, Timestamp, Timestamp] ]
  }
  override def field1 : Field[String] = Lessons.LESSONS.ID
  override def field2 : Field[String] = Lessons.LESSONS.LIB
  override def field3 : Field[Timestamp] = Lessons.LESSONS.CREATE_TIME
  override def field4 : Field[Timestamp] = Lessons.LESSONS.MODIFY_TIME
  override def component1 : String = getId
  override def component2 : String = getLib
  override def component3 : Timestamp = getCreateTime
  override def component4 : Timestamp = getModifyTime
  override def value1 : String = getId
  override def value2 : String = getLib
  override def value3 : Timestamp = getCreateTime
  override def value4 : Timestamp = getModifyTime

  override def value1(value : String) : LessonsRecord = {
    setId(value)
    this
  }

  override def value2(value : String) : LessonsRecord = {
    setLib(value)
    this
  }

  override def value3(value : Timestamp) : LessonsRecord = {
    setCreateTime(value)
    this
  }

  override def value4(value : Timestamp) : LessonsRecord = {
    setModifyTime(value)
    this
  }

  override def values(value1 : String, value2 : String, value3 : Timestamp, value4 : Timestamp) : LessonsRecord = {
    this.value1(value1)
    this.value2(value2)
    this.value3(value3)
    this.value4(value4)
    this
  }

  /**
   * Create a detached, initialised LessonsRecord
   */
  def this(id : String, lib : String, createTime : Timestamp, modifyTime : Timestamp) = {
    this()

    set(0, id)
    set(1, lib)
    set(2, createTime)
    set(3, modifyTime)
  }
}
