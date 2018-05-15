package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:46.
 * github:luoyincheng
 */
public class OrcArmy implements Army {
    static final String DESCRIPTION = "this is the orc army";

    @Override

    public String getDescription() {
        return DESCRIPTION;
    }
}
