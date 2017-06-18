package com.dbs.book.ui.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import com.dbs.book.R;
import com.dbs.book.ui.fragment.UserCenterFragment;
import com.dbs.book.utils.PreferenceManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//测试用
public class DialogActivity extends Activity implements OnClickListener {
	private static final int CAMERA_WITH_DATA = 0;   //拍照
	private static final int PHOTO_PICKED_WITH_DATA = 1;  //gallery
	private static final File PHOTO_DIR = 
				new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");//图片的存储目录
    File mCurrentPhotoFile; 
	ImageView view;
	
	private TextView toPhotoAlbum,takePictrue,selectCancle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_picture_dialog_layout);
        toPhotoAlbum = (TextView) this.findViewById(R.id.to_photo_album);
		takePictrue = (TextView) this.findViewById(R.id.take_pictrue);
		selectCancle = (TextView) this.findViewById(R.id.select_cancle);
		view = (ImageView) this.findViewById(R.id.text_imageview_one);
		
		takePictrue.setOnClickListener(this);
		toPhotoAlbum.setOnClickListener(this);
		selectCancle.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.to_photo_album:
			getPicFromContent();
			break;
		case R.id.take_pictrue:
			getPicFromCapture(); //拍照
			break;
		case R.id.select_cancle:
			finish();
			break;
		default:
			break;
		}
    }

    //拍照
  private void getPicFromCapture(){
	  if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){ 
		    PHOTO_DIR.mkdir(); 
		    mCurrentPhotoFile = new File(PHOTO_DIR,getPhotoFileName()); //用当前时间给取得的图片命名 
			
			Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");  
			Uri fromFile = Uri.fromFile(mCurrentPhotoFile);
			String string = fromFile.toString();
			Log.i("gp", string);
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCurrentPhotoFile)); 
			startActivityForResult(intent, CAMERA_WITH_DATA); 
	  	}  else{  
          Toast.makeText(DialogActivity.this, "没有sd卡", Toast.LENGTH_LONG);  
      }  
  }
  //用当前时间给取得的图片命名 
  private String getPhotoFileName() {  
	    Date date = new Date(System.currentTimeMillis());  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");  
	    return dateFormat.format(date) + ".jpg";  
	}
  
 //相册
 private void getPicFromContent(){
		  try {  
		        Intent intent = getPhotoPickIntent();  
		        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);  
		    } catch (Exception e) {  
		        Toast.makeText(this, "错误",Toast.LENGTH_LONG).show();  
		    }  
	  }
//对图片进行剪裁
 public static Intent getPhotoPickIntent() {  
     Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);  
     intent.setType("image/*");  
     intent.putExtra("crop", "true");  
     intent.putExtra("aspectX", 1);  
     intent.putExtra("aspectY", 1);  
     intent.putExtra("outputX", 80);  
     intent.putExtra("outputY", 80);  
     intent.putExtra("return-data", true);  
     return intent;  
 } 
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	  super.onActivityResult(requestCode, resultCode, data); 
	    if (resultCode != RESULT_OK)  
	        return; 
	    switch (requestCode) {  
        case PHOTO_PICKED_WITH_DATA: { //相册
        	Log.i("gp", "相机回来了吗");
        	Bundle extras = data.getExtras();
        	Bitmap bitmap = (Bitmap) extras.get("data");
        	
        	SharedPreferences pre = getSharedPreferences("dingboshi",Context.MODE_APPEND);
        	Editor editor = pre.edit();
        	editor.putString("bitmap", bitmaptoString(bitmap));
        	editor.commit();
        	Log.d("Diabitmap", "dialaog = "+bitmaptoString(bitmap));
        	Log.d("Diabitmap", "dialaog = "+pre.getString("bitmap", ""));
        	
        	finish();
        	break;  
        }  
        case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片  
        	Log.i("gp", "照相机回来了吗");
        	doCropPhoto(mCurrentPhotoFile);
            break;  
        }
    }  
  }
  public static String bitmaptoString(Bitmap bitmap){
//将Bitmap转换成字符串
    String string=null;
    ByteArrayOutputStream bStream=new ByteArrayOutputStream();
    bitmap.compress(CompressFormat.PNG,100,bStream);
    byte[]bytes=bStream.toByteArray();
    string=Base64.encodeToString(bytes,Base64.DEFAULT);
    return string;
   }
  protected void doCropPhoto(File f) {  
	    try {  
	        // 启动gallery去剪辑这个照片  
	        final Intent intent = getCropImageIntent(Uri.fromFile(f));  
	        startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);  
	    } catch (Exception e) {  
	    	Toast.makeText(this,"失败",Toast.LENGTH_LONG).show();
	    }  
	}
  /**  
  * Constructs an intent for image cropping. 调用图片剪辑程序  
    剪裁后的图片跳转到新的界面
  */  
  public static Intent getCropImageIntent(Uri photoUri) {  
      Intent intent = new Intent("com.android.camera.action.CROP");  
      intent.setDataAndType(photoUri, "image/*");  
      intent.putExtra("crop", "true");  
      intent.putExtra("aspectX", 1);  
      intent.putExtra("aspectY", 1);  
      intent.putExtra("outputX", 80);  
      intent.putExtra("outputY", 80);  
      intent.putExtra("return-data", true);
      return intent;  
  }

}			 