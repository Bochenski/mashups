package models

import play._
import com.mongodb._
import com.osinka.mongodb._
import com.osinka.mongodb.shape._

class MongoDB() {

	val host = Play.configuration.getProperty("db.host") 
	val port = Play.configuration.getProperty("db.port").toInt
	val name = Play.configuration.getProperty("db.name")
	val username = Play.configuration.getProperty("db.username") 
	val password = Play.configuration.getProperty("db.password") 
	Logger.info(host + " " + port + " " + name + " " + username + " " + password)
	val server:Mongo = new Mongo(host, port)
	val db:DB = server.getDB(name)
	val connected = db.authenticate(username, password.toCharArray)

  if (!connected) {
  	Logger.fatal("Unable to authenticate against database")
	}
}

object MongoDB {
	private val mongodbInstance = new MongoDB

  def getDB = mongodbInstance.db
	def isValid = mongodbInstance.connected
	def reset = {
	  val colls = getDB.getCollectionNames()
	  Logger.info(colls.toString)
	}
}
