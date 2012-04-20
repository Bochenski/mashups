import cucumber.runtime.{ScalaDsl, EN}
import junit.framework.Assert._
import play.api.test._
import play.api.test.Helpers._
import play.api._

class fundamentalStepDefinitions extends ScalaDsl with EN {

	val server = TestServer(3333)
	val browser = TestBrowser.firefox

	When("""^I visit the site$"""){ () =>
	    browser.goTo("http://localhost:3333")
	}

	Then("""^I should see the home page$"""){ () =>
	    Logger.info(browser.title())
	    assertEquals("Welcome to Stack",browser.title())
	}

	Before("@fundamental"){
	    println("Starting Server")
	    server.start
	}

	After("@fundamental"){
		println("Stopping Server")
		server.stop
		if (browser != null) { browser.quit() }
	}
}
