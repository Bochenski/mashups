package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def index = Action { request =>
  	request.domain match {
  		case "localhost" => controllers.Local.localhost(request)
  		case "afakedomain.com" => controllers.Fake.faking(request)
  	}
  }
  
}