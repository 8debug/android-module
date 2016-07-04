package gesoft.push.receiver;

import android.content.Context;
import android.content.Intent;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import gesoft.push.GPushConstant;
import gesoft.push.common.NotificationService;
import gesoft.push.po.GCRMessage;
import gesoft.push.po.GRMessage;
import gesoft.push.po.GTextMessage;
import gesoft.push.po.XGNotification;

public class MessageReceiver extends XGPushBaseReceiver {

	private Intent intent = new Intent(GPushConstant.XG_NOTIFACTION_SHOW_RECEIVER);

	private final static String Y_M_D_H_M_S = "yyyy-MM-dd HH:mm:ss";

	// 通知展示
	@Override
	public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
		if (context == null || notifiShowedRlt == null) {
			return;
		}

		XGNotification notific = new XGNotification();
		notific.setMsg_id(notifiShowedRlt.getMsgId());
		notific.setTitle(notifiShowedRlt.getTitle());
		notific.setContent(notifiShowedRlt.getContent());
		// notificationActionType==1为Activity，2为url，3为intent
		notific.setNotificationActionType(notifiShowedRlt.getNotificationActionType());
		// Activity,url,intent都可以通过getActivity()获得
		notific.setActivity(notifiShowedRlt.getActivity());
		notific.setUpdate_time(new SimpleDateFormat(Y_M_D_H_M_S, Locale.CHINESE).format(Calendar.getInstance().getTime()));
		NotificationService.getInstance(context).save(notific);
		//show(context, "您有1条新消息, " + "通知被展示 ， " + notifiShowedRlt.toString());
		context.sendBroadcast(intent);
	}

	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		if (context == null) {
			return;
		}

		//反注册成功
		if (errorCode == XGPushBaseReceiver.SUCCESS) {

		} else {

		}
	}

	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}

		//设置成功
		if(errorCode == XGPushBaseReceiver.SUCCESS) {

		}else{

		}

	}

	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		if (context == null) {
			return;
		}

		//删除成功
		if (errorCode == XGPushBaseReceiver.SUCCESS) {

		} else {

		}

	}

	// 通知点击回调 actionType=1为该消息被清除，actionType=0为该消息被点击
	@Override
	public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
		if (context == null || message == null) {
			return;
		}

		GCRMessage msg = new GCRMessage();
		msg.setActionType(message.getActionType());
		msg.setContent(message.getContent());
		msg.setCustomContent(message.getCustomContent());
		msg.setMsgId(message.getMsgId());
		msg.setTitle(message.getTitle());
		msg.setActivityName(message.getActivityName());
		msg.setNotificationActionType(message.getNotificationActionType());

		if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
			if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {

				/*
				//启动FirstActivity
				Intent intentFirst = new Intent();
				ComponentName componentName= new ComponentName( "gesoft.gandroid", "gesoft.gandroid.FirstActivity");
				intentFirst.setComponent(componentName);
				intentFirst.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

				//启动SecondActivity
				Intent intentSecond = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(GPushConstant.XG_BUNDLE_GMESSAGE, msg);
				componentName= new ComponentName( "gesoft.gandroid", "gesoft.gandroid.SecondActivity");
				intentSecond.setComponent(componentName);
				intentSecond.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentSecond.putExtra(GPushConstant.XG_INTENT_BUNDLE, bundle);

				Intent[] intents = {intentFirst, intentSecond};

				context.startActivities(intents);*/
			}
		} else if (message.getActionType() == XGPushClickedResult.NOTIFACTION_DELETED_TYPE) {
			// 通知被清除啦。。。。
			// APP自己处理通知被清除后的相关动作
		}
	}

	@Override
	public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult message) {

		if (context == null || message == null) {
			return;
		}

		//注册成功
		if ( errorCode == XGPushBaseReceiver.SUCCESS ) {
			GRMessage msg = new GRMessage();
			msg.setAccount(message.getAccount());
			msg.setAccessId(message.getAccessId());
			msg.setDeviceId(message.getDeviceId());
			msg.setTicket(message.getTicket());
			msg.setTicketType(message.getTicketType());
			msg.setToken(message.getToken());
		}
		//注册失败
		else {

		}

	}

	// 消息透传
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		GTextMessage msg = new GTextMessage();
		msg.setTitle(message.getTitle());
		msg.setContent(message.getContent());
		msg.setCustomContent(message.getCustomContent());
	}

}
