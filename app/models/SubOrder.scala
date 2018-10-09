package models


import java.sql.Date

import ai.x.play.json.Jsonx
import javax.inject.Inject
// import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import shapeless._
import slickless._
import slick.jdbc.JdbcProfile

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

case class SubOrder(
                     var orderNo: Option[String] = None,
                     var mainOrderNo: Option[String] = None,
                     var containerNoHead: Option[String] = None,
                     var containerNoBody: Option[String] = None,
                     var waybillNo: Option[String] = None,
                     var waybillId: Option[String] = None,
                     var receiver: Option[String] = None,
                     var trainNumber: Option[String] = None,
                     var sendWeight: Option[String] = None,
                     var fleetId: Option[String] = None,
                     var fleetName: Option[String] = None,
                     var driverId: Option[String] = None,
                     var driverName: Option[String] = None,
                     var truckId: Option[Int] = None,
                     var truckPlate: Option[String] = None,
                     var shippingAddress: Option[String] = None,
                     var bindTime: Option[String] = None,
                     var poundNo: Option[String] = None,
                     var grossWeight: Option[String] = None,
                     var tareWeight: Option[String] = None,
                     var netWeight: Option[String] = None,
                     var grossWeightTime: Option[String] = None,
                     var tareWeightTime: Option[String] = None,
                     var uploadPoundTime: Option[String] = None,
                     var guardConfirmTime: Option[String] = None,
                     var isAuto: Option[Int] = Option(0),
                     var voucherImg: Option[String] = None,
                     var contacts: Option[String] = None,
                     var contactsMobile: Option[String] = None,
                     var coalNo: Option[String] = None,
                     var dutyUpdateNo: Option[String] = None,
                     var sort: Option[Int] = Option(0),
                     var coalBillType: Option[String] = None,
                     var coalState: Option[Int] = Option(0),
                     var recordUser: Option[String] = None,
                     var dutyUser: Option[String] = None,
                     var shipper: Option[String] = None,
                     var startStationCode: Option[String] = None,
                     var startStationName: Option[String] = None,
                     var startStationTime: Option[String] = None,
                     var endStationCode: Option[String] = None,
                     var endStationName: Option[String] = None,
                     var endStationTime: Option[String] = None,
                     var trainType: Option[String] = None,
                     var dispatchTime: Option[String] = None,
                     var fleetDispatch: Option[Int] = Option(0),
                     var powerConfirmTime: Option[String] = None,
                     var isDel: Option[Int] = Option(0),
                     var paraPositionTime: Option[String] = None,
                     var sendAddress: Option[String] = None,
                     var optionTime: Option[Date] = None,
                     var recordTime: Option[String] = None
                   )

case class SubOrderListWrapper(orders: List[SubOrder], count: Int)

object SubOrder {
  implicit val SubOrderJSONFormat = Jsonx.formatCaseClass[SubOrder]
}


object SubOrderListWrapper {
  implicit val SubOrderListWrapperJSONFormat = Jsonx.formatCaseClass[SubOrderListWrapper]
}
/**
object SubOrder {
  implicit val SubOrderJSONFormat = Json.format[SubOrder]
}

object SubOrderListWrapper {
  implicit val SubOrderListWrapperJSONFormat = Json.format[SubOrderListWrapper]
}
*/



class SubOrders @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  class SubOrdersTable(tag: Tag) extends Table[SubOrder](tag, "xl_sub_order") {


