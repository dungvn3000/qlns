package auth

import jp.t2v.lab.play20.auth.{CacheIdContainer, IdContainer, AuthConfig}
import models.{User, Permission}
import play.api.mvc._
import play.api.mvc.Results._
import controllers.routes
import scala.reflect._
import play.api.Play
import play.api.Play.current

/**
 * The Class AuthConfigImpl.
 *
 * @author Nguyen Duc Dung
 * @since 11/6/12 10:10 AM
 *
 */
trait AuthConfigImpl extends AuthConfig {

  type Id = String

  type User = models.User

  type Authority = Permission

  implicit val idTag: ClassTag[Id] = classTag[Id]

  def sessionTimeoutInSeconds = 3600

  def resolveUser(id: Id) = User.findByUserName(id)

  def loginSucceeded(request: RequestHeader) = Redirect(routes.Application.index())

  def logoutSucceeded(request: RequestHeader) = Redirect(routes.Application.index())

  def authenticationFailed(request: RequestHeader) = Redirect(routes.Login.login())

  def authorizationFailed(request: RequestHeader) = Forbidden("bien di may")

  def authorize(user: User, authority: Authority) = User.login(user.username, user.password)

  //Hacking auth plugin, keep session after reload application, this code won't work in production mode @dungvn3000
  override lazy val idContainer: IdContainer[Id] = if (Play.isDev) new HackIdContainer else new CacheIdContainer[Id]
}