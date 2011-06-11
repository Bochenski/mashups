package controllers
 
import play._
import play.mvc._ 
import models._
import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

trait Secure {
  self: Controller =>

  @Before 
  def checkSecurity = {
    session("username") match {
      case Some(username) =>  Logger.info("Logged as %s", username) 
                            Continue
      case None =>  Logger.info("User not logged in");
                     Action(Login.index)
    }
  }
}
