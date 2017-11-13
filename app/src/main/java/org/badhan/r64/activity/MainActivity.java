package org.badhan.r64.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import org.badhan.r64.R;
import org.badhan.r64.activity.cadre.CadresActivity;
import org.badhan.r64.adapter.CadreTypesAdapter;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.entity.CadreType;
import org.badhan.r64.view.MainNavDrawer;

import java.util.ArrayList;

public class MainActivity extends BaseAuthActivity implements CadreTypesAdapter.OnCadreTypeClickListener {
    private CadreTypesAdapter adapter;
    private ArrayList<CadreType> cadreTypes;


    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("United R64");
        setNavDrawer(new MainNavDrawer(this));


        adapter = new CadreTypesAdapter(this,this);
        cadreTypes = adapter.getCadreTypes();

        RecyclerView recyclerView = findViewById(R.id.main_activity_cadre_type_list);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        cadreTypes.addAll(CadreType.getAllTypes());


    }

    @Override
    public void onCadreTypeClick(CadreType cadreType) {
        Intent intent = new Intent(this, CadresActivity.class);
        intent.putExtra(CadresActivity.EXTRA_CADRE_TYPE, cadreType);
        startActivity(intent);
    }
}
