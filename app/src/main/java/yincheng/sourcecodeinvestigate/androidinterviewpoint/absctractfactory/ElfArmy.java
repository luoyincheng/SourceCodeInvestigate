package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:39.
 * github:luoyincheng
 */
public class ElfArmy implements Army {
    static final String DESCRIPTION = "this is the elven army";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
