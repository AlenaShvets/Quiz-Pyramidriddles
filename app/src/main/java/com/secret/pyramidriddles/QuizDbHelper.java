package com.secret.pyramidriddles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.secret.pyramidriddles.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("The universal equivalent by which you can estimate the value of something:", "Money", "Promotions", "Gold", 1);
        addQuestion(q1);
        Question q2 = new Question("A financial and credit institution that performs various types of transactions with money, securities and provides financial services to the government, enterprises, citizens and each other:", "Ministry of Finance", "Bank", "Financial department", 2);
        addQuestion(q2);
        Question q3 = new Question("Securities and funds deposited with a credit institution:", "Promotions", "Deposit", "Credit", 2);
        addQuestion(q3);
        Question q4 = new Question("Provision of funds in debt on terms of repayment, payment and urgency:", "Credit", "Deposit", "Payment in installments", 1);
        addQuestion(q4);
        Question q5 = new Question("Depreciation of money, manifested in the form of an increase in prices for goods and services, not due to an increase in their quality:", "Disinflation", "Depreciation", "Inflation", 3);
        addQuestion(q5);
        Question q6 = new Question("Withdrawal from circulation of a part of the money supply in order to prevent its growth and suppress inflation:", "Deflation", "Inflation", "Disinflation", 1);
        addQuestion(q6);
        Question q7 = new Question("Securities issued by a joint stock company and entitling their owner to receive a certain income from the profits of a joint stock company:", "Promotions", "Gold", "Money", 1);
        addQuestion(q7);
        Question q8 = new Question("Share owner; a person who owns a share and enjoys the rights arising from this:", "Businessman", "Shareholder", "Banker", 2);
        addQuestion(q8);
        Question q9 = new Question("Annual income distributed to shareholders:", "Income", "Percent", "Dividend", 3);
        addQuestion(q9);
        Question q10 = new Question("An intermediary facilitating the conclusion of various transactions between interested parties - clients, on their behalf and at their expense:", "Agent", "Broker", "Manager", 2);
        addQuestion(q10);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
