package controllers

import play.api.mvc._
import models.Dao._
import models.Job
import org.springframework.transaction.TransactionStatus
import com.googlecode.mapperdao.Query._
import com.googlecode.mapperdao.Delete._

object Application extends Controller {

  def index = Action {

    tx {
      status: TransactionStatus =>
        mapperDao.insert(Job, Job("giam doc"))
    }

    val q = delete from Job

    q.run

    Ok(views.html.index(Nil))
  }

}