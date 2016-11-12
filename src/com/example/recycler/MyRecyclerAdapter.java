package com.example.recycler;

import java.util.ArrayList;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>/*<RecyclerView.ViewHolder>*//*RecyclerView.Adapter*/{
    
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    private View mHeaderView;
    private View mFooterView;
    ArrayList<Product> products;
    
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
        Log.d("HK", "setHeaderView  called" );
    }
    
    public void setFooterView(View footerView) {
        mFooterView =footerView;
        int viewCount = getItemCount();
        notifyItemInserted(viewCount-1);
    }
    
    public int getItemViewType(int position) {
        Log.d("HK", "getItemViewType  called "+position);
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }
    
    public View getHeaderView() {
        return mHeaderView;
    }
 
    public View getFooterView() {
        return mFooterView;
    }
    public MyRecyclerAdapter(ArrayList<Product> list) {
        products = list;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        Log.d("HK", "getRealPosition  called "+position);
        return mHeaderView == null ? position : position - 1;
    }
    
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        Log.d("HK", "onViewAttachedToWindow  called ");
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }
    
    @Override
    public void onBindViewHolder(ViewHolder arg0, final int position) {
        Log.d("HK", "onBindViewHolder  called "+position);
        if(getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(arg0);
        final Product data = products.get(pos);
        if(arg0 instanceof  MasonryView) {
            ((MasonryView) arg0).textView.setText(products.get(position).getTitle());
            ((MasonryView) arg0).imageView.setImageResource(products.get(position).getImg());
        }
        ImageView image = ((MasonryView)arg0).imageView;
        TextView text = ((MasonryView)arg0).textView;
        image.setImageResource(products.get(position).getImg());
        text.setText(products.get(position).getTitle());
        View view = ((MasonryView)arg0).view;
//        view .setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Log.d("HK", "view on click " + arg1);
//            }
//        });
//        image.setOnClickListener(new View.OnClickListener() {
//            
//            @Override
//            public void onClick(View v) {
//                Log.d("HK", "onClick  image " + products.get(arg1).getTitle());
//            }
//        });
//        text.setOnLongClickListener(new View.OnLongClickListener() {
//            
//            @Override
//            public boolean onLongClick(View v) {
//                Log.d("HK", "setOnLongClickListener  TEXT " + products.get(arg1).getTitle());
//                return false;
//            }
//        });
        
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("HK", "onCreateViewHolder  called "+viewType);
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MasonryView(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.receycler_item, parent, false);
        return new MasonryView(layout);
    }

     class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        View view;

       public MasonryView(View itemView){
           super(itemView);
           view = itemView;
           imageView= (ImageView) itemView.findViewById(R.id.item_img );
           textView= (TextView) itemView.findViewById(R.id.item_title);
       }

    }
}
