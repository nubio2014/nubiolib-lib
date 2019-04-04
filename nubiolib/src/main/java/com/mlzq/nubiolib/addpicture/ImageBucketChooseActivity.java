package com.mlzq.nubiolib.addpicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mlzq.nubiolib.R;
import com.mlzq.nubiolib.app.BaseActivity;
import com.mlzq.nubiolib.http.L;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 选择相册
 * 
 */

public class ImageBucketChooseActivity extends BaseActivity
{
	private ImageFetcher mHelper;
	private List<ImageBucket> mDataList = new ArrayList<ImageBucket>();
	private ListView mListView;
	private ImageBucketAdapter mAdapter;
	private int availableSize,max;
	static ImageBucketChooseActivity instance=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContent(R.layout.act_image_bucket_choose);

		instance=ImageBucketChooseActivity.this;
		mHelper = ImageFetcher.getInstance(getApplicationContext());

		initData();
		initView();
	}

	private void initData()
	{
		////CustomConstants.MAX_IMAGE_SIZE= Integer.parseInt(getIntent().getStringExtra("max"));
		mDataList = mHelper.getImagesBucketList(false);
		availableSize = getIntent().getIntExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, CustomConstants.MAX_IMAGE_SIZE);

	}

	private void initView()
	{
		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = new ImageBucketAdapter(this, mDataList);
		mListView.setAdapter(mAdapter);
		//TextView titleTv  = (TextView) findViewById(R.id.title);
		setTitleName("相册");
		mListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
			{

				selectOne(position);

				Intent intent = new Intent(ImageBucketChooseActivity.this,ImageChooseActivity.class);
				intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,(Serializable) mDataList.get(position).imageList);
				intent.putExtra(IntentConstants.EXTRA_BUCKET_NAME,mDataList.get(position).bucketName);
				intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
						availableSize);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
				startActivityForResult(intent,1);

			}
		});

setLeftListener();
//		setRightListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent = new Intent(ImageBucketChooseActivity.this,
//						CustomConstants.context.getClass());
//				startActivity(intent);
//				ImageBucketChooseActivity.this.finish();
//			}
//		});
	}

	private void selectOne(int position)
	{
		int size = mDataList.size();
		for (int i = 0; i != size; i++)
		{
			if (i == position) mDataList.get(i).selected = true;
			else
			{
				mDataList.get(i).selected = false;
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode){
			case 1:
				L.d("传值页面："+data);
				setResult(1,data);
				finish();
				break;
		}
	}
}
