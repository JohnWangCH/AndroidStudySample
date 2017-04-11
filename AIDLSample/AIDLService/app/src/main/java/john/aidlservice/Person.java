package john.aidlservice;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by John.Wang on 2017/4/10.
 */

public class Person implements Parcelable {

    private String name;
    private int sex;

    public String getName()
    {
        return name;
    }

    public int getSex()
    {
        return sex;
    }

    protected Person(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }

    protected Person(Parcel in) {

        readFromParcel(in);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(sex);
    }

    public void readFromParcel(Parcel parcel)
    {
        //讀取參數的順序必須與當初寫入parcel的順序一致，才能取得正確的value
        name = parcel.readString();
        sex = parcel.readInt();
    }
}
