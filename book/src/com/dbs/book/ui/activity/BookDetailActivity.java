package com.dbs.book.ui.activity;
import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dbs.book.R;
import com.dbs.book.app.ProjectApp;
import com.dbs.book.net.ApiClient;
import com.dbs.book.ui.model.BookInfo;
import com.dbs.book.ui.model.ContentsInfo;
import com.dbs.book.ui.model.Response;
import com.dbs.book.ui.view.normal.NoScrollListView;

import com.dbs.book.utils.StringUtil;
import com.duowan.mobile.netroid.Listener;
import com.duowan.mobile.netroid.NetroidError;
import com.duowan.mobile.netroid.image.NetworkImageView;
import com.duowan.mobile.netroid.toolbox.FileDownloader;
import com.loopj.android.http.RequestParams;
@SuppressLint("ResourceAsColor")
public class BookDetailActivity extends BaseActivity implements OnClickListener {
	public static final String TAG = "BookDetailActivity";
	public static final String BOOKINFO = "bookInfo";
	
	private NetworkImageView bookDetailImage;
	private TextView bookDeatilName;;
	private TextView bookDetailType;
	private TextView bookDetailrCount;
	private TextView bookChapterNum;
	private TextView bookDetailrSummary;
	private NoScrollListView listview;
	private BookInfo bookInfo;
	private boolean goToRead,addShelfScuss,downLoadScuss;
	
   //查看全部  、 开始阅读 、 加入书架、  下载
	private TextView bookMuluLookAll,bookDetailStartRead,bookDetailDownload,bookDetailAddShelf;
	
	private List<ContentsInfo> contents ;
	
