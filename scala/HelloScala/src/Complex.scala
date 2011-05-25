class Complex(val real: Int, val imaginary: Int) {
	def +(operand: Complex) : Complex = {
		println("Caling +")
		new Complex(real + operand.real, imaginary + operand.imaginary)
	}
	
	def *(operand: Complex) : Complex = {
		println("Calling *")
		new Complex(real * operand.real - imaginary * operand.imaginary, real * operand.imaginary + imaginary * operand.real)
	}
	override def toString() : String = { 
		real + (if (imaginary < 0) "" else "+") + imaginary + "i"
	}
}