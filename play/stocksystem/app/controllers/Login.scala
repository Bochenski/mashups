package controllers

import play._
import play.mvc._

object Login extends Controller {

  def index = {
    Logger.info("Login")
    <h1>Login</h1>
  }
}
