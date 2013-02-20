package plugin

import play.api.mvc.Call

/**
 * The Class CURDRoutes.
 *
 * @author Nguyen Duc Dung
 * @since 11/16/12 10:35 AM
 *
 */
case class CURDRoutes(_index: (CURDPage) => Call, _add: (CURDPage) => Call, _delete: (CURDPage) => Call, _save: (CURDPage) => Call, _edit: (CURDPage) => Call) {

  //Helper method.
  def index(id: String = "", currentPage: Int = 0, currentFilter: String = "", orderBy: String = "", orderValue: Int = 0)(implicit page: CURDPage): Call = {
    val newPage = page.copyToNewPage(
      id = id,
      currentPage = currentPage,
      currentFilter = currentFilter,
      orderBy = orderBy,
      orderValue = orderValue
    )
    _index(newPage)
  }

  def save(id: String = "", currentPage: Int = 0, currentFilter: String = "", orderBy: String = "", orderValue: Int = 0)(implicit page: CURDPage): Call = {
    val newPage = page.copyToNewPage(
      id = id,
      currentPage = currentPage,
      currentFilter = currentFilter,
      orderBy = orderBy,
      orderValue = orderValue
    )
    _save(newPage)
  }

  def add(id: String = "", currentPage: Int = 0, currentFilter: String = "", orderBy: String = "", orderValue: Int = 0)(implicit page: CURDPage): Call = {
    val newPage = page.copyToNewPage(
      id = id,
      currentPage = currentPage,
      currentFilter = currentFilter,
      orderBy = orderBy,
      orderValue = orderValue
    )
    _add(newPage)
  }

  def edit(id: String = "", currentPage: Int = 0, currentFilter: String = "", orderBy: String = "", orderValue: Int = 0)(implicit page: CURDPage): Call = {
    val newPage = page.copyToNewPage(
      id = id,
      currentPage = currentPage,
      currentFilter = currentFilter,
      orderBy = orderBy,
      orderValue = orderValue
    )
    _edit(newPage)
  }

  def delete(id: String = "", currentPage: Int = 0, currentFilter: String = "", orderBy: String = "", orderValue: Int = 0)(implicit page: CURDPage): Call = {
    val newPage = page.copyToNewPage(
      id = id,
      currentPage = currentPage,
      currentFilter = currentFilter,
      orderBy = orderBy,
      orderValue = orderValue
    )
    _delete(newPage)
  }

}
