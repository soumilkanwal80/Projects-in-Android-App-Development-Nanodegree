package com.example.android.pets;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.android.pets.data.PetContract.PetEntry;
import com.example.android.pets.data.PetDbHelper;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    PetCursorAdapter mCursorAdapter;
    public static final int PET_LOADER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });


        ListView petListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        petListView.setEmptyView(emptyView);

        mCursorAdapter = new PetCursorAdapter(this, null);
        petListView.setAdapter(mCursorAdapter);
        getLoaderManager().initLoader(PET_LOADER, null, this);

        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri currentPetUri = ContentUris.withAppendedId(PetEntry.CONTENT_URI, id);
                Intent i = new Intent(CatalogActivity.this, EditorActivity.class);
                i.setData(currentPetUri);
                startActivity(i);
            }
        });

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */

    private ContentValues getDummyData(){
        PetDbHelper m = new PetDbHelper(this);
        SQLiteDatabase db = m.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(PetEntry.COLUMN_PET_NAME, "Toto");
        c.put(PetEntry.COLUMN_PET_BREED, "Terrier");
        c.put(PetEntry.COLUMN_PET_GENDER, PetEntry.GENDER_MALE);
        c.put(PetEntry.COLUMN_PET_WEIGHT, 7);
        return c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                ContentValues c = getDummyData();
                Uri neUri = getContentResolver().insert(PetEntry.CONTENT_URI, c);
                //displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                int rowsDeleted = getContentResolver().delete(PetEntry.CONTENT_URI, null, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String [] projections = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_BREED,
        };
        return new CursorLoader(this, PetEntry.CONTENT_URI, projections, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}