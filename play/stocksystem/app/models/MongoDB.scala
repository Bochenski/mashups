package models

import play._
import com.mongodb._
import com.osinka.mongodb._
import com.osinka.mongodb.shape._
object MongoDB {
  private var server:Mongo = null
  private var db:DB = null
  
  def init {
      server = new Mongo("mongo.stocksystem.dotcloud.com", 5811)
      db = server.getDB("stocksystem")
      if (!db.authenticate("mdbuser", "Glieck>21".toCharArray))
        Logger.fatal("Unable to authenticate against database")
  }

  def isOpen = server != null
  def getDB = if (isOpen) db else { init; db }
}
