package yincheng.sourcecodeinvestigate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import yincheng.sourcecodeinvestigate.view.explosion.ExplosionField;

public class MainActivity extends AppCompatActivity {
    private TextView tv_show;
    private String[] stringsArray;
    private String resultString;
    private ExplosionField explosionField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        explosionField = ExplosionField.attach2Window(this);
//        stringsArray = "wo,de shi ji -7347847857 e kai shi xia xue".split("\\w");
//        resultString = "wo de shi jie kai shi xia xue".replaceAll("wo", "ni");
//        resultString = resultString.replaceAll("xia", "ran");
//        resultString = resultString.replaceAll("xue", "shao");
//        Log.i("wodeshijie", resultString);
//        tv_show = findViewById(R.id.tv_show);
//        tv_show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                explosionField.explode(v);
//                for (String s : stringsArray) {
//                    Log.i("wodeshijiekaishixiaxue", s);
//                }
//            }
//        });
//
//        CoffeeGenerator generator = new CoffeeGenerator();
//        for (int i = 0; i < 5; i++) {
//            Log.i("wodeshijie", generator.next().toString());
//        }
//
//        for (Coffee coffee : new CoffeeGenerator(5)) {
//            Log.i("wodeshijied", coffee.toString());
//        }

        StringBuilder builder = new StringBuilder();
        builder.append(" ").append("15655");
        StringBuilder builder1 = new StringBuilder();
        builder1.append(' ').append("15655");
        StringBuilder builder2 = new StringBuilder();
        builder2.append("15655");
        Log.i("wodeshijie", builder.toString());
        Log.i("wodeshijie", builder1.toString());
        Log.i("wodeshijie", builder2.toString());
    }
}
