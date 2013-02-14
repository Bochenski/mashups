import std.stdio;
import std.file;
import std.conv;
import std.math;

int main()
{
  int[] initialConfiguration = new int[10000];
  int i = 0;
  foreach( line; File("QuickSort.txt").byLine())
  {
    initialConfiguration[i] = parse!int(line);
    i ++;
  }
  writeln("array imported");
  ulong comparisons = quickSort(initialConfiguration, 0, initialConfiguration.length);
  writeln("comparisons:");
  writeln(comparisons);
  return 0;

}

void swap (ref int[] A, ulong l, ulong r) {
  int temp = A[l];
  A[l] = A[r];
  A[r] = temp;
}

ulong quickSort(ref int[] A, int l, ulong r) {

  //base case
  //if (r-l <= 1)
  //  return 0;
  if (r == l)
    return 0;

  //writefln("quick sorting %d %d", l, r);
  //writefln("My items are %(%s %|, %).", A);
  //for the first version choose a pivot as the first element of the   
  //(sub) Array A

  //swap first and last element
  int middlePos = l + to!int(floor( (r-l -1) / 2));
  //writefln("middlePos: %d", middlePos);
  int middle = A[middlePos];
  int first = A[l];
  int last = A[r-1];

  //writefln("first: %d middle: %d last %d", first,middle,last);
  if (first < middle)
  {
    if (middle < last)
      swap(A, l, middlePos);
    else if (first < last)
      swap(A, l, r-1);
  }
  else 
  {
    if (last < middle)
      swap(A, l, middlePos);
    else if (last < first)
      swap(A, l, r-1);
  }

  int pivot = A[l];
  //writefln("pivoting around %d", A[l]);
  int i = l + 1;
  foreach (j; l + 1 .. r) {
    //writefln("considering position: %d : %d", j, A[j]);
    if (A[j] < pivot) {
      swap(A,i,j); 
      i++;
    }
  }

  //swap the pivot into place
  swap(A, l, i-1);
  //writefln("After partition around %d : %(%s %|, %).", pivot, A);
  //call recursively on the two halves
  return r - l - 1 + quickSort(A, l, i - 1) + quickSort(A, i, r);

}