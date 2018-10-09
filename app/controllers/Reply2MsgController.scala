package controllers

import javax.inject.Inject
import models.reply.{Reply, Reply2Message}
import play.api.data
import play.api.data.Forms._
import play.api.libs.Codecs
import play.api.libs.json.{JsNull, Json}
import play.api.mvc.{AbstractController, ControllerComponents}
import utils.ResultStatus

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Reply2MsgController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  val reply2MessageForm = data.Form(
    mapping(
      "aid" -> number(),
      "content" -> text(),
      "name" -> text(),
      "quote" -> number(),
      "email" -> optional(email),
      "url" -> optional(text()),
      "captcha" -> optional(text())
    )(Tuple7.apply)(Tuple7.unapply)
  )

  def retrieve(rid: Long) = Action.async {
    for {
      Some(reply) <- Reply2Message.retrieve(rid)
    } yield Ok(Json.obj("ret" -> 1, "con" -> Json.toJson(reply), "des" -> ResultStatus.status_1))
  }

  def initReply2Message = Action.async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    val captchaText = request.session.get("captcha")
    request.session.-("captcha")

    val reqReplyForm = reply2MessageForm.bindFromRequest().get
    val guestAuth = captchaText == Option(Codecs.sha1(reqReplyForm._7.getOrElse("").toUpperCase))

    val aid = reqReplyForm._1
    val content = reqReplyForm._2
    val name = reqReplyForm._3
    val quote = reqReplyForm._4

    if (uid != 0L || guestAuth) {
      val contentFormat = if (quote != 0) content.substring(content.indexOf(":") + 1) else content
      val reply = Reply(
        Option(aid),
        Option(uid),
        Option(name),
        reqReplyForm._6,
        reqReplyForm._5,
        Option(contentFormat),
        Option(quote)
      )

      Reply2Message.init(reply).map {rid =>
        reply.rid = rid
        Redirect("/blog/message#article_comment")
      }
    } else {
      Future(Ok(Json.obj("ret" -> 5, "con" -> JsNull, "des" -> ResultStatus.status_5)))
    }
  }

  def updateSmileCount(rid: Long) = Action.async {
    {
      for (reply <- Reply2Message.retrieve(rid)) yield reply
    }.flatMap {
      case Some(x) =>
        for (
          updSmile <- Reply2Message.updateSmileCount(rid, x.smile.getOrElse(0) + 1)
        ) yield Ok(Json.obj("ret" -> 1, "con" -> Json.toJson(updSmile), "des" -> ResultStatus.status_1))
      case _ => Future(Ok(Json.obj("ret" -> 0, "con" -> JsNull, "des" -> ResultStatus.status_0)))
    }
  }
}
