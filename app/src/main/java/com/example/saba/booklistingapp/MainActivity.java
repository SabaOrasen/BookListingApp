package com.example.saba.booklistingapp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String URL_GOOGLE_BOOKS_API ="https://www.googleapis.com/books/v1/volumes?q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search_button = (Button) findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userSearchInput_EditText = (EditText) findViewById(R.id.search_editText);
                String searchInput = URL_GOOGLE_BOOKS_API + userSearchInput_EditText.getText().toString();
                BooksAsyncTask task =  new BooksAsyncTask();
                task.execute(searchInput);
            }
        });
    }

     private class BooksAsyncTask extends AsyncTask<String, Void, List<Books>>{
         @Override
         protected List<Books> doInBackground(String ... strings) {
             return QueryUtils.fetchData(strings[0]);
         }

         protected void onPostExecute(List<Books> bookslist) {
             ListView listView = (ListView) findViewById(R.id.book_list);
             BookAdapter bookAdapter = new BookAdapter(MainActivity.this, bookslist);
             listView.setAdapter(bookAdapter);
             Log.v("AsyncTask PostExecute","AsyncTask is successfully executed");
         }

     }
}



