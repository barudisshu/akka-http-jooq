package info.galudisu.repository

import java.util.UUID

import com.cplier.hourse.matrix.redflagcal._
import com.cplier.hourse.model.Tables._
import info.galudisu.matrix.redflagcal.{Clazz, Grade, Item, Quota, Result}
import org.jooq.DSLContext

/**
  * 流动红旗分数处理
  * @author Galudisu
  * @param dsl
  */
class QuotaRepository(val dsl: DSLContext) extends JooqRepository {

  private[this] def queryAllQuotaResult(): List[Quota] = {
    val data =
      dsl
        .select(T_QUOTA.ID,
                T_QUOTA.GRADE_ID,
                T_GRADE.NAME,
                T_QUOTA.CLASS_ID,
                T_CLASS.NAME,
                T_QUOTA.MORALITY_ID,
                T_MORALITY.NAME,
                T_QUOTA.SCORE)
        .from(T_QUOTA)
        .leftJoin(T_CLASS)
        .on(T_CLASS.ID.eq(T_QUOTA.CLASS_ID))
        .leftJoin(T_GRADE)
        .on(T_GRADE.ID.eq(T_QUOTA.GRADE_ID))
        .leftJoin(T_MORALITY)
        .on(T_MORALITY.ID.eq(T_QUOTA.MORALITY_ID))
        .fetch()

    data.asScala.map { k =>
      Quota(k.component1(),
            Grade(k.component2(), k.component3()),
            Clazz(k.component4(), k.component5()),
            Item(k.component6(), k.component7()),
            k.component8())
    }.toList
  }

  private[this] def truncateClazzFlowRedFlagTable(): Unit = {
    dsl.truncate(T_CLASS_FLOW_RED_FLAG).execute()
  }

  private[this] def persistClazzFlowRedFlagTable(result: Iterable[Result]): Unit = {
    result
      .foldLeft(
        dsl
          .insertInto(T_CLASS_FLOW_RED_FLAG)
          .columns(T_CLASS_FLOW_RED_FLAG.ID,
                   T_CLASS_FLOW_RED_FLAG.CLASS_ID,
                   T_CLASS_FLOW_RED_FLAG.MORALITY_ID,
                   T_CLASS_FLOW_RED_FLAG.MORALITY_NAME))((stmt, v) => {
        stmt.values(UUID.randomUUID().toString, v.clazz.id, v.item.id, v.item.name)
      })
      .execute()
  }

  private[this] def truncateQuotaTable(): Unit = {
    dsl.truncate(T_QUOTA).execute()
  }
}
