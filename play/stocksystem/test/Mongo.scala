import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

import models._


class MongoTests extends UnitFlatSpec with ShouldMatchers {
    
    it should "Test mongoDB connection" in {
      Casbah.isValid should equal (true)
    }
    
    it should "Reset all data in test database" in {
      Casbah.reset should equal (true)
    }

}