package bootstrap.liftweb

import _root_.net.liftweb.common._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.com.gintellect.stocksystem.model._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
	def boot{
  // Setup mongo
    MongoConfig.setup

    // where to search snippet
    LiftRules.addToPackages("com.gintellect.stocksystem")

    // Build SiteMap
    LiftRules.setSiteMapFunc(() => sitemap())
//    LiftRules.setSiteMap(SiteMap(entries: _*))
  }

  def sitemap() = SiteMap(
    Menu("Home") / "index",
    Menu("Pet") / "pet" submenus (Menu("Edit Pet") / "pet" / "edit"))
}
