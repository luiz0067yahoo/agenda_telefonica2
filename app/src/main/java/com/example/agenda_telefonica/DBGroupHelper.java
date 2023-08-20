package com.example.agenda_telefonica;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBGroupHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ContactsPlusDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_GROUPS = "groups";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_GROUP_NAME = "group_name";

    public DBGroupHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_GROUPS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GROUP_NAME + " TEXT " +
                ")";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String createTableQuery = "DROP TABLE IF EXISTS " + TABLE_GROUPS;
        sqLiteDatabase.execSQL(createTableQuery);
    }
    public void seedGroups() {
        SQLiteDatabase db = this.getWritableDatabase();
        //deleteAllGroups();

        if(!checkGroupExistsByName("familia"))
        {
            insertGroup(1,"familia");
        }
        if(!checkGroupExistsByName("trabalho"))
        {

            insertGroup(2,"trabalho");
        }
        if(!checkGroupExistsByName("amigos"))
        {

            insertGroup(3,"amigos");
        }
        db.close();
    }
    public boolean checkGroupExistsByName(String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {COLUMN_ID};
        String selection = COLUMN_GROUP_NAME + " = ?";
        String[] selectionArgs = {groupName};

        Cursor cursor = db.query(TABLE_GROUPS, columns, selection, selectionArgs, null, null, null);

        boolean groupExists = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return groupExists;
    }
    public long insertGroup(String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUP_NAME, groupName);

        long groupId = db.insert(TABLE_GROUPS, null, values);
        db.close();
        return groupId;
    }
    public long insertGroup(int id, String groupName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_GROUP_NAME, groupName);

        long groupId = db.insertWithOnConflict(TABLE_GROUPS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return groupId;
    }

    public Group updateGroup(long id, String newName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Group group=new Group(id,newName);
        ContentValues values = new ContentValues();
        values.put(COLUMN_GROUP_NAME, newName);

        // Atualiza o grupo com o ID especificado
        int rowsAffected = db.update(TABLE_GROUPS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();
        if(rowsAffected>0)
            return group;
        else
            return  null;
    }
    public long deleteGroup(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long deletedRows = db.delete(TABLE_GROUPS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows;
    }

    public long deleteAllGroups() {
        SQLiteDatabase db = this.getWritableDatabase();
        long deletedRows = db.delete(TABLE_GROUPS, null, null);

        db.close();
        return deletedRows;
    }

    public ArrayList<Group> getAllGroups() {
        ArrayList<Group> groupsList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_GROUPS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Group group = new Group();
                int id_index= cursor.getColumnIndex(COLUMN_ID);
                group.setId(cursor.getLong(id_index));
                int id_name= cursor.getColumnIndex(COLUMN_GROUP_NAME);
                group.setName(cursor.getString(id_name));
                groupsList.add(group);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return groupsList;
    }

}
