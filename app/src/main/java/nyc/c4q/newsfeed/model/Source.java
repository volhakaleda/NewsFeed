package nyc.c4q.newsfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Source  implements Parcelable{

    private String id;
    private String name;

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    protected Source(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}
