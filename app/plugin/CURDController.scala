package plugin

import play.api.mvc._


/**
 * The Class CURDController.
 *
 * @author Nguyen Duc Dung
 * @since 11/15/12 8:25 AM
 *
 */
trait CURDController extends Controller {

  val curdRoutes: CURDRoutes

  def index(implicit page: CURDPage): Action[AnyContent]

  def add(implicit page: CURDPage): Action[AnyContent]

  def save(implicit page: CURDPage): Action[AnyContent]

  def delete(implicit page: CURDPage): Action[AnyContent]

  def edit(implicit page: CURDPage): Action[AnyContent]

  def curdAction(block: CURDRoutes => Request[AnyContent] => Result): Action[AnyContent] = Action(block(curdRoutes))


}
