package controllers

import play.api.mvc._
import models.User
import org.joda.time.DateTime

object Application extends Controller {

  def index = Action {
//    User.insert(User(
//      username = "dungvn3000",
//      password = "ducdung2512",
//      phone = "0905611699",
//      address = "12 dao duy anh",
//      firstName = "Duc Dung",
//      lastName = "Nguyen",
//      role = "admin",
//      dob = DateTime.now()
//    ))

    val users = User.all()
    Ok(views.html.index(users))
  }

}