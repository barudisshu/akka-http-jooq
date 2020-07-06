package info.galudisu.repository

import com.cplier.hourse.model.Tables._
import org.jooq.DSLContext

import scala.compat.java8.FutureConverters._
import scala.concurrent.{ExecutionContext, Future}

/**
  * 系统级别数据操作任务
  *
  * @author Galudisu
  * @param dsl [[DSLContext]] jooq dsl
  */
class SysRepository(val dsl: DSLContext)(implicit ec: ExecutionContext) extends JooqRepository {

  // 删除所有数据记录[不可以drop schema]
  def truncateDb(): Future[Integer] = {
    // 清空[教师、学生、班级、年级、课程德育]
    // todo: 反向清空表内容
    for {
      r <- dsl.truncate(T_STUFF).executeAsync().toScala
      _ <- dsl.truncate(T_STUDENT).executeAsync().toScala
      _ <- dsl.truncate(T_CLASS).executeAsync().toScala
      _ <- dsl.truncate(T_GRADE).executeAsync().toScala
      _ <- dsl.truncate(T_COURSE).executeAsync().toScala
      _ <- dsl.truncate(T_MORALITY).executeAsync().toScala
    } yield r
  }
  // 使用脚本初始化表结构
  def initDb(): Unit = {}
}
