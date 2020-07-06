package info.galudisu

import akka.actor.typed.ActorRef

trait Command

// 数据库处理内容
trait DbExecAsyncResult                                                                                  extends Command
case class DbExecAsyncSuccess[T](result: T, replyTo: ActorRef[Command])                                  extends DbExecAsyncResult
case class DbExecAsyncFailure(replyTo: ActorRef[Command])                                                extends DbExecAsyncResult
private final case class DbExecAsyncWrappedResult(result: DbExecAsyncResult, replyTo: ActorRef[Command]) extends Command


// 公共事件
case object Finish extends Command
case object Done extends Command

case object Trash extends Command
