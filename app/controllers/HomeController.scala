package controllers

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.Date

import javax.inject._
import models.reply.{Reply, Reply2Article, Reply2Message, ReplyListTree}
import models.{Articles, Moods, Users}
import org.apache.commons.codec.binary.Base64
import play.api.Configuration
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(
                                cc: ControllerComponents,
                                configuration: Configuration,
                                users: Users,
                                articles: Articles,
                                moods: Moods
                              )(implicit val config: Configuration) extends AbstractController(cc) {

  implicit lazy val shaEncoder = MessageDigest.getInstance("SHA-1")

  val resumeForm = Form(
    mapping(
      "password" -> text()
    )(Tuple1.apply)(Tuple1.unapply)
  )

  val adminForm = Form(
    mapping(
      "name" -> text(),
      "password" -> text(),
    )(Tuple2.apply)(Tuple2.unapply)
  )

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index(page: Int, size: Int) = Action async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    val name = request.session.get("name").getOrElse("")

    val pageFinal = if (page < 1) 0 else page - 1

    users.queryArticleListJoinUser.map { articleList =>
      articleList.map(p => (p._1.wrapArticleList(), p._2))
      Ok(
          "success")
      /**
      Ok(
        views.html.index(uid, name)(articleList.slice(pageFinal * size, pageFinal * size + size), pageFinal)
      )
      * 
      */
    }
  }

  def toLogin = Action.async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    uid match {
      case 0 => Future(
          //Ok(views.html.login(0, ""))
          Ok("success")
          )
      case _ =>
        users.retrieve(uid).map {
          case Some(user) => 
            // Ok(views.html.login(user.uid.getOrElse(0L), user.name.getOrElse("")))
            Ok("success")
        }
    }
  }

  def article(aid: Long) = Action.async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    println(uid + " user uid")
    val name = request.session.get("name").getOrElse("")

    for {
      Some(article) <- articles.retrieve(aid)
      Some(user) <- users.retrieve(article.uid.get)
      replyList <- Reply2Article.queryByAid(aid)
    } yield {
      request.session.get(s"aid:$aid") match {
        case None => articles.updateReadCount(aid, article.read.getOrElse(0) + 1)
        case _ =>
      }
      val replySuper = replyList.filter(_.quote.contains(0L)).sortBy(_.rid)
      val replyListTree = replySuper.map(p =>
        ReplyListTree(replyList, p, Reply2Article.parseReplyTree(Seq(p.rid.get), replyList, new ListBuffer[Reply]).toList.sortBy(_.rid))
      )
      // Ok(views.html.article(uid, name)(user, article, replyListTree)).withSession(request.session + (s"aid:$aid" -> "true"))
      Ok("success")
    }
  }

  def logOut = Action { implicit request =>
    val returnUrl = request.getQueryString("url")
    Redirect(returnUrl.getOrElse("/")).withSession(request.session - "uid")
  }

  def toMessage = Action.async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    val name = request.session.get("name").getOrElse("")

    Reply2Message.queryByAid(0L).map { replyList =>
      val replySuper = replyList.filter(_.quote.contains(0L)).sortBy(_.rid)
      val replyListTree = replySuper.map(p => ReplyListTree(replyList, p, Reply2Message.parseReplyTree(Seq(p.rid.get), replyList, new ListBuffer[Reply]).toList.sortBy(_.rid)))
      //Ok(views.html.message(uid, name)(replyListTree))
      Ok("success")
    }
  }

  def toCatalog(catalog: String, page: Int, size: Int) = Action.async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    val name = request.session.get("name").getOrElse("")
    val pageFinal = if (page < 1) 0 else page - 1

    users.queryArticleByCatalogJoinUser(catalog).flatMap { x =>
      if (x.length == 1) {
        Reply2Article.queryByAid(x.head._1.aid.get).map { replyList =>
          val replySuper = replyList.filter(_.quote.contains(0L)).sortBy(_.rid)
          val replyListTree = replySuper.map(p => ReplyListTree(replyList, p, Reply2Article.parseReplyTree(Seq(p.rid.get), replyList, new ListBuffer[Reply]).toList.sortBy(_.rid)))
          //Ok(views.html.article(uid, name)(x.head._2, x.head._1, replyListTree))
          Ok("success")
        }
      } else {
        x.map(p => (p._1.wrapArticleList(), p._2))
        Future {
          /*
          Ok(
            views.html.index(uid, name)(x.slice(pageFinal * size, pageFinal * size + size), pageFinal)
          )
          * 
          */
          Ok("success")
        }
      }
    }
  }

  def myblogs(uid: Long, page: Int, size: Int) = Action.async { implicit request =>
    val loginUid = request.session.get("uid").getOrElse("0").toLong
    val name = request.session.get("name").getOrElse("")
    val pageFinal = if (page < 1) 0 else page - 1

    for {
      Some(user) <- users.retrieve(uid)
      uArticleList <- articles.queryByUid(uid)
    } yield {
      uArticleList.foreach(p => p.wrapArticleList())
      //Ok(views.html.myblogs(loginUid, name)(user, uArticleList, pageFinal))
      Ok("success")
    }
  }

  def userCenter = TODO

  def toNewArticle = Action { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    val name = request.session.get("name").getOrElse("")

    uid match {
      case 0 => 
        //Ok(views.html.login(uid, name))
        Ok("success")
      case _ => 
        //Ok(views.html.article_new(uid, name))
        Ok("success")
    }
  }

  def toUpdate(aid: Long) = Action.async { implicit request =>
    val uid = request.session.get("uid").getOrElse("0").toLong
    val name = request.session.get("name").getOrElse("")
    for {
      Some(article) <- articles.retrieve(aid)
    } yield {
      //Ok(views.html.article_update(uid, name)(article))
      Ok("success")
    }
  }

  def about = Action {
    //Ok(views.html.footer.about.render())
    Ok("success")
  }

  def toResume = Action {
    //Ok(views.html.footer.toResume.render())
    Ok("success")
  }

  def contactus = Action {
    //Ok(views.html.footer.contactus.render(config))
    Ok("success")
  }

  def checkResumeForm = Action { implicit request =>
    val regForm = resumeForm.bindFromRequest().get
    val password = regForm._1
    password match {
      case x if x == new SimpleDateFormat("MMddHH").format(new Date()) => 
        //Ok(views.html.footer.resume.render())
        Ok("success")
      case _ => Redirect("/blog/resume")
    }
  }

  def toUpload = Action {
    //Ok(views.html.upload())
    Ok("success")
  }

  def resetPassWord = Action {
    //Ok(views.html.password_update())
    Ok("success")
  }

  def qcLoginBack = Action {
    //Ok(views.html.qcback())
    Ok("success")
  }

  def test = Action {
    //Ok(views.html.text())
    Ok("success")
  }

  def adminIndex = Action { implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.index())
      Ok("success")
    }
  }

  def adminLogin = Action { implicit request =>
    //Ok(views.html.admin.login())
    Ok("success")
  }

  def userRegister = Action { implicit request =>
    //Ok(views.html.admin.register())
    Ok("success")
  }

  def adminInfo = Action { implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin)
    } else {
      //Ok(views.html.admin.info())
      Ok("success")
    }
  }

  def adminArticleAdd = Action {implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.article_add())
      Ok("success")
    }
  }

  def adminArticleList(page: Int, size: Int) = Action.async { implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Future.successful(Redirect(routes.HomeController.adminLogin()))
    } else {
      articles.query(page, size).map { list => 
        //Ok(views.html.admin.article_list(list))
        Ok("success")
        }
    }
  }

  def adminArticleReply = Action {implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.article_reply())
      Ok("success")
    }
  }

  def adminMessage = Action {implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.message())
      Ok("success")
    }
  }

  def adminUser = Action {implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.user())
      Ok("success")
    }
  }

  def adminPassword = Action {implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.password())
      Ok("success")
    }
  }

  def adminCategory = Action { implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.category())
      Ok("success")
    }
  }

  def adminCategoryEdit = Action { implicit request =>
    val uid = request.session.get("admin").getOrElse("0").toLong
    if (uid == 0) {
      Redirect(routes.HomeController.adminLogin())
    } else {
      //Ok(views.html.admin.category_edit())
      Ok("success")
    }
  }

  def adminLoginForm = Action.async { implicit request =>
    val reqForm = adminForm.bindFromRequest().get
    val password = Base64.encodeBase64String(shaEncoder.digest(reqForm._2.getBytes()))
    users.queryByName(reqForm._1).map {

      case Some(user) =>
        if (Option(password) == user.password) {
          //Ok(views.html.admin.index.render()).withSession("admin" -> user.uid.get.toString)
          Ok("success")
        } else {
          Redirect(routes.HomeController.adminLogin())
        }
    }
  }
}
