package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Eduardo on 6/19/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter
{

    // context of the app
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm)
    {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case(0):
                return new NumbersFragment();
            case(1):
                return new FamilyMembersFragment();
            case(2):
                return new ColorsFragment();
            case(3):
                return new PhrasesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch(position)
        {
            case(0):
                return mContext.getString(R.string.numbers_page_title);
            case(1):
                return mContext.getString(R.string.family_page_title);
            case(2):
                return mContext.getString(R.string.colors_page_title);
            case(3):
                return mContext.getString(R.string.phrases_page_title);
            default:
                return null;
        }
    }
}
