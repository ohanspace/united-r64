package org.badhan.r64.activity.cadre;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.squareup.otto.Subscribe;

import org.badhan.r64.R;
import org.badhan.r64.activity.contact.ContactsActivity;
import org.badhan.r64.adapter.CadresAdapter;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.core.ServiceRequest;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.entity.CadreType;
import org.badhan.r64.service.cadre.GetCadres;
import org.badhan.r64.view.MainNavDrawer;

import java.util.ArrayList;


public class CadresActivity extends BaseAuthActivity implements CadresAdapter.OnCadreClickListener {
    public static final String EXTRA_CADRE_TYPE = "EXTRA_CADRE_TYPE";
    private static final int REQUEST_VIEW_CADRE = 1;

    private CadresAdapter adapter;
    private ArrayList<Cadre> cadres;
    private View progressBarFrame;
    private ArrayAdapter<GroupSpinnerItem> groupSpinnerAdapter;

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

        Spinner spinner = findViewById(R.id.cadres_activity_groupSpinner);
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



    @Subscribe
    public void onCadresLoaded(GetCadres.Response response){
        hideProgressBar();
        response.showErrorToast(this);

        int oldcadresSize = cadres.size();
        cadres.clear();
        adapter.notifyItemRangeRemoved(0, oldcadresSize);

        cadres.addAll(response.cadres);
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
