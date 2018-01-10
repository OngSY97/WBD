package my.edu.tarc.wbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class AddDevice extends AppCompatActivity {
    private Button buttonAdd, buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonSkip = (Button) findViewById(R.id.buttonSkip);
    }
}
