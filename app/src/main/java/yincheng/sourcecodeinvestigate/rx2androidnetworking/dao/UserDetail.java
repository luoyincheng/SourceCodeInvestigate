package yincheng.sourcecodeinvestigate.rx2androidnetworking.dao;

/**
 * Created by yincheng on 2018/5/11/15:58.
 * github:luoyincheng
 */
public class UserDetail {

    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "UserDetail{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
