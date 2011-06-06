package models

import com.osinka.mongodb._
import com.osinka.mongodb.shape._
import com.mongodb._

class User extends MongoObject {
  var name: String = _
  var email: String = _
  var isAdmin: Boolean = _
}

object User extends MongoObjectShape[User] {
  lazy val name = Field.scalar("name", _.name)
  lazy val email = Field.scalar("email", _.email)
  lazy val isAdmin = Field.scalar("isAdmin", _.isAdmin)
  override lazy val * = List(name, email, isAdmin)
  override def factory(dbo: DBObject) = Some(new User)
}
