package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;


/**
 * Created by yincheng on 2018/5/15/18:46.
 * github:luoyincheng
 */
public class OrcKing implements King {
    static final String DESCRIPTION = "this is the orc king";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
