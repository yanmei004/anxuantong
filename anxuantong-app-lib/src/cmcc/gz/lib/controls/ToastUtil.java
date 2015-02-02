package cmcc.gz.lib.controls;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 探出提示框Toast的工具类封装
 * @author lin
 *
 */
public class ToastUtil {
    
	/**
	 * 有时候我们需要判断自己的应用是否在前台显示
	 * 需要添加android.permission.GET_TASKS权限
	 * @param activity
	 * @return
	 */
	protected static boolean isActiveActivity(Context activity){
		if (activity == null)
			return false;
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo>  tasksInfo = activityManager.getRunningTasks(1);  
        if(tasksInfo.size() > 0){  
            //应用程序位于堆栈的顶层  
            if ((activity != null) && (tasksInfo.get(0).topActivity.getClassName().indexOf(activity.getClass().getSimpleName()) >= 0)){
                return true;  
            }  
        }  
        return false;
    }
		
	/**
	 * 系统默认的短时间提示框
	 * @param context  
	 * @param str_content 输入提示的信息内容
	 * @author lin
	 */
	public static void showShortToast(Context context,String str_content){
		
		if(isActiveActivity(context)){
		   Toast.makeText(context, str_content, Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 系统默认的长时间提示框
	 * @param context
	 * @param str_content 输入提示的内容
	 * @author lin
	 */
	public static void showLongToast(Context context,String str_content){
		if(isActiveActivity(context)){
		   Toast.makeText(context, str_content, Toast.LENGTH_LONG).show();
		}
		
	}
	
	/**
	 * 系统默认的自定义时间提示框
	 * @param context
	 * @param str_content 输入提示的内容
	 * @author lin
	 */
	public static void showToastByDfineTime(Context context,String str_content,int duration){
		if(isActiveActivity(context)){
		  Toast.makeText(context, str_content, duration).show();
		}
	}
	
	/**
	 * 自定义位置短时间的toast提示框
	 * @param context
	 * @param str_content 输入的提示内容
	 * @param gravity 位置参数
	 * @author lin
	 */
	public static void 	customShowShortToastGravity(Context context,String str_content,int gravity){
		if(isActiveActivity(context)){
			Toast toast = Toast.makeText(context, str_content, Toast.LENGTH_SHORT);     
			toast.setGravity(gravity, 0, 0);    
			toast.show(); 
		}
	}
	
	/**
	 * 自定义位置长时间显示的toast提示框
	 * @param context
	 * @param str_content 输入的提示内容
	 * @param gravity 位置参数
	 * @author lin
	 */
    public static void 	customShowLongToastGravity(Context context,String str_content,int gravity){
    	if(isActiveActivity(context)){
	    	Toast toast = Toast.makeText(context, str_content, Toast.LENGTH_LONG);     
			toast.setGravity(gravity, 0, 0);    
			toast.show(); 
    	}
	}
	
    /**
	 * 自定义位置和自定义显示时间的toast提示框
	 * @param context
	 * @param str_content 输入的提示内容
	 * @param gravity 位置参数
	 * @author lin
	 */
	public static void 	customToastGravity(Context context,String str_content,int gravity,int duration){
		if(isActiveActivity(context)){
			Toast toast = Toast.makeText(context, str_content, duration);     
			toast.setGravity(gravity, 0, 0);    
			toast.show(); 
		}
	}
	
	/**
	 * 自定义短时间显示图片的toast
	 * @param context
	 * @param str_content 输入的提示内容
	 * @param img 图片
	 * @author lin
	 */
	public static void customShowShortToastImage(Context context,String str_content,int img){
		if(isActiveActivity(context)){
			Toast toast = Toast.makeText(context, str_content, Toast.LENGTH_SHORT);     
			toast.setGravity(Gravity.CENTER, 0, 0);     
			LinearLayout toastView = (LinearLayout) toast.getView();     
			ImageView imageView = new ImageView(context);    
			imageView.setImageResource(img);    
			toastView.addView(imageView, 0);     
			toast.show();  
		}
	}
	
	/**
	 * 自定义长时间显示图片的toast
	 * @param context
	 * @param str_content 输入的提示内容
	 * @param img 图片
	 * @author lin
	 */
	public static void customShowLongToastImage(Context context,String str_content,int img){
		if(isActiveActivity(context)){
			Toast toast = Toast.makeText(context, str_content, Toast.LENGTH_LONG);     
			toast.setGravity(Gravity.CENTER, 0, 0);     
			LinearLayout toastView = (LinearLayout) toast.getView();     
			ImageView imageView = new ImageView(context);    
			imageView.setImageResource(img);    
			toastView.addView(imageView, 0);     
			toast.show();  
		}
		
	}
	
	/**
	 * 
	 * @param context
	 * @param str_content 输入的提示内容
	 * @param img 图片
	 * @param gravity 位置
	 * @param duration 自定义的时间
	 * @author lin
	 */
    public static void customToastImage(Context context,String str_content,int img,int gravity,int duration){
    	if(isActiveActivity(context)){
	    	Toast toast = Toast.makeText(context, str_content, duration);     
			toast.setGravity(gravity, 0, 0);     
			LinearLayout toastView = (LinearLayout) toast.getView();     
			ImageView imageView = new ImageView(context);    
			imageView.setImageResource(img);    
			toastView.addView(imageView, 0);     
			toast.show();  
    	}
    	
	}
    
    /**
     * 完全自定义的toast提示框
     * @param context
     * @param layout 布局文件
     * @author lin
     */
    public static void customToast(Context context,View layout){
    	if(isActiveActivity(context)){            
    	   Toast toast = new Toast(context);  
    	   //设置Toast的位置   
    	   toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);  
    	   toast.setDuration(Toast.LENGTH_SHORT);  
    	   //让Toast显示为我们自定义的样子   
    	   toast.setView(layout);  
    	   toast.show();  
    	}
    	
    }
	
}
