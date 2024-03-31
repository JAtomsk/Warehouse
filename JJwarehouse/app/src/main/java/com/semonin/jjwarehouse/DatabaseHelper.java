package com.semonin.jjwarehouse;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventoryApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table and columns names
    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_QUANTITY + " INTEGER)";
        db.execSQL(CREATE_TABLE_ITEMS);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

// implement new user registration
public void addUser(String username, String password) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_USER_NAME, username);
    // Hash the password using SHA-256
    String hashedPassword = SecurityUtils.hashPassword(password);
    values.put(COLUMN_USER_PASSWORD, hashedPassword);
    db.insert(TABLE_USERS, null, values);
    db.close();
}
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String hashedPassword = SecurityUtils.hashPassword(password);

        Cursor cursor = db.query("users",
                new String[]{"username"}, // Just need to check existence, not fetch the password
                "username=? AND password=?",
                new String[]{username, hashedPassword}, // Use hashed password for comparison
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
public boolean checkUserExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount()  > 0;
        cursor.close();
        return exists;
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }

    // Method to add an item Algo
    public void addItem(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        db.insert(TABLE_ITEMS, null, values);
        db.close();

        Log.d("DatabaseHelper", "Item added: Name=" + name + ", Quantity=" + quantity);

    }
    // Method to get all items
// ALGO
    public List<Item> getItems() {
        List<Item> itemList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
                int quantityIndex = cursor.getColumnIndex(COLUMN_QUANTITY);

                if (idIndex != -1) {
                    item.setId(cursor.getInt(idIndex));
                }
                if (nameIndex != -1) {
                    item.setName(cursor.getString(nameIndex));
                }
                if (quantityIndex != -1) {
                    item.setQuantity(cursor.getInt(quantityIndex));
                }
                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }
    // Additional CRUD methods (update, delete) can be added here as needed
    public Item getItemById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ITEMS, new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_QUANTITY }, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setId(cursor.getInt(0));
        item.setName(cursor.getString(1));
        item.setQuantity(cursor.getInt(2));
        cursor.close();

        return item;
    }

    public void updateItem(int id, String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);

        db.update(TABLE_ITEMS, values, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    // Method to delete an item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}

// Item class to represent each item
class Item {
    private int id;
    private String name;
    private int quantity;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}