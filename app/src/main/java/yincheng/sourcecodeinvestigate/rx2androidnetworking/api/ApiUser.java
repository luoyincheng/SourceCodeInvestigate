package yincheng.sourcecodeinvestigate.rx2androidnetworking.api;

/**
 * Created by yincheng on 2018/5/11/11:22.
 * github:luoyincheng
 */
public class ApiUser {
    public long id;
    public String firstname;
    public String lastname;

    @Override
    public String toString() {
        return "ApiUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
