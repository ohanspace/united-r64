package org.badhan.r64.activity.cadre;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import org.badhan.r64.R;
import org.badhan.r64.core.BaseAuthActivity;
import org.badhan.r64.entity.Cadre;
import org.badhan.r64.view.MainNavDrawer;
import org.w3c.dom.Text;


public class CadreActivity extends BaseAuthActivity implements View.OnClickListener {
    public static final String EXTRA_CADRE = "EXTRA_CADRE";
    public static final String RESULT_EXTRA_CADRE_ID = "RESULT_EXTRA_CADRE_ID";
    public static final String STATE_CADRE = "STATE_CADRE";

    private Cadre currentCadre;
    private View progressBarFrame;
    private Button callBtn;
    private Button emailBtn;

    private ImageView avatar;
    private TextView displayName;
    private TextView rollNo;
    private TextView cadreId;
    private TextView batchCadreType;
    private TextView postingAddress;
    private TextView bloodGroup;
    private TextView homeDistrict;
    private TextView university;
    private TextView session;


    @Override
    protected void onAppCreate(Bundle savedState) {
        setContentView(R.layout.activity_cadre);
        getSupportActionBar().setTitle("Cadre Details");
        toolbar.setNavigationIcon(R.drawable.crop__ic_cancel);

        progressBarFrame = findViewById(R.id.cadre_activity_progressBarFrame);

        callBtn = findViewById(R.id.cadre_activity_callBtn);
        emailBtn = findViewById(R.id.cadre_activity_emailBtn);

        avatar = findViewById(R.id.cadre_activity_avatar);
        rollNo = findViewById(R.id.cadre_activity_rollNo);
        displayName = findViewById(R.id.cadre_activity_displayName);
        cadreId = findViewById(R.id.cadre_activity_cadreId);
        batchCadreType = findViewById(R.id.cadre_activity_cadreBatchType);
        postingAddress = findViewById(R.id.cadre_activity_postingAddress);
        bloodGroup = findViewById(R.id.cadre_activity_bloodGroup);
        homeDistrict = findViewById(R.id.cadre_activity_homeDistrict);
        university = findViewById(R.id.cadre_activity_university);
        session = findViewById(R.id.cadre_activity_session);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               closeCadreDetails(RESULT_OK);
            }
        });

        callBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);

        Cadre cadre = null;
        if (savedState != null){
            cadre = savedState.getParcelable(EXTRA_CADRE);
        }

        if (cadre == null){
            cadre = getIntent().getParcelableExtra(EXTRA_CADRE);
        }

        if (cadre != null){
            showCadreDetails(cadre);
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STATE_CADRE, currentCadre);
    }

    private void showCadreDetails(Cadre cadre){
        progressBarFrame.setVisibility(View.GONE);

        currentCadre = cadre;
        getSupportActionBar().setTitle(cadre.getName());

        cadre.getAvatarStorageRef().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(CadreActivity.this)
                        .load(uri.toString())
                        .placeholder(R.drawable.ic_person_pin_round)
                        .error(R.drawable.ic_person_pin_round)
                        .into(avatar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                avatar.setImageResource(R.drawable.ic_person_pin_round);
            }
        });


        rollNo.setText(cadre.getRollNo());
        displayName.setText(cadre.getDisplayName());
        cadreId.setText(cadre.getCadreId());
        batchCadreType.setText(cadre.getCadreBatchType());
        postingAddress.setText(cadre.getPostingAddress());
        bloodGroup.setText(cadre.getBloodGroup());
        homeDistrict.setText(cadre.getHomeDistrict());
        university.setText(cadre.getUniversity());
        session.setText(cadre.getSession());


        if (cadre.getTelephone() == null){
            callBtn.setEnabled(false);
        }

        if (cadre.getEmail() == null)
            emailBtn.setEnabled(false);
    }


    private void closeCadreDetails(int result_code){
        setResult(result_code);
        finish();
        return;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cadre_activity_callBtn:
                String telephone = currentCadre.getTelephone();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+ telephone));
                startActivity(callIntent);
                break;

            case R.id.cadre_activity_emailBtn:
                String email = currentCadre.getEmail();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.fromParts("mailto",email,null));
                startActivity(emailIntent);
                break;
        }
    }
}
