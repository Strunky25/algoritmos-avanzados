package transfer;

import core.model.chesspieces.*;

/**
 * Valors de configuració de l'aplicació.
 * Actualment només conté el codi per identificar els distints tipus de peces.
 * Els vostres algorismes han de retornar aquests valors per indicar quina pesa
 * hi ha en una determinada cassella.
 * 
 * @author Bernat Galmés Rubert
 */
public class Config {

    public static final int FIGURES_NONE_CODE = 0;
    public static final int FIGURES_BISHOP_CODE = 1;
    public static final int FIGURES_KNIGHT_CODE = 2;
    public static final int FIGURES_PAWN_CODE = 3;
    public static final int FIGURES_KING_CODE = 4;
    public static final int FIGURES_QUEEN_CODE = 5;
    public static final int FIGURES_ROOK_CODE = 6;
    public static final int FIGURES_INVENTED_1_CODE = 7;
    public static final int FIGURES_INVENTED_2_CODE = 8;
    
    /**
     * Method used to get the respective chesspiece object of a given code.
     * @param figureCode code of the figure.
     * @return respective chesspiece object.
     */
    public static Chesspiece getPiece(int figureCode){
        Chesspiece piece = null;
        switch(figureCode){
            case FIGURES_BISHOP_CODE -> piece =  new Bishop(Chesspiece.Color.white);
            case FIGURES_KNIGHT_CODE -> piece =  new Knight(Chesspiece.Color.white);
            case FIGURES_PAWN_CODE -> piece = new Pawn(Chesspiece.Color.white);
            case FIGURES_KING_CODE -> piece = new King(Chesspiece.Color.white);
            case FIGURES_QUEEN_CODE -> piece = new Queen(Chesspiece.Color.white);
            case FIGURES_ROOK_CODE -> piece = new Rook(Chesspiece.Color.white);
            case FIGURES_INVENTED_1_CODE -> piece = new Ferz(Chesspiece.Color.white);
            case FIGURES_INVENTED_2_CODE -> piece = new Dabbaba(Chesspiece.Color.white);
        }
        return piece;
    }
    
    /**
     * Method used to get the code of a given chesspiece.
     * @param piece piece to be codified.
     * @return the respective code of the given chesspiece.
     */
    public static int getCode(Chesspiece piece){
        int code = 0;
        if(piece != null){
            switch(piece.getType()){
                case "Bishop" -> code = 1;
                case "Knight" -> code = 2;
                case "Pawn" -> code = 3;
                case "King" -> code = 4;
                case "Queen" -> code = 5;
                case "Rook" -> code = 6;
                case "Ferz" -> code = 7;
                case "Dabbaba" -> code = 8;
            }
        }
        return code;
    }

    /**
     * Method used to get all codes from a chesspiece board.
     * @param board board to be transformed.
     * @return  the codified board.
     */
    public static int[][] getBoardCodes(Chesspiece[][] board) {
        int[][] codesBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                codesBoard[i][j] = getCode(board[i][j]);
            }
        }
        return codesBoard;
    }
}