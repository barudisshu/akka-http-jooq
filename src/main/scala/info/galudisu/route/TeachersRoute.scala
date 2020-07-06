package info.galudisu.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import info.galudisu.common.{TeachersCreateRequest, TeachersCreateResponse}
import info.galudisu.service.TeachersService

import scala.concurrent.{ExecutionContext, Future}

class TeachersRoute(val teachersService: TeachersService)(implicit ec: ExecutionContext) {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import info.galudisu.common.JsonFormats._

  val route: Route = pathPrefix("teachers") {
    post {
      entity(as[TeachersCreateRequest]) { request =>
        val response: Future[Option[TeachersCreateResponse]] = teachersService.create(request)
        onSuccess(response) { performed =>
          rejectEmptyResponse {
            complete((StatusCodes.Created, performed))
          }
        }
      }
    }
  }

}
