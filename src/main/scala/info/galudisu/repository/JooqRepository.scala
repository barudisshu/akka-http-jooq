package info.galudisu.repository

import org.jooq.DSLContext

trait JooqRepository {
  def dsl: DSLContext
}
