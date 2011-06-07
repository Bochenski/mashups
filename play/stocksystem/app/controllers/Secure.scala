package controllers
 
import play._
import play.mvc._ 
import models._
 
trait Secure {
  self: Controller =>

  @Before 
  def checkSecurity = {
    session("email") match {
      case Some(email) =>  Logger.info("Logged as %s", User.byEmail(email).username) 
                            Continue
      case None =>  Logger.info("User not logged in");
                     Action(Login.index)
    }
  }
}
