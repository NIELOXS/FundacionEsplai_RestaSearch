package com.cursfundacionesplai.restasearch.classesextended;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.cursfundacionesplai.restasearch.HistorialActivity;
import com.cursfundacionesplai.restasearch.IdiomaActivity;
import com.cursfundacionesplai.restasearch.MainActivity;
import com.cursfundacionesplai.restasearch.PoliticaActivity;
import com.cursfundacionesplai.restasearch.PreferitsActivity;
import com.cursfundacionesplai.restasearch.R;
import com.google.android.material.navigation.NavigationView;

public class ToolbarEx {

    private AppCompatActivity activity;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public ToolbarEx(AppCompatActivity activity, Toolbar toolbar, DrawerLayout drawerLayout, NavigationView navigationView) {
        this.activity = activity;
        this.toolbar = toolbar;
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        exec();
    }

    //region Propietats

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    //endregion

    private void exec() {
        activity.setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        /*MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);*/
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        System.gc();

        Intent intent;

        switch (item.getItemId()){
            case R.id.nav_inici:
                intent = new Intent(activity, MainActivity.class);
                intent.putExtra("titol",activity.getResources().getString(R.string.menu_inici));
                break;
            case R.id.nav_preferits:
                intent = new Intent(activity, PreferitsActivity.class);
                intent.putExtra("titol",activity.getResources().getString(R.string.menu_preferits));
                break;
            case R.id.nav_historial:
                intent = new Intent(activity, HistorialActivity.class);
                intent.putExtra("titol",activity.getResources().getString(R.string.menu_historial));
                break;
            case R.id.nav_idioma:
                intent = new Intent(activity, IdiomaActivity.class);
                break;
            case R.id.nav_privacitat_us:
                intent = new Intent(activity, PoliticaActivity.class);
                intent.putExtra("titol",activity.getResources().getString(R.string.menu_privacitat_us));
                break;
            default:
                throw new IllegalArgumentException("Aquesta opció encara no està implementada");
        }

        activity.startActivity(intent);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
