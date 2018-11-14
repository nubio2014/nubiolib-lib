package com.mlzq.nubiolib.addpicture;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mlzq.nubiolib.R;

import java.util.ArrayList;
import java.util.List;


public class SubmitImageAdapter extends BaseAdapter
{
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private Context mContext;
	private int max;

	public SubmitImageAdapter(Context context, List<ImageItem> dataList, int max)
	{
		this.mContext = context;
		this.mDataList = dataList;
		this.max=max;
	}

	public int getCount()
	{
		// 多返回一个用于展示添加图标
		if (mDataList == null)
		{
			return 1;
		}
		else if (mDataList.size() == max)
		{
			return max;
		}
		else
		{
			return mDataList.size() + 1;
		}
	}

	public Object getItem(int position)
	{
		if (mDataList != null&& mDataList.size() == max)
		{
			return mDataList.get(position);
		}

		else if (mDataList == null || position - 1 < 0
				|| position > mDataList.size())
		{
			return null;
		}
		else
		{
			return mDataList.get(position - 1);
		}
	}

	public long getItemId(int position)
	{
		return position;
	}

	@SuppressLint("ViewHolder")
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		//所有Item展示不满一页，就不进行ViewHolder重用了，避免了一个拍照以后添加图片按钮被覆盖的奇怪问题
		convertView = View.inflate(mContext, R.layout.item_submit, null);
		ImageView imageIv = (ImageView) convertView
				.findViewById(R.id.is_submit_image);
		ImageView imageDel = (ImageView) convertView.findViewById(R.id.is_submit_del);

		if (isShowAddItem(position))
		{
			imageIv.setImageResource(R.drawable.btn_add_a_photo);
			imageDel.setVisibility(View.GONE);
		}
		else
		{
			final ImageItem item = mDataList.get(position);
			ImageDisplayer.getInstance(mContext).displayBmp(imageIv,
					item.thumbnailPath, item.sourcePath);
		}
		imageDel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDataList.remove(position);
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	private boolean isShowAddItem(int position)
	{
		int size = mDataList == null ? 0 : mDataList.size();
		return position == size;
	}

}
