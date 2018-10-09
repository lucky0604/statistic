package models

import java.io.InputStream

import utils.CaptchaGenerator
import scala.concurrent.ExecutionContext.Implicits.global


case class CaptchaInfo(text: String, value: InputStream)

object CaptchaInfo {
  def create(w: Int, h: Int): CaptchaInfo = {
    var captcha = CaptchaGenerator.getCaptcha(w: Int, h: Int)
    CaptchaInfo(captcha._1, captcha._2)
  }
}
