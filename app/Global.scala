import play.api.cache.Cache
import play.api.mvc.RequestHeader
import play.api.{Logger, Application, GlobalSettings}
import play.api.Play.current

/**
 * The Class Global.
 *
 * @author Nguyen Duc Dung
 * @since 11/2/12 12:23 AM
 *
 */
object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Starting Application")
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

  override def onRouteRequest(request: RequestHeader) = {
    //TODO: Hacking auth plugin, keep session after reload application, this code will be remove in production @dungvn3000
    request.session.get("sessionId").map(sessionId => {
      Cache.set(sessionId + ":sessionId", "dungvn3000")
    })
    super.onRouteRequest(request)
  }
}
