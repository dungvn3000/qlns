package plugin


/**
 * The Class Page.
 *
 * @author Nguyen Duc Dung
 * @since 11/15/12 8:39 AM
 *
 */
case class CURDPage(id: String = "", currentPage: Int = 1, currentFilter: String = "", orderBy: String = "id", orderValue: Int = 1) {
  //Default
  var display: Int = 10

  var totalRow: Int = _

  def skip: Int = {
    (currentPage - 1) * display
  }

  def totalPages: Int = {
    var page = totalRow / display
    if (totalRow - page * display > 0) {
      page += 1
    }
    page
  }

  def idAsLongId = id.toLong

  def copyToNewPage(id: String = "", currentPage: Int = 0, currentFilter: String = "", orderBy: String = "", orderValue: Int = 0) = {
    var newPage = this
    newPage = newPage.copy(id = id)
    if (currentPage > 0) newPage = newPage.copy(currentPage = currentPage)
    if (orderValue != 0) newPage = newPage.copy(orderValue = orderValue)
    if (!currentFilter.isEmpty) newPage = newPage.copy(currentFilter = currentFilter)
    if (!orderBy.isEmpty) newPage = newPage.copy(orderBy = orderBy)
    newPage
  }
}