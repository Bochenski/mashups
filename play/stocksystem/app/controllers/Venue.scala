package controllers

import play._
import play.mvc._
import models._

object Venue extends Controller {
    
    import views.Venue._

    def index = {
			val venues = models.Venue.getVenues.map{ venue => venue.toMap }
		  html.index(venues)
		}
		
		def addVenue = {
			val venueName= params.get("name")
			val description = params.get("description")
			models.Venue.create(venueName,description)
			Action(index)
		}
		
		def edit(id :String) = {
		  Logger.info("we made it into update")
		}
}
