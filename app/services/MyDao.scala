package services

import javax.inject.Inject
import models._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class MyDao @Inject()(moods: Moods, users: Users, articles: Articles, links: Links) {
  def moodHead = Await.result(moods.queryHead, Duration.Inf)

  def queryUserList = Await.result(users.query, Duration.Inf)

  def queryLinkList = Await.result(links.query, Duration.Inf)

  def queryCatalogList = Await.result(articles.queryCatalog, Duration.Inf)

  def queryArticleBySmileRank = Await.result(articles.queryBySmileCount(0, 5), Duration.Inf)

  def queryArticleByReadRank = Await.result(articles.queryByReadCount(0, 5), Duration.Inf)

  def queryArticleByReplyRank = Await.result(articles.queryByReplyCount(0, 5), Duration.Inf)
}
