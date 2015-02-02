package com.example.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.Contst;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HTHttpResponseHandler;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.HTBaseApplication;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.enty.Update;
import com.hengtong.library.utils.AppManager;
import com.anxuantong.ym.R;
/**
 * 应用程序更新工具�?
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.1
 * @created 2012-6-29
 */
public class UpdateManager {

	private static final int DOWN_NOSDCARD = 0;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;

	private static final int DIALOG_TYPE_LATEST = 0;
	private static final int DIALOG_TYPE_FAIL = 1;

	private static UpdateManager updateManager;

	private Context mContext;
	// 通知对话�?
	private Dialog noticeDialog;
	// 下载对话�?
	private Dialog downloadDialog;
	// '已经是最�? 或�? '无法获取�?��版本' 的对话框
	private Dialog latestOrFailDialog;
	// 进度�?
	private ProgressBar mProgress;
	// 显示下载数�?
	private TextView mProgressText;
	// 查询动画
	private ProgressDialog mProDialog;
	// 进度�?
	private int progress;
	// 下载线程
	private Thread downLoadThread;
	// 终止标记
	private boolean interceptFlag;
	// 提示�?
	private String updateMsg = "";
	// 返回的安装包url
	private String apkUrl = "";
	// 下载包保存路�?
	private String savePath = "";
	// apk保存完整路径
	private String apkFilePath = "";
	// 临时下载文件路径
	private String tmpFilePath = "";
	// 下载文件大小
	private String apkFileSize;
	// 已下载文件大�?
	private String tmpFileSize;

