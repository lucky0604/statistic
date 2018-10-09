package utils

import javax.inject.Inject
import play.api.libs.mailer.{Email, MailerClient}

class MailUtil @Inject()(mailerClient: MailerClient) {
  def send(email: Email) = mailerClient.send(email)

  def send(from: String, subject: String, to: Seq[String], bodyText: Option[String], bodyHtml: Option[String], charset: Option[String]): Unit = {
    val email = Email(
      subject,
      from,
      to,
      bodyText,
      bodyHtml,
      charset
    )

    mailerClient.send(email)
  }
}
