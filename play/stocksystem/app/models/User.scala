package models

import com.osinka.mongodb._
import com.osinka.mongodb.shape._
import com.mongodb._

import play.libs.Codec;

class User extends MongoObject {
  var username: String = _
  var email: String = _
  var isAdmin: Boolean = _
  var password: String = _
}

object User extends MongoObjectShape[User] {

	//for the benefit of the factory we need to show it how to get and set variables
  lazy val username = Field.scalar("username", _.username, (x: User, v: String) => x.username = v)
  lazy val email = Field.scalar("email", _.email, (x: User, v: String) => x.email = v)
  lazy val isAdmin = Field.scalar("isAdmin", _.isAdmin, (x: User, v: Boolean) => x.isAdmin = v)
  lazy val password = Field.scalar("password", _.password, (x: User, v: String) => x.password = v)
  
  override lazy val * = List(username, email, isAdmin, password)
  override def factory(dbo: DBObject) = Some(new User)
  
  def validate(username: String, password: String) = {
    //return TRUE if there is a matching username / passwordHASH
    val user = getUser(username)
    user match { 
      case Some(user) => user.password == Codec.hexMD5(password) 
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
	
  def create(username: String, password: String) = {
    //check whether the user exists
		if ((username == "") || (username == null) || (password == "") || (password == null))
			 false
		else
		{	
    	val user = getUser(username)
    	user match {
      	case Some(_) => false
      	case None => {
        	val newUser = new User()
        	newUser.username = username
        	newUser.password = Codec.hexMD5(password)
        	getUsers << newUser
        	true
      	}
			}
    }
  }


//convenience functions  

  def getUsers = MongoDB.getDB.getCollection("Users") of User
  
	def getUser(username: String) = { (User where { User.username is_== username}take 1 in getUsers).headOption }
  
	def getUserByEmail(email: String) = { (User where { User.email is_== email } take 1 in getUsers).headOption }
}
