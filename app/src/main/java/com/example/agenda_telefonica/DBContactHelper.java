package com.example.agenda_telefonica;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBContactHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ContactsPlusDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_GROUPS = "groups";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CONTACT_NAME = "contact_name";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_FAVORITE = "favorite";
    private static final String COLUMN_ID_GROUP = "id_group";

    public DBContactHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate() {
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }
    public void seedContacts() {
        if(!checkContactExistsByName("nome1")) {
            insertContact("nome1", "(99) 99999-9999", "Masculino", true, 1);
        }
        if(!checkContactExistsByName("nome2")) {
            insertContact("nome2", "(88) 88888-8888", "Feminino", false, 2);
        }
        if(!checkContactExistsByName("nome3")) {
            insertContact("nome3", "(88) 88888-8888", "Feminino", false, 3);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTACT_NAME + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_FAVORITE + " INTEGER, " +
                COLUMN_ID_GROUP + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_ID_GROUP + ") REFERENCES " +
                TABLE_GROUPS + "(" + COLUMN_ID + ")" +
                ")";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String createTableQuery = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;
        sqLiteDatabase.execSQL(createTableQuery);
    }

    public void onDestroy() {
        SQLiteDatabase db = this.getWritableDatabase();
        String createTableQuery = "DROP TABLE IF EXISTS " + TABLE_CONTACTS;
        db.execSQL(createTableQuery);
    }

    public boolean checkContactExistsByName(String contactName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {COLUMN_ID};
        String selection = COLUMN_CONTACT_NAME + " = ?";
        String[] selectionArgs = {contactName};

        Cursor cursor = db.query(TABLE_CONTACTS, columns, selection, selectionArgs, null, null, null);

        boolean contactExists = cursor != null && cursor.moveToFirst();

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return contactExists;
    }
    public long insertContact(String contactName,String phoneNumber,String gender,boolean favorite,long idGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, contactName);
        values.put(COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_FAVORITE, favorite ? 1 : 0);
        values.put(COLUMN_ID_GROUP, idGroup+"");
        long contactId = db.insert(TABLE_CONTACTS,
                COLUMN_CONTACT_NAME+","+
                COLUMN_PHONE_NUMBER+","+
                COLUMN_GENDER+"," +
                COLUMN_FAVORITE+"," +
                COLUMN_ID_GROUP+""
        , values);
        db.close();
        return contactId;
    }

    public Contact updateContact(long id, String newName,String newPhone, String newGender, boolean newIsFavorite,long newId_group,String newName_group) {
        SQLiteDatabase db = this.getWritableDatabase();
        Contact contact=new Contact(id,newName,newPhone,newGender,newId_group,newName_group,newIsFavorite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACT_NAME, newName);
        values.put(COLUMN_PHONE_NUMBER, newPhone);
        values.put(COLUMN_GENDER, newGender);
        values.put(COLUMN_FAVORITE, newIsFavorite ? 1 : 0);
        values.put(COLUMN_ID_GROUP, newId_group+"");

        // Atualiza o grupo com o ID especificado
        int rowsAffected = db.update(TABLE_CONTACTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        db.close();
        if(rowsAffected>0)
            return contact;
        else
            return  null;
    }
    public long deleteContact(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long deletedRows = db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows;
    }

    public long deleteAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        long deletedRows = db.delete(TABLE_CONTACTS, null, null);
        db.close();
        return deletedRows;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactsList = new ArrayList<>();

        String selectQuery = "SELECT " + TABLE_CONTACTS + ".*, " +
                TABLE_GROUPS + ".group_name " +
                "FROM " + TABLE_CONTACTS +" "+
                "LEFT JOIN " + TABLE_GROUPS +
                " ON (" + TABLE_CONTACTS + "." + COLUMN_ID_GROUP + " = " + TABLE_GROUPS + "." + COLUMN_ID + ")";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                int id_index= cursor.getColumnIndex(COLUMN_ID);
                contact.setId(cursor.getInt(id_index));

                int name_index= cursor.getColumnIndex(COLUMN_CONTACT_NAME);
                contact.setName(cursor.getString(name_index));

                int geder_index= cursor.getColumnIndex(COLUMN_GENDER);
                contact.setGender(cursor.getString(geder_index));

                int favorie_index= cursor.getColumnIndex(COLUMN_FAVORITE);
                int favoriteValue = cursor.getInt(favorie_index);
                boolean isFavorite = favoriteValue == 1; // Convert int to boolean
                contact.setIsFavorite(isFavorite);

                int fone_number_index= cursor.getColumnIndex(COLUMN_PHONE_NUMBER);
                contact.setPhone(cursor.getString(fone_number_index));

                int group_name_index= cursor.getColumnIndex("group_name");
                contact.setNameGroup(cursor.getString(group_name_index));

                int group_id_index= cursor.getColumnIndex(COLUMN_ID_GROUP);
                long IDGroup=cursor.getLong(group_id_index);
                contact.setIDGroup(IDGroup);

                contactsList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return contactsList;
    }


}
