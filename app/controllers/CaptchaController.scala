package controllers

import akka.stream.scaladsl.{Source, StreamConverters}
import akka.util.ByteString
import javax.inject.{Inject, Singleton}
import models.CaptchaInfo
import play.api.libs.Codecs
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.ExecutionContext.Implicits.global



@Singleton
class CaptchaController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getCaptcha(w: Int, h: Int) = Action { implicit request =>
    val captcha = CaptchaInfo.create(w, h)
    val data = captcha.value
    val dataContent: Source[ByteString, _] = StreamConverters.fromInputStream(() => data)
    Ok.chunked(dataContent).withSession(request.session + ("captcha" -> Codecs.sha1(captcha.text)))
  }
}
