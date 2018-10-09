package controllers

import javax.inject.{Inject, Singleton}
import models.{SubOrderListWrapper, SubOrders}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}

import scala.concurrent.ExecutionContext.Implicits.global


@Singleton
class SubOrderController @Inject()(subOrders: SubOrders)(cc: ControllerComponents)
  extends AbstractController(cc) {

  def query(page: Int, size: Int) = Action.async {
    /*
    subOrders.query.map { x =>
      val subOrderList = SubOrderListWrapper(x.toList, x.length)
      Ok(Json.obj("ret" -> 1, "con" -> Json.toJson(subOrderList)))
    }
    */
    subOrders.query.map {
      x =>
        val subOrderList = SubOrderListWrapper(
          x.toList.slice(size * page, size * page + size),
          x.length
        )

        Ok(Json.obj("ret" -> 1, "con" -> Json.toJson(subOrderList)))
    }
  }
  
  
}
