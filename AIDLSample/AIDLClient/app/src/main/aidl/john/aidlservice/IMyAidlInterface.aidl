// IMyAidlInterface.aidl
package john.aidlservice;
import john.aidlservice.Person;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    String Greating(String who);

    String Complimenting(in Person who);
}
