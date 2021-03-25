public class Bishop extends Piece{

    public Bishop(String color){
        super(color);
    }

    @Override
    public boolean canMove(String newPosition){

        int x_y1[] = parsePosition(newPosition);
        int x1 = x_y1[0];
        int y1 = x_y1[1];

        if(x1==8 || y1<0 || y1>7) return false;

        int x_y2[] = parsePosition(this.getPosition());
        int x2 = x_y2[0];
        int y2 = x_y2[1];

        if(x2==8 || y2<0 || y2>7) return false;
        if(x1==x2 && y1==y2) return false;

        if((y2-y1 == x2-x1) || (y2-y1 == -(x2-x1))) return true;
        return false;
    }

    @Override
    String[] getAllMoves(){

        int number = 0;
        
        for(int i=97; i<=104; i++){
            for(int y=0; y<8; y++){
                if(canMove(Character.toString((char)i)+String.valueOf(y+1))) number++;
            }
        }

        if(number == 0) return null;

        String[] move = new String[number];
        number = 0;
        for(int i=97; i<=104; i++){
            for(int y=0; y<8; y++){
                String position = Character.toString((char)i)+String.valueOf(y+1);
                if(canMove(position)){
                    move[number] = position;
                    number++;
                }
            }
        }
        return move;
    }
}
