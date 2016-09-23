package gesoft.gapp.databases.camera;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gesoft.gapp.common.T;

/**
 * Created by yhr on 2016/9/20.
 * 用于保存拍照后的照片
 */
public class GCameraSQLHelper extends SQLiteOpenHelper {

    private static GCameraSQLHelper instance;
    private Context mContext;

    private static final String SQL_CREATE_TABLE = "create table "+GCamerField.TABLE_NAME+" (" +
            GCamerField.COLUMN_ID + " integer primary key autoincrement, "+
            GCamerField.COLUMN_LNG + " real, "+
            GCamerField.COLUMN_LAT + " real, "+
            GCamerField.COLUMN_INPUT_DATE + " integer, "+
            GCamerField.COLUMN_PATH + " text) ";

    public static GCameraSQLHelper getInstance( Context context, int version ){
        instance = new GCameraSQLHelper(context, GCamerField.DB_NAME, null, version);
        return instance;
    }

    private GCameraSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
        T.show("表创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+GCamerField.TABLE_NAME);
        onCreate(db);
    }

    public void insertLocation( double lng, double lat, String path, long time ){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put( GCamerField.COLUMN_LAT, lat );
            values.put( GCamerField.COLUMN_LNG, lng );
            values.put( GCamerField.COLUMN_PATH, path );
            values.put( GCamerField.COLUMN_INPUT_DATE, time );
            db.insert( GCamerField.TABLE_NAME, null, values );
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            db.endTransaction();
        }
    }

    public GCameraPhoto queryLocation(String path ){
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = new String[]{ GCamerField.COLUMN_LAT, GCamerField.COLUMN_LNG, GCamerField.COLUMN_PATH, GCamerField.COLUMN_INPUT_DATE };
        Cursor cursor = db.query( GCamerField.TABLE_NAME, columns, GCamerField.COLUMN_PATH + " = ?" , new String[]{ path }, null, null, null );
        GCameraPhoto GCameraPhoto = null;
        if( cursor!=null && cursor.moveToFirst() ){
            GCameraPhoto = new GCameraPhoto();
            GCameraPhoto.setLat( cursor.getDouble( cursor.getColumnIndex( GCamerField.COLUMN_LAT ) ) );
            GCameraPhoto.setLng( cursor.getDouble( cursor.getColumnIndex( GCamerField.COLUMN_LNG ) ) );
            GCameraPhoto.setPath( cursor.getString( cursor.getColumnIndex( GCamerField.COLUMN_PATH ) ) );
            GCameraPhoto.setTime( cursor.getLong( cursor.getColumnIndex( GCamerField.COLUMN_INPUT_DATE ) ) );
            cursor.close();
        }
        return GCameraPhoto;
    }

}
