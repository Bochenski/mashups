//import java.util._

object HelloScala {
	
	def main (args: Array[String]) {
		val info = getPersonInfo(1)
		println("First Name Is " + info._1)
		
		println("Surname is " + info._2)
		println("Email is " + info._3)
		
		val c1 = new Complex(1, 4)
		val c2 = new Complex(2, -3)
		val sum = c1 + c2
		val c3 = new Complex(2,2)
		
		println("("+ c1 + ") + (" + c2 + ") = " + sum)
		println(c1 + c2 * c3)
		
		val str1 = "hello"
		val str2 = "hello"
		val str3 = new String("hello")
		
		println(str1 == str2) //equivalent to str1.equals(str2)
		println(str1 eq str2) //equivalent to java's str1 == str2
		println(str1 == str3)
		println(str1 eq str3)

		for (i <- 1 to 3) {
			print(i + ",")
		}                                                                                                                                                                                                                                                                                                        
		println("Scala Rocks!!!")
		
		for (input <- Set("test", "hack")) {
			val comment = commentOnPractice(input)
			println("input " + input + " comment " + comment.getOrElse("Found no comments"))
		}
		
		val array = Array(2,3,5,1,6,4)
		val sumtotal = inject(array, 0) { (carryOver, elem) => carryOver + elem}
		println("Sum of elements in array " + array.toString() + " is " + sumtotal)
		
		val max = inject(array, Integer.MIN_VALUE) { (carryOver, elem) => Math.max(carryOver,elem)}
		println("Max of elemente in array is " + max)
		
	
	}
	
	def inject(arr: Array[Int], initial: Int) (operation: (Int, Int) => Int) : Int = {
		var carryOver = initial
		arr.foreach(element => carryOver = operation(carryOver, element))
		carryOver
	}
	
	
	def getPersonInfo(primaryKey : Int) = {
		("David", "Bochenski", "David@Bochenski.co.uk")
	}
	
	def commentOnPractice(input: String) = {
		//rather than returning null
		if( input == "test") Some("good") else None
	}
	

}