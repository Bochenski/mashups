package controllers

import play._
import play.mvc._
import models._

object Venue extends Controller {
    
    import views.Venue._

    def index = {
			val names = models.Venue.getVenueNames
		  html.index(names)
		}
		
		def addVenue = {
			val venueName= params.get("name")
			val description = params.get("description")
			models.Venue.create(venueName,description)
			Action(index)
		}
}
