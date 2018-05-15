package yincheng.sourcecodeinvestigate.androidinterviewpoint.absctractfactory;

/**
 * Created by yincheng on 2018/5/15/18:43.
 * github:luoyincheng
 */
public class ElfKing implements King {
    static final String DESCRIPTION = "this is the elven king";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
