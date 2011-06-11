package controllers

import play._
import play.mvc._
import models._

import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

object Login extends Controller {

  import views.Login._

   def index = html.index()

   def login = {
			User.login(params.get("username"), params.get("password")) match {
				case Some(user) => Logger.info("User ID %s logged in", user.as[String]("username"))
												   setSessionUser(user.as[String]("username"))
													 Action(Administration.index)
				case None 			=> flash += ("error" -> "Invalid email and/or password")
									 	 			 Action(index)
				}
		}

	  def register = html.register()

	  def doRegister = {
			(User.create(params.get("email"),params.get("password"),params.get("email"))) match {
				case true  => Action(login)
				case false => flash += ("error" -> "Registration failed")
										  Action(register)
			}
	  }

		private def setSessionUser(username: String) = session.put("username", username)
	}
