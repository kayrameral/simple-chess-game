public class Piece{

    private String position;
    private String color;
    
    public Piece(String color){
        this.color = color;
    }

    public boolean canMove(String newPosition){ return false;}
    String[] getAllMoves(){ return null;}

    public void setPosition(String newPostion){
        this.position = newPostion;
    }

    public String getPosition(){
        return this.position;
    } 

    public String getColor(){
        return this.color;
    }

    public int[] parsePosition(String pos){

        char letter = pos.charAt(0);
        int y;
        int x = Integer.valueOf(String.valueOf(pos.charAt(1)));
        x = 8 - x;

        switch(letter) {
            case 'a':
                y = 0;
                break;
            case 'b':
                y = 1;
                break;
            case 'c':
                y = 2;
                break;
            case 'd':
                y = 3;
                break;
            case 'e':
                y = 4;
                break;
            case 'f':
                y = 5;
                break;
            case 'g':
                y = 6;
                break;
            case 'h':
                y = 7;
                break;
            default:
                y = 8;
                break;
        }
        return new int[]{x,y};
    }
}