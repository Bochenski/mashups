package com.gintellect.stocksystem.model

import net.liftweb._
import mongodb._
import util.Props
import com.mongodb.{Mongo, MongoOptions, ServerAddress}
import net.liftweb.mongodb.{DefaultMongoIdentifier,MongoDB}


object MongoConfig {
  def setup = {
	val monhost = MongoHost("mongo.stocksystem.dotcloud.com", 5811)
	val monadd = MongoAddress(monhost, "stocksystem")

    MongoDB.defineDbAuth(DefaultMongoIdentifier,monadd, "mdbuser", "Glieck>21")
  }
}


