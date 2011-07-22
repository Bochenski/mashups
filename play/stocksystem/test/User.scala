import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

import models._


class UserTests extends UnitFlatSpec with ShouldMatchers {
	it should "Create new user" in {

	  //Count number of users
	  val s = User.getUsers.size

	  //create new user
	  User.create("newuser","secret","newuser@gmail.com")
	  //Is difference in user count 1

		val t = User.getUsers.size
		(t - s) should equal (1)
	}

	it should "Not create duplicate users" in 
	{
		val s = User.getUsers.size
		User.create("newuser","secret","newuser@gmail.com")

		val t = User.getUsers.size
		(t - s) should equal (0)
	}

	it should "not create users without usernames or passwords" in
	{
		User.create("","secret","") should equal (false)
		User.create("unsecureuser","","") should equal (false)
		User.create(null,"secret","") should equal (false)
		User.create("unsecureuser",null,"") should equal (false)
	}

	it should "validate valid users" in
	{
		User.validate("newuser","secret") should equal (true)
	}

	it should "not validate invalid users" in
	{
		User.validate("newuser","nosecret") should equal (false)
		User.validate("olduser","secret") should equal (false)
	}
}
