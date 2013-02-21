import std.stdio;
import std.string;
import std.file;
import std.conv;
import std.math;
import std.array;
import std.random;
import std.algorithm;

int main()
{
  Cut[] graph;
  int i = 0;

  foreach (line; File("kargerMinCut.txt").byLine())
  {
    graph.length = i + 1;
    int j = 0;
    Cut cut = new Cut();

    foreach (word; std.array.splitter(strip(line))) {

      if (j == 0){
        cut.vertices.length = 1;
        cut.vertices[0] = parse!ulong(word);
      } else {
        cut.edges.length = j;
        cut.edges[j-1] = parse!ulong(word);
      }
      j++;
    }

    graph[i] = cut;
    i ++;
  }
  ulong result = 200000;
  for (int j = 0; j < 1000; j++) {

    Cut[] clone;
    auto app = appender(clone);
    foreach (cut; graph){
      app.put(cut.dup());
    }
    clone = app.data;
    ulong sample = minCut(clone);

    if (sample < result)
    {
      writeln("-----");
      writeln("new best");
      writeln(sample);
      writeln("");
      result = sample;
      foreach (node; clone){
        writeln("vertices");
        writeln(node.vertices);
      }
    }
  }

  writeln(result);
  return 0;

}

ulong minCut(ref Cut[] graph) {
  
  while (graph.length > 2) {
    ulong r1 = uniform(0, graph.length);
    ulong r2 = uniform(0, graph[r1].edges.length);


    ulong v1 = graph[r1].vertices[0];
    ulong v2 = graph[r1].edges[r2];

    for (int k=0; k<graph.length; k++){
      if (graph[k].vertices[0] == v2)
      {
        r2 = k;
        break;
      }
    }

    //for each of the edges in the second item
    auto app = appender(graph[r1].edges);
    foreach (edge; graph[r2].edges) {
      if (edge != v1) {
       // writefln("appending %d", edge);
        app.put(edge);
        //provided it's not a self loop, add it to the v1 edges
      }
    }
    graph[r1].edges = app.data;

    //remove self loops
    ulong[] newEdges;
    app = appender(newEdges);
    foreach (edge; graph[r1].edges) {
      if (edge != v2 && edge != v1 ) {
        app.put(edge);
      }
    }
    
    graph[r1].edges = app.data;



    app = appender(graph[r1].vertices);
    foreach (vertex; graph[r2].vertices) {
      app.put(vertex);      
    }
    graph[r1].vertices = app.data;

    //reduce the size of the graph    
    graph = remove(graph,r2);

    //now update the edge labels else
    foreach (cut; graph) {
      for (int k = 0; k < cut.edges.length; k++) {
        if (cut.edges[k] == v2)
        {
          cut.edges[k] = v1;
        }
      }
    }
  }
  auto result = graph[0].edges.length;
  return result;
}

class Cut { 
  ulong[] vertices; 
  ulong[] edges; 
  Cut dup() {
    Cut ret = new Cut();
    ret.vertices = vertices.dup;
    ret.edges = edges.dup;
    return ret;
  }
}