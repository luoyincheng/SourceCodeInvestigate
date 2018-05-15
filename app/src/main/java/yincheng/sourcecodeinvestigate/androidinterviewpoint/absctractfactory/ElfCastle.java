package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:41.
 * github:luoyincheng
 */
public class ElfCastle implements Castle {
    static final String DESCRIPTION = "this is the elven castle";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
