package com.example.aswoo.memoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth mfirebaseAuth;
    private FirebaseUser mfirebaseUser;
    private FirebaseDatabase mFirebasedatabase;

    private EditText edContent;

    private TextView txtEmail,txtName;

    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mfirebaseAuth = FirebaseAuth.getInstance();
        mfirebaseUser = mfirebaseAuth.getCurrentUser();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        edContent = (EditText) findViewById(R.id.content);

               if(mfirebaseUser == null)
        {
            startActivity(new Intent(MainActivity.this,AuthActivity.class));
            finish();
            return;//메소드 탈출 아래 메소드들 실행을 위해서
        }

        setSupportActionBar(toolbar);

        FloatingActionButton fabNewMomo= (FloatingActionButton) findViewById(R.id.new_memo);
        FloatingActionButton fabSaveMomo= (FloatingActionButton) findViewById(R.id.save_memo);
        fabNewMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              InitMemo();
            }
        });
        fabSaveMomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               saveMemo();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0); //헤더가 하나라서 index 0;

        txtEmail = (TextView) headerView.findViewById(R.id.txtEmail);
        txtName = (TextView) headerView.findViewById(R.id.txtName);
        mNavigationView.setNavigationItemSelectedListener(this);
        profileUpdate();
        displayMemos();
    }

    private void saveMemo() {
        String text = edContent.getText().toString();
        if(text.isEmpty()) {
            return;
        }
        Memos memo = new Memos();
        memo.setTxt(edContent.getText().toString());
        memo.setCreateDate(new Date());
        mFirebasedatabase.getReference("memos/"+mfirebaseUser
                .getUid())
                .push()
                .setValue(memo)
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(edContent,"메모 저장 완료.",Snackbar.LENGTH_SHORT).show();
                        InitMemo();
                    }
                });
    }

    private void InitMemo() {
        edContent.setText("");
    }

    private void profileUpdate(){
        txtEmail.setText(mfirebaseUser.getEmail());
        txtName.setText(mfirebaseUser.getDisplayName());
    }

    private void displayMemos(){
        mFirebasedatabase.getReference("memos/"+mfirebaseUser.getUid())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Memos memo = dataSnapshot.getValue(Memos.class);
                        memo.setKey(dataSnapshot.getKey());
                        displayMemoList(memo);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }


    private void displayMemoList(Memos memo){
        Menu leftMenu = mNavigationView.getMenu();

        MenuItem item = leftMenu.add(memo.getTitle());
        //for tag
        View view = new View(getApplicationContext());
        view.setTag(memo);
        item.setActionView(view);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
