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
  lazy val username = Field.scalar("username", _.username)
  lazy val email = Field.scalar("email", _.email)
  lazy val isAdmin = Field.scalar("isAdmin", _.isAdmin)
  lazy val password = Field.scalar("password", _.password, 
           (x: User, v: String) => x.password = Codec.hexMD5(v))
  
  
  override lazy val * = List(username, email, isAdmin, password)
  override def factory(dbo: DBObject) = Some(new User)
  
  def isValidLogin(username: String, password: String) = {
    //return TRUE if there is a matching username / passwordHASH
    val user = (User where { User.username is_== username }take 1 in getUsers).headOption
    user match { 
      case Some(user) => user.password == Codec.hexMD5(password) 
      case None => false
    }
  }
  
  def register(username: String, password: String) = {
    //check whether the user exists
    val user = (User where { User.username is_== username}take 1 in getUsers).headOption
    user match {
      case Some(_) => false
      case None => {
        val newUser = new User()
        newUser.username = username
        newUser.password = password
        getUsers << newUser
        true
      }
    }
  }
  
  def getUsers = MongoDB.getDB.getCollection("Users") of User
  
  def byEmail(email: String) = {
    val db = MongoDB.getDB
    val users = db.getCollection("Users") of User
    val user = (User where { User.email is_== email } take 1 in users).head
    user
  }
}
