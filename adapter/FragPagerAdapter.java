package com.anytalk.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 
 * @author 黄松炎 修改 方泽斯 ViewPager适配器
 */
public class FragPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragmentsList;

	public FragPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public FragPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.mFragmentsList = fragments;
	}

	@Override
	public int getCount() {
		return mFragmentsList != null ? mFragmentsList.size() : 0;
	}

	@Override
	public Fragment getItem(int arg0) {
		return mFragmentsList != null && mFragmentsList.size() > 0 ? mFragmentsList
				.get(arg0) : null;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

}
