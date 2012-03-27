package controllers

import play.api._
import play.api.mvc._

object Local extends Controller {
  
  def localhost = Action { request =>
  	Logger.info(request.domain)
    Ok(views.html.index("domain is: "  + request.domain + " we're in a local controller."))
  }
  
}