	private Handler mBookDetailHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (null == this || null==BookDetailActivity.this || BookDetailActivity.this.isFinishing())
				return true;
			switch (msg.what) {
				case ApiClient.DATA_OK:
					onBookDetailSuccess(msg.obj.toString());
					Log.e("========", msg.obj.toString());
					break;
				case ApiClient.DATA_ERR:
				case ApiClient.SER_ERR:
					Toast.makeText(BookDetailActivity.this, msg.obj.toString(), 0).show();
					break;
			}
			return true;
		}
	});
	
	private Handler addToShelfHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (null == this || null==BookDetailActivity.this || BookDetailActivity.this.isFinishing())
				return true;
			switch (msg.what) {
				case ApiClient.DATA_OK:
					onaddToShelfSuccess(msg.obj.toString());
					Log.e("========", msg.obj.toString());
					break;
				case ApiClient.DATA_ERR:
				case ApiClient.SER_ERR:
					Toast.makeText(BookDetailActivity.this, msg.obj.toString(), 0).show();
					break;
			}
			return true;
		}
	});
	
	private void onBookDetailSuccess(String string) {
		Log.d(TAG,"contents = "+string) ;
		contents = JSON.parseArray(string, ContentsInfo.class);
		if(null != contents){
			ContentsAdapter adapter = new ContentsAdapter();
			listview.setAdapter(adapter);
		}
	}

	protected void onaddToShelfSuccess(String string) {
		Response response = JSON.parseObject(string, Response.class);
		if(response != null){
			if(response.getError().equals("0")){
				Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, response.getError(),Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(this, ApiClient.DATA_TITLE, Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	protected int provideLayoutResId() {
		return R.layout.book_detail_layout;
	}
	@Override
	protected void initView(Bundle savedInstanceState) {
		bookDeatilName = (TextView) this.findViewById(R.id.book_detail_name);
		bookDetailImage = (NetworkImageView) this.findViewById(R.id.book_detail_Image);
		bookDetailType = (TextView) this.findViewById(R.id.book_detail_type);
		bookDetailrCount = (TextView) this.findViewById(R.id.book_detail_readed_person_count);
		bookDetailrSummary = (TextView) this.findViewById(R.id.book_detail_summary);
		bookChapterNum = (TextView) findViewById(R.id.book_detail_all_count);
		//数据目录
		listview = (NoScrollListView) findViewById(R.id.bookdetail_contents_listview);
		
		//底部按钮
		bookMuluLookAll = (TextView) this.findViewById(R.id.book_mulu_look_all);
		bookDetailStartRead = (TextView) this.findViewById(R.id.book_detail_start_read);
		bookDetailAddShelf = (TextView) this.findViewById(R.id.book_detail_add_shujia);
		bookDetailDownload = (TextView) this.findViewById(R.id.book_detail_download);
	}
	@Override
	protected void initListener() {
		bookMuluLookAll.setOnClickListener(this);
		bookDetailStartRead.setOnClickListener(this);
		bookDetailAddShelf.setOnClickListener(this);
		bookDetailDownload.setOnClickListener(this);
	}
 
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.book_mulu_look_all:
			//ReadActivity.bookId = bookInfo.getId();
			Intent intent = new Intent(this, ContentsListActivity.class);
			intent.putExtra("justShow", 1);
			startActivity(intent);
			break;
		case R.id.book_detail_start_read:
			read();
			break;
		case R.id.book_detail_add_shujia:
			addToShelf();
			break;
		case R.id.book_detail_download:
			download();
			break;
		default:
			break;
		}
	}
	private void read() {
		//goToRead = true;
		//if(goToRead){
			//带查找问题 书名的书序
//			ReadActivity.bookId = bookInfo.getId();
//			ReadActivity.fileName = bookInfo.getName();
			startActivity(new Intent(BookDetailActivity.this, ReadActivity.class));
		//}
//		addToShelf();
//		download();//下载成功才能读取 不能每次都去下载啊 矛盾就在 书籍的名称不能全存在本地啊
	}

	@Override
	protected void initData(Bundle savedInstanceState) {
		 Intent intent  = getIntent();
		 bookInfo = (BookInfo) intent.getSerializableExtra(BOOKINFO);
		 contents = bookInfo.getContents();
		 
		 ReadActivity.bookId = bookInfo.getId();
		 ReadActivity.fileName = bookInfo.getName();
		 
		 mTitleBar.setTitle(bookInfo.getName());
		 bookDetailImage.setImageUrl(bookInfo.getCoverUrl(), ProjectApp.mImageLoader);
		 bookDeatilName.setText(bookInfo.getName());
		 bookDetailType.setText(bookInfo.getType());
		 bookDetailrCount.setText("已有"+bookInfo.getCount()+"人阅读");
		 bookDetailrSummary.setText(bookInfo.getSummary());
		 bookChapterNum.setText("共" + bookInfo.getChapterNum() + "章");
		 
		 Log.d(TAG, bookInfo.getName());
		 Log.d(TAG, bookInfo.getSummary());
		 
		 RequestParams params = new RequestParams();
		 params.add("limit","5");
		 params.add("bookId",bookInfo.getId());
		 ApiClient.getContentsByBookId(params,mBookDetailHandler);
	}
	
	private void addToShelf(){
		RequestParams params = new RequestParams();
		params.put("bookId", bookInfo.getId());
		ApiClient.addToShelf(params, addToShelfHandler);
	}
	
	public void download(){
		String downloadPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File file = new File(downloadPath, "dingboshi");
		if(!file.exists()){
			file.mkdir();
		}
		FileDownloader.DownloadController controller = ProjectApp.mFileDownloader.add(file.getAbsolutePath() + "/" + bookInfo.getName() + ".txt", bookInfo.getTxtUrl(), new Listener<Void>() {
			@Override
			public void onSuccess(Void arg0) {
//				if(goToRead){
//					ReadActivity.bookId = bookInfo.getId();
//					ReadActivity.fileName = bookInfo.getName();
//					startActivity(new Intent(BookDetailActivity.this, ReadActivity.class));
//					//本该在这里 进行的状态的判断的addShelfScuss
//				}else{
				Toast.makeText(BookDetailActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
//				}
				//goToRead = false;
			}
			@Override
			public void onError(NetroidError error) {
				super.onError(error);
				goToRead = false;
				String msg = "";
				if(!StringUtil.isEmpty(error.getMessage())){
					msg = error.getMessage();
				}
				Toast.makeText(BookDetailActivity.this, "下载失败!" + msg, 0).show();
			}
		});
	}
	
	@Override
	public void onLeftClicked() {
		super.onLeftClicked();
		finish();
	}
	
	@Override
	public void onActionClicked() {
		MainActivity.WHICH = MainActivity.SHELF;
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
	
	class ContentsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return contents == null ? 0 : contents.size();
		}

		@Override
		public Object getItem(int position) {
			return contents.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = getLayoutInflater().inflate(R.layout.contents_list_item, null);
			TextView name = (TextView) v.findViewById(R.id.contents_list_title);
			ImageView arrow = (ImageView) v.findViewById(R.id.contents_list_arraw);
			
			name.setText(contents.get(position).getName());
			arrow.setVisibility(View.GONE);
			return v;
		}
		
	}

}
