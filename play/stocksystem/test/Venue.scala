import play._
import play.test._

import org.scalatest._
import org.scalatest.junit._
import org.scalatest.matchers._

import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

import models._


class VenueTests extends UnitFlatSpec with ShouldMatchers {
    it should "Create new venue" in {
      
      //Count number of users
      val s = Venue.getVenues.size
	
      //create new user
      Venue.create("newvenue","Fantastic New Venue in lovely countryside setting")
      //Is difference in user count 1

			val t = Venue.getVenues.size
			(t - s) should equal (1)
    }

		it should "Not create duplicate venues" in 
		{
			val s = Venue.getVenues.size
			Venue.create("newvenue","Anohter Fantastic newe venue")
			
			val t = Venue.getVenues.size
			(t - s) should equal (0)
		}
		it should "update venues" in
		{
		true should equal(true)
		}
    
		it should "not create venues without a name" in
		{
			Venue.create("","secret") should equal (false)
		}

}