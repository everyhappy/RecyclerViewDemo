package com.example.recycler;

import java.util.ArrayList;

import com.example.recycler.MyRecyclerAdapter.MasonryView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyNormalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Product> products;
    private View mHeaderView;
    private View mFooterView;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_FOOTER = 2;
    private OnItemClickLitener mOnItemClickLitener;
    
    public MyNormalRecyclerAdapter(ArrayList<Product> list) {
        products = list;
    }
    
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
        Log.d("HK", "setHeaderView  called" );

    }
    
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        int footIndex = getItemCount()-1;
        notifyItemInserted(footIndex);
        Log.d("HK", "setFooterView  called and index = "+footIndex);
    }
    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null) return products.size();
        if(mHeaderView != null && mFooterView !=null) return products.size()+2;
        return products.size() + 1;
    }
    
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        Log.d("HK", "getRealPosition called "+position);
        return mHeaderView == null ? position : position - 1;
    }
    
    public int getItemViewType(int position) {
        Log.d("HK", "getItemViewType  called "+position);
        if((mHeaderView == null) && (mFooterView == null)) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        if(position == getItemCount()-1) return TYPE_FOOTER;
        return TYPE_NORMAL;
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder viewholder, final int position) {
        // TODO Auto-generated method stub
        if(getItemViewType(position) == TYPE_HEADER) return;
        if(getItemViewType(position) == TYPE_FOOTER) return;
        final int pos = getRealPosition(viewholder);
        Log.d("HK", "onBindViewHolder  called position pos "+position+" "+pos);
        final Product data = products.get(pos);
        if(viewholder instanceof  MyViewHolder) {
            ((MyViewHolder) viewholder).textView.setText(data.getTitle());
            ((MyViewHolder) viewholder).textView.setHeight(100 + (position % 3) * 30);
            ((MyViewHolder) viewholder).imageView.setImageResource(data.getImg());
             
//            ((MyViewHolder) viewholder).view.setOnClickListener(new OnClickListener() {
//                
//                @Override
//                public void onClick(View v) {
//                    // TODO Auto-generated method stub
//                    Log.d("HK", "view on click " + position);
//                }
//            });
            if (mOnItemClickLitener != null)
            {
                viewholder.itemView.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int pos = viewholder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(viewholder.itemView, pos);
                    }
                });

                viewholder.itemView.setOnLongClickListener(new OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        int pos = viewholder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(viewholder.itemView, pos);
                        return false;
                    }
                });
            }
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    return (getItemViewType(position) == TYPE_HEADER||getItemViewType(position) == TYPE_FOOTER)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }
    
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if(holder.getLayoutPosition() == 0 ||(holder.getLayoutPosition() == getItemCount()-1)) {
                p.setFullSpan(true);
            }
        }
    }
    
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO Auto-generated method stub
        if(mHeaderView != null && viewType == TYPE_HEADER) return new MyViewHolder(mHeaderView);
        if(mFooterView !=null && viewType == TYPE_FOOTER) return new MyViewHolder(mFooterView);
        
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.receycler_item, parent, false));
        
        return myViewHolder;
    }
    
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    
    class  MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        View view;

       public MyViewHolder(View itemView){
           super(itemView);
           view = itemView;
           imageView= (ImageView) itemView.findViewById(R.id.item_img );
           textView= (TextView) itemView.findViewById(R.id.item_title);
       }

    }
}
