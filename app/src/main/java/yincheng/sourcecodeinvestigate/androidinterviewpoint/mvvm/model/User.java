package yincheng.sourcecodeinvestigate.androidinterviewpoint.mvvm.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yincheng on 2018/5/22/14:34.
 * github:luoyincheng
 */
public class User implements Parcelable {
    public long id;
    public String name;
    public String url;
    public String email;
    public String login;
    public String location;
    /**
     * 说明:@SerializedName的作用:avatar_url是服务器返回的json数据中的格式，但是我们客户端程序开发者如果感觉后端
     * 的命名不规范或者不“言简意赅”,可以用@SerializedName标记该字段，@SerializedName括号后面的字符串是对应于服
     * 务器上返回的字段的，下面的avatarUrl是客户端工程师对该字段比较合理的命名
     * An annotation that indicates this member should be serialized to JSON with
     * the provided name value as its field name.
     */
    @SerializedName("avatar_url") public String avatarUrl;

    public User() {}

    protected User(Parcel in) {
        id = in.readLong();
        name = in.readString();
        url = in.readString();
        email = in.readString();
        login = in.readString();
        location = in.readString();
        avatarUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override public User[] newArray(int size) {
            return new User[size];
        }
    };

    public boolean hasEmail() { return email != null && !email.isEmpty(); }

    public boolean hasLocation() { return location != null && !location.isEmpty(); }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.email);
        dest.writeString(this.login);
        dest.writeString(this.location);
        dest.writeString(this.avatarUrl);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (url != null ? !url.equals(user.url) : user.url != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (location != null ? !location.equals(user.location) : user.location != null)
            return false;
        return !(avatarUrl != null ? !avatarUrl.equals(user.avatarUrl) : user.avatarUrl != null);
    }

    @Override public int hashCode() {// TODO: 2018/5/22 to un
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }
}
