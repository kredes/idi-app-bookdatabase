package com.example.pr_idi.mydatabaseexample;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView menuLateral;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        setSupportActionBar((Toolbar) findViewById(R.id.appbar_frag));


        // Fragment por defecto
        Fragment defaultFrag = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_contenido, defaultFrag)
                .commit();



        // Menú lateral
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuLateral = (NavigationView) findViewById(R.id.navview);

        menuLateral.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_home: {
                                fragment = new MainFragment();
                                fragmentTransaction = true;
                                break;
                            }
                            case R.id.menu_buscar: {
                                fragment = new BuscarPorAutorFragment();
                                fragmentTransaction = true;
                                break;
                            }
                            case R.id.menu_buscar_titulo: {
                                fragment = new BuscarPorTituloFragment();
                                fragmentTransaction = true;
                                break;
                            }
                            case R.id.menu_crear_libro: {
                                fragment = new AltaLlibreFragment();
                                fragmentTransaction = true;
                                break;
                            }
                            case R.id.menu_ordenar_categoria: {
                                fragment = new LlibresPerCategoriaFragment();
                                fragmentTransaction = true;
                                break;
                            }
                            case R.id.menu_help: {
                                fragment = new HelpFragment();
                                fragmentTransaction = true;
                                break;
                            }
                            case R.id.menu_about: {
                                fragment = new AboutFragment();
                                fragmentTransaction = true;
                                break;
                            }
                        }

                        if(fragmentTransaction) {
                            Fragment fragmentActual = getSupportFragmentManager().findFragmentById(R.id.frame_contenido);

                            if (!fragment.getClass().equals(fragmentActual.getClass())) {
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_contenido, fragment)
                                        .addToBackStack(fragment.getClass().getSimpleName())
                                        .commit();

                                menuItem.setChecked(true);
                            }
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                }
        );
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}