package utils

import java.text.SimpleDateFormat

object DateUtil {
  def format(long: Long): String = {
    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(long)
  }

  def format(long: Option[Long] = Some(System.currentTimeMillis() / 1000)): String = {
    format(long.get)
  }
}
