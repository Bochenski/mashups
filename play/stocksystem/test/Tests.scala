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
      MongoDB.isValid should equal (true)
    }
    
    it should "Reset all data in test database" in {
      MongoDB.reset should equal (true)
    }
    
    it should "Register new user" in {
      
      //Count number of users
      val s = User.getUsers.size
	
      //Register new user
      User.register("newuser","secret")
      //Is difference in user count 1

			val t = User.getUsers.size
			(t - s) should equal (1)
    }

		it should "Not register duplicate users" in 
		{
			val s = User.getUsers.size
			User.register("newuser","secret")
			
			val t = User.getUsers.size
			(t - s) should equal (0)
		}
    
		it should "work" in
		{
			1 should equal (1)
		}
    

}