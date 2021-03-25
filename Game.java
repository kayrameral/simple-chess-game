public class Game{
    public static void main(String[] args) {
        
        Board B = new Board();
        B.putPiece(new King("black"), "g8");
        B.putPiece(new Rook("black"), "e7");
        B.putPiece(new Knight("black"), "b6");
        B.putPiece(new King("white"), "c2");
        B.putPiece(new Bishop("white"), "c4");
        B.putPiece(new Queen("white"), "e3");
        B.putPiece(new Rook("white"), "b4"); if (B.check("white"))
        System.out.println("Beyazin sahi tehdit altinda"); if (B.check("black"))
        System.out.println("Siyahin sahi tehdit altinda");
        if (B.getPiece("b6").canMove("c8") && B.getPiece("c8") == null) System.out.println("Siyahin ati c8’e hareket edebilir");
        if (B.checkMate("white")) System.out.println("Siyah kazandi");
        if (B.checkMate("black")) System.out.println("Beyaz kazandi");
        String[] moves = B.getPiece("c2").getAllMoves(); for (String s: moves)
        System.out.print(s+" ");
        System.out.println("");
        if (B.getPiece("e1") == null) System.out.println("e1 bos");
    }
}
