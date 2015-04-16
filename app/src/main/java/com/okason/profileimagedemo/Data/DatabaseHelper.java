package com.okason.profileimagedemo.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.okason.profileimagedemo.Helpers.Constants;
import com.okason.profileimagedemo.Model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentine on 4/15/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    // Context in which this database exists.
    private Context mContext;

    // Database version.
    public static final int DATABASE_VERSION = 1;

    // Database name.
    public static final String DATABASE_NAME = "ImageExample";

    // Table names.
    public static final String TABLE_CUSTOMERS = "customers";

    private final static String TAG = DatabaseHelper.class.getSimpleName();


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
      }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);

    }

    // Command to create a table of clients.
    private static final String CREATE_CUSTOMER_TABLE = "CREATE TABLE " + TABLE_CUSTOMERS + " ("
            + Constants.COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY, "
            + Constants.COLUMN_IMAGE_PATH + " TEXT, "
            + Constants.COLUMN_NAME + " TEXT, "
            + Constants.COLUMN_PHONE + " TEXT, "
            + Constants.COLUMN_EMAIL + " TEXT, "
            + Constants.COLUMN_STREET + " TEXT, "
            + Constants.COLUMN_CITY + " TEXT, "
            + Constants.COLUMN_STATE + " TEXT, "
            + Constants.COLUMN_ZIP_CODE + " TEXT)";

    // Database lock to prevent conflicts.
    public static final Object[] databaseLock = new Object[0];


    public List<Customer> getAllCustomers() {
        //Initialize an empty list of Customers
        List<Customer> customerList = new ArrayList<Customer>();

        //Command to select all Customers
        String selectQuery = "SELECT * FROM " + TABLE_CUSTOMERS;

        //lock database for reading
        synchronized (databaseLock) {
            //Get a readable database
            SQLiteDatabase database = getReadableDatabase();

            //Make sure database is not empty
            if (database != null) {

                //Get a cursor for all Customers in the database
                Cursor cursor = database.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        Customer customer = getCustomer(cursor);
                        customerList.add(customer);
                        cursor.moveToNext();
                    }
                }
                //Close the database connection
                database.close();
            }
            //Return the list of customers
            return customerList;
        }

    }


    private static Customer getCustomer(Cursor cursor) {
        Customer customer = new Customer();
        customer.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_CUSTOMER_ID)));
        customer.setName(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_NAME)));
        customer.setEmailAddress(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_EMAIL)));
        customer.setPhoneNumber(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_PHONE)));
        customer.setStreetAddress(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_STREET)));
        customer.setCity(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_CITY)));
        customer.setState(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_STATE)));
        customer.setPostalCode(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_ZIP_CODE)));
        customer.setImagePath(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_IMAGE_PATH)));
        return customer;
    }

    public Long addCustomer(Customer customer) {
        Long ret = null;

        //Lock database for writing
        synchronized (databaseLock) {
            //Get a writable database
            SQLiteDatabase database = getWritableDatabase();

            //Ensure the database exists
            if (database != null) {
                //Prepare the customer information that will be saved to the database
                ContentValues values = new ContentValues();
                values.put(Constants.COLUMN_NAME, customer.getName());
                values.put(Constants.COLUMN_EMAIL, customer.getEmailAddress());
                values.put(Constants.COLUMN_PHONE, customer.getPhoneNumber());
                values.put(Constants.COLUMN_CITY, customer.getCity());
                values.put(Constants.COLUMN_STREET, customer.getStreetAddress());
                values.put(Constants.COLUMN_STATE, customer.getState());
                values.put(Constants.COLUMN_ZIP_CODE, customer.getPostalCode());
                values.put(Constants.COLUMN_IMAGE_PATH, customer.getImagePath());

                //Attempt to insert the client information into the transaction table
                try {
                    ret = database.insert(TABLE_CUSTOMERS, null, values);
                } catch (Exception e) {
                    Log.e(TAG, "Unable to add Customer to database " + e.getMessage());
                }
                //Close database connection
                database.close();
            }
        }
        return ret;
    }

    public Customer getCustomerById(long id){
        List<Customer> tempCustomerList = getAllCustomers();
        for (Customer customer : tempCustomerList){
            if (customer.getId() == id){
                return customer;
            }
        }
        return null;
    }

    public boolean customerExists(long id){
        //Check if there is an existing customer
        List<Customer> tempCustomerList = getAllCustomers();
        for (Customer customer : tempCustomerList){
            if (customer.getId() == id){
                return true;
            }
        }
        return false;
    }


}
