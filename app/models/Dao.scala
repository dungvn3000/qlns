package models

import play.api.db.DB
import com.googlecode.mapperdao.utils.Setup
import play.api.Play.current
import com.googlecode.mapperdao.jdbc.Transaction

/**
 * The Class Dao.
 *
 * @author Nguyen Duc Dung
 * @since 2/19/13, 10:40 AM
 *
 */
object Dao {
  lazy val dataSource = DB.getDataSource("default")
  implicit lazy val (jdbc, mapperDao, queryDao, transactionManager) = Setup.mysql(dataSource, List(Job, User))
  lazy val tx = Transaction.default(transactionManager)
}
