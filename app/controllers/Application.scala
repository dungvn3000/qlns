package controllers

import play.api.mvc._
import models.User

object Application extends Controller {

  def index = Action {
    val user = User.findByUserName("dungvn3000")
    println(user.get.id)
    Ok(views.html.index(Nil))
  }

}