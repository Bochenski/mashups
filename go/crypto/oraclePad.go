package main

import (
	"fmt"
	"log"
	"net/http"
	"encoding/hex"
)

func main() {
	res, err := http.Get("http://crypto-class.appspot.com/po?er=f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4")
	if err != nil {
		log.Fatal(err)
	}
	//cipher, _ := hex.DecodeString("f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4")
	//cipher, _ := hex.DecodeString("f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0")
	cipher, _ := hex.DecodeString("f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd")
	fmt.Printf("%d\n", res.StatusCode)
	message := decrypt(cipher)
	fmt.Printf("% x\n", message)
    fmt.Printf("%s\n", message)
}

func decrypt(cipher []byte) []byte {
	//detect the number of blocks in the cipher text
	length := len(cipher)
	blocks := (length / 16)
	message := make([]byte, length)
	shiftedMessage := make([]byte, length)
	//we can ignore the first block as it's an IV
	for i:= blocks - 1; i > 0; i -- {

		seedStart := (i-1) * 16
		fmt.Printf("Decrypting Block %d\n", i)

		for j:=15; j >= 0; j-- {
			padLength := 16 - j
			// pad := make([]byte, padLength)
			// for b range pad {
			// 	b = len(pad)
			// }
			fmt.Printf("Decrypting character %d\n", j + 1)
			index := seedStart + j
			for guess:=0; guess < 0xff; guess++ {
				insertion := make([]byte, length)
				insertion[index] = byte(0x00 + guess)
				for x := 0; x < padLength; x++ {
					insertion[index + x] = insertion[index + x] ^ byte(0x00 + padLength)
				}
				fmt.Printf("% x\n", insertion)
				temp := Xor(insertion,shiftedMessage)
				//fmt.Printf("% x\n", temp)
				attempt := Xor(temp, cipher)
				requestString := "http://crypto-class.appspot.com/po?er=" + hex.EncodeToString(attempt)
				res, _ := http.Get(requestString)
				if res.StatusCode == 404 || (res.StatusCode == 200 && j == 7) {
					fmt.Printf("got a status code: %d\n", res.StatusCode)
					message[index + 16] = (byte)(0x00 + guess)
					shiftedMessage[index] = (byte)(0x00 + guess)
					fmt.Printf("got a guess: %d\n", index + 16 )
					fmt.Printf("% x\n", message)
					fmt.Printf("%s\n", message)
					fmt.Printf("moving on")
					break
				}
			}
		}
	}
	return message
}

func Xor (a []byte, b [] byte) []byte {
  res := make([]byte, len(a))
  for i := 0; i< len(a); i ++ {
    res[i] = a[i] ^ b[i]
  }
  return res
}