/**
 * DatabaseHelper.java - Version 2.0
 *
 * Purpose:
 * Serves as a comprehensive database management tool within the app, facilitating CRUD operations on inventory items and user data, and introducing search functionality to enhance data accessibility.
 *
 * Features:
 * - Manages database creation and version management.
 * - Implements CRUD operations for inventory items.
 * - Provides user authentication functionality.
 * - Introduces search functionality for inventory items, leveraging SQLite's LIKE query.
 *
 * Enhancements:
 * - Added getItemsFilteredBy method to support dynamic search based on user queries.
 *
 * Course Outcomes:
 * - "Design, develop, and deliver professional-quality oral, written, and visual communications that are coherent, technically sound, and appropriately adapted to specific audiences and contexts."
 * - "Demonstrate an ability to use well-founded and innovative techniques, skills, and tools in computing practices for the purpose of implementing computer solutions that deliver value and accomplish industry-specific goals."
 *
 * Meeting Course Outcome:
 * The implementation of the search functionality within the DatabaseHelper class embodies the application of advanced database querying techniques and the utilization of SQLite capabilities to meet specific user needs. This enhancement not only improves the app's usability and data retrieval efficiency but also demonstrates a deep understanding of database management and the ability to integrate complex functionalities into a user-centric application.
 *
 * Reflecting on the Enhancement:
 * Enhancing the DatabaseHelper class to include search functionality was an invaluable learning experience that emphasized the importance of database optimization and user-focused design. This process underscored the critical role of data management in software development and reinforced the necessity for continual improvement and adaptation in technology practices.
 *
 * Author: [Jared Semonin]
 * Date: [04/06/2024]
 * Version: 2.0
 */



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


    // Constructor
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
    // Adding new user with hashed password
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
    // Check if user exists with given username and password

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
    // Check if username already exists in the database

    public boolean checkUserExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = cursor.getCount()  > 0;
        cursor.close();
        return exists;
}


    // Adds a new item to the database
    public void addItem(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_QUANTITY, quantity);
        db.insert(TABLE_ITEMS, null, values);
        db.close();

        Log.d("DatabaseHelper", "Item added: Name=" + name + ", Quantity=" + quantity);

    }
    // Retrieves all items from the database
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

    // Updates an existing item's details in the database

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


    // New method to fetch filtered items
    // Method to fetch filtered items based on a query
    public List<Item> getItemsFilteredBy(String query) {

        // New method implementation for search functionality
        // Utilizes LIKE query to perform partial matches on the item name

        List<Item> filteredItemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + COLUMN_NAME + " LIKE ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + query + "%"});

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int quantityIndex = cursor.getColumnIndex(COLUMN_QUANTITY);

            Item item = new Item();
            item.setId(cursor.getInt(idIndex));
            item.setName(cursor.getString(nameIndex));
            item.setQuantity(cursor.getInt(quantityIndex));
            filteredItemList.add(item);
        }
        cursor.close();
        return filteredItemList;
    }


}




/**
 * Item class to represent each inventory item.
 * Contains item id, name, and quantity.
 */class Item {
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