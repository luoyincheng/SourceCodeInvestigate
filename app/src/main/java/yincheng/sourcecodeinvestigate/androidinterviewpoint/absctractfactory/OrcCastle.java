package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:47.
 * github:luoyincheng
 */
public class OrcCastle implements Castle {
    static final String DESCRIPTION = "this is the orc castle";

    @Override

    public String getDescription() {
        return DESCRIPTION;
    }
}
