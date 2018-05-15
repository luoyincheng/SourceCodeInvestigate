package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:50.
 * github:luoyincheng
 */
public class OrcKingdomFactory implements KingdomFactory {
    @Override
    public Castle createCastle() {
        return new OrcCastle();
    }

    @Override
    public Army createArmy() {
        return new OrcArmy();
    }

    @Override
    public King createKing() {
        return new OrcKing();
    }
}
