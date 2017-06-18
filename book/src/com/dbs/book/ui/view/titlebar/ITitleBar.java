package com.dbs.book.ui.view.titlebar;

public interface ITitleBar {

	/**
	 * TitleBar右侧icon点击事件
	 */
	void onRightClicked();
	
	/**
	 * TitleBar左侧icon点击事件
	 */
	void onLeftClicked();
	
	/**
	 * TitleBar右侧text点击事件
	 */
	void onActionClicked();
}
