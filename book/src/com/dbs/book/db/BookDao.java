package com.dbs.book.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.dbs.book.ui.model.BookInfo;

public class BookDao extends SQLiteOpenHelper {
	
	private final static int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dbs";
	private static final String TABLE_NAME = "booklist";
	private static final String FEILD_ID = "_id";
	private static final String BOOK_ID = "bid";
	private static final String BOOK_NAME = "name";
	private static final String BOOK_SUMMARY = "summary";
	private static final String BOOK_COVERURL = "coverUrl";
	private static final String BOOK_TXTURL = "txtUrl";
	private static final String BOOK_COUNT = "count";
	private static final String BOOK_TYPE = "type";
	private static final String BOOK_CHAPTERNUM = "chapterNum";
	private static final String BOOK_DOWNLOADED = "isDownloaded";
	
	private SQLiteDatabase db;
	
	public BookDao(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public BookDao(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public BookDao(Context context, String name, CursorFactory factory,
			int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 String sql = "Create table " + TABLE_NAME + "(" + FEILD_ID
	                + " integer primary key autoincrement," + BOOK_ID + ","
	                + BOOK_NAME + "," + BOOK_SUMMARY + ","
	                + BOOK_COVERURL + "," + BOOK_TXTURL + ","
	                + BOOK_COUNT + "," + BOOK_TYPE + " ," + BOOK_CHAPTERNUM
	                + ")";
	        db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
	     db.execSQL(sql);
	     onCreate(db);
	     db.execSQL(sql);
	}

	 public void delete(String id) {
	        db = this.getWritableDatabase();
	        String where = BOOK_ID + "=?";
	        String[] whereValue = { id };
	        db.delete(TABLE_NAME, where, whereValue);
	        db.close();
	    }
	 
	 public long insert(BookInfo book) {
	        db = this.getReadableDatabase();
	        ContentValues cv = new ContentValues();
	        cv.put(BOOK_ID, book.getId());
	        cv.put(BOOK_NAME, book.getName());
	        cv.put(BOOK_SUMMARY, book.getSummary());
	        cv.put(BOOK_COVERURL, book.getCoverUrl());
	        cv.put(BOOK_TXTURL, book.getTxtUrl());
	        cv.put(BOOK_COUNT, book.getCount());
	        cv.put(BOOK_TYPE, book.getType());
	        cv.put(BOOK_CHAPTERNUM, book.getChapterNum());
	        cv.put(BOOK_DOWNLOADED, book.isDownloaded());
	        long row = db.insert(TABLE_NAME, null, cv);
	        db.close();
	        return row;
	    }
	 
	 public BookInfo query(String bookId){
		 BookInfo bookInfo = new BookInfo();
		 db = this.getReadableDatabase();
		 
		 return bookInfo;
	 }
	 
	 public List<BookInfo> queryAll(){
		 List<BookInfo> bookList = new ArrayList<BookInfo>();
		 
		 return bookList;
	 }
}
