package main

import (
  "fmt"
  "math/big"
  "github.com/cznic/mathutil"
)

func main() {
  fmt.Println("Question 1:")
  q1()
  fmt.Println("Question 2:")
  q2()
}

func q1() {
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

func q2() {
  n := new(big.Int)
  a := new(big.Int)
  asquared := new(big.Int)
  one := new(big.Int)
  x := new(big.Int)
  xsquared := new(big.Int)
  p := new(big.Int)
  q := new(big.Int)
  candidate := new(big.Int)

  n.SetString("648455842808071669662824265346772278726343720706976263060439070378797308618081116462714015276061417569195587321840254520655424906719892428844841839353281972988531310511738648965962582821502504990264452100885281673303711142296421027840289307657458645233683357077834689715838646088239640236866252211790085787877", 10)
  one.SetString("1",10)

  a = mathutil.SqrtBig(n)
  for {
    a.Add(a, one)
    asquared.Mul(a,a)
    xsquared.Sub(asquared,n)
    x = mathutil.SqrtBig(xsquared)
    p.Sub(a,x)
    q.Add(a,x)
    if candidate.Mul(p,q).Cmp(n) == 0 {
      fmt.Println(p.String())
      break
    }
  }
}