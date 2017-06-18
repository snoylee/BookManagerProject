package com.dbs.book.ui.view.read;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.dbs.book.ui.activity.ReadActivity;
import com.dbs.book.ui.model.BookInfo;
import com.dbs.book.utils.PreferenceManager;

import android.R.color;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Toast;

public class BookPageFactory {

	private File book_file = null;
	private MappedByteBuffer m_mbBuf = null;
	private int m_mbBufLen = 0;
	int m_mbBufBegin = 0;
	private int m_mbBufEnd = 0;
	//private String m_strCharsetName = "GBK";
	private String m_strCharsetName = "GBK";
	private Bitmap m_book_bg = null;
	private int mWidth;
	private int mHeight;

	private Vector<String> m_lines = new Vector<String>();
	// -----------------------卢-----------------------修改
	public int m_fontSize;// 字体大小
	SharedPreferences preferences ;
	SharedPreferences.Editor edit;
	// ----------------------------------------------------------
	public int m_textColor = Color.parseColor("#666666");// 字体颜色
	private int m_backColor = 0xffff9e85; // 背景颜色
	private int marginWidth = 15; // 左右与边缘的距离
	private int marginHeight = 50; // 上下与边缘的距离

	private int mLineCount; // 每页可以显示的行数
	private float mVisibleHeight; // 绘制内容的高
	private float mVisibleWidth; // 绘制内容的宽
	private boolean m_isfirstPage, m_islastPage;

	public Paint mPaint;
	private List<Bitmap> mBitmaps;
	private float fPercent;
    private Context context;
    
    private SharedPreferences spr;
    private Editor editor;
    
    private int newoffset;
    //记录点击章节的位置的
	public  void setBuffEnd(int bufferEnd,int justShow){
		if(justShow == 1){
			this.m_mbBufBegin = bufferEnd;
			this.m_mbBufEnd = m_mbBufBegin;
		}else{
			findOffset();
		}
	}
	public BookPageFactory(Context context, List<Bitmap> bitmaps, int w, int h) {
		this.context=context;
		mWidth = w;
		mHeight = h;
		
		findOffset();
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setColor(m_textColor);
		mPaint.setTextSize(12);
		
		// --------------------------卢--------------
		preferences = context.getSharedPreferences("myBook",
				Context.MODE_WORLD_WRITEABLE);
		edit = preferences.edit();
		int size = preferences.getInt("size", 28);
		m_fontSize = size;
		
		// -----------------------------------------------
		mPaint.setTextSize(m_fontSize);
		mVisibleWidth = mWidth - marginWidth * 2;
		mVisibleHeight = mHeight - marginHeight * 2;
		mLineCount = (int) (mVisibleHeight / m_fontSize); // 可显示的行数
		mBitmaps = bitmaps;
	}
	
	private void findOffset() {
		spr = this.context.getSharedPreferences(ReadActivity.fileName+"bookOffset",Context.MODE_APPEND);//参数存储的时候 不能用private这个权限
		editor = spr.edit();

		m_mbBufBegin = spr.getInt("offset",0);//拿到上次记录的位置
		m_mbBufEnd = m_mbBufBegin;//控制再次进入的时候 读取的位置
	}
	
	public void openbook(File file) throws IOException {
		book_file = file;
		long lLen = book_file.length();
		m_mbBufLen = (int) lLen;
		m_mbBuf = new RandomAccessFile(book_file, "r").getChannel().map(
				FileChannel.MapMode.READ_ONLY, 0, lLen);
	}

	// ___------------------lu-----------------
	// 新增加的方法，设置字体大小
	public void setTextSize(int a) {
		this.m_fontSize = a;
		mPaint.setTextSize(a);
		mLineCount = (int) (mVisibleHeight / m_fontSize);
		m_lines = pageDown(true);
		
		edit.putInt("size", a);
		edit.commit();
	}

