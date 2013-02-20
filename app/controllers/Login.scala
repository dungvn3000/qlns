package controllers

import play.api.mvc.{Action, Controller}
import jp.t2v.lab.play20.auth.LoginLogout
import auth.AuthConfigImpl
import play.api.data._
import play.api.data.Forms._
import models.User

/**
 * The Class Login.
 *
 * @author Nguyen Duc Dung
 * @since 2/20/13, 10:26 AM
 *
 */
object Login extends Controller with LoginLogout with AuthConfigImpl {

  val loginForm = Form {
    tuple(
      "userName" -> nonEmptyText,
      "passWord" -> nonEmptyText
    ) verifying("Invalid user name or password", fields => fields match {
      case (userName, passWord) => User.login(userName, passWord)
    })
  }

  def login = Action {
    Ok(views.html.login(loginForm))
  }

  def authenticate = Action {
    implicit request =>
      loginForm.bindFromRequest.fold(
        errors => BadRequest(views.html.login(errors)),
        user => gotoLoginSucceeded(user._1)
      )
  }

  def logout = Action {
    implicit request => gotoLogoutSucceeded
  }

}
