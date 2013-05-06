package main

import (
  "fmt"
  "math/big"
  "github.com/cznic/mathutil"
)

func main() {
  n := new(big.Int)
  sqrtN := new (big.Int)
  a := new(big.Int)
  asquared := new(big.Int)
  one := new(big.Int)
  x := new(big.Int)
  xsquared := new(big.Int)
  //p := new(big.Int)

  n.SetString("179769313486231590772930519078902473361797697894230657273430081157732675805505620686985379449212982959585501387537164015710139858647833778606925583497541085196591615128057575940752635007475935288710823649949940771895617054361149474865046711015101563940680527540071584560878577663743040086340742855278549092581", 10)
  one.SetString("1",10)

  sqrtN = mathutil.SqrtBig(n)
  a.Add(sqrtN, one)
  asquared.Mul(a,a)
  
  xsquared.Sub(asquared,n)

  x = mathutil.SqrtBig(xsquared)

  fmt.Println(a.Sub(a,x).String())

}