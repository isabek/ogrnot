package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.fragments.MainMenuFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.PasswordFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.PersonalInfoFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.TakenLessonsFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.TranscriptFragment;
import com.itashiev.ogrnot.ogrnotapplication.menu.adapter.MenuDrawerListAdapter;
import com.itashiev.ogrnot.ogrnotapplication.menu.model.MenuDrawerItem;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ActionBarDrawerToggle toggle;
    private ListView navigationDrawerList;
    private String[] screenTitles;
    private DrawerLayout drawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        TypedArray menuIcons = getResources().obtainTypedArray(R.array.menu_drawer_icons);

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }

        };

        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        navigationDrawerList = (ListView) findViewById(R.id.left_navigation_drawer);
        screenTitles = getResources().getStringArray(R.array.menu_array);

        navigationDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        ArrayList<MenuDrawerItem> menuDrawerItems = new ArrayList<>();

        for (String screenTitle : screenTitles)
            menuDrawerItems.add(new MenuDrawerItem(screenTitle, R.drawable.menu_item_icon_non_active));

        menuIcons.recycle();

        MenuDrawerListAdapter drawerListAdapter = new MenuDrawerListAdapter(getApplicationContext(), menuDrawerItems);
        navigationDrawerList.setAdapter(drawerListAdapter);

        selectDrawerListItem(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }


    public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerListItem(position);
        }
    }

    private void selectDrawerListItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new MainMenuFragment();
                break;
            case 1:
                fragment = new PersonalInfoFragment();
                break;
            case 2:
                fragment = new TakenLessonsFragment();
                break;

            case 3:
                fragment = new TranscriptFragment();
                break;
            case 4:
                fragment = new PasswordFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();


            setTitle(screenTitles[position]);
            navigationDrawerList.setSelection(position);
            navigationDrawerList.setItemChecked(position, true);
            drawerLayout.closeDrawer(navigationDrawerList);

            resetMenuItemsIcons();
            changeMenuItemIcon(position);
        }
    }

    private void changeMenuItemIcon(int position) {
        MenuDrawerItem item = (MenuDrawerItem) navigationDrawerList.getItemAtPosition(position);
        item.setIcon(R.drawable.menu_item_icon_active);
    }
    private void resetMenuItemsIcons()
    {
        for(int i = 0; i < navigationDrawerList.getChildCount(); i ++) {
            MenuDrawerItem item = (MenuDrawerItem) navigationDrawerList.getItemAtPosition(i);
            item.setIcon(R.drawable.menu_item_icon_non_active);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

}