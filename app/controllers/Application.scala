package controllers

import play.api.mvc._
import models.{UserJob, Job, User}
import org.joda.time.DateTime

object Application extends Controller {

  def index = Action {


    val userJobs = User.findJobNames(11)

    println(userJobs)

    Ok(views.html.index(Nil))
  }

}