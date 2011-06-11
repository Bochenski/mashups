package models

import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

import play.libs.Codec;

object User {
 
  def validate(username: String, password: String) = {
    //return TRUE if there is a matching username / passwordHASH
    val user = getUser(username)
    user match { 
      case Some(user) => user.as[String]("password") == Codec.hexMD5(password) 
      case None => false
    }
  }
  
 	def login(username: String, password: String) = {
		if (validate(username, password)) {
			getUser(username)
		}
		else
		{
			None
		}
	}
	
  def create(username: String, password: String, email: String) = {
    //check whether the user exists
		if ((username == "") || (username == null) || (password == "") || (password == null))
			 false
		else
		{	
    	val user = getUser(username)
    	user match {
      	case Some(_) => false
      	case None => {
        	val userBuilder = MongoDBObject.newBuilder
        	userBuilder += "username" -> username
        	userBuilder += "password" -> Codec.hexMD5(password)
					userBuilder += "email" -> email
        	val newUser = userBuilder.result
					Casbah.getDB("Users").save(newUser)
        	true
      	}
			}
    }
  }


//convenience functions  

  def getUsers = Casbah.getDB("Users")
  
	def getUser(username: String) = Casbah.getDB("Users").findOne(MongoDBObject("username" -> username))
  
	def getUserByEmail(email: String) = Casbah.getDB("Users").findOne(MongoDBObject("email" -> email))
}
