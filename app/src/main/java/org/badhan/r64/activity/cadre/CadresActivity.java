package org.badhan.r64.activity.cadre;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.adapter.CadresAdapter;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.CadreType;
import org.badhan.r64.service.cadre.GetCadres;
import org.badhan.r64.view.MainNavDrawer;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class CadresActivity extends BaseAuthActivity implements CadresAdapter.OnCadreClickListener {
    public static final String EXTRA_CADRE_TYPE = "EXTRA_CADRE_TYPE";
    private static final int REQUEST_VIEW_CADRE = 1;
    private static final String STATE_CADRE_TYPE = "STATE_CADRE_TYPE";

    private CadresAdapter adapter;
    private ArrayList<Cadre> cadres;
    private ArrayList<Cadre> responseCadres;
    private View progressBarFrame;
    private ArrayAdapter<GroupSpinnerItem> groupSpinnerAdapter;
    private Spinner spinner;

    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_cadres);
        setNavDrawer(new MainNavDrawer(this));
        getSupportActionBar().setTitle("R64 Cadre List");

        adapter = new CadresAdapter(this, this);
        cadres = adapter.getCadres();
        progressBarFrame = findViewById(R.id.cadres_activity_progressBarFrame);


        groupSpinnerAdapter = new ArrayAdapter<GroupSpinnerItem>(this, R.layout.toolbar_spinner_list_item);
        groupSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        groupSpinnerAdapter.add(new GroupSpinnerItem("Both Group",
                Color.parseColor("#00BEDE"),
                new GetCadres.Request()));
        groupSpinnerAdapter.add(new GroupSpinnerItem("Group A",
                Color.parseColor("#00BCD4"),
                new GetCadres.Request("A")));
        groupSpinnerAdapter.add(new GroupSpinnerItem("Group B",
                Color.parseColor("#00BCEE"),
                new GetCadres.Request("B")));

        spinner = findViewById(R.id.cadres_activity_groupSpinner);
        spinner.setAdapter(groupSpinnerAdapter);
        spinner.setOnItemSelectedListener(new CadresActivity.SpinnerItemSelectedHandler());

        getSupportActionBar().setTitle(null);

        RecyclerView recyclerView = findViewById(R.id.cadres_activity_cadre_list);
        recyclerView.setAdapter(adapter);

        if (isTablet)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        else
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showProgressBar();
        bus.post(new GetCadres.Request());
    }

    @Override
    public void onCadreClick(Cadre cadre) {
        Intent intent = new Intent(this, CadreActivity.class);
        intent.putExtra(CadreActivity.EXTRA_CADRE, cadre);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadres_activity_menu, menu);
        MenuItem searchOption = menu.findItem(R.id.cadres_activity_menu_item_search);
        SearchView searchView = (SearchView) searchOption.getActionView();
        searchView.setQueryHint("cadres name, batch or address");

        searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                if (focused)
                    //hide spinner
                    spinner.setVisibility(View.GONE);
                else
                    spinner.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("text change", newText);
                ArrayList newList = new ArrayList();
                for (Cadre cadre : responseCadres){

                    Boolean matched = Pattern.compile(
                            Pattern.quote(newText),
                            Pattern.CASE_INSENSITIVE)
                            .matcher(cadre.getSearchableText())
                            .find();
                    if (matched){
                        newList.add(cadre);
                    }
                }

                refreshCadresData(newList);
                return true;
            }
        });
        return true;
    }


    private void filterByCadreType(CadreType cadreType){
        ArrayList newList = new ArrayList();
        for (Cadre cadre : responseCadres){

            Boolean matched = Pattern.compile(
                    Pattern.quote(cadreType.getKey()),
                    Pattern.CASE_INSENSITIVE)
                    .matcher(cadre.getCadreType())
                    .find();
            if (matched){
                newList.add(cadre);
            }
        }

        refreshCadresData(newList);
    }



    @Subscribe
    public void onCadresLoaded(GetCadres.Response response){
        CadreType cadreType = getIntent().getParcelableExtra(EXTRA_CADRE_TYPE);
        responseCadres = response.cadres;
        hideProgressBar();
        response.showErrorToast(this);
        if (cadreType != null){
            Log.e("cadres",cadreType.getKey());
            filterByCadreType(cadreType);
        }else {
            refreshCadresData(response.cadres);
        }
    }

    private void refreshCadresData(ArrayList newCadres){
        int oldCadresSize = cadres.size();
        cadres.clear();
        adapter.notifyItemRangeRemoved(0, oldCadresSize);

        cadres.addAll(newCadres);
        adapter.notifyItemRangeInserted(0, cadres.size());

        progressBarFrame.setVisibility(View.GONE);
    }


    private class SpinnerItemSelectedHandler implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            showProgressBar();
            GroupSpinnerItem item = groupSpinnerAdapter.getItem(i);
            if (item == null)
                return;
            bus.post(item.getRequest());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
    private class GroupSpinnerItem{
        private final String title;

        private final int color;

        private ServiceRequest request;

        public GroupSpinnerItem(String title, int color, ServiceRequest request) {
            this.title = title;
            this.color = color;
            this.request = request;
        }

        public String getTitle() {
            return title;
        }

        public int getColor() {
            return color;
        }
        public ServiceRequest getRequest() {
            return request;
        }
        @Override
        public String toString() {
            return title;
        }

    }

    private void showProgressBar(){
        progressBarFrame.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressBarFrame.setVisibility(View.GONE);
    }
}
