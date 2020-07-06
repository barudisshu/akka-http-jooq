package info.galudisu

trait State {
  def entityId: String
  def resp: Option[Response] = None
}
