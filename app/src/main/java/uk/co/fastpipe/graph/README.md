# Dijkstra algorithm 

Dijkstra algorithm used to find the shortest path over the London Tube.
Originally the source code was taken from https://www.baeldung.com/java-dijkstra. 
However we found that it's not working (some parts were missing). We fixed it. 

* Node - single node in graph. In fact this is a station with all its connections
* Graph - class implementing all the logic

# Issues

Currently graph have no weight set for changing the line. I.e. it think that changing from Canary Wharf DLR to 
Canary Wharf tube station takes no time. This affects the routes built.