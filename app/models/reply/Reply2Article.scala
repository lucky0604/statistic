package models.reply

import slick.jdbc.MySQLProfile.api._

object Reply2Article extends Replys {
  override val table = TableQuery[ReplysTable](
    (tag: Tag) => new ReplysTable(tag, "reply2article")
  )
}