	// ------------------------------lu-----------------
	protected byte[] readParagraphBack(int nFromPos) {
		int nEnd = nFromPos;
		int i;
		byte b0, b1;
		if (m_strCharsetName.equals("UTF-16LE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x0a && b1 == 0x00 && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}

		} else if (m_strCharsetName.equals("UTF-16BE")) {
			i = nEnd - 2;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				b1 = m_mbBuf.get(i + 1);
				if (b0 == 0x00 && b1 == 0x0a && i != nEnd - 2) {
					i += 2;
					break;
				}
				i--;
			}
		} else {
			i = nEnd - 1;
			while (i > 0) {
				b0 = m_mbBuf.get(i);
				if (b0 == 0x0a && i != nEnd - 1) {
					i++;
					break;
				}
				i--;
			}
		}
		if (i < 0)
			i = 0;
		int nParaSize = nEnd - i;
		int j;
		byte[] buf = new byte[nParaSize];
		for (j = 0; j < nParaSize; j++) {
			buf[j] = m_mbBuf.get(i + j);
		}
		return buf;
	}

	// 读取上一段落
	protected byte[] readParagraphForward(int nFromPos) {
		int nStart = nFromPos;
		int i = nStart;
		byte b0, b1;
		// 根据编码格式判断换行
		if (m_strCharsetName.equals("UTF-16LE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x0a && b1 == 0x00) {
					break;
				}
			}
		} else if (m_strCharsetName.equals("UTF-16BE")) {
			while (i < m_mbBufLen - 1) {
				b0 = m_mbBuf.get(i++);
				b1 = m_mbBuf.get(i++);
				if (b0 == 0x00 && b1 == 0x0a) {
					break;
				}
			}
		} else {
			while (i < m_mbBufLen) {
				b0 = m_mbBuf.get(i++);
				if (b0 == 0x0a) {
					break;
				}
			}
		}
		int nParaSize = i - nStart;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mbBuf.get(nFromPos + i);
		}
		return buf;
	}

	// 增加一个判断
	protected Vector<String> pageDown(boolean size) {

		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		// 如果真的话字体变化改变数据
		if (size) {
			m_mbBufEnd = m_mbBufBegin;
		}
		while (lines.size() < mLineCount && m_mbBufEnd < m_mbBufLen) {
			byte[] paraBuf = readParagraphForward(m_mbBufEnd); // 读取一个段落
			m_mbBufEnd += paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String strReturn = "";
			if (strParagraph.indexOf("\r\n") != -1) {
				strReturn = "\r\n";
				strParagraph = strParagraph.replaceAll("\r\n", "");
			} else if (strParagraph.indexOf("\n") != -1) {
				strReturn = "\n";
				strParagraph = strParagraph.replaceAll("\n", "");
			} else if (strParagraph.indexOf("(@.@)") != -1) {
				strReturn = "(@.@)";
				strParagraph = strParagraph.replaceAll("(@.@)", "");
			}

			if (strParagraph.length() == 0) {
				lines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				lines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
				if (lines.size() >= mLineCount) {
					break;
				}
			}
			if (strParagraph.length() != 0) {
				try {
					m_mbBufEnd -= (strParagraph + strReturn).getBytes(m_strCharsetName).length;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
	}

	protected void pageUp() {
		if (m_mbBufBegin < 0)
			m_mbBufBegin = 0;
		Vector<String> lines = new Vector<String>();
		String strParagraph = "";
		while (lines.size() < mLineCount && m_mbBufBegin > 0) {
			Vector<String> paraLines = new Vector<String>();
			byte[] paraBuf = readParagraphBack(m_mbBufBegin);
			m_mbBufBegin -= paraBuf.length;
			try {
				strParagraph = new String(paraBuf, m_strCharsetName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "");
			strParagraph = strParagraph.replaceAll("\n", "");

			if (strParagraph.length() == 0) {
				paraLines.add(strParagraph);
			}
			while (strParagraph.length() > 0) {
				int nSize = mPaint.breakText(strParagraph, true, mVisibleWidth,
						null);
				paraLines.add(strParagraph.substring(0, nSize));
				strParagraph = strParagraph.substring(nSize);
			}
			lines.addAll(0, paraLines);
		}
		while (lines.size() > mLineCount) {
			try {
				m_mbBufBegin += lines.get(0).getBytes(m_strCharsetName).length;
				lines.remove(0);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		m_mbBufEnd = m_mbBufBegin;
		return;
	}

	public void prePage() throws IOException {
		if (m_mbBufBegin <= 0) {
			m_mbBufBegin = 0;
			m_isfirstPage = true;
			return;
		} else
			m_isfirstPage = false;
		m_lines.clear();
		pageUp();
		m_lines = pageDown(false);
	}

	public void nextPage() throws IOException {
		if (m_mbBufEnd >= m_mbBufLen) {
			m_islastPage = true;
			return;
		} else
			m_islastPage = false;
		m_lines.clear();
		m_mbBufBegin = m_mbBufEnd;
		m_lines = pageDown(false);
	}

	public void onDraw(Canvas c) {
		PreferenceManager.save(ReadActivity.fileName, m_mbBufEnd);
		if (m_lines.size() == 0)
			m_lines = pageDown(false);
		if (m_lines.size() > 0) {
			if (m_book_bg == null)
				c.drawColor(m_backColor);
			else
				c.drawBitmap(m_book_bg, null, new RectF(0, 0, mWidth, mHeight ), null);
			int y = marginHeight;
			for (String strLine : m_lines) {
				y += m_fontSize;
				c.drawText(strLine, marginWidth, y, mPaint);
			}
		}
		fPercent = (float) (m_mbBufBegin * 1.0 / m_mbBufLen);
		
		DecimalFormat df = new DecimalFormat("#0.00");
		String strPercent = df.format(fPercent * 100) + "%";
		int nPercentWidth = (int) mPaint.measureText("999.9%") + 1;
		c.drawText(strPercent, mWidth - nPercentWidth, mHeight - 5, mPaint);
		float m_titlefontsize = ((this.mWidth - 2.0F * (this.mWidth / 18.0F)) / 26.0F);
		
		String filename = this.book_file.getName();
		//String filename = "关于本游戏：本游戏是做测试用的，这些文字也是，都不是瞎写的！关于本游戏：本游戏是做测试用的，这些文字也是，都不是瞎写的！ ";
		if(filename.length() > 25){
			//Toast.makeText(context, filename.substring(0, filename.length()/2), 0).show();
			c.drawText(filename.substring(0,20)+"...", 40, m_titlefontsize + 20, mPaint);
		}else{
			c.drawText(filename, this.mWidth / 2 - filename.length() * m_titlefontsize / 2.0F, m_titlefontsize + 10, mPaint);
		}
	
		
		//处理花文字换行的问题
//		TextPaint textPaint = new TextPaint();
//		//textPaint.setARGB(0xffffff, 0x00ff, 0xff00, 0);
//		textPaint.setColor(Color.parseColor("#666666"));
//		textPaint.setTextSize(30.0F);
//		String aboutTheGame = "关于本游戏：本游戏是做测试用的，这些文字也是，都不是瞎写的！ ";
//		/**
//		* aboutTheGame ：要 绘制 的 字符串   ,textPaint(TextPaint 类型)设置了字符串格式及属性 的画笔,240为设置 画多宽后 换行，后面的参数是对齐方式...
//		*/
//		StaticLayout layout = new StaticLayout(aboutTheGame ,textPaint,this.mWidth,Alignment.ALIGN_NORMAL,1.0F,0.0F,true);
//		//从 (20,80)的位置开始绘制
//		//c.translate(mWidth/3,40);
//		layout.draw(c);
		
        String str2 = new SimpleDateFormat("HH:mm").format(new Date());
        c.drawText(str2, -1 + (this.mWidth - (int)this.mPaint.measureText(str2)), m_titlefontsize + 10, mPaint);
       //后添加的2015-5-26号 新加改动 记住读书标记
        spr =  context.getSharedPreferences(ReadActivity.fileName+"bookOffset", Context.MODE_APPEND);
        editor = spr.edit();
        editor.putInt("offset",m_mbBufBegin);
        editor.commit();
	}

	public void setBgBitmap(Bitmap BG) {
		m_book_bg = BG;
	}

	public boolean isFirstPage() {
		return m_isfirstPage;
	}

	public boolean isLastPage() {
		return m_islastPage;
	}
}
