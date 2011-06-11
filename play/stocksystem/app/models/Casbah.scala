package models

import com.mongodb.casbah.Imports._
import com.mongodb._
import scala.collection.JavaConverters._
import play._

class Casbah() {

	val host = Play.configuration.getProperty("db.host") 
	val port = Play.configuration.getProperty("db.port").toInt
	val name = Play.configuration.getProperty("db.name")
	val username = Play.configuration.getProperty("db.username") 
	val password = Play.configuration.getProperty("db.password") 
	
	Logger.info(host + " " + port + " " + name + " " + username + " " + password)
	val mongo: Mongo = new Mongo(host, port)
	val conn:MongoConnection = new MongoConnection(mongo)
	val db:MongoDB = conn(name)
	val connected = db.authenticate(username, password)

  if (!connected) {
  	Logger.fatal("Unable to authenticate against database")
	}
}

object Casbah {
	private val mongodbInstance = new Casbah

  def getDB = mongodbInstance.db
	
	def isValid = mongodbInstance.connected
	
	def reset = {
	  val colls = getDB.getCollectionNames()

		colls.foreach(col => {
			if (col.startsWith("system."))
				Logger.info("not dropping collection: " + col)
			else
			{
				Logger.info("dropping collection: " + col)
				getDB(col).drop
			}
		})
		true
	}
}