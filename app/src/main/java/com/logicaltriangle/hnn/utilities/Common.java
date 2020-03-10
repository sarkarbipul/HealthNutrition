package com.logicaltriangle.hnn.utilities;

import android.content.Context;
import android.content.res.Resources;

import com.logicaltriangle.hnn.R;
import com.logicaltriangle.hnn.entities.Item_desc;

public class Common {

    private Resources resources;

    /*public Common(){
        // do nothing..
    }*/

    public Common(Resources resources){
        this.resources = resources;
    }


    public int getResourceId(Context context, Item_desc itemDesc, int imgSerialNo) {
        String imgName = "N";
        switch (imgSerialNo) {
            case 1:
                imgName = itemDesc.imgName1;
                break;
            case 2:
                imgName = itemDesc.imgName2;
                break;
            case 3:
                imgName = itemDesc.imgName3;
                break;
            case 4:
                imgName = itemDesc.imgName4;
                break;
            default:
                imgName = "N";
                break;
        }
        try {
            if (imgName.length() > 1 && imgName.contains("."))
                imgName = imgName.substring(0, imgName.indexOf("."));
        } catch (Exception ex) {}

        return resources.getIdentifier("drawable/" + imgName, null, context.getPackageName());
    }


    //getting titles array by parentcatid
    public String[] getTitles(int parentCatId) {
        String[] titles = {};
        switch (parentCatId) {
            case 0:
                return resources.getStringArray(R.array.exercise_titles);
            case 1:
                return resources.getStringArray(R.array.disease_titles);
            case 2:
                return resources.getStringArray(R.array.nutrition_kids_ttl);
            case 3:
                return resources.getStringArray(R.array.nutrition_woman_ttl);
            case 4:
                return resources.getStringArray(R.array.nutrition_men_ttl);
            case 5:
                return resources.getStringArray(R.array.nutrition_senior_ttl);
            default:
                return titles;
        }
    }

    //getting titles array by parentcatid and itemPosition
    public String getTitle(int parentCatId, int itemPosition) {
        String[] items = {};
        switch (parentCatId) {
            case 0:
                items = resources.getStringArray(R.array.exercise_titles);
                break;
            case 1:
                items = resources.getStringArray(R.array.disease_titles);
                break;
            case 2:
                items = resources.getStringArray(R.array.nutrition_kids_ttl);
                break;
            case 3:
                items = resources.getStringArray(R.array.nutrition_woman_ttl);
                break;
            case 4:
                items = resources.getStringArray(R.array.nutrition_men_ttl);
                break;
            case 5:
                items = resources.getStringArray(R.array.nutrition_senior_ttl);
                break;
            default:
                break;
        }
        return items[itemPosition];
    }
}
