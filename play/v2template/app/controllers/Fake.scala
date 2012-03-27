package controllers

import play.api._
import play.api.mvc._

object Fake extends Controller {
  
  def faking = Action { request =>
  	Logger.info(request.domain)
    Ok(views.html.index("domain is: "  + request.domain + " we're in a faking controller."))
  }
  
}