    def orderNo = column[Option[String]]("order_no")
    def mainOrderNo = column[Option[String]]("main_order_no")
    def containerNoHead = column[Option[String]]("container_no_head")
    def containerNoBody = column[Option[String]]("container_no_body")
    def waybillNo = column[Option[String]]("waybill_no")
    def waybillId = column[Option[String]]("waybill_id")
    def receiver = column[Option[String]]("receiver")
    def trainNumber = column[Option[String]]("train_number")
    def sendWeight = column[Option[String]]("send_weight")
    def fleetId = column[Option[String]]("fleet_id")
    def fleetName = column[Option[String]]("fleet_name")
    def driverId = column[Option[String]]("driver_id")
    def driverName = column[Option[String]]("driver_name")
    def truckId = column[Option[Int]]("truck_id")
    def truckPlate = column[Option[String]]("truck_plate")
    def shippingAddress = column[Option[String]]("shipping_address")
    def bindTime = column[Option[String]]("bind_time")
    def poundNo = column[Option[String]]("pound_no")
    def grossWeight = column[Option[String]]("gross_weight")
    def tareWeight = column[Option[String]]("tare_weight")
    def netWeight = column[Option[String]]("net_weight")
    def grossWeightTime = column[Option[String]]("gross_weight_time")
    def tareWeightTime = column[Option[String]]("tare_weight_time")
    def uploadPoundTime = column[Option[String]]("upload_pound_time")
    def guardConfirmTime = column[Option[String]]("guard_confirm_time")
    def isAuto = column[Option[Int]]("is_auto")
    def voucherImg = column[Option[String]]("voucher_img")
    def contacts = column[Option[String]]("contacts")
    def contactsMobile = column[Option[String]]("contacts_mobile")
    def coalNo = column[Option[String]]("coal_no")
    def dutyUpdateTime = column[Option[String]]("duty_update_time")
    def sort = column[Option[Int]]("sort")
    def coalBillType = column[Option[String]]("coal_bill_type")
    def coalState = column[Option[Int]]("coal_state")
    def recordUser = column[Option[String]]("record_user")
    def dutyUser = column[Option[String]]("duty_user")
    def shipper = column[Option[String]]("shipper")
    def startStationCode = column[Option[String]]("start_station_code")
    def startStationName = column[Option[String]]("start_station_name")
    def startStationTime = column[Option[String]]("start_station_time")
    def endStationCode = column[Option[String]]("end_station_code")
    def endStationName = column[Option[String]]("end_station_name")
    def endStationTime = column[Option[String]]("end_station_time")
    def trainType = column[Option[String]]("train_type")
    def dispatchTime = column[Option[String]]("dispatch_time")
    def fleetDispatch = column[Option[Int]]("fleet_dispatch")
    def powerConfirmTime = column[Option[String]]("power_confirm_time")
    def isDel = column[Option[Int]]("is_del")
    def paraPositionTime = column[Option[String]]("para_position_time")
    def sendAddress = column[Option[String]]("send_address")
    def optionTime = column[Option[Date]]("option_time")
    def recordTime = column[Option[String]]("record_time")

    def * = (
      orderNo ::
      mainOrderNo ::
      containerNoHead ::
      containerNoBody ::
      waybillNo ::
      waybillId ::
      receiver ::
      trainNumber ::
      sendWeight ::
      fleetId ::
      fleetName ::
      driverId ::
      driverName ::
      truckId ::
      truckPlate ::
      shippingAddress ::
      bindTime ::
      poundNo ::
      grossWeight ::
      tareWeight ::
      netWeight ::
      grossWeightTime ::
      tareWeightTime ::
      uploadPoundTime ::
      guardConfirmTime::
      isAuto ::
      voucherImg ::
      contacts ::
      contactsMobile ::
      coalNo ::
      dutyUpdateTime ::
      sort ::
      coalBillType ::
      coalState ::
      recordUser ::
      dutyUser ::
      shipper ::
      startStationCode ::
      startStationName ::
      startStationTime ::
      endStationCode ::
      endStationName ::
      endStationTime ::
      trainType ::
      dispatchTime ::
      fleetDispatch ::
      powerConfirmTime ::
      isDel ::
      paraPositionTime ::
      sendAddress ::
      optionTime ::
      recordTime :: HNil
    ).mappedWith(Generic[SubOrder])
  }

  val table = TableQuery[SubOrdersTable]

  def query: Future[Seq[SubOrder]] = {
    db.run(table.sortBy(_.recordTime.asc).result)
  }

  def query(page: Int, size: Int): Future[Seq[SubOrder]] = {
    db.run(table.drop(page * size).take(size).result)
  }
}