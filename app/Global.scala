import com.typesafe.config.ConfigFactory
import java.io.File
import play.api._

/**
 * The Class Global.
 *
 * @author Nguyen Duc Dung
 * @since 11/2/12 12:23 AM
 *
 */
object Global extends GlobalSettings {

  val devConfFilePath = "conf/dev.conf"
  val prodConfFilePath = "conf/prod.conf"

  override def onStart(app: Application) {
    Logger.info("Starting Application")
  }

  override def onStop(app: Application) {
    Logger.info("Application shutdown...")
  }

  override def onLoadConfig(config: Configuration, path: File, classloader: ClassLoader, mode: Mode.Mode) = {
    if (mode == Mode.Prod) {
      val devconfig = ConfigFactory.parseFileAnySyntax(new File(path, prodConfFilePath))
      config ++ Configuration(devconfig)
    } else {
      val devconfig = ConfigFactory.parseFileAnySyntax(new File(path, devConfFilePath))
      config ++ Configuration(devconfig)
    }
  }
}
