package fooddiarydatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DiaryOperations {
    public static final String LOGTAG = "FOOD_MNG_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DiaryDBHandler.COLUMN_DIARY_ID,
            DiaryDBHandler.COLUMN_DATE,
            DiaryDBHandler.COLUMN_FOOD_NAME,
            DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT,
            DiaryDBHandler.COLUMN_MEAL_TYPE
    };

    public DiaryOperations(Context context){
        dbhandler = new DiaryDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }

    public FoodDiary addFoodDiary(FoodDiary foodDiary) {
        /*String current_date_str = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Date current_date = new SimpleDateFormat("yyyy-MM-dd").parse(current_date_str);*/
        ContentValues values = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_DATE, foodDiary.getDate());
        values.put(DiaryDBHandler.COLUMN_FOOD_NAME, foodDiary.getFood_name());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, foodDiary.getFood_serving_measurement());
        values.put(DiaryDBHandler.COLUMN_MEAL_TYPE, foodDiary.getMeal_type());

        long insertid = database.insert(DiaryDBHandler.TABLE_DIARY,null, values);
        foodDiary.setDiary_id(insertid);
        return foodDiary;
    }

    public FoodDiary getFoodDiary(long id) {

        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY, allColumns,DiaryDBHandler.COLUMN_DIARY_ID + "=?", new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        FoodDiary e = new FoodDiary(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));

        return e;
    }

    public List<FoodDiary> getAllFoodDiary() {
        Cursor cursor = database.query(DiaryDBHandler.TABLE_DIARY, allColumns,null,null,null, null, null);
        List<FoodDiary> foodDiaryList = new ArrayList<>();

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                FoodDiary foodDiary = new FoodDiary();
                foodDiary.setDiary_id(cursor.getLong(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DIARY_ID)));
                foodDiary.setDate(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_DATE)));
                foodDiary.setFood_name(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_NAME)));
                foodDiary.setFood_serving_measurement(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT)));
                foodDiary.setMeal_type(cursor.getString(cursor.getColumnIndex(DiaryDBHandler.COLUMN_MEAL_TYPE)));

                foodDiaryList.add(foodDiary);
            }
        }
        return foodDiaryList;
    }

    public int updateFoodDiary(FoodDiary foodDiary) {
        ContentValues values = new ContentValues();
        values.put(DiaryDBHandler.COLUMN_DATE, foodDiary.getDate());
        values.put(DiaryDBHandler.COLUMN_FOOD_NAME, foodDiary.getFood_name());
        values.put(DiaryDBHandler.COLUMN_FOOD_SERVING_MEASUREMENT, foodDiary.getFood_serving_measurement());
        values.put(DiaryDBHandler.COLUMN_MEAL_TYPE, foodDiary.getMeal_type());

        return database.update(DiaryDBHandler.TABLE_DIARY, values,
                DiaryDBHandler.COLUMN_DIARY_ID + "=?", new String[] { String.valueOf(foodDiary.getDiary_id())});
    }

    public void removeFoodDiary(FoodDiary foodDiary) {
        database.delete(DiaryDBHandler.TABLE_DIARY,DiaryDBHandler.COLUMN_DIARY_ID + "=" + foodDiary.getDiary_id(),null);
    }
}