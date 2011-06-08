package controllers
 
import play._
import play.mvc._ 
import models._
 
trait Secure {
  self: Controller =>

  @Before 
  def checkSecurity = {
    session("username") match {
      case Some(username) =>  Logger.info("Logged as %s", User.getUser(username).get.username) 
                            Continue
      case None =>  Logger.info("User not logged in");
                     Action(Login.index)
    }
  }
}
