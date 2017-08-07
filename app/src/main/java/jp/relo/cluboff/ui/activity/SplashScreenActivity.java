package jp.relo.cluboff.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import framework.phvtActivity.BaseActivity;
import jp.relo.cluboff.R;
import jp.relo.cluboff.util.Constant;
import jp.relo.cluboff.util.LoginSharedPreference;

/**
 * Created by HuyTran on 3/21/17.
 */

public class SplashScreenActivity extends BaseActivity {
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int[] layouts;
    boolean isDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushvisorHandlerActivity.checkOpenedThisScreen=false;
    }


    private void goNextScreen() {
        PushvisorHandlerActivity.checkOpenedThisScreen = true;
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void getMandatoryViews(Bundle savedInstanceState) {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        layouts = new int[]{
                R.layout.splash_1_layout,
                R.layout.splash_2_layout,
                R.layout.splash_3_layout,
                R.layout.splash_4_layout,
                R.layout.splash_5_layout};
        myViewPagerAdapter = new SplashScreenActivity.MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
    }

    @Override
    protected void registerEventHandlers() {
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        boolean lastPageChange = false;
        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrolled(int position, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            int lastIdx = myViewPagerAdapter.getCount() - 1;
            int curItem = viewPager.getCurrentItem();
            if(curItem==lastIdx  && state==1){
                lastPageChange = true;
            }else  {
                lastPageChange = false;
            }
        }
    };

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Button btnCloseSplash;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            if(position==layouts.length-1){
                btnCloseSplash = (Button) view.findViewById(R.id.btnCloseSplash);
                if(btnCloseSplash!=null){
                    btnCloseSplash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean notFirst = LoginSharedPreference.getInstance(SplashScreenActivity.this).get(Constant.TAG_IS_FIRST, Boolean.class);
                            if(notFirst){
                                finish();
                            }else{
                                LoginSharedPreference.getInstance(SplashScreenActivity.this).put(Constant.TAG_IS_FIRST, true);
                                goNextScreen();
                            }
                        }
                    });
                }
            }
            container.addView(view);
            return view;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
