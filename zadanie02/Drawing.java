
public class Drawing implements SimpleDrawing {

    public int[][] canvas;
    public int size;
    public int currentCoordinateX;
    public int currentCoordinateY;
   

    public void setCanvasGeometry(Geometry input){
        currentCoordinateX=input.getInitialFirstCoordinate();
        currentCoordinateY=input.getInitialSecondCoordinate();
        setSize(input.getSize());
        canvas=new int[size][size];
    }

    public int setSize(int n){
        size=n;
        return size;
    }

    public void draw(Segment segment){

        int direction=segment.getDirection();
        int length=segment.getLength();
        int max_length;
        /*w prawo*/
        if(direction==1){
            max_length=size-currentCoordinateX;
            if(length>max_length){
                length=max_length;
            }
            for(int i=0;i<length;i++){
                canvas[currentCoordinateX][currentCoordinateY]=segment.getColor();
                currentCoordinateX++;
            }
        currentCoordinateX--;    
        }
        /*w lewo*/
        if(direction==-1){
            max_length=currentCoordinateX+1;
            if(length>max_length){
                length=max_length;
            }
            for(int i=0;i<length;i++){
                canvas[currentCoordinateX][currentCoordinateY]=segment.getColor();
                currentCoordinateX--; 
            }
        currentCoordinateX++;    
        }
        /*w górę */
        if(direction==2){
            max_length=size-currentCoordinateY;
            if(length>max_length){
                length=max_length;
            }
            for(int i=0;i<length;i++){
                canvas[currentCoordinateX][currentCoordinateY]=segment.getColor();
                currentCoordinateY++;
            }
        currentCoordinateY--;
        }
        /*w dół */
        if(direction==-2){
            max_length=currentCoordinateY+1;
            if(length>max_length){
                length=max_length;
            }
            for(int i=0;i<length;i++){
                canvas[currentCoordinateX][currentCoordinateY]=segment.getColor();
                currentCoordinateY--;
            }
        currentCoordinateY++;
        }
        return;
    }

    public int[][] getPainting(){
        return canvas;
    }

    public void clear(){
        for(int n=0;n<size;n++){
            for(int m=0;m<size;m++){
                canvas[m][n]=0;
            }
        }

    }

}