/*
 * This file is generated by jOOQ.
 */
package info.galudisu.model


import info.galudisu.model.tables.Classrooms
import info.galudisu.model.tables.Lessons
import info.galudisu.model.tables.Plans
import info.galudisu.model.tables.Teachers
import info.galudisu.model.tables.records.ClassroomsRecord
import info.galudisu.model.tables.records.LessonsRecord
import info.galudisu.model.tables.records.PlansRecord
import info.galudisu.model.tables.records.TeachersRecord

import javax.annotation.Generated

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.Internal

import scala.Array


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>timetable</code> schema.
 */
@Generated(
  value = Array(
    "http://www.jooq.org",
    "jOOQ version:3.12.4"
  ),
  comments = "This class is generated by jOOQ"
)
object Keys {

  // -------------------------------------------------------------------------
  // IDENTITY definitions
  // -------------------------------------------------------------------------


  // -------------------------------------------------------------------------
  // UNIQUE and PRIMARY KEY definitions
  // -------------------------------------------------------------------------

  val KEY_CLASSROOMS_PRIMARY = UniqueKeys0.KEY_CLASSROOMS_PRIMARY
  val KEY_LESSONS_PRIMARY = UniqueKeys0.KEY_LESSONS_PRIMARY
  val KEY_PLANS_PRIMARY = UniqueKeys0.KEY_PLANS_PRIMARY
  val KEY_TEACHERS_PRIMARY = UniqueKeys0.KEY_TEACHERS_PRIMARY

  // -------------------------------------------------------------------------
  // FOREIGN KEY definitions
  // -------------------------------------------------------------------------

  val PLANS_TEACHER_ID = ForeignKeys0.PLANS_TEACHER_ID
  val PLANS_LESSON_ID = ForeignKeys0.PLANS_LESSON_ID
  val PLANS_CLASSROOM_ID = ForeignKeys0.PLANS_CLASSROOM_ID

  // -------------------------------------------------------------------------
  // [#1459] distribute members to avoid static initialisers > 64kb
  // -------------------------------------------------------------------------

  private object UniqueKeys0 {
    val KEY_CLASSROOMS_PRIMARY : UniqueKey[ClassroomsRecord] = Internal.createUniqueKey(Classrooms.CLASSROOMS, "KEY_classrooms_PRIMARY", Classrooms.CLASSROOMS.ID)
    val KEY_LESSONS_PRIMARY : UniqueKey[LessonsRecord] = Internal.createUniqueKey(Lessons.LESSONS, "KEY_lessons_PRIMARY", Lessons.LESSONS.ID)
    val KEY_PLANS_PRIMARY : UniqueKey[PlansRecord] = Internal.createUniqueKey(Plans.PLANS, "KEY_plans_PRIMARY", Plans.PLANS.ID)
    val KEY_TEACHERS_PRIMARY : UniqueKey[TeachersRecord] = Internal.createUniqueKey(Teachers.TEACHERS, "KEY_teachers_PRIMARY", Teachers.TEACHERS.ID)
  }

  private object ForeignKeys0 {
    val PLANS_TEACHER_ID : ForeignKey[PlansRecord, TeachersRecord] = Internal.createForeignKey(info.galudisu.model.Keys.KEY_TEACHERS_PRIMARY, Plans.PLANS, "plans_teacher_id", Plans.PLANS.TEACHER_ID)
    val PLANS_LESSON_ID : ForeignKey[PlansRecord, LessonsRecord] = Internal.createForeignKey(info.galudisu.model.Keys.KEY_LESSONS_PRIMARY, Plans.PLANS, "plans_lesson_id", Plans.PLANS.LESSON_ID)
    val PLANS_CLASSROOM_ID : ForeignKey[PlansRecord, ClassroomsRecord] = Internal.createForeignKey(info.galudisu.model.Keys.KEY_CLASSROOMS_PRIMARY, Plans.PLANS, "plans_classroom_id", Plans.PLANS.CLASSROOM_ID)
  }
}
