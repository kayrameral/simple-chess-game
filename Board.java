import java.util.ArrayList;

public class Board {
    
    private Piece[][] board = new Piece[8][8];

    Board(){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                board[i][j] = null;
            }
        }
    }

    public boolean putPiece(Piece p, String pos){
       
        int x_y[] = parsePosition(pos);
        int x = x_y[0];
        int y = x_y[1];

        if(x==8 || y<0 || y>7) return false;
        this.board[x][y] = p;
        p.setPosition(pos);
        return true;
    }

    public Piece getPiece(String pos){
        
        int x_y[] = parsePosition(pos);
        int x = x_y[0];
        int y = x_y[1];

        return this.board[x][y];
    }

    public boolean check(String color){

        String pos_of_king = null;
        int x = 0;
        int y = 0;

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]!=null && board[i][j].getColor().equals(color) && board[i][j] instanceof King){
                    pos_of_king = Character.toString((char)(j+97))+String.valueOf(8-i);
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        if(pos_of_king == null) return false;
   
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]!=null && !board[i][j].getColor().equals(color)){
                    if(isCheck(x,y,pos_of_king,i,j,board[i][j])) return true;
                }
            }
        }
        return false;
    }

    public boolean checkMate(String color){

        String pos_of_king = null;
        int x = 0;
        int y = 0;

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]!=null && board[i][j].getColor().equals(color) && board[i][j] instanceof King){
                    pos_of_king = Character.toString((char)(j+97))+String.valueOf(8-i);
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        if(pos_of_king == null) return false;

        boolean check = false;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]!=null && !board[i][j].getColor().equals(color)){
                    if(isCheck(x,y,pos_of_king,i,j,board[i][j])){
                        check = true;
                        break;
                    }
                }
            }
        }
        if(!check) return false;

        String[] moves_of_king = board[x][y].getAllMoves();

        for(int i=0; i<moves_of_king.length; i++) {
            if(!isEmpty(moves_of_king[i]) && getPiece(moves_of_king[i]).getColor().equals(color))
                moves_of_king[i] = null; 
        }

        ArrayList<Piece> enemies = new ArrayList<>();

        for(String move: moves_of_king){
            if(move==null) continue;
            int[] xy = parsePosition(move);
            int x2 = xy[0];
            int y2 = xy[1];

            check = false;
            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(board[i][j]!=null && !board[i][j].getColor().equals(color)){
                        if(isCheck(x2,y2,move,i,j,board[i][j])){
                            check = true;
                            enemies.add(board[i][j]);
                            break;
                        }
                    }
                }
            }
            if(!check) return false;
        }

        if(enemies.size()==1 && isThereProtector(enemies.get(0),pos_of_king,x,y,color)){
            return false;
        }

        boolean isNotBeat = true;
        for(Piece piece: enemies){
            int[] xy = parsePosition(piece.getPosition());
            int x2 = xy[0];
            int y2 = xy[1];

            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(board[i][j]!=null && board[i][j].getColor().equals(color)){
                        if(isCheck(x2,y2,piece.getPosition(),i,j,board[i][j])){
                            isNotBeat = false;
                            break;
                        }
                    }
                }
            }
        }
        return isNotBeat;
    }

    public boolean isEmpty(String pos){

        int x_y[] = parsePosition(pos);
        int x = x_y[0];
        int y = x_y[1];
        return (this.board[x][y] == null);
    }

    private int[] parsePosition(String pos){

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

    private boolean isCheck(int x1, int y1, String pos, int x2, int y2, Piece p){

        if((p instanceof King || p instanceof Knight) && p.canMove(pos)) return true;
        else if(p instanceof Rook && p.canMove(pos) && isEmpyBetweenXY(x1,y1,x2,y2)) return true;
        else if(p instanceof Queen && p.canMove(pos)){
            if((x1==x2 || y1==y2) && isEmpyBetweenXY(x1,y1,x2,y2)) return true;
            else if(isEmpyBetweenCross(x1,y1,x2,y2)) return true;
        }
        else if(p instanceof Bishop && p.canMove(pos) && isEmpyBetweenCross(x1,y1,x2,y2)) return true;
        else if(p instanceof Pawn && ((p.getColor().equals("white") && x2-1==x1 && y2-1==y1) 
        || (p.getColor().equals("black") && x2+1==x1 && y2-1==y1) 
        || (p.getColor().equals("white") && x2-1==x1 && y2+1==y1) 
        || (p.getColor().equals("black") && x2+1==x1 && y2+1==y2))) return true;

        return false;
    }

    private boolean isThereProtector(Piece enemy,String pos_of_king,int x1,int y1,String color){
     
        int[] xy = parsePosition(enemy.getPosition());
        int x2 = xy[0];
        int y2 = xy[1];

        if( ((enemy instanceof Queen || enemy instanceof Bishop) && isEmpyBetweenCross(x1, y1, x2, y2)) ||
            (enemy instanceof Rook && isEmpyBetweenXY(x1, y1, x2, y2)) ){
            
            ArrayList<String> between = findBetweenPositions(enemy,x1, y1, x2, y2);

            boolean isBlocked = false;

            for(int i=0; i<8; i++){
                for(int j=0; j<8; j++){
                    if(i==x1 && j==y1) continue;
                    if(board[i][j]!=null && board[i][j].getColor().equals(color)){
                        for(String move: between){
                            if(board[i][j].canMove(move)){
                                isBlocked = true;
                                Piece p = board[i][j];
                                board[i][j] = null;
                                putPiece(p, move);

                                for(int k=0; k<8; k++){
                                    for(int l=0; l<8; l++){
                                        if(board[k][l]!=null && !board[k][l].getColor().equals(color)){
                                            if(isCheck(x1,y1,pos_of_king,k,l,board[k][l])){
                                                isBlocked = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                                
                                board[i][j] = p;
                                int[] xy2 = parsePosition(move);
                                int x3 = xy2[0];
                                int y3 = xy2[1];
                                board[x3][y3] = null;
                                break;
                            }
                        }
                    }
                }
            }
            if(!isBlocked) return false;
        }
        return true;
    }

    private ArrayList<String> findBetweenPositions(Piece p, int x1, int y1, int x2, int y2){

        ArrayList<String> positions = new ArrayList<>();

        if(p instanceof Rook || (p instanceof Queen && (x1==x2 || y1==y2))){

            int start = 0;
            int end = 0;
            if(x1==x2){
                if(y1<y2){
                    start = y1;
                    end = y2;
                }
                else{
                    start = y2;
                    end = y1;
                }

                for(start+=1; start<end; start++){
                    positions.add(Character.toString((char)(start+97))+String.valueOf(8-x1));
                }
            }

            else if(y1==y2){
                if(x1<x2){
                    start = x1;
                    end = x2;
                }
                else{
                    start = x2;
                    end = x1;
                }
                for(start+=1; start<end; start++){
                    positions.add(Character.toString((char)(y1+97))+String.valueOf(8-start));
                }
            }
        }
        
        else if(p instanceof Bishop || (p instanceof Queen && x1!=x2 && y1!=y2)){
            int startX = x1+1, endX = x2;
            int startY = y1+1;
            int increaseX = 0, increaseY = 0;

            if(x1<x2) increaseX = 1;
            else increaseX = -1;

            if(y1<y2) increaseY = 1;
            else increaseY = -1;

            while(startX<endX){
                positions.add(Character.toString((char)(startY+97))+String.valueOf(8-startX));
                startX += increaseX;
                startY += increaseY;
            }
        }
        return positions;
    }

    private boolean isEmpyBetweenXY(int x1, int y1, int x2, int y2){

        if(x1==x2 && (y1-y2==1 || y1-y2==-1)) return true;
        if(y1==y2 && (x1-x2==1 || x1-x2==-1)) return true;
        
        int start = 0;
        int end = 0;
        if(x1==x2){
            if(y1<y2){
                start = y1;
                end = y2;
            }
            else{
                start = y2;
                end = y1;
            }
            for(start+=1; start<end; start++){
                if(board[x1][start] != null) return false;
            }
        }

        else if(y1==y2){
            if(x1<x2){
                start = x1;
                end = x2;
            }
            else{
                start = x2;
                end = x1;
            }
            for(start+=1; start<end; start++){
                if(board[start][y1] != null) return false;
            }
        }
        return true;
    }

    private boolean isEmpyBetweenCross(int x1, int y1, int x2, int y2){

        if((x1-1==x2 && y1-1==y2) || (x1-1==x2 && y1+1==y2)) return true;
        if((x1+1==x2 && y1-1==y2) || (x1+1==x2 && y1+1==y2)) return true;

        int startX = x1+1, endX = x2;
        int startY = y1+1;
        int increaseX = 0, increaseY = 0;

        if(x1<x2) increaseX = 1;
        else increaseX = -1;

        if(y1<y2) increaseY = 1;
        else increaseY = -1;

        while(startX<endX){
            if(board[startX][startY] != null) return false;
            startX += increaseX;
            startY += increaseY;
        }
        return true;
    }
}
