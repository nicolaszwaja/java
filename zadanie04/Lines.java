import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Lines implements LinesInterface {

    public int iloscPunktow=0;
    public Set<Point> punkty = new HashSet<>();
    public Set<Segment> odcinki = new HashSet<>();
    public List<Segment> connection = new ArrayList<Segment>();
    public Map<Point, Set<Point>> graph = new HashMap<>();
    public Map<Point, Set<Segment>> graph2 = new HashMap<>();
    public Map<Point, Set<Segment>> endpointToSegments = new HashMap<>();
    public Set<Point> setOfReachablePoints = new HashSet<>();

    @Override
    public void addPoints(Set<LinesInterface.Point> points) {
        iloscPunktow=0;
        for(Point x: points){
            punkty.add(x);
            graph.put(x, new HashSet<Point>());
            graph2.put(x,new HashSet<Segment>());
            iloscPunktow++;
        }
    }

    @Override
    public void addSegments(Set<LinesInterface.Segment> segments) {
        for(Segment s : segments){
            odcinki.add(s);
            graph.get(s.getEndpoint1()).add(s.getEndpoint2());
            graph.get(s.getEndpoint2()).add(s.getEndpoint1());
            graph2.get(s.getEndpoint1()).add(s);
            graph2.get(s.getEndpoint2()).add(s);
        }
    }

    @Override
    public List<Segment> findConnection(Point start, Point end){

        Set<Point> visited = new HashSet<>();
        Queue<List<Point>> paths = new LinkedList<>();
        List<Point> temp = new LinkedList<>();
        Point head;

        Segment segment;
        visited.add(start);
        temp.add(start);
        paths.add(temp);
        

        while (paths.isEmpty()==false) {
            List<Point> path = paths.poll();  
            head = path.get(path.size() - 1);
            
            if (head==end) {
                /*zmieniamy listę punktów na listę odcinków */
                for (int i = 0; i < path.size() - 1; ++i) {
                    Point point1 = path.get(i);
                    Point point2 = path.get(i + 1);
                    segment=null;
                    for(Segment s: odcinki){
                        if(s.getEndpoint1()==point1 &&s.getEndpoint2()==point2 ){
                            segment = s;
                            connection.add(segment);
                        }
                        if(s.getEndpoint1()==point2 &&s.getEndpoint2()==point1 ){
                            segment = s;
                            connection.add(segment);
                        }   
                    }
                }
                return connection;
            }
            else {
                for (Point child : graph.get(head)) {
                    if (!visited.contains(child)) {
                        visited.add(child);
                        List<Point> newPath = new LinkedList<>(path);
                        newPath.add(child);
                        paths.add(newPath);
                    }
                }  
            }
        }
        return connection;   
    }

    @Override
    public Map<LinesInterface.Point, Set<LinesInterface.Segment>> getMapEndpointToSegments() {
        return graph2;
    }

    public Set<Point> findSetofPoints(Point x1,int i,int depth,Set<Point> visited){

        Set<Point> result = new HashSet<>();
        if (i <= 0 || depth > i){
            return result;
        }

        for (Point x2 : graph.get(x1)) {
            if (!visited.contains(x2)){
                Set<Point> start_visited = new HashSet<>(visited);
                start_visited.add(x1);
                start_visited.add(x2);
                if (depth == i) {
                    result.add(x2);
                }
                result.addAll(findSetofPoints(x2, i, depth + 1, start_visited));
            }   
        }
        return result;
    }

    @Override
    public Map<Point, Map<Integer, Set<Point>>> getReachableEndpoints() {

        Map<Point, Map<Integer, Set<Point>>> reachable = new HashMap<>();

        for (Point x1 : punkty) {
            Map<Integer, Set<Point>> temp = new HashMap<>();
            for (int i = 1; i <= 4; ++i) {
                temp.put(i,findSetofPoints(x1, i, 1, new HashSet<>()));
            }
            reachable.put(x1, temp);
        }

        return reachable;
    }
    
}


