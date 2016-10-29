package itp341.wang.cherrie.parkhere;

/**
 * Created by glarencezhao on 10/29/16.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.prototypes.CardSection;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;

/**
 * Sectioned adapter
 */
public class CustomSectionAdapter extends SectionedCardAdapter {

    /*
     * Define your layout in the constructor
     */
    public CustomSectionAdapter(Context context, CardArrayAdapter cardArrayAdapter) {
        super(context, R.layout.custom_section_layout, cardArrayAdapter);
    }

    /*
     * Override this method to customize your layout
     */
    @Override
    protected View getSectionView(int position, View view, ViewGroup parent) {

        //Override this method to customize your section's view

        //Get the section
        CardSection section = (CardSection) getCardSections().get(position);

        if (section != null ) {
            //Set the title
            TextView title = (TextView) view.findViewById(R.id.customSectionTitle);
            if (title != null)
                title.setText(section.getTitle());
        }

        return view;
    }
}
