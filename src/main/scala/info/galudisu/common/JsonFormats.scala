package info.galudisu.common

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object JsonFormats {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val teachersCreateRequestJsonFormat: RootJsonFormat[TeachersCreateRequest] = jsonFormat2(TeachersCreateRequest)
  implicit val teachersCreateResponseJsonFormat: RootJsonFormat[TeachersCreateResponse] = jsonFormat3(TeachersCreateResponse)
}
