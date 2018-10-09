import filters.LoggingFilter
import javax.inject.{Inject, Singleton}
import play.api.http.HttpFilters
import play.api.mvc.EssentialFilter
import play.filters.cors.CORSFilter
import play.filters.gzip.GzipFilter

@Singleton
class Filters @Inject()(gzip: GzipFilter, log: LoggingFilter, corsFilter: CORSFilter) extends HttpFilters {

  override def filters: Seq[EssentialFilter] = Seq(gzip, log, corsFilter)

}