	// private String curVersionName = "";
	private int curVersionCode;
	private Update mUpdate;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				mProgressText.setText(tmpFileSize + "/" + apkFileSize);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			case DOWN_NOSDCARD:
				downloadDialog.dismiss();
				Toast.makeText(mContext, "无法下载安装文件，请检查sd是否安装",
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	public static UpdateManager getUpdateManager() {
		if (updateManager == null) {
			updateManager = new UpdateManager();
		}
		updateManager.interceptFlag = false;
		return updateManager;
	}

	/**
	 * �?��App更新
	 * 
	 * @param context
	 * @param isShowMsg
	 *            是否显示提示消息
	 */
	@SuppressLint("HandlerLeak")
	public void checkAppUpdate(Context context, final boolean isShowMsg) {
		this.mContext = context;
		getCurrentVersion();
		if (isShowMsg) {
			if (mProDialog == null) {
				mProDialog = ProgressDialog.show(mContext, null, "正在更新，请稍后...",
						true, true);
				mProDialog.setOnKeyListener(new OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialog, int keyCode,
							KeyEvent event) {
						if (mProDialog != null) {
							mProDialog = null;
						}
						return false;
					}
				});
			} else if (mProDialog.isShowing()
					|| (latestOrFailDialog != null && latestOrFailDialog
							.isShowing()))
				return;
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				// 进度条对话框不显�?- �?��结果也不显示
				if (mProDialog != null && !mProDialog.isShowing()) {
					return;
				}
				// 关闭并释放释放进度条对话�?
				if (isShowMsg && mProDialog != null) {
					mProDialog.dismiss();
					mProDialog = null;
				}
				// 显示�?��结果
				if (msg.what == 1) {
					mUpdate = (Update) msg.obj;
					if (mUpdate != null) {
						if (curVersionCode < mUpdate.getVersionCode()) {
							apkUrl = mUpdate.getDownloadUrl();
							updateMsg = mUpdate.info;
							showNoticeDialog();
						} else if (isShowMsg) {
							showLatestOrFailDialog(DIALOG_TYPE_LATEST);
						}
					}
				} else if (isShowMsg) {
					showLatestOrFailDialog(DIALOG_TYPE_FAIL);
				}
			}
		};

		IHttpRequestDriver requestDriverImp = HttpRequestDriverImp
				.getSingleInstance(mContext);
		requestDriverImp.checkUpdate(new HTHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					HTResponseObject response) {
				if (response.ismSuccess()) {
					if (response.getmResult() != null
							&& !"null".equals(response.getmResult())) {
						Message msg = new Message();
						Update update = new Update(response.getmResult()
								.toString());
						// update.mForce = true;//测试的时候用
						msg.what = 1;
						msg.obj = update;
						handler.sendMessage(msg);
					} else {
						handler.sendEmptyMessage(1000);
					}
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				handler.sendEmptyMessage(1000);
			}
		});

	}

	/**
	 * 显示'已经是最�?或�?'无法获取版本信息'对话�?
	 */
	private void showLatestOrFailDialog(int dialogType) {
		if (latestOrFailDialog != null) {
			// 关闭并释放之前的对话�?
			latestOrFailDialog.dismiss();
			latestOrFailDialog = null;
		}
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("系统提示");
		if (dialogType == DIALOG_TYPE_LATEST) {
			builder.setMessage("您当前已经是最新版本");
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			builder.setMessage("无法获取版本更新信息");
		}
		builder.setPositiveButton("确定", null);
		latestOrFailDialog = builder.create();
		latestOrFailDialog.show();
	}

	/**
	 * 获取当前客户端版本信
	 */
	private void getCurrentVersion() {
		try {
			PackageInfo info = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0);
			// curVersionName = info.versionName;
			curVersionCode = info.versionCode;
//			Contst.versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * 显示版本更新通知对话�?
	 */
	private void showNoticeDialog() {
		// 判断是否为强制更新提示语
		String textStr = "以后再说";
		String titleStr = "软件版本更新";
		if (mUpdate.mForce) {
			textStr = "";
			titleStr = "软件版本更新(必须更新)";
		}
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(titleStr);
		builder.setMessage(Html.fromHtml(updateMsg));
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton(textStr, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (mUpdate.mForce) {
					AppManager.getAppManager().AppExit(mContext);
				}
			}
		});
		noticeDialog = builder.create();
		noticeDialog.setCanceledOnTouchOutside(false);
		noticeDialog.setCancelable(false);
		noticeDialog.show();
	}

	/**
	 * 显示下载对话�?
	 */
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("正在下载新版");

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
				if (mUpdate.mForce) {
					HTBaseApplication.getInstance().closeAllActivitys();
				}
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.setCancelable(false);
		downloadDialog.show();

		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				String apkName = "CYZLApp_" + mUpdate.getVersionName() + ".apk";
				String tmpApk = "CYZLApp_" + mUpdate.getVersionName() + ".tmp";
				// 判断是否挂载了SD�?
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					// savePath =
					// Environment.getExternalStorageDirectory().getAbsolutePath()
					// + "/cyzl/Update/";
					savePath = Environment.getExternalStorageDirectory()
							.getAbsolutePath() + "/anxuantong/UpdateApk/";
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					apkFilePath = savePath + apkName;
					tmpFilePath = savePath + tmpApk;
				}

				// 没有挂载SD卡，无法下载文件
				if (apkFilePath == null || apkFilePath == "") {
					mHandler.sendEmptyMessage(DOWN_NOSDCARD);
					return;
				}

				File ApkFile = new File(apkFilePath);

				// 是否已下载更新文�?
				if (ApkFile.exists()) {
					downloadDialog.dismiss();
					installApk();
					return;
				}

				// 输出临时下载文件
				File tmpFile = new File(tmpFilePath);
				FileOutputStream fos = new FileOutputStream(tmpFile);

				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				// 显示文件大小格式�?个小数点显示
				DecimalFormat df = new DecimalFormat("0.00");
				// 进度条下面显示的总文件大�?
				apkFileSize = df.format((float) length / 1024 / 1024) + "MB";

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					// 进度条下面显示的当前下载文件大小
					tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
					// 当前进度�?
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成 - 将临时下载文件转成APK文件
						if (tmpFile.renameTo(ApkFile)) {
							// 通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
						}
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下�?

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	/**
	 * 下载apk
	 * 
	 * @param url
	 */
	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile = new File(apkFilePath);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
