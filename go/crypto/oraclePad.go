package main

import (
	"fmt"
	"log"
	"net/http"
)

func main() {
	res, err := http.Get("http://crypto-class.appspot.com/po?er=f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4")
	if err != nil {
		log.Fatal(err)
	}
	fmt.Printf("%d", res.StatusCode)
}
