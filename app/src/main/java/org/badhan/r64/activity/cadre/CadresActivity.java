package org.badhan.r64.activity.cadre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.adapter.CadresAdapter;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.service.cadre.GetCadres;
import org.badhan.r64.view.MainNavDrawer;

import java.util.ArrayList;


public class CadresActivity extends BaseAuthActivity implements CadresAdapter.OnCadreClickListener {
    private static final int REQUEST_VIEW_CADRE = 1;

    private CadresAdapter adapter;
    private ArrayList<Cadre> cadres;
    private View progressBarFrame;

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_cadres);
        setNavDrawer(new MainNavDrawer(this));
        getSupportActionBar().setTitle("R64 Cadre List");

        adapter = new CadresAdapter(this, this);
        cadres = adapter.getCadres();

        progressBarFrame = findViewById(R.id.cadres_activity_progressBarFrame);

        RecyclerView recyclerView = findViewById(R.id.cadres_activity_cadre_list);
        recyclerView.setAdapter(adapter);

        if (isTablet)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        bus.post(new GetCadres.Request());
    }

    @Override
    public void onCadreClick(Cadre cadre) {
        Intent intent = new Intent(this, CadreActivity.class);
        intent.putExtra(CadreActivity.EXTRA_CADRE, cadre);
        startActivity(intent);
    }



    @Subscribe
    public void onCadresLoaded(GetCadres.Response response){
        response.showErrorToast(this);

        int oldcadresSize = cadres.size();
        cadres.clear();
        adapter.notifyItemRangeRemoved(0, oldcadresSize);

        cadres.addAll(response.cadres);
        adapter.notifyItemRangeInserted(0, cadres.size());

        progressBarFrame.setVisibility(View.GONE);
    }

}
