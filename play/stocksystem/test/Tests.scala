import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import com.mongodb._
import com.osinka.mongodb._

import models._

class MongoTests extends UnitFlatSpec with ShouldMatchers {
    
    it should "Test mongoDB connection" in {
      MongoDB.init
    }
    
    it should "Create user object and save" in {
      val db = MongoDB.getDB
      val users = db.getCollection("Users") of User
      val user = new User
      user.name = "Bob"
      user.email = "a@b"
      user.isAdmin = true
      users << user
    }

}