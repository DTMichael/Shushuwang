package com.example.administrator.shushu1;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2017/6/14.
 */

public class Clear
{       public static List<Activity>activities=new ArrayList<>();
        public static void addActivity(Activity activity){
            activities.add(activity);
        }
}
