package example.provider.content.contentproviderexample.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import example.provider.content.contentproviderexample.R;
import example.provider.content.contentproviderexample.adapter.ContentProviderAdapter;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void deleteAllBirthdays (View view) {
        // delete all the records and the table of the database provider
        String URL = "content://com.example.provider.Birthday/friends";
        Uri friends = Uri.parse(URL);
        int count = getContentResolver().delete(
                friends, null, null);
        String countNum = "No of: "+ count +" records are deleted.";
        Toast.makeText(getBaseContext(),
                countNum, Toast.LENGTH_LONG).show();

    }

    public void addBirthday(View view) {
        // Add a new birthday record
        ContentValues values = new ContentValues();

        values.put(ContentProviderAdapter.NAME,
                ((EditText)findViewById(R.id.name)).getText().toString());

        values.put(ContentProviderAdapter.BIRTHDAY,
                ((EditText)findViewById(R.id.birthday)).getText().toString());

        Uri uri = getContentResolver().insert(
                ContentProviderAdapter.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                "URI : " + uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
    }


    public void showAllBirthdays(View view) {
        // Show all the birthdays sorted by friend's name
        String URL = "content://com.example.provider.Birthday/friends";
        Uri friends = Uri.parse(URL);
        Cursor c = getContentResolver().query(friends, null, null, null, "name");
        String result = "Results:";

        if (!c.moveToFirst()) {
            Toast.makeText(this, result+" no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" + c.getString(c.getColumnIndex(ContentProviderAdapter.NAME)) +
                        " with id " +  c.getString(c.getColumnIndex(ContentProviderAdapter.ID)) +
                        " has birthday: " + c.getString(c.getColumnIndex(ContentProviderAdapter.BIRTHDAY));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }
}
