package model.chesspieces;

public class Boot extends Chesspiece{
    
    public static final int[] dx = {0,1,2,3,-1,-2,-3, 3, 3, 3,3,3,3,-3,-3,-3,-3,-3,-3,-2,-1, 0, 1, 2};
    public static final int[] dy = {3,3,3,3, 3, 3, 3,-3,-2,-1,0,1,2,-3,-2,-1, 0, 1, 2,-3,-3,-3,-3,-3};

    @Override
    public Type getType() {
        return Type.Boot;
    }

    @Override
    public int[] getDx() {
        return dx;
    }

    @Override
    public int[] getDy() {
        return dy;
    }
}
