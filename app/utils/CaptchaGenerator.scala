package utils

import java.awt.geom.AffineTransform
import java.awt.{Color, Font, RenderingHints}
import java.awt.image.BufferedImage
import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream}

import javax.imageio.ImageIO

import scala.util.Random

object CaptchaGenerator {

  // 验证码组成元素
  lazy val SOURCE = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ"

  // 验证码组成元素长度
  lazy val sourceLength = SOURCE.length

  lazy val random = new Random(System.currentTimeMillis())

  def getCaptcha(w: Int, h: Int): (String, InputStream) = {
    val captchaText = generateCaptchaText(4)
    val image = generateCaptchaImage(w, h, captchaText)
    val os = new ByteArrayOutputStream()
    ImageIO.write(image, "gif", os)
    val captchaValue = new ByteArrayInputStream(os.toByteArray)
    (captchaText, captchaValue)
  }

  /**
    * 生成随机验证码
    * @param captchaSize
    * @return
    */
  def generateCaptchaText(captchaSize: Int): String = {
    (1 to captchaSize).map(_ => SOURCE.charAt(random.nextInt(sourceLength - 1))).mkString("")
  }

  /**
    * 获取随机颜色
    * @param foregroundColor  前景色
    * @param backgroundColor  背景色
    * @return
    */
  def getRandomColor(foregroundColor: Int, backgroundColor: Int): Color = {
    val r: Int = foregroundColor + random.nextInt(backgroundColor - foregroundColor)
    val g: Int = foregroundColor + random.nextInt(backgroundColor - foregroundColor)
    var b: Int = foregroundColor + random.nextInt(backgroundColor - foregroundColor)
    new Color(r, g, b)
  }

  /**
    * 获取随机颜色
    * @return
    */
  def getRandomIntColor: Int = {
    val rgb = getRandomRGB
    var color = 0
    for (single <- rgb) {
      color = color << 8
      color = color | single
    }
    color
  }

  /**
    * 获取随机RGB颜色
    * @return
    */
  def getRandomRGB: Seq[Int] = {
    (1 to 3).map(_ => random.nextInt(255))
  }

  def generateCaptchaImage(w: Int, h: Int, captchaText: String) = {
    val verifySize = captchaText.length
    val image: BufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
    val g2 = image.createGraphics
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    // 设置背景色
    val c = getRandomColor(200, 250)
    g2.setColor(c)
    g2.fillRect(0, 0, w, h)

    // 绘制干扰线
    val random: Random = new Random
    g2.setColor(getRandomColor(160, 200))
    for (i <- 0 until 20) {
      val x = random.nextInt(w - 1)
      val y = random.nextInt(h - 1)
      val xl = random.nextInt(6) + 1
      val yl = random.nextInt(12) + 1
      g2.drawLine(x, y, x + xl + 40, y + yl + 20)
    }

    // 添加噪点
    val yawpRate = 0.02f

    // 噪声率
    val area = (yawpRate * w * h).toInt
    for (i <- 0 until area) {
      val x = random.nextInt(w)
      val y = random.nextInt(h)
      val rgb = getRandomIntColor
      image.setRGB(x, y, rgb)
    }

    g2.setColor(getRandomColor(100, 160))

    val fontSize = if (h >= 40) 16 else h - 15
    val font = new Font("Algerian", Font.ITALIC, fontSize)
    g2.setFont(font)
    val chars = captchaText.toCharArray
    for (i <- 0 until verifySize) {
      val affine = new AffineTransform
      affine.setToRotation(Math.PI / 4 * random.nextDouble * (if (random.nextBoolean) 1 else -1), (w / verifySize) * i + fontSize / 2, h / 2)
      g2.setTransform(affine)
      g2.drawChars(chars, i, 1, ((w - 2) / verifySize) * i + 2, h / 2 + fontSize / 2 - 1)
    }
    g2.dispose()
    image
  }
}
