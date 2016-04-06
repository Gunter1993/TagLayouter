package com.coding.taglayouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coding.tag_layouter.AutoLayoutContainer;


public class MainActivity extends AppCompatActivity implements AutoLayoutContainer.OnSelectListener {

    private AutoLayoutContainer alc_horizontal,alc_vertical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alc_horizontal = (AutoLayoutContainer) findViewById(R.id.alc_horizontal);
        alc_vertical = (AutoLayoutContainer) findViewById(R.id.alc_vertical);
        alc_horizontal.addTag(R.layout.tag, "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk");
        alc_horizontal.setOnSelectListener(this);

        alc_vertical.addTag(R.layout.tag, "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk"
                , "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk", "dkfkldas", "dfjask", "dfjask", "dsafk");
        alc_vertical.setOnSelectListener(this);
    }


    @Override
    public void onSelect(View tag, int position) {
        if (tag.isSelected())
            tag.setSelected(false);
        else tag.setSelected(true);
    }
}
