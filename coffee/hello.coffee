console.log "Hello CoffeeScript!"

class Animal
	price: 5

	sell: ->
		console.log "Give me #{@price} shillings!"

animal = new Animal
animal.sell()