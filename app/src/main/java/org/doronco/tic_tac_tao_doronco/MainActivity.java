package org.doronco.tic_tac_tao_doronco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9;
    List<Button> btns = new ArrayList<>();
    int[][] mat = new int[3][3];
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initGame();
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        button.setText("X"); // update UI
        String ch = (String) button.getTag();
        int i = Integer.parseInt(ch.split("_")[0]);
        int j = Integer.parseInt(ch.split("_")[1]);
        mat[i][j] = 1 ; // update matrix
        int go = gameOver();
        if(go!=-1) {
            clean();
            if(go == 1)
                Toast.makeText(this,"Player 1 WIN", Toast.LENGTH_LONG).show();
            else if (go == 2)
                Toast.makeText(this,"Player 2 WIN", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this,"Draw", Toast.LENGTH_LONG).show();
        }else {
            IA_level0();
            go = gameOver();
            if(go!=-1) {
                clean();
                if(go == 1)
                    Toast.makeText(this,"Player 1 WIN", Toast.LENGTH_LONG).show();
                else if (go == 2)
                    Toast.makeText(this,"Player 2 WIN", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this,"Draw", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void IA_level0(){
        int i,j ;
        do {
            i = random.nextInt(3);
            j = random.nextInt(3);
        }while (mat[i][j] != 0);
        for(Button button : btns){
            if(((String)button.getTag()).equals(i+"_"+j)) {
                button.setText("O"); // update UI
                break;
            }
        }
        mat[i][j] = -1 ;
    }

    public void clean(){
        for(Button button : btns){
            button.setText("");
            button.setOnClickListener(null);
        }
    }

    /**
     * Game Over
     * @return 1:Player, 2:IA, 0:draw, -1:continue
     */
    public int gameOver(){
        int l,c,d=0,dd=0;
        boolean draw = true ;
        for(int i=0;i<3;i++){
            l = 0; c= 0;
            d += mat[i][i];
            for(int j=0;j<3;j++){
                l+=mat[i][j];
                c+=mat[j][i];
                if(i+j==2)
                    dd+=mat[i][j];
                if(mat[i][j]==0)
                    draw = false ;
            }
            if(l==3 || c==3)
                return 1;
            if(l==-3 || c==-3)
                return 2;
        }
        if(d==3 || dd==3)
            return 1;
        if(d==-3 || dd==-3)
            return 2;
        if(draw)
            return 0;
        else
            return -1;
    }

    private void initGame(){
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        btns.addAll(List.of(b1,b2,b3,b4,b5,b6,b7,b8,b9));
        int k = 0 ;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                btns.get(k).setTag(i+"_"+j);
                btns.get(k).setOnClickListener(this);
                k++;
            }
        }
    }


    public void restart(View view) {
        initGame();
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                mat[i][j] = 0 ;
            }
        }
    }
}