package controllers

import play._
import play.mvc._

object Administration extends Controller with Secure {

  def index = <h1>This is A Secure Page</h1>
}
