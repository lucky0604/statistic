import play.api.http.HttpErrorHandler
import play.api.mvc.RequestHeader
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.Future

class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      //Ok(views.html.error40x(statusCode, message))
        Ok("success")
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      //Ok(views.html.error50x(exception.getMessage))
        Ok("failed")
    )
  }

}
