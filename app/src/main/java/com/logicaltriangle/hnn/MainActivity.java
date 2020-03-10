package com.logicaltriangle.hnn;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.logicaltriangle.hnn.dao.CategoryDao;
import com.logicaltriangle.hnn.dao.ItemDescDao;
import com.logicaltriangle.hnn.db.HnnDatabase;
import com.logicaltriangle.hnn.entities.Category;
import com.logicaltriangle.hnn.entities.Favorite;
import com.logicaltriangle.hnn.fragments.DiseaseFragment;
import com.logicaltriangle.hnn.fragments.ExerciseFragment;
import com.logicaltriangle.hnn.fragments.FavoriteFragment;
import com.logicaltriangle.hnn.fragments.HealthFragment;
import com.logicaltriangle.hnn.fragments.MainFragment;
import com.logicaltriangle.hnn.fragments.NutritionFragment;
import com.logicaltriangle.hnn.fragments.SearchResultFragment;
import com.logicaltriangle.hnn.fragments.SingleDiseaseFragment;
import com.logicaltriangle.hnn.provider.MySuggestionProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int EXERCISE_CATID = 1;
    public static final int DISEASE_CATID = 2;
    public static final int N_KIDS_CATID = 3;
    public static final int N_WOMAN_CATID = 4;
    public static final int N_MEN_CATID = 5;
    public static final int N_SENIOR_CATID = 6;

    //database
    public static final String DB_NAME = "health_nutrition.db";
    public static HnnDatabase hnnDatabase;
    public static ItemDescDao itemDescDao;

    public static final String TAG = "HealthNNutrition";

    // widgets
    public static FloatingActionButton fabFavorite;
    private FloatingActionButton fabSearch;

    private Button healthBtn, nutritionBtn;

    //Fragment
    private FragmentManager fragmentManager;

    //Top bar widgets
    public static TextView tvAppBarTtl;
    private ImageView ivMenuSettings, ivMenuArrowBack;

    private Resources resources;

    //current paretid and itempos
    public static int currParentCatId = 0;
    public static int currItemId = 0;

    //parent category id arg key
    public static String PARENT_CATID_KEY = "parent_cat_key";
    public static String ITEM_ID_KEY = "item_id_key";

    //preference name
    public static String PREFS_NAME = "healthAndNutrition";
    public static String FAV_PREF_KEY = "fav_pref_key";

    //shared preferences
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Set<String> favSet;
    private String[] favItems;
    private Intent intent;
    private Bundle bundle;

    public static boolean backToHome = false;
    private boolean isInHome = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init or create from asset
        initDb();

        itemDescDao = hnnDatabase.itemDescDao();

        CategoryDao categoryDao = hnnDatabase.categoryDao();
        List<Category> categories = categoryDao.getAll();

        //getting bundle
        intent = getIntent();
        bundle = getIntent().getExtras();

        //init preferences
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();

        favSet = preferences.getStringSet(FAV_PREF_KEY, new HashSet<String>());

        //initiating res
        resources = getResources();

        //topbar widgets
        tvAppBarTtl = findViewById(R.id.tvAppBarTtl);

        ivMenuSettings = findViewById(R.id.ivMenuSettings);

        ivMenuArrowBack = findViewById(R.id.ivMenuArrowBack);
        ivMenuArrowBack.setOnClickListener(this);

        tvAppBarTtl.setText(resources.getString(R.string.app_name));
        ivMenuSettings.setOnClickListener(this);

        //adding fragment
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                String bsFName = "";
                List<Fragment> fragments = fragmentManager.getFragments();
                Log.d( "Tag456", "FSize: " + fragments.size() );
                for (Fragment fragment : fragments) {
                    bsFName = fragment.getClass().getName();
                    Log.d( "Tag456", fragment.getClass().getName() );
                }
                /*if (bsFName.contains("HealthFragment")) {
                    healthSubFID = 0;
                } else if(bsFName.contains("ExerciseFragment")) {
                    healthSubFID = 1;
                } else if(bsFName.contains("DiseaseFragment")) {
                    healthSubFID = 2;
                }*/
            }
        });

        handleSearchIntent(intent);

        //finding views by id
        healthBtn = findViewById(R.id.healthBtn);
        healthBtn.setOnClickListener(this);

        nutritionBtn = findViewById(R.id.nutritionBtn);
        nutritionBtn.setOnClickListener(this);

        fabFavorite = findViewById(R.id.fabFavorite);
        fabFavorite.hide();
        fabFavorite.setOnClickListener(this);

        //search button
        fabSearch = findViewById(R.id.fabSearch);
        fabSearch.setOnClickListener(this);
    }

    public void initDb() {
        hnnDatabase = Room.databaseBuilder(this, HnnDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        try {
            if (hnnDatabase.categoryDao().getAll().size() == 0) {
                //init db
                reCreateFromAsset();
            }
        } catch (Exception e){
            //init db
            reCreateFromAsset();
        }
    }

    public void reCreateFromAsset() {
        hnnDatabase = Room.databaseBuilder(this, HnnDatabase.class, DB_NAME)
                .createFromAsset("db/health_nutrition.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    private void handleSearchIntent(Intent intent) {
        try {
            if (intent != null && intent.getExtras() != null) {
                if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
                    String query = intent.getStringExtra(SearchManager.QUERY);
                    SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                            MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                    suggestions.saveRecentQuery(query, null);

                    SearchResultFragment resultFragment = new SearchResultFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(SearchManager.QUERY, query);
                    resultFragment.setArguments(bundle);

                    fragmentManager.beginTransaction().
                            replace(R.id.fragmentContainer, resultFragment)
                            .addToBackStack(null)
                            .commit();
                } else if (bundle.getInt(MainActivity.ITEM_ID_KEY) > 0) {
                    SingleDiseaseFragment singleDiseaseFragment = new SingleDiseaseFragment();
                    Bundle args = new Bundle();
                    args.putInt(MainActivity.PARENT_CATID_KEY, bundle.getInt(MainActivity.PARENT_CATID_KEY));
                    args.putInt(MainActivity.ITEM_ID_KEY, bundle.getInt(MainActivity.ITEM_ID_KEY));
                    singleDiseaseFragment.setArguments(args);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, singleDiseaseFragment)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().
                            replace(R.id.fragmentContainer, new MainFragment())
                            .addToBackStack(null)
                            .commit();
                }
            } else {
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new MainFragment())
                        .addToBackStack(null)
                        .commit();
            }
        } catch (Exception ex) {
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentContainer, new MainFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMenuSettings:
                MenuBuilder menuBuilder = new MenuBuilder(this);
                MenuInflater inflater = new MenuInflater(this);
                inflater.inflate(R.menu.main, menuBuilder);
                MenuPopupHelper optionsMenu = new MenuPopupHelper(this, menuBuilder, v);
                optionsMenu.setForceShowIcon(true);
                // Set Item Click Listener
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                        int id = item.getItemId();
                        //noinspection SimplifiableIfStatement
                        if (id == R.id.action_home) {
                            fabFavorite.hide();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentContainer, new MainFragment()).addToBackStack(null)
                                    .commit();
                            return true;
                        } else if (id == R.id.action_fav) {
                            List<Favorite> favorites = MainActivity.hnnDatabase.favoriteDao().getAllFavItems();

                            if (favorites.size() > 0) {
                                fabFavorite.hide();
                                FavoriteFragment favoriteFragment = new FavoriteFragment();
                                Bundle bundle = new Bundle();
                                bundle.putStringArray(FAV_PREF_KEY, favItems);
                                favoriteFragment.setArguments(bundle);
                                fragmentManager.beginTransaction()
                                        .replace(R.id.fragmentContainer, favoriteFragment).addToBackStack(null)
                                        .commit();

                                tvAppBarTtl.setText(resources.getString(R.string.favorites));
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.no_items_found), Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        } else if (id == R.id.action_rate_app) {
                            Intent ratingIntent = new Intent(Intent.ACTION_VIEW);
                            ratingIntent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.logicaltriangle.hnn"));
                            try {
                                startActivity(ratingIntent);
                            } catch (Exception e) {
                            }
                            //startActivity(new Intent(MainActivity.this, ContactMe.class));
                            return true;
                        } else if (id == R.id.action_share) {
                            Intent i = new Intent();
                            i.setType("text/plain");
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setAction(Intent.ACTION_SEND);
                            i.putExtra(Intent.EXTRA_SUBJECT, "Health and Nutrition App");
                            String sAux = "\n*Health and Nutrition*:  \n";
                            sAux = sAux + "https://play.google.com/store/apps/details?id=com.logicaltriangle.hnn \n";
                            i.putExtra(Intent.EXTRA_TEXT, sAux);
                            startActivity(i);
                            return true;
                        } else if (id == R.id.action_info) {
                            startActivity(new Intent(MainActivity.this, DevInfoActivity.class));
                            return true;
                        }
                        return true;
                    }

                    @Override
                    public void onMenuModeChange(MenuBuilder menu) {
                    }
                });
                // Display the menu
                optionsMenu.show();
                break;

            case R.id.healthBtn:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new HealthFragment()).addToBackStack(null)
                        .commit();
                fabFavorite.hide();
                setBackToHome(true);
                //Intent hIntent = new Intent(MainActivity.this, HealthActivity.class);
                //startActivity(hIntent);
                break;

            case R.id.nutritionBtn:

                // start nutrition activity here..
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new NutritionFragment())
                        .commit();
                fabFavorite.hide();
                setBackToHome(true);
                break;

            case R.id.fabFavorite:

                setBackToHome(false);

                String favItemString = "" + currItemId;

                Favorite favorite = MainActivity.hnnDatabase.favoriteDao().getFavItemId(currItemId);

                if (favorite != null) {
                    showSnackbar("Already added");
                } else {
                    Favorite fav = new Favorite();
                    fav.itemID = currItemId;
                    MainActivity.hnnDatabase.favoriteDao().insert(fav);

                    fabFavorite.setImageResource(R.drawable.ic_favorite_fill);
                    //fabFavorite.hide();
                    //fabFavorite.show();
                    showSnackbar("Successfully added");
                }
                break;

            case R.id.fabSearch:
                fabFavorite.hide();
                setBackToHome(false);
                Log.d(TAG, "I am here.11");

                onSearchRequested();
                break;

            case R.id.ivMenuArrowBack:
                fabFavorite.hide();
                //Toast.makeText(this, "Press Back", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleSearchIntent(intent);
    }

    public void showSnackbar(String message) {
        Snackbar.make(findViewById(R.id.fabFavorite), message, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onBackPressed() {
        fabFavorite.hide();
        if (backToHome) {
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentContainer, new MainFragment()).addToBackStack(null)
                    .commit();
            backToHome = false;
        } else if (fragmentManager.getBackStackEntryCount() > 0) {

            String fragClassName = "";
            List<Fragment> fragments = fragmentManager.getFragments();
            for (Fragment fragment : fragments) {
                fragClassName = fragment.getClass().getSimpleName();
            }

            if (fragClassName.equals("MainFragment")) {
                showExitDialog();
            } else if (fragClassName.equals("HealthFragment") || fragClassName.equals("NutritionFragment")) {
                //go to feture
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new MainFragment()).
                        commit();
            } else if (fragClassName.equals("SingleExerciseFragment")) {
                //go to exercise
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new ExerciseFragment()).
                        commit();
            } else if (fragClassName.equals("SingleDiseaseFragment")) {
                //go to disease or nutrition
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new DiseaseFragment()).
                        commit();
            } else if (fragClassName.equals("SingleNutritionFragment")) {
                //go to disease or nutrition
                fragmentManager.popBackStackImmediate();
                /*fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new NutritionFragment()).
                        commit();*/
            } else if (fragClassName.equals("ExerciseFragment") || fragClassName.equals("DiseaseFragment")) {
                //go to health
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new HealthFragment()).
                        commit();
            } else if (fragClassName.equals("NutritionGridFragment")) {
                //go to nutrition
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new NutritionFragment()).
                        commit();
            } else {
                fragmentManager.beginTransaction().
                        replace(R.id.fragmentContainer, new MainFragment()).
                        commit();
            }
        } else
            fragmentManager.beginTransaction().
                    replace(R.id.fragmentContainer, new MainFragment()).
                    commit();
    }

    //show exit dialog
    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert!");
        builder.setIcon(R.drawable.ic_alert);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //backToHome setter
    public static void setBackToHome(boolean isBackToHome) {
        backToHome = isBackToHome;
    }
}
