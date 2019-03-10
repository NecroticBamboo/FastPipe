Classes for raw tube map.

#Raw tube map data

All data stored in resources in form of CSV files.

* TubeStation - represents a single station with its code and name
* TubeLine - tube line with its code, name and color
* TubeConnection - connection (tunnel) between two stations (links to TubeStation via station code and a link to TubeLine via line code)

#Controllers

* TubeReader - static class for reading raw tube map from CSV
* TubeGraph - storage class for the loaded tube map. Contains links to all stations, connections and lines. This class also can convert raw map to nodes for use in Graph