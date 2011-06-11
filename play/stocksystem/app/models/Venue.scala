package models

import com.mongodb.casbah.Imports._
import scala.collection.JavaConverters._

import play.libs.Codec;

class Venue {
	
}

object Venue {
 
	val colname = "Venues"
  
	def update(id :ObjectId, name: String, description: String) = {
		//find the venue by id
		getById(id) match {
			case Some(venue) => {
				venue.put("name" , name)
				venue.put("description" , description)
				Casbah.getDB(colname).save(venue)
			}
		}
	}
	
  def create(name: String, description: String) = {
    //check whether the venue exists
		if ((name == "") || (name == null))
			 false
		else
		{	
    	val user = getVenue(name)
    	user match {
      	case Some(_) => false
      	case None => {
        	val venueBuilder = MongoDBObject.newBuilder
        	venueBuilder += "name" -> name
        	venueBuilder += "description" -> description
        	val newVenue = venueBuilder.result
					Casbah.getDB(colname).save(newVenue)
        	true
      	}
			}
    }
  }


//convenience functions  

  def getVenues = Casbah.getDB(colname)
	
	def getVenueNames = getVenues.toList.map {venue => venue.as[String]("name")}
	
	def getById(id: ObjectId) = Casbah.getDB(colname).findOne(MongoDBObject("_id" -> id))
  
	def getVenue(name: String) = Casbah.getDB(colname).findOne(MongoDBObject("name" -> name))
  
}
