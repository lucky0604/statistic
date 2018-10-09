package services

import play.api.Application

object ViewAccessPoint {
  private val myDaoCache = Application.instanceCache[MyDao]

  object Implicits {
    implicit def myDao(implicit application: Application): MyDao = myDaoCache(application)
  }
}
