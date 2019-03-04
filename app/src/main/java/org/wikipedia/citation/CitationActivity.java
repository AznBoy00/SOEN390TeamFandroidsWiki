package org.wikipedia.citation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.page.PageBackStackItem;
import org.wikipedia.page.tabs.Tab;
import org.wikipedia.readinglist.database.ReadingListPage;

public class CitationActivity extends AppCompatActivity {

    private RadioGroup citationStyleGroup;
    private RadioButton citationStyleBtn;

    private RadioButton citationStyleBtn_APA;
    private RadioButton citationStyleBtn_MLA;
    private RadioButton citationStyleBtn_IEEE;

    private CheckBox citationLaTeXBtn;

    private Tab currentTab = new Tab();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.page_toolbar);
        setSupportActionBar(toolbar);

        citationStyleGroup = (RadioGroup) findViewById(R.id.citation_radiogroup_btn);
        citationStyleBtn_APA = citationStyleGroup.findViewById(R.id.button_apa);
        citationStyleBtn_MLA = citationStyleGroup.findViewById(R.id.button_mla);
        citationStyleBtn_IEEE = citationStyleGroup.findViewById(R.id.button_ieee);
        citationLaTeXBtn = findViewById(R.id.button_latex);
        setCitationStyleBtnBG(R.id.button_apa);
        setCitationLaTeXBtnBG(false);


        // Get Page Information
        Intent intent = getIntent();
        String URLText = intent.getStringExtra("item_MobileURI");
        String TitleText = intent.getStringExtra("item_Title");

        TextView citation_box = (TextView) findViewById(R.id.citation_box_text);
        // Find and Monitor Button Click
        Button btn = (Button) findViewById(R.id.IEEE_Button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setup Citation Generator
                CitationGenerator generator =  new CitationGenerator(URLText);
                // Create IEEE Citation String
                String citation  = generator.IEEECitationGenerator(TitleText);
                citation_box.setText(citation);
                Toast.makeText(CitationActivity.this, citation, Toast.LENGTH_SHORT).show();
                //setContentView(citation_box);
            }
        });

        addListenerOnRadioGroupButton();
        addListenerOnCheckButton();
    }



    public void addListenerOnRadioGroupButton() {

        citationStyleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                citationStyleBtn = group.findViewById(checkedId);

                if (citationStyleBtn != null && checkedId > -1) {
                    setCitationStyleBtnBG(checkedId);
                    Toast.makeText(CitationActivity.this, citationStyleBtn.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // this is for the button style change
    public void setCitationStyleBtnBG(int checkedId){
        if(checkedId == R.id.button_apa)
        {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_black);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_white);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_white);

        }
        if(checkedId == R.id.button_mla)
        {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_white);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_black);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_white);
        }
        if(checkedId == R.id.button_ieee)
        {
            citationStyleBtn_APA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_MLA.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationStyleBtn_IEEE.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationStyleBtn_APA.setTextColor(R.color.color_state_white);
            citationStyleBtn_MLA.setTextColor(R.color.color_state_white);
            citationStyleBtn_IEEE.setTextColor(R.color.color_state_black);
        }
    }

    public void setOnClickListener() {

    }

    public void addListenerOnCheckButton() {

        citationLaTeXBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setCitationLaTeXBtnBG(isChecked);
            }
        } );
    }

    // this is for the button style change
    public void setCitationLaTeXBtnBG(boolean isChecked){
        if (isChecked)
        {
            citationLaTeXBtn.setBackgroundResource(R.drawable.citation_style_button_selected);
            citationLaTeXBtn.setTextColor(R.color.color_state_black);
        }
        else {
            citationLaTeXBtn.setBackgroundResource(R.drawable.citation_style_button_unselected);
            citationLaTeXBtn.setTextColor(R.color.color_state_white);
        }
    }
}
