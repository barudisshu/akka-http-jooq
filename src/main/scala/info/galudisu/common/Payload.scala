package info.galudisu.common

import info.galudisu.{Request, Response}

final case class TeachersCreateRequest(firstName: String, lastName: String)                     extends Request
final case class TeachersCreateResponse(teacherId: String, firstName: String, lastName: String) extends Response
