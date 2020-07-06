package info.galudisu.repository

import org.jooq.{Condition, DSLContext}

import scala.concurrent.Future

trait JooqRepository[T, ID] {

  def dsl: DSLContext
  def insertOne(t: T): Future[T]
  def deleteOne(t: T): Future[Int]
  def deleteById(id: ID): Future[Int]
  def update(t: T): Future[T]
  def selectOne(id: ID): Future[T]
  def selectAll(): Future[List[T]]
  def select(condition: Condition): Future[List[T]]
}
