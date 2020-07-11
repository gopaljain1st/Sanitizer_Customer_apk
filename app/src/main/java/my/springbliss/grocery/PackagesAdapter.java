package my.springbliss.grocery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import my.springbliss.grocery.R;

public class PackagesAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;
    Integer[] images={R.drawable.scre1,R.drawable.scre2,R.drawable.allproduct,R.drawable.slider2s,R.drawable.slider3s,R.drawable.slider4s};

    public PackagesAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View view=layoutInflater.inflate(R.layout.custom_layout,null);

        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp=(ViewPager)container;
        View view=(View)object;
        vp.removeView(view);
    }
}
