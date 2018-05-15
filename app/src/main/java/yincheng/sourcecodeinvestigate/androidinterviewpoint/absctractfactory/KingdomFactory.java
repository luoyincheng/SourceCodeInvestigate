package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:45.
 * github:luoyincheng
 */
public interface KingdomFactory {
    Castle createCastle();

    Army createArmy();

    King createKing();
}
