package gesoft.gandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import gesoft.gapp.databases.camera.GCameraSQLHelper;


public class SQLiteActivity extends Activity {

    GCameraSQLHelper sqlHelper;
    //MyDatabaseHelper sqlHelper2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        sqlHelper = GCameraSQLHelper.getInstance(this, 1);
        //sqlHelper = new GCameraSQLHelper(this, GCamerField.DB_NAME, null, 2);
        //sqlHelper2 = new MyDatabaseHelper(this, "yhr.db", null, 2);
    }

    public void createSQLTable( View view ){
        //sqlHelper2.getWritableDatabase();
        sqlHelper.getWritableDatabase();
    }
}
