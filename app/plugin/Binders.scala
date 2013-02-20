package plugin

import play.api.mvc.{JavascriptLitteral, QueryStringBindable}

/**
 * The Class Binders.
 *
 * @author Nguyen Duc Dung
 * @since 11/15/12 10:39 AM
 *
 */
object Binders {

  type CURDPage = plugin.CURDPage

  implicit def pageToQueryString(implicit binder: QueryStringBindable[String]) = new QueryStringBindable[CURDPage] {
    def bind(key: String, params: Map[String, Seq[String]]): Option[Either[String, CURDPage]] = {
      for {
        id <- binder.bind(key + ".id", params)
        currentPage <- binder.bind(key + ".p", params)
        currentFilter <- binder.bind(key + ".f", params)
        orderBy <- binder.bind(key + ".o", params)
        orderValue <- binder.bind(key + ".ov", params)
      } yield {
        (id, currentPage, currentFilter, orderBy, orderValue) match {
          case (Right(id), Right(currentPage), Right(currentFilter), Right(orderBy), Right(orderValue)) => {
            Right(CURDPage(
              id = id,
              currentPage = currentPage.toInt,
              currentFilter = currentFilter,
              orderBy = orderBy,
              orderValue = orderValue.toInt
            ))
          }
          case _ => Left("Cannot parse parameter " + key + " as Page")
        }
      }
    }

    def unbind(key: String, value: CURDPage): String = binder.unbind(key + ".p", value.currentPage.toString) + "&" +
      binder.unbind(key + ".f", value.currentFilter) + "&" +
      binder.unbind(key + ".o", value.orderBy) + "&" +
      binder.unbind(key + ".ov", value.orderValue.toString) + "&" +
      binder.unbind(key + ".id", value.id)
  }

  implicit def pageJavascriptLitteral = new JavascriptLitteral[CURDPage] {
    def to(value: plugin.CURDPage) = value.toString
  }

}
