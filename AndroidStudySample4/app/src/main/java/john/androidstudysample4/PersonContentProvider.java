package john.androidstudysample4;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;

public class PersonContentProvider extends ContentProvider {

    private static final String AUTHORITY = "android.study.sample.sample4";
    private static final int PERSON_ALL = 0;
    private static final int PERSON_ONE = 1;


    private static final UriMatcher matcher;
    private static final Uri NOTIFY_URI = Uri.parse("content://" + AUTHORITY + "/persons");
    private DBHelper helper;
    private SQLiteDatabase db;

    static
    {
        //這裡在定義哪uri的格式，用以區分是針對單筆資料還是全部資料
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "persons", PERSON_ALL);
        matcher.addURI(AUTHORITY, "persons/#", PERSON_ONE);
    }

    public PersonContentProvider() {
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        helper = new DBHelper(this.getContext());
        return true;
    }

    private static final String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/sample4.person";
    private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sample4.person";
    //用來定義MIME type
    @Override
    public String getType(Uri uri) {
        int i = matcher.match(uri);
        switch (i)
        {
            case PERSON_ALL:
                return CONTENT_DIR_TYPE;
            case PERSON_ONE:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException();
        }
    }


    //必須實作這些與database互動的function
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = helper.getWritableDatabase();
        int match = matcher.match(uri);
        switch (match)
        {
            case PERSON_ALL:
                break;
            case PERSON_ONE:
                long _id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
                break;
        }
        //其實最後都是透過db去更變資料
        int count = db.delete("person", selection, selectionArgs);
        if(count > 0)
        {
            notifyDataChange();
        }
        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = matcher.match(uri);
        if(match != PERSON_ALL)
        {
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = helper.getWritableDatabase();
        if(values == null)
        {
            values = new ContentValues();
            values.put("name", "unknow");
            values.put("age", "");
            values.put("info", "");
        }
        long rowId = db.insert("person", null, values);
        if(rowId > 0)
        {
            notifyDataChange();
            return ContentUris.withAppendedId(uri, rowId);
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = helper.getReadableDatabase();
        int match = matcher.match(uri);
        switch (match)
        {
            case PERSON_ALL:
                break;
            case PERSON_ONE:
                long _id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
                break;
            default:
                throw new IllegalArgumentException("Not supported");
        }
        return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        db = helper.getReadableDatabase();
        int match = matcher.match(uri);
        switch (match)
        {
            case PERSON_ALL:
                break;
            case PERSON_ONE:
                long _id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(_id)};
                break;
            default:
                throw new IllegalArgumentException();
        }
        int count = db.update("person", values, selection, selectionArgs);
        if(count > 0)
        {
            notifyDataChange();
        }
        return count;
    }

    private void notifyDataChange() {
        getContext().getContentResolver().notifyChange(NOTIFY_URI, null);
    }
}
