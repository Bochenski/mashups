package controllers

import play._
import play.mvc._
import models._

object Login extends Controller {

  import views.Login._

   def index = html.index()

   def login = {
			User.login(params.get("username"), params.get("password")) match {
				case Some(user) => Logger.info("User ID %s logged in", user.username)
												   setSessionUser(user)
													 Action(Administration.index)
				case None 			=> flash += ("error" -> "Invalid email and/or password")
									 	 			 Action(index)
				}
		}

	  def register = html.register()

	  def doRegister = {
			(User.create(params.get("email"),params.get("password"))) match {
				case true  => val user = User.login(params.get("email"), params.get("password"))
			   						  setSessionUser(user.get)
										  Action(Administration.index)
				case false => flash += ("error" -> "Registration failed")
										  Action(register)
			}
	  }

		private def setSessionUser(user: User) = session.put("username", user.username)
	}
