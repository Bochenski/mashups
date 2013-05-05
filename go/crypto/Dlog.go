package main

import (
  "fmt"
  "math/big"
)

func main() {
  p := new(big.Int)
  g := new(big.Int)
  h := new(big.Int)

  p.SetString("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084171", 10)
  g.SetString("11717829880366207009516117596335367088558084999998952205599979459063929499736583746670572176471460312928594829675428279466566527115212748467589894601965568", 10)
  h.SetString("3239475104050450443565264378728065788649097520952449527834792452971981976143292558073856937958553180532878928001494706097394108577585732452307673444020333", 10)

  x0 := new(big.Int)
  x1 := new(big.Int)
  //1048577
  
  hash := make(map[string]int)
  for i := 0; i < 1048577; i ++ {
    if i % 10000 == 0 {
      fmt.Println(i)
    }
    x1.SetInt64(int64(i))
    candidate := new(big.Int)
    candidate.Exp(g,x1,p).ModInverse(candidate,p)
    hash[candidate.Mul(candidate,h).Mod(candidate,p).String()] = i
  }

  for i := 0; i < 1048577; i ++ {
    if i % 10000 == 0 {
      fmt.Println(i)
    }
    x0.SetInt64(int64(i))

    candidate := new(big.Int)
    candidate.SetString("1048576", 10)
    candidate.Exp(g, candidate, p).Exp(candidate, x0, p)
    bob, ok := hash[candidate.String()]
    if ok {
      fmt.Println("found a candidate!!!")
      x1.SetInt64(int64(bob))
      break
    }
  }

  B := new(big.Int)
  B.SetString("1048576", 10)
  result := new(big.Int)
  result.Mul(B,x0).Add(result,x1)
  fmt.Println(result.String())

}