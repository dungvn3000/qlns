package controllers

import play.api.mvc._
import models.{UserJob, Job, User}
import org.joda.time.DateTime

object Application extends Controller {

  def index = Action {


    val userJobs = User.findJobNamesByUserName("dungvn3000")

    println(userJobs)

    Ok(views.html.index(Nil))
  }

}