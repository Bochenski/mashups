package controllers

import play._
import play.mvc._

object Login extends Controller {

  import views.Login._
  def index = {
    Logger.info("Login")
    html.index()
  }
}
