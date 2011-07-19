package controllers

import play._
import play.mvc._
import models._
import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._
object Venue extends Controller {
    
    import views.Venue._

    def index = {
      Logger.info(request.format.toString())
      lazy val venues = models.Venue.getVenues
      request.format match {
        case "xml" => { 
          val venuexml  = 
          <venues> {
            for (v <- venues) yield
            <venue>
             <name>{v.as[String]("name")}</name>
             <description>{v.as[String]("description")}</description>
            </venue>
           }
           </venues>
        Xml(venuexml)
        }
				case "json" => {		
					Json(venues.map { venue => venue.toMap })
				}
        case _ => html.index(venues.map { venue => venue.toMap })
      }
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
