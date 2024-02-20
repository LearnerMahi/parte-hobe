package com.example.partehobe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {
    private Button del , show , upd;
    private TextView n , e , p ;
    private DatabaseReference dbr ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        dbr = FirebaseDatabase.getInstance().getReference().child("users") ;
        del = findViewById(R.id.del) ;
        show = findViewById(R.id.Show) ;
        upd = findViewById(R.id.Update) ;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String email = user.getEmail() ;
        DatabaseReference drf = FirebaseDatabase.getInstance().getReference();
        Query qry = drf.child("users").orderByChild("email").equalTo(email) ;
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = findViewById(R.id.names) ;
                e = findViewById(R.id.emails) ;
                p = findViewById(R.id.passs) ;
                Toast.makeText(Homepage.this, email, Toast.LENGTH_SHORT).show();
                qry.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot i : snapshot.getChildren()){
                            User ur = i.getValue(User.class) ;
                            e.setText(ur.email);
                            n.setText(ur.name);
                            p.setText(ur.pass);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String em = e.getText().toString() ;
                String nm = n.getText().toString();
                String pw = p.getText().toString() ;


                User uur = new User(nm,em,pw);
                qry.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot i : snapshot.getChildren()){
                            //drf.child("users").child(String.valueOf(i.getRef())).setValue(uur);
                            //e.setText((CharSequence) i.getRef());
                            i.getRef().setValue(uur) ;
                            Toast.makeText(Homepage.this, uur.name, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                qry.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot i : snapshot.getChildren()){
                            i.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Homepage.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}