import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.*;

public class Meeting implements MeetingInterface{

    public List<PawnPosition2D> pionki = new LinkedList<PawnPosition2D>();
    public List<PawnPosition2D> temp = new LinkedList<PawnPosition2D>();
    public int tura=1;
    public int X,Y;
    public int licznik;

    public void addPawns(List<PawnPosition> positions){
        PawnPosition2D p2;
        for(PawnPosition p : positions){
            p2=new PawnPosition2D(p.pawnId(),p.x(),p.y());
            pionki.add(p2);
        }
    }

    public void addMeetingPoint(Position meetingPointPosition){
        X = meetingPointPosition.x();
        Y = meetingPointPosition.y();
    }

    public boolean isAvailable(PawnPosition2D pos){
        int i=0;
        for(PawnPosition2D p: pionki){
            if(p.x()==pos.x() && p.y()==pos.y()){
                i=1;
            }
        }
        for(PawnPosition2D p: temp){
            if(p.x()==pos.x() && p.y()==pos.y()){
                i=1;
            }
        }
        return i==0;
    }

    public void noMove(PawnPosition2D p){
        temp.add(new PawnPosition2D(p.pawnId(),p.x(),p.y()));
        licznik++;
    }

    /*dodać żeby nie było dwóch pionków w tym samym miejscu*/
    public void singleMove(PawnPosition2D p){
                int dx = X-p.x();
                int dy = Y-p.y();

                if(Math.abs(dx)>Math.abs(dy) && dx>0){
                    /*ruch w prawo*/ 
                    PawnPosition2D pos =new PawnPosition2D(p.pawnId(),p.x()+1,p.y());
                    if(isAvailable(pos)==true)
                        temp.add(pos);
                    else
                        noMove(p);    
                }
                else if(Math.abs(dx)>Math.abs(dy) && dx<0){
                    /*ruch w lewo*/
                    PawnPosition2D pos =new PawnPosition2D(p.pawnId(),p.x()-1,p.y());
                    if(isAvailable(pos)==true)
                        temp.add(pos);
                    else
                        noMove(p);    
                }
                else if(Math.abs(dx)<=Math.abs(dy) && dy>0){
                    /*ruch w górę*/
                    PawnPosition2D pos =new PawnPosition2D(p.pawnId(),p.x(),p.y()+1);
                    if(isAvailable(pos)==true)
                        temp.add(pos);
                    else
                        noMove(p);    
                }
                else if(Math.abs(dx)<=Math.abs(dy) && dy<0){
                    /*ruch w dół*/
                    PawnPosition2D pos =new PawnPosition2D(p.pawnId(),p.x(),p.y()-1);
                    if(isAvailable(pos)==true)
                        temp.add(pos);
                    else
                        noMove(p);
                }
                else{
                    /*brak ruchu*/
                    noMove(p);
                }
    }

    public void move(){
        do{
            licznik=0;
            for(PawnPosition2D p : pionki){
                singleMove(p);
            }
            pionki.clear();
            for(PawnPosition2D t : temp){
                pionki.add(t);
            }
            Collections.reverse(pionki);
            temp.clear();

        }while(licznik!=pionki.size());
  
    }

    public Set<PawnPosition> getAllPawns(){
        Set<PawnPosition> pozycje = new HashSet<>();
        for(PawnPosition p : pionki){
            pozycje.add(p);
        }
        return pozycje;
    }

    public Set<PawnPosition> getNeighbours(int pawnId){
        Set<PawnPosition> pozycjeSasiadow = new HashSet<>();
        int x=0,y=0;
        for(PawnPosition p : pionki){
            if(p.pawnId()==pawnId){
                x=p.x();
                y=p.y();
            }
        }
        for(PawnPosition p : pionki){
            if(x==p.x()+1 ||x==p.x()-1 ||y==p.y()+1 || y==p.y()-1){
            pozycjeSasiadow.add(p);
            }
        }
        return pozycjeSasiadow;
    }
    

}